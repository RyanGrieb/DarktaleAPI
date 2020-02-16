package com.darktale.darktaleapi.data.player.command.test;

import com.darktale.darktaleapi.data.player.DarktalePlayer;
import com.darktale.darktaleapi.data.player.command.APICommand;

/**
 *
 * @author Ryan
 */
public class LongCommand extends APICommand {

    public LongCommand() {
        super("long");

        registerSubCommand(new Command());
        registerSubCommand(new Short());
    }

    @Override
    public void execute(DarktalePlayer player, String[] arguments) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    class Command extends APICommand {

        public Command() {
            super("command");

            registerSubCommand(new Example());
        }

        @Override
        public void execute(DarktalePlayer player, String[] arguments) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

    }

    class Short extends APICommand {

        public Short() {
            super("short");
        }

        @Override
        public void execute(DarktalePlayer player, String[] arguments) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

    }

    class Example extends APICommand {

        public Example() {
            super("example");
        }

        @Override
        public void execute(DarktalePlayer player, String[] arguments) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

    }
}
