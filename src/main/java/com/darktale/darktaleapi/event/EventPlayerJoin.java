package com.darktale.darktaleapi.event;

import com.darktale.darktaleapi.DarktaleAPI;

public class EventPlayerJoin implements Event {

    private String playerName;

    public EventPlayerJoin(String playerName) {
        this.playerName = playerName;
    }

    @Override
    public void execute() {
        String message = "Welcome to darktale, " + playerName;
        //Call on the plugin to execute a sendMessage method
        //Bukkit.getPlayer(playerName).sendMessage("test"); <--- cant use that.

        DarktaleAPI.getAPI().apiListener().callListener("apiExecuteListener");
    }

    public String getPlayerName() {
        return playerName;
    }

}
