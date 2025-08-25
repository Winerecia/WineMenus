package me.kendal.wineMenus.objects;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public abstract class Action {
    private final Map<String, Object> arguments = new HashMap<>();
    private final String name;

    public Action(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    // ===== Метаданные (переопределяются по желанию) =====

    /** Краткое описание действия */
    public abstract String getDescription();

    /** Автор действия */
    public abstract String getAuthor();

    /** Исключительно для работы того или иного плагина */
    public abstract String isDev();

    /** Ссылка на автора или проект */
    public abstract String getAuthorUrl();
    /** Название группы */
    public abstract String  getGroup();


    /** Версия действия */
    public abstract String getVersion();



    // ===== Работа с аргументами =====
    public void setArgument(String key, Object value) {
        arguments.put(key, value);
    }

    public void setArguments(Map<String, Object> args) {
        arguments.putAll(args);
    }

    public Object getArgument(String key) {
        return arguments.get(key);
    }

    public Map<String, Object> getArguments() {
        return Collections.unmodifiableMap(arguments);
    }


    /** Основной метод выполнения */
    public abstract void execute(ClickContext context);
}
