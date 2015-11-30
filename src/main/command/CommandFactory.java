package main.command;

import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This factory is used to create instances of command.
 * One willing to create a new command should call the addCommand(NewClass.TAG, NewClass.class)
 * in the static initializer.
 */
public final class CommandFactory {
    private static Map<String, Class<? extends Command>> cmds;
    private static final Logger logger;

    static {
        cmds = new HashMap<>();
        logger = Logger.getLogger(CommandFactory.class.getName());
        // ADD Commands HERE
        addCommand(HelpCmd.TAG, HelpCmd.class);
        addCommand(OpenCmd.TAG, OpenCmd.class);
        addCommand(SaveCmd.TAG, SaveCmd.class);
        addCommand(Rot90Cmd.TAG, Rot90Cmd.class);
        addCommand(MonoCmd.TAG, MonoCmd.class);
        addCommand(LookCmd.TAG, LookCmd.class);
        addCommand(QuitCmd.TAG, QuitCmd.class);
        addCommand(ScriptCmd.TAG, ScriptCmd.class);
        addCommand(UndoCmd.TAG, UndoCmd.class);
        addCommand(PutCmd.TAG, PutCmd.class);
        addCommand(GetCmd.TAG, GetCmd.class);
    }

    /**
     * Class to inherit when one desire to create a new command
     */
    public static abstract class Command {
        protected String[] args;

        /**
         * Always implement this constructor in inherited class.
         *
         * @param args is a list of arguments
         */
        public Command(String[] args) {
            this.args = args;
        }

        /**
         * Used to retreive the tag at runtime
         * @return the tag
         */
        public abstract String getTag();

        /**
         * Main command execution method.
         * Will be called automatically.
         * @return false if the execution went critically wrong, true otherwise
         */
        public abstract boolean execute();
    }

    /**
     * Class to inherit to create an undoable command
     */
    public static abstract class UndoableCommand extends Command {

        public UndoableCommand(String[] args) {
            super(args);
        }

        public abstract boolean undo();
    }

    /**
     * Gets a new instance of a command.
     * Commands will throw RuntimeException when arguments are incorrect
     *
     * @param tag of the command to look for
     * @return the command instance, or null if the command doesn't exists
     */
    public static Command get(String tag, String... args) {
        Class<? extends Command> cmd = cmds.get(tag.toUpperCase());
        try {
            return (cmd == null) ? null : cmd.getDeclaredConstructor(String[].class).newInstance((Object) args);
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            //Should never be triggered
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            throw (ArgumentException) e.getCause();
        }
        return null;
    }

    /**
     * Add a command to the list of commands.
     * Will insert a log message if two commands with the same tags exist.
     *
     * @param tag the command what
     * @param cmd the command class
     * @return true if the command overrides another with the same tag.
     */
    public static boolean addCommand(String tag, Class<? extends Command> cmd) {
        boolean exists;
        if (tag == null) {
            logger.log(Level.WARNING, MessageFormat.format("Tag is null for class: {0}", cmd.getName()));
            return false;
        }
        if (exists = (cmds.put(tag.toUpperCase(), cmd) != null)) {
            logger.log(Level.WARNING, MessageFormat.format("Command tag: {0} already exists", tag));
        }
        return exists;
    }
}