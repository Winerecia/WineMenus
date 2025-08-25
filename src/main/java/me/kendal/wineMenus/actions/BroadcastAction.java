package me.kendal.wineMenus.actions;

import me.kendal.wineMenus.objects.Action;
import me.kendal.wineMenus.objects.ClickContext;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;

import java.util.Map;

public class BroadcastAction extends Action {
    public BroadcastAction() {
        super("BroadcastAction");
    }

    @Override
    public String getDescription() {
        return "";
    }

    @Override
    public String getAuthor() {
        return "";
    }

    @Override
    public String isDev() {
        return "";
    }

    @Override
    public String getAuthorUrl() {
        return "";
    }

    @Override
    public String getGroup() {
        return "";
    }

    @Override
    public String getVersion() {
        return "";
    }

    @Override
    public void execute(ClickContext context) {
        String message = (String) getArgument("message");
        Bukkit.broadcast(MiniMessage.miniMessage().deserialize(message));
    }
}
