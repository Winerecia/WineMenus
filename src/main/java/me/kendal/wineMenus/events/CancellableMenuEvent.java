package me.kendal.wineMenus.events;

import org.bukkit.Bukkit;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

public abstract class CancellableMenuEvent extends Event implements Cancellable {
    private boolean isCancelled = false;
    private String cancelMessage = "Sorry, this event was cancelled.";

    public CancellableMenuEvent() {
        super(!Bukkit.getServer().isPrimaryThread());
    }

    /**
     * @return true if this event is cancelled.
     */
    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    /**
     * Sets the event to be cancelled or not.
     *
     * @param cancelled true will cancel the event.
     */
    @Override
    public void setCancelled(boolean cancelled) {
        isCancelled = cancelled;
    }

    /**
     * @return cancelMessage a String which will be displayed to the player/sender
     *         informing them that the action they commited is being denied, if
     *         cancelMessage is blank then no message will be displayed.
     */
    @NotNull
    public String getCancelMessage() {
        return cancelMessage;
    }

    /**
     * Sets the cancellation message which will display to the player. Set to "" to
     * display nothing.
     *
     * @param msg cancelMessage to display as feedback.
     */
    public void setCancelMessage(@NotNull String msg) {
        this.cancelMessage = msg;
    }

}