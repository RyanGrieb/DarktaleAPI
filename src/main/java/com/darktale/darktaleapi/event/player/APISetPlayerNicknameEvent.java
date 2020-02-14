package com.darktale.darktaleapi.event.player;

import com.darktale.darktaleapi.DarktaleAPI;

/**
 *
 * @author Ryan
 */
public class APISetPlayerNicknameEvent extends APIPlayerEvent {

    private String nickname;

    public APISetPlayerNicknameEvent(String playerID, String playerName, String nickname) {
        super(playerID, playerName);
        this.nickname = nickname;
    }

    @Override
    public boolean execute() {
        DarktaleAPI.getAPI().listenerHandler().callbackEvent(this);
        return true;
    }

    public String getNickname() {
        return nickname;
    }
}
