package me.kendal.wineMenus.objects;

import me.kendal.wineMenus.objects.interfaces.ItemsOwner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Frame implements ItemsOwner{
    Map<Integer, Item> animMap;
    List<Action> actions;

    public Frame() {
        this.animMap = new HashMap<>();
        this.actions = new ArrayList<>();
    }

    public void setFrame(ItemsOwner io) {
        for (Map.Entry entry : animMap.entrySet()) {
            io.setItem((Integer) entry.getKey(), (Item) entry.getValue());
        }
    }

    @Override
    public Item getItem(int slot) {
        return animMap.get(slot);
    }

    @Override
    public void setItem(int slot, Item item) {
        animMap.put(slot, item);
    }
}
