package com.darktale.darktaleapi.event;

public class EventHandler {

    //Note: Events are DarktaleAPI, Listeners are Bukkit.
    public EventHandler() {
    }

    public void callEvent(Event event) {
        //I want the API to then tell the plugin to call a specific function.
        // The specific function is inside the plugin and can differ between hytale and minecraft.

        //For example, when the player joins, the plugin calls this event. Then the API tells the plugin to send a greet message
        //executeGreetMessage(params...);
        event.execute();

    }
}
