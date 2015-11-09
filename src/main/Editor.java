package main;

import main.command.CommandFactory;
import main.gui.ConsoleController;
import main.image.ColorImage;
import main.image.ImageManager;
import main.locale.LocaleManager;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

/**
 * This class is the main processing class of the Fotoshop application.
 * Fotoshop is a very simple image editing tool. Users can apply a number of
 * filters to an image. That's all. It should really be extended to make it more
 * useful!
 * <p>
 * To edit an image, create an instance of this class and call the "edit"
 * method.
 * <p>
 * This main class creates and initializes all the others: it creates the parser
 * and  evaluates and executes the commands that the parser returns.
 *
 * @author Richard Jones
 * @version 2013.09.10
 */

public class Editor {

    Parser parser;
    ColorImage currentImage;
    String name;
    String filter1;
    String filter2;
    String filter3;
    String filter4;

    ConsoleController mConsole;

    /**
     * Create the editor and initialize its parser.
     */
    public Editor() {
        parser = new Parser();
        mConsole = new ConsoleController();
    }

    /**
     * main.Main edit routine. Loops until the end of the editing session.
     */
    public void edit() {

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the editing session is over.
        boolean finished = false;
        while (!finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println(LocaleManager.getInstance().getString("screen.end"));
    }


    /**
     * Given a command, edit (that is: execute) the command.
     *
     * @param command The command to be processed.
     * @return true If the command ends the editing session, false otherwise.
     */
    private boolean processCommand(Command command) {
        boolean wantToQuit = false;

        if (command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }
        String commandWord = command.getCommandWord();
        ArrayList<String> str = new ArrayList<>();
        str.add(command.getSecondWord());
        str.add(command.getThirdWord());
        if (commandWord.equals("help")) {
            CommandFactory.Command cmd = CommandFactory.get("help", str.toArray(new String[str.size()]));
            cmd.execute();
        } else if (commandWord.equals("open")) {
            //TODO: get should be in parser
            CommandFactory.Command cmd = CommandFactory.get("open", str.toArray(new String[str.size()]));
            cmd.execute();
        } else if (commandWord.equals("save")) {
            CommandFactory.Command cmd = CommandFactory.get("save", str.toArray(new String[str.size()]));
            cmd.execute();
        } else if (commandWord.equals("mono")) {
            CommandFactory.Command cmd = CommandFactory.get("mono", str.toArray(new String[str.size()]));
            cmd.execute();
        } else if (commandWord.equals("rot90")) {
            CommandFactory.Command cmd = CommandFactory.get("rot90", str.toArray(new String[str.size()]));
            cmd.execute();
        } else if (commandWord.equals("look")) {
            CommandFactory.Command cmd = CommandFactory.get("look", str.toArray(new String[str.size()]));
            cmd.execute();
        } else if (commandWord.equals("script")) {
            wantToQuit = script(command);
        } else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }
        return wantToQuit;
    }

//----------------------------------
// Implementations of user commands:
//----------------------------------
    /**
     * The 'script' command runs a sequence of commands from a
     * text file.
     * <p>
     * IT IS IMPORTANT THAT THIS COMMAND WORKS AS IT CAN BE USED FOR TESTING
     *
     * @param command the script command, second word of which is the name of
     *                the script file.
     * @return whether to quit.
     */
    private boolean script(Command command) {
        if (!command.hasSecondWord()) {
            // if there is no second word, we don't know what to open...
            System.out.println("which script");
            return false;
        }

        String scriptName = command.getSecondWord();
        Parser scriptParser = new Parser();
        try (FileInputStream inputStream = new FileInputStream(scriptName)) {
            scriptParser.setInputStream(inputStream);
            boolean finished = false;
            while (!finished) {
                try {
                    Command cmd = scriptParser.getCommand();
                    finished = processCommand(cmd);
                } catch (Exception ex) {
                    return finished;
                }
            }
            return finished;
        } catch (FileNotFoundException ex) {
            System.out.println("Cannot find " + scriptName);
            return false;
        } catch (IOException ex) {
            throw new RuntimeException("Panic: script barfed!");
        }
    }

    /**
     * "Quit" was entered. Check the rest of the command to see whether we
     * really quit the editor.
     *
     * @param command the command given.
     * @return true, if this command quits the editor, false otherwise.
     */
    private boolean quit(Command command) {
        if (command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        } else {
            return true;  // signal that we want to quit
        }
    }
}
