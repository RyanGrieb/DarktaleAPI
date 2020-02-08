package com.darktale.darktaleapi.data.player.command.clan;

import com.darktale.darktaleapi.DarktaleAPI;
import com.darktale.darktaleapi.data.clan.Clan;
import com.darktale.darktaleapi.data.player.rank.ClanRank;
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
        registerSubCommand(new ClanInvite());
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
            if (player.getClan() != null) {
                player.sendMessage("Error: You're already in a clan");
                return;
            }

            if (arguments.length <= 2) {
                player.sendMessage("Error: No clan name specified");
                printHelp(player);
                return;
            }
            String clanName = arguments[2];

            Clan.createClan(player, clanName);
            DarktaleAPI.getAPI().eventHandler().callEvent(new APIBroadcastEvent(player.getName() + " created the clan: " + clanName));
        }
    }

    class ClanJoin extends APICommand {

        public ClanJoin() {
            super("join", "clan join");
        }

        @Override
        public void execute(DarktalePlayer player, String[] arguments) {
            if (player.getClan() != null) {
                player.sendMessage("Error: You're already in a clan");
                return;
            }

            if (arguments.length <= 2) {
                player.sendMessage("Error: No clan name specifed");
                printHelp(player);
                return;
            }

            player.sendMessage("Joined " + arguments[2]);

        }

    }

    class ClanInvite extends APICommand {

        public ClanInvite() {
            super("invite", "clan invite");
        }

        @Override
        public void execute(DarktalePlayer player, String[] arguments) {
            if (player.getClan() == null) {
                player.sendMessage("Error: You're not in a clan to invite members");
                return;
            }

            if (arguments.length <= 2) {
                player.sendMessage("Error: No player specifed");
                printHelp(player);
                return;
            }

            if (player.getClanRank().value() < ClanRank.OFFICER.value()) {
                player.sendMessage("Error: You don't have permission to invite players");
                return;
            }

            player.sendMessage("Invited " + arguments[2] + " to " + player.getClan().getName());
        }

    }
}
