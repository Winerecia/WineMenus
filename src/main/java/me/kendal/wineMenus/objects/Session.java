package me.kendal.wineMenus.objects;

import org.bukkit.inventory.InventoryView;

import java.util.HashMap;

public class Session {
    private HashMap<Integer, Item> localSlots;
    private InventoryView view;

    public Session(InventoryView view) {
        this.view = view;
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
        view.setItem(slot, item.getItemstack());
    }
    public InventoryView getView() {
        return view;
    }
}

