package com.darktale.darktaleapi.data.player;

import com.darktale.darktaleapi.data.player.rank.ClanRank;
import com.darktale.darktaleapi.DarktaleAPI;
import com.darktale.darktaleapi.data.clan.Clan;
import com.darktale.darktaleapi.data.file.FileManager;
import com.darktale.darktaleapi.data.file.JSONFile;
import com.darktale.darktaleapi.data.file.JSONManager;
import static com.darktale.darktaleapi.data.file.JSONManager.makeJSONFile;
import com.darktale.darktaleapi.data.player.rank.StaffRank;
import com.darktale.darktaleapi.event.player.APISendPlayerMessageEvent;
import java.util.HashMap;

/**
 *
 * @author Ryan
 */
public class DarktalePlayer {

    private static HashMap<String, DarktalePlayer> darktalePlayers = new HashMap<String, DarktalePlayer>();

    private String playerID;
    private String playerName;

    //Json variables
    private Clan clan;
    private ClanRank clanRank;
    private StaffRank staffRank;

    private JSONFile jsonFile;

    public DarktalePlayer(String playerID, String playerName) {
        this.playerID = playerID;
        this.playerName = playerName;
        try {

            this.jsonFile = new JSONFile(getPlayerJSONPath(playerID));
        } catch (Exception e) {
            e.printStackTrace();
        }

        loadJSONVariables();
        darktalePlayers.put(playerID, this);
    }

    private void loadJSONVariables() {
        //Load clan information. Drop the nullcheck eventually.
        String clanName = (String) JSONManager.getObject(jsonFile, "name", "clan");
        if (clanName != null) {
            this.clan = Clan.getClan(clanName);
            this.clanRank = ClanRank.toRank((String) JSONManager.getObject(jsonFile, "rank", "clan"));
        }
    }

    public void sendMessage(String message) {
        DarktaleAPI.getAPI().eventHandler().callEvent(new APISendPlayerMessageEvent(playerID, playerName, message));
    }

    public void setClan(Clan clan) {
        this.clan = clan;
        JSONManager.appendJSONObject(jsonFile, clan.getName(), "name", "clan");

    }

    public void setClanRank(ClanRank clanRank) {
        this.clanRank = clanRank;
        JSONManager.appendJSONObject(jsonFile, clanRank.value(), "rank", "clan");
    }

    public String getID() {
        return playerID;
    }

    public String getName() {
        return playerName;
    }

    public boolean isNew() {
        if (jsonFile.has("newPlayer")) {
            if ((Boolean) JSONManager.getObject(jsonFile, "newPlayer")) {
                JSONManager.appendJSONObject(jsonFile, false, "newPlayer");
            }
        } else if (!jsonFile.has("newPlayer")) {
            JSONManager.appendJSONObject(jsonFile, true, "newPlayer");
        }

        return (Boolean) JSONManager.getObject(jsonFile, "newPlayer");
    }

    public ClanRank getClanRank() {
        return clanRank;
    }

    public Clan getClan() {
        return clan;
    }

    public static DarktalePlayer getPlayer(String playerID, String playerName) {
        if (!darktalePlayers.containsKey(playerID)) {
            DarktalePlayer player = new DarktalePlayer(playerID, playerName);
        }
        return darktalePlayers.get(playerID);
    }

    private static String getPlayerJSONPath(String playerID) throws Exception {

        //Check if the players json file exists
        FileManager.makeDirectory("./DarktaleConfig/");
        FileManager.makeDirectory("./DarktaleConfig/player");
        String playerJSONPath = "./DarktaleConfig/player/" + playerID + ".json";

        makeJSONFile(playerJSONPath);

        return playerJSONPath;
    }
}
