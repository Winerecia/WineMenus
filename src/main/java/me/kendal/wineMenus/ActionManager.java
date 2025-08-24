package me.kendal.wineMenus;


import me.kendal.wineMenus.objects.Action;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ActionManager {
    private final Map<String, Action> actions = new HashMap<>();

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
        action.execute(args);
    }

    /**
     * Список всех зарегистрированных экшенов
     */
    public Collection<Action> getAllActions() {
        return actions.values();
    }
}