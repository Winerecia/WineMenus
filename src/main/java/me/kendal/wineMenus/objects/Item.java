package me.kendal.wineMenus.objects;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class Item {
    private Material material;
    private int slot;
    private String name;
    private ItemStack itemStack;
    private List<Component> lore;
    private List<Action> actions;


    public Item(Material material) {
        this.itemStack = new ItemStack(material);
    }

    private void setItemMeta(ItemMeta itemMeta) {
        this.itemStack.setItemMeta(itemMeta);
    }

    private ItemMeta getItemMeta() {
        return this.itemStack.getItemMeta();
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setLore(List<Component> lore) {
        ItemMeta meta = getItemMeta();
        meta.lore(lore);
        setItemMeta(meta);
    }

    public List<Component> getLore() {
        return getItemMeta().lore();
    }


    public void setActions(List<Action> actions) {
        this.actions = actions;
    }

    public List<Action> getActions() {
        return this.actions;
    }

    public void setItemstack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public ItemStack getItemstack() {
        return itemStack;
    }


//    public Item clone() {
//        Item item = new Item();
//        return item;
//    }
}
