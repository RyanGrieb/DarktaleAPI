package com.darktale.darktaleapi.data.clan;

import com.darktale.darktaleapi.DarktaleAPI;
import com.darktale.darktaleapi.data.player.rank.ClanRank;
import com.darktale.darktaleapi.data.file.FileManager;
import com.darktale.darktaleapi.data.file.JSONFile;
import static com.darktale.darktaleapi.data.file.JSONManager.makeJSONFile;
import com.darktale.darktaleapi.data.player.DarktalePlayer;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Ryan
 */
public class Clan {

    private static HashMap<String, Clan> clans = new HashMap<String, Clan>();

    //Note: the clanPlayers keys are player NAMES.
    private HashMap<String, ClanPlayer> clanPlayers;
    private String name;

    public Clan(String name) {
        clanPlayers = new HashMap<String, ClanPlayer>();

        this.name = name;
    }

    public void addPlayer(DarktalePlayer player) {
        if (clanPlayers.containsKey(player.getName()) && clanPlayers.get(player.getName()).inClan()) {
            player.sendMessage("Error: You're already in this clan");
            return;
        }

        for (DarktalePlayer onlinePlayer : getOnlinePlayers()) {
            onlinePlayer.sendMessage(player.getName() + " has joined the clan");
        }

        player.setClan(this);
        clanPlayers.put(player.getName(), new ClanPlayer(player, ClanRank.RECRUIT, false));
    }

    public void removePlayer(DarktalePlayer player) {
        if (!clanPlayers.containsKey(player.getName())) {
            return;
        }

        player.removeClan();
        clanPlayers.remove(player.getName());

        //If the clan is empty, delete the clan
        if (clanPlayers.size() <= 0) {
            Clan.destroyClan(name);
        }
    }

    public void addInvitation(String playerName) {
        if (clanPlayers.containsKey(playerName)) {
            return;
        }

        clanPlayers.put(playerName, new ClanPlayer(playerName));
    }

    public void setClanRank(String playerName, ClanRank rank) {
        clanPlayers.get(playerName).setRank(rank);
    }

    public ClanRank getClanRank(String playerName) {
        return clanPlayers.get(playerName).getRank();
    }

    public boolean isInvited(String playerName) {
        if (!clanPlayers.containsKey(playerName)) {
            return false;
        }

        return clanPlayers.get(playerName).isInvited();
    }

    public String getName() {
        return name;
    }

    //TODO: I don't really like this and the clanPlayers hashmap. Think of a way to streamline this.
    public ArrayList<DarktalePlayer> getOnlinePlayers() {
        ArrayList<DarktalePlayer> players = new ArrayList<DarktalePlayer>();
        for (String playerName : clanPlayers.keySet()) {
            DarktalePlayer player = DarktalePlayer.getPlayerByName(playerName);
            if (player != null) {
                players.add(player);
            }
        }

        return players;
    }

    public HashMap<String, ClanPlayer> getClanPlayers() {
        return clanPlayers;
    }

    //TODO: We might not need this method. It seems redundant /w the one below
    public static void createClan(DarktalePlayer player, String name) {
        Clan clan = new Clan(name);
        clans.put(clan.getName(), clan);
        clan.addPlayer(player);
        player.setClanRank(ClanRank.LEADER);
    }

    public static Clan getClan(String clanName) {
        if (clanName == null) {
            return null;
        }

        String clanJSONPath = "./DarktaleConfig/clan/" + clanName + ".json";

        if (!clans.containsKey(clanName) && new File(clanJSONPath).isFile()) {
            Clan clan = DarktaleAPI.getAPI().getGSON().fromJson(new JSONFile(clanJSONPath).toString(), Clan.class);
            clans.put(clan.getName(), clan);
        }

        return clans.get(clanName);
    }

    public static void destroyClan(String clanName) {
        clans.remove(clanName, clans);
        try {
            Files.deleteIfExists(Paths.get("./DarktaleConfig/clan/" + clanName + ".json"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void saveClanStates() {

        FileManager.makeDirectory("./DarktaleConfig/");
        FileManager.makeDirectory("./DarktaleConfig/clan");

        for (Clan clan : clans.values()) {
            String clanJSONPath = "./DarktaleConfig/clan/" + clan.getName() + ".json";
            makeJSONFile(clanJSONPath);
            FileManager.setFileText(clanJSONPath, DarktaleAPI.getAPI().getGSON().toJson(clan));
        }

    }
}
