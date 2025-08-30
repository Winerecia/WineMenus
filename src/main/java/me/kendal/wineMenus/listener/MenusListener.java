package me.kendal.wineMenus.listener;

import me.kendal.wineMenus.MenuHolder;
import me.kendal.wineMenus.MenuManager;
import me.kendal.wineMenus.objects.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

import java.util.List;
import java.util.function.Consumer;

public class MenusListener implements Listener {

    @EventHandler
    public void onMenuClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;
        if (!(event.getInventory().getHolder() instanceof MenuHolder holder)) return;

        // получаем меню по идентификатору
        Menu menu = MenuManager.getInstance().getMenu(holder.getIdentificator());
        if (menu == null) return;

        // отключаем стандартное поведение
        event.setCancelled(true);

        // создаём контекст
        ClickContext context = new ClickContext(menu, event);

        Item clickedItem = null;

        if (menu.isUsingSessions()) {
            Session session = menu.getSession(player.getUniqueId());
            if (session == null) {
                Bukkit.getLogger().warning("[WineMenus] Сессия не найдена для игрока " + player.getName());
                return;
            }
            context.setSession(session);

            clickedItem = session.getRenderedItem(event.getSlot());
        } else {
            clickedItem = menu.getItem(event.getSlot());
        }

        if (clickedItem == null) return;

        List<Action> actions = clickedItem.getActions();
        Consumer<ClickContext> customHandler = clickedItem.getCustomHandler();
        if (actions == null || actions.isEmpty()) return;

        for (Action action : actions) {
            try {
                action.execute(context);
                Bukkit.getLogger().info("[WineMenus] Executed action: " + action.getName() +
                        (clickedItem.getName() != null ? " on item " + clickedItem.getName() : ""));
            } catch (Exception e) {
                Bukkit.getLogger().severe("[WineMenus] Ошибка при выполнении Action " + action.getName());
                e.printStackTrace();
            }
        }
        if (customHandler != null) {
            customHandler.accept(context);
        }
    }

    @EventHandler
    public void onMenuClose(InventoryCloseEvent event) {
        if (!(event.getInventory().getHolder() instanceof MenuHolder holder)) return;

        Menu menu = MenuManager.getInstance().getMenu(holder.getIdentificator());
        if (menu == null) return;

        menu.removeSession(event.getPlayer().getUniqueId());
    }
}