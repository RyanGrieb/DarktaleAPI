package com.darktale.darktaleapi.event;

public class EventHandler {

    public EventHandler() {
    }

    public void callEvent(Event event) {
        event.execute();
    }
}
