package main;

import main.command.ArgumentException;
import main.command.CommandFactory;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class is taken from the "World of Zuul" application.
 * "World of Zuul" is a very simple, text based adventure game.
 * <p>
 * This parser reads user input and tries to interpret it as an "Adventure"
 * command. Every time it is called it reads a line from the terminal and
 * tries to interpret the line as a three word command. It returns the command
 * as an object of class CommandFactory.Command.
 * <p>
 * The parser has a set of known command words. It checks user input against
 * the known commands, and if the input is not one of the known commands, it
 * returns a command object that is marked as an unknown command.
 *
 * @author Michael Kolling and David J. Barnes
 * @version 2006.03.30
 */
public class Parser {
    private Scanner reader;         // source of command input

    public enum PARSED_STATE {
        VALID,
        INVALID,
        INVALID_ARG,
        EMPTY
    }

    public class ParsedInput {
        public PARSED_STATE eState;
        public CommandFactory.Command cmd;
        public String what;

        private ParsedInput(PARSED_STATE eState) {
            this.eState = eState;
        }

        private ParsedInput(PARSED_STATE eState, String what) {
            this.eState = eState;
            this.what = what;
        }

        private ParsedInput(PARSED_STATE eState, String what, CommandFactory.Command cmd) {
            this.eState = eState;
            this.cmd = cmd;
            this.what = what;
        }
    }

    /**
     * Create a parser to read from the terminal window.
     */
    public Parser() {
        reader = new Scanner(System.in);
    }

    public void setInputStream(FileInputStream str) {
        reader = new Scanner(str);
    }

    /**
     * @return The next command from the user.
     */
    public ParsedInput getCommand() {
        String cmdName = "";
        ArrayList<String> args = new ArrayList<>();
        String inputLine = reader.nextLine();

        // Find up to two words on the line.
        Scanner tokenizer = new Scanner(inputLine);
        if (tokenizer.hasNext()) {
            cmdName = tokenizer.next();
        }
        while (tokenizer.hasNext()) {
            args.add(tokenizer.next());
        }
        if (cmdName.equals("")) return new ParsedInput(PARSED_STATE.EMPTY);
        CommandFactory.Command cmd;
        try {
            cmd = CommandFactory.get(cmdName, args.toArray(new String[args.size()]));
        } catch (ArgumentException e) {
            return new ParsedInput(PARSED_STATE.INVALID_ARG, e.getMessage());
        }
        return new ParsedInput((cmd == null) ? PARSED_STATE.INVALID : PARSED_STATE.VALID, cmdName, cmd);
    }
}
