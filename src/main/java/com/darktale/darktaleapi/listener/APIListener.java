package com.darktale.darktaleapi.listener;

import java.util.HashMap;

public class APIListener {

    private HashMap<String, Listener> listeners = new HashMap<String, Listener>();

    public void registerListener(Listener listener) {
        listeners.put(listener.getName(), listener);
    }

    public void callListener(String name) {
        listeners.get(name).onCall();
    }

}
