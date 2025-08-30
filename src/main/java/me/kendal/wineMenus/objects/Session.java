package me.kendal.wineMenus.objects;

import me.kendal.wineMenus.MenuHolder;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Session {
    private final List<Widget> widgets = new ArrayList<>();
    private HashMap<Integer, Item> localSlots;
    private Inventory inventory;

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



    public void addWidget(Widget widget) {
        widgets.add(widget);
        widgets.sort((w1, w2) -> Integer.compare(w2.getZIndex(), w1.getZIndex())); // по убыв z-index
    }

    public void removeWidget(Widget widget) {
        widgets.remove(widget);
    }

    /**
     * Получаем Item с учётом виджетов
     */
    public Item getRenderedItem(int slot) {
        for (Widget widget : widgets) {
            if (widget.isActive() && widget.getSlots().containsKey(slot)) {
                return widget.getItem(slot);
            }
        }
        return localSlots.get(slot);
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

