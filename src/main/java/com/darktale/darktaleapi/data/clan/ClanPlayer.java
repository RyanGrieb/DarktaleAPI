package com.darktale.darktaleapi.data.clan;

import com.darktale.darktaleapi.data.player.DarktalePlayer;
import com.darktale.darktaleapi.data.player.rank.ClanRank;

/**
 *
 * @author Ryan
 */
public class ClanPlayer {

    private String playerName;
    private String playerID;
    private ClanRank rank;
    private boolean isInivted;

    public ClanPlayer(DarktalePlayer player, ClanRank rank, boolean isInivted) {
        this.playerName = player.getName();
        this.playerID = player.getID();
        this.rank = rank;
        this.isInivted = isInivted;
    }

    public ClanPlayer(String playerName) {
        this.playerName = playerName;
        this.isInivted = true;
    }

    public void setRank(ClanRank rank) {
        this.rank = rank;
    }

    public ClanRank getRank() {
        return rank;
    }

    public boolean isInvited() {
        return isInivted;
    }

    public boolean inClan() {
        return !isInivted;
    }
}
