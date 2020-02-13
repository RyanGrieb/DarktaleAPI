package com.darktale.darktaleapi.data.clan;

import com.darktale.darktaleapi.data.player.rank.ClanRank;
import com.darktale.darktaleapi.data.file.FileManager;
import com.darktale.darktaleapi.data.file.JSONFile;
import com.darktale.darktaleapi.data.file.JSONManager;
import static com.darktale.darktaleapi.data.file.JSONManager.makeJSONFile;
import com.darktale.darktaleapi.data.player.DarktalePlayer;
import java.util.HashMap;

/**
 *
 * @author Ryan
 */
public class Clan {

    private static HashMap<String, Clan> clans = new HashMap<String, Clan>();

    private HashMap<String, ClanRank> clanPlayers;
    private String name;
    private JSONFile jsonFile;

    public Clan(String name) {
        clanPlayers = new HashMap<String, ClanRank>();

        this.name = name;
        this.jsonFile = new JSONFile(getClanJSONPath(name));
        loadJSONVariables();
    }

    private void loadJSONVariables() {
        if (JSONManager.hasObject(jsonFile, "players", "clan")) {
            //players = (ArrayList<String>) JSONManager.getObject(jsonFile, "players", "clan");
        }
    }

    public void addPlayer(DarktalePlayer player) {
        player.setClan(this);
        clanPlayers.put(player.getID(), ClanRank.RECRUIT);
        JSONManager.appendJSONObject(jsonFile, player.getClanRank().value(), "rank", player.getID(), "players", "clan");
    }

    public void setClanRank(String playerID, ClanRank rank) {
        clanPlayers.replace(playerID, rank);
        DarktalePlayer player = DarktalePlayer.getPlayer(playerID);
        JSONManager.appendJSONObject(jsonFile, player.getClanRank().value(), "rank", player.getID(), "players", "clan");
    }

    public ClanRank getClanRank(String playerID) {
        return clanPlayers.get(playerID);
    }

    public String getName() {
        return name;
    }

    public static void createClan(DarktalePlayer player, String name) {
        Clan clan = new Clan(name);
        clan.addPlayer(player);
        //player.setClanRank(ClanRank.LEADER);
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

    public static Clan getClan(String clanName) {
        if (clanName == null) {
            return null;
        }

        if (!clans.containsKey(clanName)) {
            clans.put(clanName, new Clan(clanName));
        }

        //TODO: If the clan is not in the hashmap, attempt to load it from json.
        return clans.get(clanName);
    }
}
