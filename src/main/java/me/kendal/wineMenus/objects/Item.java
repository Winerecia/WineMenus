package me.kendal.wineMenus.objects;

import me.kendal.wineMenus.utils.ItemUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.Serializable;
import java.util.*;
import java.util.function.Consumer;

/**
 * Класс-обёртка над {@link ItemStack}, который добавляет:
 * - хранение действий по кликам (ClickType)
 * - гибкую настройку метаданных (имя, лор, флаги, энчанты)
 * - поддержку кастомных обработчиков кликов
 *
 * Используется в WineMenus как базовый элемент GUI.
 */
public class Item implements Cloneable, Serializable {

    // --- Основные поля ---
    private ItemStack itemStack;
    private final Map<ClickType, List<Action>> actions = new EnumMap<>(ClickType.class);
    private Consumer<ClickContext> customHandler;

    // --- Конструкторы ---
    public Item(Material material) {
        this(new ItemStack(material), true);
    }

    public Item(ItemStack itemStack) {
        this(itemStack, true);
    }

    /**
     * @param itemStack ItemStack, на основе которого создаётся объект
     * @param zeroName  если true — очищает имя предмета (делает пустым)
     */
    public Item(ItemStack itemStack, boolean zeroName) {
        this.itemStack = itemStack;
        if (zeroName) {
            ItemMeta meta = getItemMeta();
            meta.displayName(ItemUtils.mini(""));
            setItemMeta(meta);
        }
    }

    // =========================================================
    // ================ Метаданные предмета =====================
    // =========================================================

    public void setAmount(int amount) {
        this.itemStack.setAmount(amount);
    }

    public int getAmount() {
        return this.itemStack.getAmount();
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

    public void setEnchanted() {
        ItemMeta meta = getItemMeta();
        meta.addEnchant(Enchantment.AQUA_AFFINITY, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        setItemMeta(meta);
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

    private ItemMeta getItemMeta() {
        return itemStack.getItemMeta();
    }

    private void setItemMeta(ItemMeta itemMeta) {
        itemStack.setItemMeta(itemMeta);
    }

    // =========================================================
    // ================ Работа с действиями =====================
    // =========================================================

    /**
     * Устанавливает список действий для конкретного типа клика.
     */
    public void setActions(ClickType clickType, List<Action> actions) {
        this.actions.put(clickType, new ArrayList<>(actions));
    }

    /**
     * Добавляет одно действие для конкретного типа клика.
     */
    public void addAction(ClickType clickType, Action action) {
        this.actions.computeIfAbsent(clickType, k -> new ArrayList<>()).add(action);
    }

    /**
     * Добавляет сразу несколько действий для конкретного типа клика.
     */
    public void addActions(ClickType clickType, List<Action> actions) {
        this.actions.computeIfAbsent(clickType, k -> new ArrayList<>()).addAll(actions);
    }

    /**
     * Получает список действий для данного клика.
     */
    public List<Action> getActions(ClickType clickType) {
        return this.actions.getOrDefault(clickType, Collections.emptyList());
    }

    /**
     * Устанавливает действия для обычного и шифтового клика левой кнопкой.
     */
    public void setLeftActions(List<Action> actions) {
        setActions(ClickType.LEFT, actions);
        setActions(ClickType.SHIFT_LEFT, actions);
    }

    /**
     * Устанавливает действия для обычного и шифтового клика правой кнопкой.
     */
    public void setRightActions(List<Action> actions) {
        setActions(ClickType.RIGHT, actions);
        setActions(ClickType.SHIFT_RIGHT, actions);
    }

    /**
     * Устанавливает действия для клика средней кнопкой.
     */
    public void setMiddleActions(List<Action> actions) {
        setActions(ClickType.MIDDLE, actions);
    }

    /**
     * Возвращает все действия предмета по всем типам кликов.
     */
    public Map<ClickType, List<Action>> getAllActions() {
        return Collections.unmodifiableMap(actions);
    }

    // =========================================================
    // ================ Кастомный обработчик ====================
    // =========================================================

    /**
     * Позволяет задать собственный обработчик клика,
     * который будет вызван вместо стандартных Action'ов.
     */
    public void setCustomHandler(Consumer<ClickContext> customHandler) {
        this.customHandler = customHandler;
    }

    public Consumer<ClickContext> getCustomHandler() {
        return this.customHandler;
    }

    // =========================================================
    // ==================== Прочее ==============================
    // =========================================================

    public ItemStack getItemstack() {
        return itemStack;
    }

    public void setItemstack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    /**
     * Создаёт глубокую копию Item (включая метаданные и действия).
     */
    @Override
    public Item clone() {
        ItemStack clonedStack = this.itemStack.clone();
        Item newItem = new Item(clonedStack, false);

        // Лор
        if (this.getLore() != null) {
            newItem.setLore(new ArrayList<>(this.getLore()));
        }

        // Копируем действия
        for (Map.Entry<ClickType, List<Action>> entry : this.actions.entrySet()) {
            List<Action> clonedActions = new ArrayList<>();
            for (Action action : entry.getValue()) {
                clonedActions.add(action.clone());
            }
            newItem.setActions(entry.getKey(), clonedActions);
        }

        // Копируем флаги
        ItemMeta meta = newItem.getItemMeta();
        meta.addItemFlags(this.getItemMeta().getItemFlags().toArray(new ItemFlag[0]));
        newItem.setItemMeta(meta);

        // Копируем кастомный обработчик
        newItem.setCustomHandler(this.customHandler);

        return newItem;
    }
}
