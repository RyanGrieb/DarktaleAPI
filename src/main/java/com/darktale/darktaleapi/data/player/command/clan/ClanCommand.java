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
        super("clan");

        registerSubCommand(new ClanJoin());
        registerSubCommand(new ClanCreate());
        registerSubCommand(new ClanInvite());
        registerSubCommand(new ClanLeave());
        registerSubCommand(new ClanKick());
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
            super("create");
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
            super("join");
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

            Clan clan = Clan.getClan(arguments[2]);

            if (clan == null) {
                player.sendMessage("Error: Clan not found");
                return;
            }

            if (clan.isInvited(player.getName())) {
                clan.addPlayer(player);
            } else {
                //TODO: Check if the leader set the clan to open invitation
                player.sendMessage("Error: You're not invited to join " + clan.getName());
                return;
            }

            player.sendMessage("Joined " + arguments[2]);
        }

    }

    class ClanInvite extends APICommand {

        public ClanInvite() {
            super("invite");
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

            DarktalePlayer invitedPlayer = DarktalePlayer.getPlayerByName(arguments[2]);
            if (invitedPlayer == null) {
                player.sendMessage("Error: You can only invite online players");
                return;
            }

            if (player.getClan().isInvited(invitedPlayer.getName())) {
                player.sendMessage("Error: You already invited " + invitedPlayer.getName() + " to the clan");
                return;
            }

            if (player.getClan().getClanPlayers().keySet().contains(invitedPlayer.getID())) {
                player.sendMessage("Error: " + invitedPlayer.getName() + " is already in the clan");
                return;
            }

            //Add the player name to the clans's invitation list
            player.getClan().addInvitation(invitedPlayer.getName());
            invitedPlayer.sendMessage("You have been invited to join " + player.getClan().getName());
            player.sendMessage("Invited " + invitedPlayer.getName() + " to " + player.getClan().getName());
        }

    }

    class ClanLeave extends APICommand {

        public ClanLeave() {
            super("leave");
        }

        @Override
        public void execute(DarktalePlayer player, String[] arguments) {
            if (player.getClan() == null) {
                player.sendMessage("Error: You're not in a clan leave");
                return;
            }

            for (DarktalePlayer onlinePlayer : player.getClan().getOnlinePlayers()) {
                onlinePlayer.sendMessage(player.getName() + " has left the clan");
            }

            player.sendMessage("You have left " + player.getClan().getName());
            player.getClan().removePlayer(player);
        }

    }

    class ClanKick extends APICommand {

        public ClanKick() {
            super("kick");
        }

        @Override
        public void execute(DarktalePlayer player, String[] arguments) {
            if (player.getClan() == null) {
                player.sendMessage("Error: You're not in a clan");
                return;
            }

            if (player.getClanRank().value() < ClanRank.OFFICER.value()) {
                player.sendMessage("Error: You don't have permission to kick players");
                return;
            }

            //Wow, this brings up a bunch of problems. What if the player is offline?
            //What do we do? The command arguments only provides the player name...
            DarktalePlayer targetPlayer = DarktalePlayer.getPlayerByName(arguments[2]);

            // player.getClan().removePlayer();
            player.getClan().removePlayer(targetPlayer);
        }

    }
}
