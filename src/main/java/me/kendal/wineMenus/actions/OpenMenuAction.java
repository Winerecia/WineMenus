package me.kendal.wineMenus.actions;

import me.kendal.wineMenus.MenuManager;
import me.kendal.wineMenus.objects.Action;
import me.kendal.wineMenus.objects.ClickContext;
import org.bukkit.entity.Player;

public class OpenMenuAction extends Action {
    /**
     * Конструктор действия.
     */
    public OpenMenuAction() {
        super("OpenMenu");
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
        Player player = (Player) context.getEvent().getWhoClicked();

        MenuManager.getInstance().getMenu(command).open(player);
    }
}
