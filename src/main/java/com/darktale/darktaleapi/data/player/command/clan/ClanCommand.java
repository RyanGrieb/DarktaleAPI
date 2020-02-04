package com.darktale.darktaleapi.data.player.command.clan;

import com.darktale.darktaleapi.DarktaleAPI;
import com.darktale.darktaleapi.data.player.DarktalePlayer;
import com.darktale.darktaleapi.data.player.command.APICommand;
import com.darktale.darktaleapi.event.misc.APIBroadcastEvent;

/**
 *
 * @author Ryan
 */
public class ClanCommand extends APICommand {

    public ClanCommand() {
        super("clan", "clan");

        registerSubCommand(new ClanJoin());
        registerSubCommand(new ClanCreate());
    }

    @Override
    public void execute(DarktalePlayer player, String[] arguments) {
        if (arguments.length <= 1) {
            printHelp(player);
            return;
        }

        if (!executeSubCommand(player, arguments[1], arguments)) {
            player.sendMessage("Error: Subcommand not found");
            printHelp(player);
            return;
        }

    }

    class ClanCreate extends APICommand {

        public ClanCreate() {
            super("create", "clan create");
        }

        @Override
        public void execute(DarktalePlayer player, String[] arguments) {
            if (arguments.length <= 2) {
                player.sendMessage("Error: No clan name specified");
                printHelp(player);
                return;
            }

            DarktaleAPI.getAPI().eventHandler().callEvent(new APIBroadcastEvent(player.getName() + " created the clan: " + arguments[2]));
        }
    }

    class ClanJoin extends APICommand {

        public ClanJoin() {
            super("join", "clan join");
        }

        @Override
        public void execute(DarktalePlayer player, String[] arguments) {
            if (arguments.length <= 2) {
                player.sendMessage("Error: No clan name specifed");
                printHelp(player);
                return;
            }

            //TODO: Send message to all faction members
            player.sendMessage("Joined " + arguments[2]);
        }

    }
}
