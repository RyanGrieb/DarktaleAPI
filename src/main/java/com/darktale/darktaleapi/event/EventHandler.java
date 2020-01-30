package com.darktale.darktaleapi.event;

public class EventHandler {

    public EventHandler() {
    }

    public void callEvent(APIEvent event) {
        event.execute();
    }
}
