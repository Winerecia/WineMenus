package me.kendal.wineMenus.objects;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Item {
    private Material material;
    private int slot;
    private String name;
    private ItemStack itemStack;
    private List<Component> lore;
    private List<Action> actions;
    private Map<String, List<Action>> widgetActions;

    public Item(Material material) {
        new Item(new ItemStack(material));
    }
    public Item(ItemStack itemStack) {
        this.itemStack = itemStack;
        actions = new ArrayList<>();
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

    public void addAction(Action action) {
        this.actions.add(action);
    }

    public void setItemstack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public ItemStack getItemstack() {
        return itemStack;
    }


    @Override
    public Item clone() {
        ItemStack clonedStack = itemStack.clone(); // копируем ItemStack
        Item newItem = new Item(clonedStack);

        // имя
        newItem.setName(this.name);

        // копия lore
        if (this.getLore() != null) {
            newItem.setLore(new ArrayList<>(this.getLore()));
        }

        // копия действий
        if (this.actions != null) {
            List<Action> newActions = new ArrayList<>();
            for (Action action : this.actions) {
                newActions.add(action.clone()); // ⚠️ Action тоже должен уметь клонироваться
            }
            newItem.setActions(newActions);
        }

        return newItem;
    }
}
