package me.kendal.wineMenus.objects;

import org.bukkit.event.inventory.InventoryClickEvent;

public class ClickContext {
    private Session session;
    private final Menu menu;
    private final InventoryClickEvent event;
    public ClickContext(Menu menu, InventoryClickEvent event) {
        this.menu = menu;
        this.event = event;
    }


    public void setSession(Session session) {
        this.session = session;
    }

    public Session getSession() {
        return session;
    }


    public Menu getMenu() {
        return menu;
    }

    public InventoryClickEvent getEvent() {
        return event;
    }
}
