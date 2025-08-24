package me.kendal.wineMenus.objects;

import me.kendal.wineMenus.MenuHolder;
import me.kendal.wineMenus.MenuManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class Menu {
    private HashMap<Integer, Item> slots;
    private HashMap<Integer, AnimationFrame> animationFrames;
    private HashMap<UUID, Session> sessions;

    private final String name;
    private final Inventory inventory;
    private final boolean usingSessions;
    private final boolean globalChangeable;

    public Menu(String name, int size, String title, boolean usingSessions) {
        this.name = name;
        this.usingSessions = usingSessions;
        this.inventory = Bukkit.createInventory(new MenuHolder(name), size, title);
        this.globalChangeable = false;
        MenuManager.getInstance().registerMenu(this);
    }

    public boolean isUsingSessions() {
        return usingSessions;
    }
    public boolean isGlobalChangeable() {
        return globalChangeable;

    }
    public void open(Player player) {
        if (!globalChangeable) {
            player.openInventory(inventory);
            if (usingSessions) {
                newSession(player);
            }
        } else {

        }
    }


    public void registerComponent() {

    }

    public String getName() {
        return name;
    }

    public void setItem(int slot, Item item) {
        slots.put(slot, item);
        inventory.setItem(slot, item.getItemstack());
    }

    public Item getItem(int slot) {
        return slots.get(slot);
    }

    public void registerAnimationFrame(int count, AnimationFrame animationFrame) {
        if (usingSessions) {
            this.animationFrames.put(count, animationFrame);
        }
    }

    private void newSession(Player player) {
        Session session = new Session(player.getOpenInventory());
        session.setMap(cloneMap());
        sessions.put(player.getUniqueId(), session);
    }

    public Session getSessionOrNull(UUID uuid) {
        return sessions.get(uuid);
    }

    public void setSession(UUID uuid, Session session) {
        sessions.put(uuid, session);
    }

    public void replace(int firstIndex, int secondIndex) {
        Item firstSlot = getItem(firstIndex);
        Item secondSlot = getItem(secondIndex);

        slots.put(firstIndex, secondSlot);
        slots.put(secondIndex, firstSlot);
    }

    public HashMap<Integer, Item> cloneMap() {
        return new HashMap<>(slots);
    }
}
