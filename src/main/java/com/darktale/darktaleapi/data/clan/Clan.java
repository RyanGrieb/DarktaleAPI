package com.darktale.darktaleapi.data.clan;

import com.darktale.darktaleapi.data.player.rank.ClanRank;
import com.darktale.darktaleapi.data.file.FileManager;
import com.darktale.darktaleapi.data.file.JSONFile;
import com.darktale.darktaleapi.data.file.JSONManager;
import static com.darktale.darktaleapi.data.file.JSONManager.makeJSONFile;
import com.darktale.darktaleapi.data.player.DarktalePlayer;
import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Ryan
 */
public class Clan {

    private static HashMap<String, Clan> clans = new HashMap<String, Clan>();

    private HashMap<String, ClanRank> clanPlayers;
    private ArrayList<String> invitedPlayers;
    private String name;
    private JSONFile jsonFile;

    public Clan(String name) {
        clanPlayers = new HashMap<String, ClanRank>();
        invitedPlayers = new ArrayList<String>();

        this.name = name;
        this.jsonFile = new JSONFile(getClanJSONPath(name));
        loadJSONVariables();
    }

    private void loadJSONVariables() {
        if (JSONManager.hasObject(jsonFile, "players", "clan")) {
            JSONArray array = JSONManager.getJSONArray(jsonFile, "players", "clan");
            for (int i = 0; i < array.length(); i++) {
                clanPlayers.put(array.getJSONObject(i).getString("id"), ClanRank.toRank(array.getJSONObject(i).getInt("rank")));
            }
        }

        if (JSONManager.hasObject(jsonFile, "invitedPlayers", "clan")) {
            JSONArray array = JSONManager.getJSONArray(jsonFile, "invitedPlayers", "clan");
            for (int i = 0; i < array.length(); i++) {
                invitedPlayers.add(array.getString(i));
            }
        }
    }

    public void addPlayer(DarktalePlayer player) {
        if (clanPlayers.containsKey(player.getID())) {
            player.sendMessage("Error: You're already in this clan");
            return;
        }

        player.setClan(this);
        clanPlayers.put(player.getID(), ClanRank.RECRUIT);

        JSONObject playerObj = new JSONObject();
        playerObj.put("rank", ClanRank.RECRUIT.value());
        playerObj.put("id", player.getID());

        JSONManager.appendJSONToArray(jsonFile, playerObj, "players", "clan");
    }

    public void addInvitedPlayer(String playerID) {
        if (invitedPlayers.contains(playerID)) {
            return;
        }
        invitedPlayers.add(playerID);
        JSONManager.appendJSONToArray(jsonFile, playerID, "invitedPlayers", "clan");
    }

    public void setClanRank(String playerID, ClanRank rank) {
        clanPlayers.replace(playerID, rank);
        DarktalePlayer player = DarktalePlayer.getPlayer(playerID);

        //Append to the jsonArray /w the new rank. Right now we do this manually so it's messy.
        JSONArray array = JSONManager.getJSONArray(jsonFile, "players", "clan");
        for (int i = 0; i < array.length(); i++) {
            JSONObject playerJSONObj = array.getJSONObject(i);
            if (playerJSONObj.getString("id").equals(playerID)) {
                playerJSONObj.put("rank", rank.value());
                array.remove(i);
                array.put(i, playerJSONObj);
            }
        }

        jsonFile.getJSONObject("clan").put("players", array);
        FileManager.setFileText(jsonFile.getFilePath(), jsonFile.toString());
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
