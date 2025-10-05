package me.kendal.wineMenus.objects.interfaces;

import me.kendal.wineMenus.objects.Item;

public interface ItemsOwner {

    Item getItem(int slot);
    void setItem(int slot, Item item);

}
