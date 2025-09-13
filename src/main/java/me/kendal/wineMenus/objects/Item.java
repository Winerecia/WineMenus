package me.kendal.wineMenus.objects;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class Item {
    private Material material;
    private int slot;
    private ItemStack itemStack;
    private List<Component> lore;
    private List<Action> actions;
    private Consumer<ClickContext> customHandler;
    public Item(Material material) {
        this(new ItemStack(material));
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


    public void setName(Component name) {
        ItemMeta meta = getItemMeta();
        meta.displayName(name);
        setItemMeta(meta);
    }

    public Component getName() {
        return getItemMeta().displayName();
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



    public void setCustomHandler(Consumer<ClickContext> customHandler) {
        this.customHandler = customHandler;
    }

    public Consumer<ClickContext> getCustomHandler() {
        return this.customHandler;
    }


    @Override
    public Item clone() {
        ItemStack clonedStack = itemStack.clone(); // копируем ItemStack
        Item newItem = new Item(clonedStack);


        // копия лора
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

        var meta = newItem.getItemMeta();
        meta.addItemFlags(this.getItemMeta().getItemFlags().toArray(new ItemFlag[0]));
        newItem.setItemMeta(meta);

        newItem.setCustomHandler(this.customHandler);


        return newItem;
    }



    public void addFlag(ItemFlag flag) {
        ItemMeta meta = getItemMeta();
        meta.addItemFlags(flag);
        setItemMeta(meta);
    }


    public void addFlags(ItemFlag... flags) {
        ItemMeta meta = getItemMeta();
        meta.addItemFlags(flags);
        setItemMeta(meta);
    }


    public void removeFlag(ItemFlag flag) {
        ItemMeta meta = getItemMeta();
        meta.removeItemFlags(flag);
        setItemMeta(meta);
    }


    public void removeFlags(ItemFlag... flags) {
        ItemMeta meta = getItemMeta();
        meta.removeItemFlags(flags);
        setItemMeta(meta);
    }


    public void clearFlags() {
        ItemMeta meta = getItemMeta();
        for (ItemFlag flag : ItemFlag.values()) {
            meta.removeItemFlags(flag);
        }
        setItemMeta(meta);
    }

    public Collection<ItemFlag> getFlags() {
        return getItemMeta().getItemFlags();
    }
}
