package me.kendal.wineMenus;

import me.kendal.wineMenus.listener.MenusListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class WineMenus extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        MenuManager.init();
        ActionManager.init();
        getServer().getPluginManager().registerEvents(new MenusListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
