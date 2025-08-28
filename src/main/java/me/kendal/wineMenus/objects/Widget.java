package me.kendal.wineMenus.objects;

import java.util.HashMap;

public class Widget {
    private HashMap<Integer, Item> widjet;
    private boolean active;
    private int zindex = 0;
    private final String indentificator;

    public Widget(HashMap<Integer, Item> widjet, String indentificator) {
        this.widjet = widjet;
        this.indentificator = indentificator;
    }

    public boolean isActive() {
        return active;
    }

    public int getZIndex() {
        return zindex;
    }

    public void setZindex(int zindex) {
        this.zindex = zindex;
    }



}
