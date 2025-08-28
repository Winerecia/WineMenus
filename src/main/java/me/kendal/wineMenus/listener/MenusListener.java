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

import java.util.HashMap;
import java.util.List;

public class MenusListener implements Listener {

    @EventHandler
    public void onMenuClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;

        if (!(event.getInventory().getHolder() instanceof MenuHolder holder)) return;

        // наше меню
        Menu menu = MenuManager.getInstance().getMenu(holder.getIdentificator());
        if (menu == null) return;

        // отменяем стандартные действия
        event.setCancelled(true);

        // подготовка контекста
        ClickContext context = new ClickContext(menu, event);

        Item clickedItem = null;
        if (menu.isUsingSessions()) {
            Session session = menu.getSession(player.getUniqueId());
            if (session == null) {
                Bukkit.broadcastMessage("§c[WineMenus] Session not found for " + player.getName());
                return;
            }
            context.setSession(session);
            clickedItem = session.getItem(event.getSlot());
        } else {
            clickedItem = menu.getItem(event.getSlot());
        }

        if (clickedItem == null) return;

        List<Action> actions = clickedItem.getActions();
        if (actions == null || actions.isEmpty()) return;

        for (Action action : actions) {
            try {
                action.execute(context);
                Bukkit.broadcastMessage("§a[WineMenus] Executed action: " + action.getName());
            } catch (Exception e) {
                Bukkit.getLogger().severe("[WineMenus] Ошибка при выполнении Action " + action.getName());
                e.printStackTrace();
            }
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