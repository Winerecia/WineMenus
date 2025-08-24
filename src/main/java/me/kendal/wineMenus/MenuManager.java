package me.kendal.wineMenus;

import me.kendal.wineMenus.objects.Menu;

import java.util.HashMap;
import java.util.Map;

public class MenuManager {
    private final Map<String, Menu> openMenus = new HashMap<>();
    private static MenuManager instance;


    public static MenuManager getInstance() {
        return instance;
    }

    public static void init() {
        if (instance == null) {
            instance = new MenuManager();
        }
    }

    public void registerMenu(Menu menu) {
        openMenus.put(menu.getName(), menu);
    }

    public Menu getMenu(String name) {
        return openMenus.get(name);
    }

    public void unregisterMenu(Integer id) {
        openMenus.remove(id);
    }


}
