package com.darktale.darktaleapi.data.player.command.clan;

import com.darktale.darktaleapi.data.player.DarktalePlayer;
import com.darktale.darktaleapi.data.player.command.APICommand;

/**
 *
 * @author Ryan
 */
public class ClanCommand extends APICommand {

    public ClanCommand() {
        super("clan");

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
            return;
        }

    }

    @Override
    public void printHelp(DarktalePlayer player) {
        //TODO: Would send a APISendPlayerMessage call displaying the command information
        player.sendMessage("TODO: Help menu");
    }

    class ClanCreate extends APICommand {

        public ClanCreate() {
            super("create");
        }

        @Override
        public void execute(DarktalePlayer player, String[] arguments) {
            if (arguments.length <= 2) {
                player.sendMessage("Error: No clan name argument");
                return;
            }

            player.sendMessage("Created " + arguments[2]);
        }

        @Override
        public void printHelp(DarktalePlayer player) {
            player.sendMessage("TODO: Help menu");
        }
    }

    class ClanJoin extends APICommand {

        public ClanJoin() {
            super("join");
        }

        @Override
        public void execute(DarktalePlayer player, String[] arguments) {
            if (arguments.length <= 2) {
                player.sendMessage("Error: No clan name argument");
                return;
            }

            player.sendMessage("Joined " + arguments[2]);
        }

        @Override
        public void printHelp(DarktalePlayer player) {
            player.sendMessage("TODO: Help menu");
        }

    }
}
