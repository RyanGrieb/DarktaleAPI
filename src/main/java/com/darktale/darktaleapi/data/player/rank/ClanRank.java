package com.darktale.darktaleapi.data.player.rank;

/**
 *
 * @author Ryan
 */
public enum ClanRank {

    NONE(0),
    RECRUIT(1),
    MEMBER(2),
    OFFICER(3),
    GENERAL(4),
    LEADER(5);

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
