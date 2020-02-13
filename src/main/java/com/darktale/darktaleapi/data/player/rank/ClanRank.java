package com.darktale.darktaleapi.data.player.rank;

/**
 *
 * @author Ryan
 */
public enum ClanRank {

    RECRUIT(0),
    MEMBER(1),
    OFFICER(2),
    GENERAL(3),
    LEADER(4);

    private int value;

    ClanRank(int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }

    public static ClanRank toRank(int value) {
        return ClanRank.values()[value];
    }

    public static ClanRank toRank(String value) {
        return ClanRank.values()[Integer.valueOf(value)];
    }
}
