package com.darktale.darktaleapi.data.clan;

import com.darktale.darktaleapi.data.player.DarktalePlayer;
import java.util.HashMap;

/**
 *
 * @author Ryan
 */
public class Clan {

    private HashMap<String, DarktalePlayer> players;
    private String name;

    public Clan(String name) {
        players = new HashMap<String, DarktalePlayer>();

        this.name = name;

        //TODO: Fetch info from json
    }

    public void setLeader(DarktalePlayer player) {

    }

    public static void createClan(DarktalePlayer player, String name) {
        Clan clan = new Clan(name);
        clan.setLeader(player);
    }
}
