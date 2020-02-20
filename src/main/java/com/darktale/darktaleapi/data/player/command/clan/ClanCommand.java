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
            player.sendMessage("&cError: &7Subcommand not found");
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
                player.sendMessage("&cError: &7You're already in a clan");
                return;
            }

            if (arguments.length <= 2) {
                player.sendMessage("&cError: &7No clan name specified");
                printHelp(player);
                return;
            }
            String clanName = arguments[2];

            if (Clan.getClan(clanName) != null) {
                player.sendMessage("&cError: &7A clan with that name already exists");
                return;
            }

            Clan.createClan(player, clanName);
            DarktaleAPI.getAPI().eventHandler().callEvent(new APIBroadcastEvent(player.getName() + " &7created the clan: " + clanName));
        }
    }

    class ClanJoin extends APICommand {

        public ClanJoin() {
            super("join");
        }

        @Override
        public void execute(DarktalePlayer player, String[] arguments) {
            if (player.getClan() != null) {
                player.sendMessage("&cError: &7You're already in a clan");
                return;
            }

            if (arguments.length <= 2) {
                player.sendMessage("&cError: &7No clan name specifed");
                printHelp(player);
                return;
            }

            Clan clan = Clan.getClan(arguments[2]);

            if (clan == null) {
                player.sendMessage("&cError: &7Clan not found");
                return;
            }

            if (clan.isInvited(player.getName())) {
                clan.addPlayer(player);
            } else {
                //TODO: Check if the leader set the clan to open invitation
                player.sendMessage("&cError: &7You're not invited to join " + clan.getName());
                return;
            }

            player.sendMessage("&7Joined " + clan.getColoredName(player));
        }

    }

    class ClanInvite extends APICommand {

        public ClanInvite() {
            super("invite");
        }

        @Override
        public void execute(DarktalePlayer player, String[] arguments) {
            Clan clan = player.getClan();

            if (clan == null) {
                player.sendMessage("&cError: &7You're not in a clan to invite members");
                return;
            }

            if (arguments.length <= 2) {
                player.sendMessage("&cError: &7No player specifed");
                printHelp(player);
                return;
            }

            if (player.getClanRank().value() < ClanRank.OFFICER.value()) {
                player.sendMessage("&cError: &7You don't have permission to invite players");
                return;
            }

            DarktalePlayer invitedPlayer = DarktalePlayer.getPlayerByName(arguments[2]);
            if (invitedPlayer == null) {
                player.sendMessage("&cError: &7You can only invite online players");
                return;
            }

            if (clan.isInvited(invitedPlayer.getName())) {
                player.sendMessage("&cError: &7You already invited " + invitedPlayer.getName() + " to the clan");
                return;
            }

            if (clan.inClan(invitedPlayer.getName())) {
                player.sendMessage("&cError: &7" + invitedPlayer.getName() + " &7is already in a clan");
                return;
            }

            //Add the player name to the clans's invitation list
            clan.addInvitation(invitedPlayer.getName());
            invitedPlayer.sendMessage("&7You have been invited to join " + clan.getColoredName(player));
            player.sendMessage("&7Invited " + invitedPlayer.getName() + "&7 to " + clan.getColoredName(player));
        }

    }

    class ClanLeave extends APICommand {

        public ClanLeave() {
            super("leave");
        }

        @Override
        public void execute(DarktalePlayer player, String[] arguments) {
            Clan clan = player.getClan();

            if (clan == null) {
                player.sendMessage("&cError: &7You're not in a clan leave");
                return;
            }

            player.sendMessage("&7You have left " + clan.getColoredName(player));
            clan.removePlayer(player.getName());

            clan.sendClanMessage(player.getName() + " &7has left the clan");
        }

    }

    class ClanKick extends APICommand {

        public ClanKick() {
            super("kick");
        }

        @Override
        public void execute(DarktalePlayer player, String[] arguments) {
            Clan clan = player.getClan();

            if (clan == null) {
                player.sendMessage("&cError: &7You're not in a clan");
                return;
            }

            if (arguments.length <= 2) {
                player.sendMessage("&cError: &7No player specifed");
                printHelp(player);
                return;
            }

            if (player.getClanRank().value() < ClanRank.OFFICER.value()) {
                player.sendMessage("&cError: &7You don't have permission to kick players");
                return;
            }

            String targetPlayer = arguments[2];

            if (!clan.inClan(targetPlayer)) {
                player.sendMessage("&cError: &7Player not in the clan");
                return;
            }

            if (player.getName().equals(targetPlayer)) {
                player.sendMessage("&cError: &7You can't kick yourself");
                return;
            }

            clan.removePlayer(targetPlayer);

            if (DarktalePlayer.getPlayerByName(targetPlayer) != null) {
                DarktalePlayer targetDarktalePlayer = DarktalePlayer.getPlayerByName(targetPlayer);

                DarktalePlayer.getPlayerByName(targetPlayer).sendMessage("&7You were kicked from " + clan.getColoredName(player));
            }

            clan.sendClanMessage(targetPlayer + " &7was kicked from the clan");
        }

    }
}
