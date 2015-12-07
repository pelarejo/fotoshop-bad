package main.io.input;

import main.command.ArgumentException;
import main.command.CommandFactory;
import main.io.ouput.Output;
import main.io.ouput.Console;

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
public class Parser extends Input {
    private Scanner reader;         // source of command input
    private String prompt;
    private Output out;

    /**
     * Create a parser to read from the terminal window.
     */
    public Parser() {
        this.reader = new Scanner(System.in);
        this.prompt = "";
        this.out = new Console();
    }

    public Parser(String prompt) {
        this.reader = new Scanner(System.in);
        this.prompt = prompt;
        this.out = new Console();
    }

    public void setInputStream(FileInputStream str) {
        reader = new Scanner(str);
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    /**
     * @return The next command from the user.
     */
    public ParsedInput getCommand() {
        String cmdName = "";
        ArrayList<String> args = new ArrayList<>();

        this.out.update(this.prompt);     // print prompt

        String inputLine = reader.nextLine();

        // Find up to two words on the line.
        Scanner tokenizer = new Scanner(inputLine);
        if (tokenizer.hasNext()) {
            cmdName = tokenizer.next();
        }
        while (tokenizer.hasNext()) {
            args.add(tokenizer.next());
        }

        if (cmdName.equals("")) return new ParsedInput(PARSED_STATE.IGNORE);
        CommandFactory.Command cmd;
        try {
            cmd = CommandFactory.get(cmdName, args.toArray(new String[args.size()]));
        } catch (ArgumentException e) {
            return new ParsedInput(PARSED_STATE.INVALID_ARG, e.getMessage());
        }
        return new ParsedInput((cmd == null) ? PARSED_STATE.INVALID : PARSED_STATE.VALID, cmdName, cmd);
    }
}
