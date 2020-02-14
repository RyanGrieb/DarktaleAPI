package com.darktale.darktaleapi.data.player.command.serverconfig;

import com.darktale.darktaleapi.DarktaleAPI;
import com.darktale.darktaleapi.data.player.DarktalePlayer;
import com.darktale.darktaleapi.data.player.command.APICommand;
import com.darktale.darktaleapi.data.world.APILocation;
import com.darktale.darktaleapi.event.player.APISendPlayerMessageEvent;

/**
 *
 * @author Ryan
 */
public class SetSpawnCommand extends APICommand {

    public SetSpawnCommand() {
        super("setspawn", "setspawn");
    }

    @Override
    public void execute(DarktalePlayer player, String[] arguments) {
        DarktaleAPI.getAPI().getServerConfig().setSpawnLocation(player.getLocation());
        player.sendMessage("Spawn location set at: " + player.getLocation().toString());
    }

}
