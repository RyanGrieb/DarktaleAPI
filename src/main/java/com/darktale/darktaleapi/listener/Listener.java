package com.darktale.darktaleapi.listener;

import com.darktale.darktaleapi.event.Event;

/**
 * Listeners are defined in the plugin, specifying things such as SendPlayerMessage, TeleportPlayer, ect.
 *
 * @author Ryan
 */
public abstract class Listener {

    private String listenerName;

    public Listener(String listenerName) {
        this.listenerName = listenerName;
    }

    public String getName() {
        return listenerName;
    }

    abstract public void onCall(Event event);
}
