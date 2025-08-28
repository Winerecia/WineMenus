package me.kendal.wineMenus.objects;

import me.kendal.wineMenus.MenuHolder;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Session {
    private HashMap<Integer, Item> localSlots;
    private Inventory inventory;
    private List<Integer> widgetSlots;

    public Session(Inventory inventory) {
        this.localSlots = new HashMap<>();
        this.inventory = inventory;
    }

    public void setMap(HashMap<Integer, Item> map) {
        this.localSlots = map;
    }

    public void setAnimationFrame(AnimationFrame frame) {
        frame.setSlots(localSlots);
    }


    public Item getItem(int slot) {
        return localSlots.get(slot);
    }

    public void setItem(int slot, Item item) {
        this.localSlots.put(slot, item);
        inventory.setItem(slot, item.getItemstack());
    }
    public Inventory getInventory() {
        return inventory;
    }

    public HashMap<Integer, Item> cloneMap() {
        return new HashMap<>(localSlots);
    }

    public HashMap<Integer, Item> deepCloneMap() {
        HashMap<Integer, Item> copy = new HashMap<>();
        for (Map.Entry<Integer, Item> entry : localSlots.entrySet()) {
            if (entry.getValue() != null) {
                copy.put(entry.getKey(), entry.getValue().clone());
            } else {
                copy.put(entry.getKey(), null);
            }
        }
        return copy;
    }
}

