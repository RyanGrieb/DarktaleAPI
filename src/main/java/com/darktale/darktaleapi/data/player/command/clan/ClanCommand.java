package com.darktale.darktaleapi.data.player.command.clan;

import com.darktale.darktaleapi.data.player.DarktalePlayer;
import com.darktale.darktaleapi.data.player.command.APICommand;

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
                player.sendMessage("Error: No clan name argument");
                printHelp(player);
                return;
            }

            player.sendMessage("Created " + arguments[2]);
        }
    }

    class ClanJoin extends APICommand {

        public ClanJoin() {
            super("join", "clan join");
        }

        @Override
        public void execute(DarktalePlayer player, String[] arguments) {
            if (arguments.length <= 2) {
                player.sendMessage("Error: No clan name argument");
                printHelp(player);
                return;
            }

            player.sendMessage("Joined " + arguments[2]);
        }

    }
}
