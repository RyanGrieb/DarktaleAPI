package com.darktale.darktaleapi.data.clan;

import com.darktale.darktaleapi.data.player.rank.ClanRank;
import com.darktale.darktaleapi.data.file.FileManager;
import com.darktale.darktaleapi.data.file.JSONFile;
import com.darktale.darktaleapi.data.file.JSONManager;
import static com.darktale.darktaleapi.data.file.JSONManager.makeJSONFile;
import com.darktale.darktaleapi.data.player.DarktalePlayer;
import java.io.File;
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

    //TODO: Rethink how i want to use the clanPlayers hashmap, it seems bad....
    //I think we want a ClanPlayer class that stores information such as clanRank, isOnline, ect. Wouldn't be a wrapper for DarktalePlayer
    //as that class is only for online players.
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

        for (DarktalePlayer onlinePlayer : getOnlinePlayers()) {
            onlinePlayer.sendMessage(player.getName() + " has joined the clan");
        }

        player.setClan(this);
        clanPlayers.put(player.getID(), ClanRank.RECRUIT);

        JSONObject playerObj = new JSONObject();
        playerObj.put("rank", ClanRank.RECRUIT.value());
        playerObj.put("id", player.getID());
        JSONManager.appendToJSONArray(jsonFile, playerObj, "players", "clan");

        //Remove the player from the invited player array.
        JSONManager.removeFromJSONArray(jsonFile, player.getID(), "invitedPlayers", "clan");
        invitedPlayers.remove(player.getID());
    }

    public void removePlayer(DarktalePlayer player) {
        if (!clanPlayers.containsKey(player.getID())) {
            return;
        }

        player.setClan(null);
        clanPlayers.remove(player.getID());

        //Right now we manually remove the JSONObject since we can't really tell the json what object to remove.
        JSONArray array = jsonFile.getJSONObject("clan").getJSONArray("players");
        for (int i = 0; i < array.length(); i++) {
            if (array.getJSONObject(i).getString("id").equals(player.getID())) {
                array.remove(i);
            }
        }

        FileManager.setFileText(jsonFile.getFilePath(), jsonFile.toString());

        for (DarktalePlayer onlinePlayer : getOnlinePlayers()) {
            onlinePlayer.sendMessage(player.getName() + " has left the clan");
        }
    }

    public void addInvitation(String playerID) {
        if (invitedPlayers.contains(playerID)) {
            return;
        }
        invitedPlayers.add(playerID);
        JSONManager.appendToJSONArray(jsonFile, playerID, "invitedPlayers", "clan");
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

    //TODO: I don't really like this and the clanPlayers hashmap. Think of a way to streamline this.
    public ArrayList<DarktalePlayer> getOnlinePlayers() {
        ArrayList<DarktalePlayer> players = new ArrayList<DarktalePlayer>();
        for (String playerID : clanPlayers.keySet()) {
            DarktalePlayer player = DarktalePlayer.getPlayer(playerID);
            if (player != null) {
                players.add(player);
            }
        }

        return players;
    }

    public HashMap<String, ClanRank> getClanPlayers() {
        return clanPlayers;
    }

    public ArrayList<String> getInvitedPlayers() {
        return invitedPlayers;
    }

    //TODO: We might not need this method. It seems redundant /w the one below
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

        if (!clans.containsKey(clanName) && new File("./DarktaleConfig/clan/" + clanName + ".json").isFile()) {
            Clan clan = new Clan(clanName);
            clans.put(clan.getName(), clan);
        }

        return clans.get(clanName);
    }
}
