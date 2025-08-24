package me.kendal.wineMenus.listener;

import me.kendal.wineMenus.MenuHolder;
import me.kendal.wineMenus.MenuManager;
import me.kendal.wineMenus.objects.Action;
import me.kendal.wineMenus.objects.Menu;
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
        if (event.getInventory().getHolder() instanceof MenuHolder holder) {
            // Проверяем, что кликнул игрок
            if (!(event.getWhoClicked() instanceof Player player)) return;

            // Проверяем, что это наше меню

            // Отменяем стандартное перемещение предмета
            event.setCancelled(true);
            Menu menu = MenuManager.getInstance().getMenu(holder.getIdentificator());
            if (menu.isUsingSessions()) {

            } else {
                List<Action> actions;
                if (menu.isUsingSessions()) {
                    actions = menu.getSessionOrNull(player.getUniqueId()).getItem(event.getSlot()).getActions();
                } else {
                    actions = menu.getItem(event.getSlot()).getActions();
                }
                for (Action action : actions) {
                    action.execute(new HashMap<String, Object>());
                }
            }

        }
    }
    @EventHandler
    public void onMenuClose(InventoryCloseEvent event) {
        if (event.getInventory().getHolder() instanceof MenuHolder holder) {
            Menu menu = MenuManager.getInstance().getMenu(holder.getIdentificator());
            menu.removeSession(event.getPlayer().getUniqueId());
        }
    }
}

