package me.kendal.wineMenus;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class MenuHolder implements InventoryHolder {
    private final String id;

    public MenuHolder(String  id) {
        this.id = id;
    }

    public String getIdentificator() {
        return id;
    }

    @Override
    public Inventory getInventory() {
        return null; // не требуется
    }
}
