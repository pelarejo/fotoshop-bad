package main.command;

import main.image.ImageManager;
import sun.jvm.hotspot.utilities.Assert;

import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class CommandFactory {
    private static Map<String, Class<? extends Command>> cmds;
    private static final Logger logger;
    static {
        cmds = new HashMap<>();
        addCommand(HelpCmd.TAG, HelpCmd.class);
        addCommand(OpenCmd.TAG, OpenCmd.class);
        addCommand(Rot90Cmd.TAG, Rot90Cmd.class);
        logger = Logger.getLogger(CommandFactory.class.getName());
    }

    public static abstract class Command {
        public static final String TAG = null;
        protected String[] args;

        /**
         * Always implement this constructor in inherited class.
         *
         * @param args is a list of arguments
         */
        protected Command(String[] args) {
            this.args = args;
        }

        public abstract boolean execute();
    }

    public interface Undoable {
        void undo();
    }

    /**
     * Gets a new instance of a command.
     *
     * @param tag the command to look for
     * @return the command instance, or null if the command doesn't exists
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static Command get(String tag, String... args) {
        Class<? extends Command> cmd = cmds.get(tag.toUpperCase());
        try {
            return (cmd == null) ? null : cmd.getDeclaredConstructor(String[].class).newInstance((Object) args);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Add a command to the list of commands.
     * Will insert a log message if two commands with the same tags exist.
     *
     * @param tag the command name
     * @param cmd the command class
     * @return true if the command overrides another with the same tag.
     */
    public static boolean addCommand(String tag, Class<? extends Command> cmd) {
        boolean exists;
        //TODO: null tag should be checked
        if (exists = (cmds.put(tag.toUpperCase(), cmd) != null)) {
            logger.log(Level.WARNING, MessageFormat.format("Command tag: {0} already exists", tag));
        }
        return exists;
    }
}