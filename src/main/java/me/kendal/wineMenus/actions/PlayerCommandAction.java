package me.kendal.wineMenus.actions;

import me.kendal.wineMenus.objects.Action;
import me.kendal.wineMenus.objects.ClickContext;
import org.bukkit.entity.Player;

import java.util.Map;

public class PlayerCommandAction extends Action {

    public PlayerCommandAction() {
        super("PlayerCommand");
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
        String command = (String) getArgument("command");
        Player player = (Player) getArgument("player");
        player.performCommand(command);
    }
}
