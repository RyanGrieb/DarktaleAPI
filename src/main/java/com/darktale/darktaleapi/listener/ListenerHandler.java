package com.darktale.darktaleapi.listener;

import com.darktale.darktaleapi.event.APIEvent;
import java.util.HashMap;

/**
 *
 * @author Ryan
 */
public class ListenerHandler {

    private HashMap<String, APIListenerWrapper> apiListeners;

    public ListenerHandler() {
        apiListeners = new HashMap<String, APIListenerWrapper>();
    }

    //The callbackEvent method basically tells the Bukkit/Hytale plugin to call their listener.
    public void callbackEvent(APIEvent event) {
        for (APIListenerWrapper listeners : apiListeners.values()) {
            listeners.onEventCall(event);
        }
    }

    public void registerListener(String name, APIListener listener) {
        APIListenerWrapper listenerWrapper = new APIListenerWrapper(name, listener);
        apiListeners.put(name, listenerWrapper);
    }

}
