package me.kendal.wineMenus.objects;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Абстрактное действие, которое можно привязать к Item в меню.
 * Все действия должны наследоваться от этого класса и реализовывать execute().
 */
public abstract class Action implements Cloneable {
    private final Map<String, Object> arguments = new HashMap<>();
    private final String name;

    /**
     * Конструктор действия.
     *
     * @param name уникальное имя действия
     */
    public Action(String name) {
        this.name = name;
    }

    /**
     * @return имя действия
     */
    public String getName() {
        return name;
    }

    // ===== Метаданные =====

    /**
     * Краткое описание действия
     */
    public abstract String getDescription();

    /**
     * Автор действия
     */
    public abstract String getAuthor();

    /**
     * Флаг для разработчиков, может использоваться для внутренних целей
     */
    public abstract String isDev();

    /**
     * Ссылка на автора или проект
     */
    public abstract String getAuthorUrl();

    /**
     * Название группы действия
     */
    public abstract String getGroup();

    /**
     * Версия действия
     */
    public abstract String getVersion();

    // ===== Работа с аргументами =====

    /**
     * Установить один аргумент действия
     *
     * @param key   ключ аргумента
     * @param value значение аргумента
     */
    public void setArgument(String key, Object value) {
        arguments.put(key, value);
    }

    /**
     * Установить несколько аргументов действия
     *
     * @param args карта ключ-значение
     */
    public void setArguments(Map<String, Object> args) {
        arguments.putAll(args);
    }

    /**
     * Получить значение аргумента по ключу
     *
     * @param key ключ
     * @return значение или null
     */
    public Object getArgument(String key) {
        return arguments.get(key);
    }

    /**
     * Получить все аргументы действия
     *
     * @return карта ключ-значение (неизменяемая)
     */
    public Map<String, Object> getArguments() {
        return Collections.unmodifiableMap(arguments);
    }

    // ===== Основной метод =====

    /**
     * Выполнить действие в контексте клика
     *
     * @param context контекст клика
     */
    public abstract void execute(ClickContext context);

    // ===== Клонирование =====

    /**
     * Создать копию действия с клонированием аргументов.
     * Используется в Item.clone() для deepCloneMap.
     *
     * @return новый экземпляр Action
     */
    @Override
    public Action clone() {
        try {
            Action cloned = (Action) super.clone();
            // создаём копию аргументов
            Map<String, Object> argsCopy = new HashMap<>(this.arguments);
            cloned.setArguments(argsCopy);
            return cloned;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError("Action не поддерживает клонирование", e);
        }
    }
}