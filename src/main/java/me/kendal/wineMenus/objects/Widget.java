package me.kendal.wineMenus.objects;
import me.kendal.wineMenus.objects.interfaces.ItemsOwner;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Widget implements ItemsOwner {
    private final Map<Integer, Item> slots;
    private boolean active;
    private final int zIndex;
    private final String name;

    public Widget(String name, int zIndex) {
        this.name = name;
        this.zIndex = zIndex;
        this.slots = new HashMap<>();
        this.active = false;
    }

    /**
     * @return имя виджета
     */
    public String getName() {
        return name;
    }

    /**
     * @return z-index виджета
     */
    public int getZIndex() {
        return zIndex;
    }

    /**
     * Активировать виджет
     */
    public void activate() {
        active = true;
    }

    /**
     * Деактивировать виджет
     */
    public void deactivate() {
        active = false;
    }

    /**
     * Переключить состояние виджета
     */
    public void toggle() {
        active = !active;
    }

    /**
     * @return активен ли виджет
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Установить Item в слот виджета
     */
    public void setItem(int slot, Item item) {
        slots.put(slot, item);
    }

    /**
     * Получить Item из слота виджета
     */
    public Item getItem(int slot) {
        return slots.get(slot);
    }

    /**
     * @return копия всех слотов виджета
     */
    public Map<Integer, Item> getSlots() {
        return Collections.unmodifiableMap(slots);
    }
}
