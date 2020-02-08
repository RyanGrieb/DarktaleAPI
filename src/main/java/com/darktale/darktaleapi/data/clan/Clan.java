package com.darktale.darktaleapi.data.clan;

import com.darktale.darktaleapi.data.player.rank.ClanRank;
import com.darktale.darktaleapi.data.file.FileManager;
import com.darktale.darktaleapi.data.file.JSONFile;
import static com.darktale.darktaleapi.data.file.JSONManager.makeJSONFile;
import com.darktale.darktaleapi.data.player.DarktalePlayer;
import java.util.HashMap;

/**
 *
 * @author Ryan
 */
public class Clan {

    private static HashMap<String, Clan> clans = new HashMap<String, Clan>();

    private HashMap<String, DarktalePlayer> players;
    private String name;
    private JSONFile jsonFile;

    public Clan(String name) {
        players = new HashMap<String, DarktalePlayer>();

        this.name = name;
        this.jsonFile = new JSONFile(getClanJSONPath(name));
    }

    public void addPlayer(DarktalePlayer player) {
        player.setClan(this);
        players.put(player.getID(), player);
    }

    public String getName() {
        return name;
    }

    public static void createClan(DarktalePlayer player, String name) {
        Clan clan = new Clan(name);
        clan.addPlayer(player);
        player.setClanRank(ClanRank.LEADER);
        clans.put(clan.getName(), clan);
    }

    private static String getClanJSONPath(String clanName) {

        //Check if the players json file exists
        FileManager.makeDirectory("./DarktaleConfig/");
        FileManager.makeDirectory("./DarktaleConfig/clan");
        String playerJSONPath = "./DarktaleConfig/clan/" + clanName + ".json";

        makeJSONFile(playerJSONPath);

        return playerJSONPath;
    }
}
