package me.kendal.wineMenus;


import me.kendal.wineMenus.actions.BroadcastAction;
import me.kendal.wineMenus.actions.PlayerCommandAction;
import me.kendal.wineMenus.objects.Action;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ActionManager {
    private final Map<String, Action> actions = new HashMap<>();
    private static ActionManager instance;

    public static ActionManager getInstance() {
        return instance;
    }

    public static void init() {
        if (instance == null) {
            instance = new ActionManager();
            instance.register(new BroadcastAction());
            instance.register(new PlayerCommandAction());
        }
    }

    /**
     * Регистрирует экшен
     */
    public void register(Action action) {
        if (actions.containsKey(action.getName())) {
            throw new IllegalArgumentException("Action с именем '" + action.getName() + "' уже существует!");
        }
        actions.put(action.getName(), action);
    }

    /**
     * Получает экшен по имени
     */
    public Action getAction(String name) {
        return actions.get(name);
    }

    /**
     * Выполняет экшен по имени с аргументами
     */
    public void executeAction(String name, Map<String, Object> args) {
        Action action = actions.get(name);
        if (action == null) {
            throw new IllegalArgumentException("Action с именем '" + name + "' не найден!");
        }
        action.execute(null);
    }

    /**
     * Список всех зарегистрированных экшенов
     */
    public Collection<Action> getAllActions() {
        return actions.values();
    }
}