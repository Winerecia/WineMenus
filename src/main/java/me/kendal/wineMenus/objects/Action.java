package me.kendal.wineMenus.objects;

import java.util.Map;

public abstract class Action {
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

    /** Основной метод выполнения */
    public abstract void execute(Map<String, Object> args);
}
