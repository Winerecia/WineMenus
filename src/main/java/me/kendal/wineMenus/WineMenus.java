package me.kendal.wineMenus;

import org.bukkit.plugin.java.JavaPlugin;

public final class WineMenus extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        MenuManager.init();
        ActionManager.init();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
