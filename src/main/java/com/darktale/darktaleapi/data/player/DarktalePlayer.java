package com.darktale.darktaleapi.data.player;

import com.darktale.darktaleapi.data.player.rank.ClanRank;
import com.darktale.darktaleapi.DarktaleAPI;
import com.darktale.darktaleapi.data.clan.Clan;
import com.darktale.darktaleapi.data.file.FileManager;
import com.darktale.darktaleapi.data.file.JSONFile;
import com.darktale.darktaleapi.data.file.JSONManager;
import static com.darktale.darktaleapi.data.file.JSONManager.makeJSONFile;
import com.darktale.darktaleapi.data.player.rank.StaffRank;
import com.darktale.darktaleapi.data.world.APILocation;
import com.darktale.darktaleapi.event.player.APISendPlayerMessageEvent;
import com.darktale.darktaleapi.event.player.APISetPlayerNicknameEvent;
import com.darktale.darktaleapi.event.player.APITeleportPlayerEvent;
import com.google.gson.Gson;
import java.io.File;
import java.util.HashMap;

/**
 *
 * @author Ryan
 */
public class DarktalePlayer {

    private static HashMap<String, DarktalePlayer> darktalePlayers = new HashMap<String, DarktalePlayer>();

    private String playerID;
    private String playerName;

    private APILocation location;

    //Json variables
    private String clanName;
    private boolean newPlayer;
    private StaffRank staffRank;

    public DarktalePlayer(String playerID, String playerName) {
        this.playerID = playerID;
        this.playerName = playerName;
        this.newPlayer = true;

        darktalePlayers.put(playerID, this);

        //Set the players prefix/nickname
        updatePrefix();
    }

    public void updateLocation(APILocation location) {
        this.location = location;
    }

    public void teleport(APILocation location) {
        DarktaleAPI.getAPI().eventHandler().callEvent(new APITeleportPlayerEvent(playerID, playerName, location));
    }

    public void sendMessage(String message) {
        DarktaleAPI.getAPI().eventHandler().callEvent(new APISendPlayerMessageEvent(playerID, playerName, message));
    }

    public void updatePrefix() {
        String playerPrefix = ((clanName != null) ? "[" + clanName + "]" : "[]") + " " + playerName;
        setNickname(playerPrefix);
    }

    public void setNickname(String nickname) {
        DarktaleAPI.getAPI().eventHandler().callEvent(new APISetPlayerNicknameEvent(playerID, playerName, nickname));
    }

    public void setNew(boolean newPlayer) {
        this.newPlayer = newPlayer;
    }

    public void setClan(Clan clan) {
        this.clanName = clan.getName();
    }

    public void setClanRank(ClanRank clanRank) {
        this.getClan().setClanRank(playerName, clanRank);
    }

    public APILocation getLocation() {
        return location;
    }

    public String getID() {
        return playerID;
    }

    public String getName() {
        return playerName;
    }

    public ClanRank getClanRank() {
        return Clan.getClan(clanName).getClanRank(playerName);
    }

    public boolean isNew() {
        return newPlayer;
    }

    public Clan getClan() {
        return Clan.getClan(clanName);
    }

    public static DarktalePlayer getPlayer(String playerID, String playerName) {
        String playerJSONPath = "./DarktaleConfig/player/" + playerID + ".json";

        if (!darktalePlayers.containsKey(playerID)) {
            DarktalePlayer player;
            File file = new File(playerJSONPath);
            if (file.exists()) {
                player = DarktaleAPI.getAPI().getGSON().fromJson(new JSONFile(playerJSONPath).toString(), DarktalePlayer.class);
            } else {
                player = new DarktalePlayer(playerID, playerName);
            }
        }
        return darktalePlayers.get(playerID);
    }

    public static DarktalePlayer getPlayer(String playerID) {
        return darktalePlayers.get(playerID);
    }

    public static DarktalePlayer getPlayerByName(String playerName) {
        for (DarktalePlayer player : darktalePlayers.values()) {
            if (player.getName().equals(playerName)) {
                return player;
            }
        }

        return null;
    }

    public static void savePlayerStates() {
        FileManager.makeDirectory("./DarktaleConfig/");
        FileManager.makeDirectory("./DarktaleConfig/player");

        for (DarktalePlayer player : darktalePlayers.values()) {
            if (player.isNew()) {
                player.setNew(false);
            }

            String playerJSONPath = "./DarktaleConfig/player/" + player.getID() + ".json";
            makeJSONFile(playerJSONPath);
            FileManager.setFileText(playerJSONPath, DarktaleAPI.getAPI().getGSON().toJson(player));
        }
    }
}
