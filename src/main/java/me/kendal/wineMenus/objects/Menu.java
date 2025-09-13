package me.kendal.wineMenus.objects;

import me.kendal.wineMenus.MenuHolder;
import me.kendal.wineMenus.MenuManager;
import me.kendal.wineMenus.exceptions.NoUsingLocalRendering;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Function;

/**
 * Представляет меню с поддержкой глобальных и сессионных инвентарей.
 * Используется как API для создания настраиваемых GUI в плагинах.
 */
public class Menu {
    private final HashMap<Integer, Item> slots;
    private final HashMap<Integer, AnimationFrame> animationFrames;
    private final HashMap<UUID, Session> sessions;

    private String title;
    private final String name;
    private final Inventory inventory;
    private final boolean usingSessions;

    public Menu(String name, int size, String title, boolean usingSessions) {
        this.name = name;
        this.title = title;
        this.usingSessions = usingSessions;
        this.inventory = Bukkit.createInventory(new MenuHolder(name), size * 9, title);

        this.slots = new HashMap<>();
        this.sessions = new HashMap<>();
        this.animationFrames = new HashMap<>();

        MenuManager.getInstance().registerMenu(this);
    }

    /**
     * @return флаг, указывает работает ли меню в режиме сессий
     */
    public boolean isUsingSessions() {
        return usingSessions;
    }

    /**
     * Открывает меню игроку. Если включены сессии —
     * создаётся персональная копия меню.
     *
     * @param player игрок
     */
    public void open(Player player) {
        if (usingSessions) {
            player.openInventory(createSession(player).getInventory());
        } else {
            player.openInventory(inventory);
        }
    }

    /**
     * Открывает меню с сессией игроку с использованием аргументов
     *
     * @param player игрок
     * @param args аргументы
     */
    public void open(Player player, Map<String, Object> args) {
        if (usingSessions) {
            player.openInventory(createSession(player, args).getInventory());
        } else {
            throw new NoUsingLocalRendering("Аргументы доступны исключительно для меню, использующие локальный рендеринг(сессии)");
        }
    }

    /**
     * @return базовый инвентарь меню (глобальный)
     */
    public Inventory getInventory() {
        return inventory;
    }

    /**
     * @return уникальное имя меню
     */
    public String getName() {
        return name;
    }

    /**
     * Получить предмет из определённого слота.
     *
     * @param slot индекс слота
     * @return Item или null
     */
    public Item getItem(int slot) {
        return slots.get(slot);
    }

    /**
     * Установить предмет в слот для глобального меню и всех активных сессий.
     *
     * @param slot индекс слота
     * @param item предмет
     */
    public void setItem(int slot, Item item) {
        slots.put(slot, item);
        inventory.setItem(slot, item.getItemstack());
        for (Session session : sessions.values()) {
            session.setItem(slot, item);
        }
    }

    /**
     * Зарегистрировать кадр анимации (работает только при сессиях).
     *
     * @param count          номер кадра
     * @param animationFrame кадр
     */
    public void registerAnimationFrame(int count, AnimationFrame animationFrame) {
        if (usingSessions) {
            this.animationFrames.put(count, animationFrame);
        }
    }

    // ===============================
    // СЕССИИ
    // ===============================

    /**
     * Создать новую стандартную сессию для игрока
     * (все предметы разделяются по ссылкам).
     *
     * @param player игрок
     * @return созданная сессия
     */
    public Session createSession(Player player) {
        Inventory newInventory = Bukkit.createInventory(new MenuHolder(name), inventory.getSize(), title);
        newInventory.setContents(this.inventory.getContents());

        Session session = new Session(newInventory);
        session.setMap(cloneMap());
        sessions.put(player.getUniqueId(), session);
        return session;
    }

    /**
     * Создать новую стандартную сессию для игрока
     * (все предметы разделяются по ссылкам).
     *
     * @param player игрок
     * @return созданная сессия
     */
    public Session createSession(Player player, Map<String, Object> args) {
        Inventory newInventory;
        if (args.containsKey("WM_title")) {
            newInventory = Bukkit.createInventory(new MenuHolder(name), inventory.getSize(), (String) args.get("WM_title"));
        } else {
            newInventory = Bukkit.createInventory(new MenuHolder(name), inventory.getSize(), title);
        }
        newInventory.setContents(this.inventory.getContents());

        Session session;
        if (args == null) {
            session = new Session(newInventory);
        } else {
            session = new Session(newInventory, args);
        }
        session.setMap(cloneMap());
        sessions.put(player.getUniqueId(), session);
        return session;
    }

    /**
     * Создать новую кастомную сессию (например, TownLevelSession).
     *
     * @param player  игрок
     * @param factory фабрика, создающая экземпляр сессии
     * @param <T>     тип сессии
     * @return созданная сессия
     */
    public <T extends Session> T createSession(Player player, Function<Inventory, T> factory) {
        Inventory newInventory = Bukkit.createInventory(new MenuHolder(name), inventory.getSize(), title);
        newInventory.setContents(this.inventory.getContents());

        T session = factory.apply(newInventory);
        session.setMap(cloneMap());
        sessions.put(player.getUniqueId(), session);
        return session;
    }

    /**
     * Создать новую кастомную сессию с глубоким копированием предметов.
     * У каждого игрока будут независимые копии {@link Item}.
     *
     * @param player  игрок
     * @param factory фабрика, создающая экземпляр сессии
     * @param <T>     тип сессии
     * @return созданная сессия
     */
    public <T extends Session> T createDeepSession(Player player, Function<Inventory, T> factory) {
        Inventory newInventory = Bukkit.createInventory(new MenuHolder(name), inventory.getSize(), title);
        newInventory.setContents(this.inventory.getContents());

        T session = factory.apply(newInventory);
        session.setMap(deepCloneMap());
        sessions.put(player.getUniqueId(), session);
        return session;
    }

    /**
     * Получить сессию игрока.
     *
     * @param uuid UUID игрока
     * @return сессия или null
     */
    public Session getSession(UUID uuid) {
        return sessions.get(uuid);
    }

    /**
     * Удалить сессию игрока.
     *
     * @param uuid UUID игрока
     */
    public void removeSession(UUID uuid) {
        sessions.remove(uuid);
    }

    // ===============================
    // КОПИРОВАНИЕ КАРТЫ
    // ===============================

    /**
     * Поверхностное копирование карты предметов
     * (ссылки на те же {@link Item}).
     */
    public HashMap<Integer, Item> cloneMap() {
        return new HashMap<>(slots);
    }

    /**
     * Глубокое копирование карты предметов
     * (каждый {@link Item} клонируется).
     */
    public HashMap<Integer, Item> deepCloneMap() {
        HashMap<Integer, Item> copy = new HashMap<>();
        for (Map.Entry<Integer, Item> entry : slots.entrySet()) {
            copy.put(entry.getKey(), entry.getValue() != null ? entry.getValue().clone() : null);
        }
        return copy;
    }

    // ===============================
    // УТИЛИТЫ
    // ===============================

    /**
     * Поменять местами предметы в двух слотах.
     *
     * @param firstIndex  первый слот
     * @param secondIndex второй слот
     */
    public void replace(int firstIndex, int secondIndex) {
        Item firstSlot = getItem(firstIndex);
        Item secondSlot = getItem(secondIndex);

        slots.put(firstIndex, secondSlot);
        slots.put(secondIndex, firstSlot);
    }
}