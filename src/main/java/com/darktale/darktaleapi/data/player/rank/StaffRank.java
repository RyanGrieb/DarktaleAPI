package com.darktale.darktaleapi.data.player.rank;

/**
 *
 * @author Ryan
 */
public enum StaffRank {

    NONE(0),
    HELPER(1),
    MODERATOR(2),
    ADMIN(3),
    OWNER(4);

    private int value;

    StaffRank(int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }

}
