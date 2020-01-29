package com.darktale.darktaleapi.listener;

import com.darktale.darktaleapi.event.Event;
import java.util.HashMap;

/**
 * Class handler for API calls, such as player.sendMessage(), ect.
 *
 * @author Ryan
 */
public class APIListener {

    private HashMap<String, Listener> listeners = new HashMap<String, Listener>();

    public void registerListener(Listener listener) {
        listeners.put(listener.getName(), listener);
    }

    public void callListener(String name, Event event) {
        listeners.get(name).onCall(event);
    }

}
