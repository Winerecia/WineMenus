package me.kendal.wineMenus.objects;

import java.util.HashMap;
import java.util.Map;

public class AnimationFrame {
    Map<Integer, Item> slots;

    public AnimationFrame(HashMap<Integer, Item> slots) {
        this.slots = slots;
    }

    public void setSlots(HashMap<Integer, Item> localSlots) {
        for (Map.Entry<Integer, Item> entry : slots.entrySet()) {
            Integer key = entry.getKey();
            Item value = entry.getValue();
            localSlots.put(key, value);
        }
    }
}
