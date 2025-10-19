package me.kendal.wineMenus.objects;

import me.kendal.wineMenus.objects.interfaces.ItemsOwner;
import org.bukkit.inventory.Inventory;

import java.util.*;

/**
 * Сессия игрока: хранит текущее состояние инвентаря и виджетов.
 */
public class Session implements ItemsOwner {
    private final Map<String, Widget> widgets = new HashMap<>();
    private Map<Integer, Item> localSlots;
    private List<Frame> animFrames;
    private Map<String, Object> args = null;
    private final Inventory inventory;

    public Session(Inventory inventory) {
        this.localSlots = new HashMap<>();
        this.inventory = inventory;
    }

    public Session(Inventory inventory, Map<String, Object> args) {
        this(inventory);
        this.args = args;

        if (args.containsKey("WM_animation")) {
            @SuppressWarnings("unchecked")
            LinkedList<Frame> frames = (LinkedList<Frame>) args.get("WM_animation");
            
            for (Frame frame : frames) {
                frame.setFrame(this);
            }
        }
    }
    /**
     * Заменяет текущую карту слотов локальной карты.
     */
    public void setMap(Map<Integer, Item> map) {
        this.localSlots = new HashMap<>(map);
    }

    /**
     * Устанавливает фрейм анимации на текущие локальные слоты.
     */
//    public void setAnimationFrame(AnimationFrame frame) {
//        frame.setSlots(localSlots);
//    }

    /**
     * Получить Item из локальных слотов (без учёта виджетов).
     */
    @Override
    public Item getItem(int slot) {
        return localSlots.get(slot);
    }

    /**
     * Установить Item в слот.
     */
    @Override
    public void setItem(int slot, Item item) {
        localSlots.put(slot, item);
        inventory.setItem(slot, getRenderedItem(slot).getItemstack());
    }

    /**
     * Вернуть Bukkit-инвентарь, связанный с сессией.
     */
    public Inventory getInventory() {
        return inventory;
    }

    /**
     * Добавить новый виджет.
     */
    public void addWidget(Widget widget) {
        widgets.put(widget.getName(), widget);
    }

    /**
     * Удалить виджет по имени.
     */
    public void removeWidget(String name) {
        widgets.remove(name);
    }

    /**
     * Получить виджет по имени.
     */
    public Widget getWidget(String name) {
        return widgets.get(name);
    }

    /**
     * Активировать виджет по имени.
     * @return true, если виджет найден и активирован
     */
    public boolean activateWidget(String name) {
        Widget widget = widgets.get(name);
        if (widget != null) {
            widget.activate();
            return true;
        }
        return false;
    }

    /**
     * Деактивировать виджет по имени.
     * @return true, если виджет найден и деактивирован
     */
    public boolean deactivateWidget(String name) {
        Widget widget = widgets.get(name);
        if (widget != null) {
            widget.deactivate();
            return true;
        }
        return false;
    }

    /**
     * Переключает состояние виджета по имени.
     * @return true, если виджет найден и деактивирован
     */
    public boolean toggleWidget(String name) {
        Widget widget = widgets.get(name);
        if (widget != null) {
            widget.toggle();
            for (var key : widget.getSlots().keySet()) {
                inventory.setItem(key, getRenderedItem(key).getItemstack());
            }
            return true;
        }
        return false;
    }


    /**
     * Получить все виджеты в текущей сессии.
     */
    public Collection<Widget> getWidgets() {
        return widgets.values();
    }

    /**
     * Получить Item в слоте с учётом активных виджетов и их zIndex.
     * Если несколько виджетов перекрывают слот, используется тот, у которого zIndex выше.
     * Если ни один не подошёл, возвращается локальный слот.
     */
    public Item getRenderedItem(int slot) {
        return widgets.values().stream()
                .filter(widget -> widget.isActive() && widget.getSlots().containsKey(slot))
                .max(Comparator.comparingInt(Widget::getZIndex))
                .map(widget -> widget.getItem(slot))
                .orElse(localSlots.get(slot));
    }

    /**
     * Создать поверхностную копию карты слотов.
     */
    public Map<Integer, Item> cloneMap() {
        return new HashMap<>(localSlots);
    }

    /**
     * Создать глубокую копию карты слотов (клонирует каждый Item).
     */
    public Map<Integer, Item> deepCloneMap() {
        Map<Integer, Item> copy = new HashMap<>();
        localSlots.forEach((key, value) ->
                copy.put(key, value != null ? value.clone() : null));
        return copy;
    }



    public Object getArg(String identificator) {
        return this.args.get(identificator);
    }

}


