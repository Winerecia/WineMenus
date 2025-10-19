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
        Menu menu = MenuManager.getInstance().getMenu(holder.getIdentificator());
        if (menu == null) return;

        event.setCancelled(true);

        ClickContext context = new ClickContext(menu, event);
        Item clickedItem;

        if (menu.isUsingSessions()) {
            Session session = menu.getSession(player.getUniqueId());
            if (session == null) return;

            context.setSession(session);
            clickedItem = session.getRenderedItem(event.getSlot());
        } else {
            clickedItem = menu.getItem(event.getSlot());
        }

        if (clickedItem == null) return;

        // customHandler
        Consumer<ClickContext> customHandler = clickedItem.getCustomHandler();
        if (customHandler != null) {
            try {
                customHandler.accept(context);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // actions
        List<Action> actions = clickedItem.getActions(event.getClick());
        if (actions != null && !actions.isEmpty()) {
            for (Action action : actions) {
                try {
                    action.execute(context);
                } catch (Exception e) {
                    e.printStackTrace();
                }
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