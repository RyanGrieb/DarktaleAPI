package com.darktale.darktaleapi.event;

public class EventHandler {

    public EventHandler() {
    }

    public boolean callEvent(APIEvent event) {
        return !(event.execute());
    }
}
