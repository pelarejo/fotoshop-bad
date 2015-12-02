package main;

import javafx.application.Application;
import javafx.stage.Stage;
import main.gui.ConsoleView;
import main.locale.LocaleManager;

import java.text.MessageFormat;

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

public class Workbench {

    private static PROGRAM_STATE eState;

    private Parser parser;
    private ConsoleView console = new ConsoleView();

    public enum PROGRAM_STATE {
        RUN, QUIT
    }

    /**
     * Create the editor and initialize its parser.
     */
    public Workbench() {
        parser = new Parser();
    }

    public static void setState(PROGRAM_STATE state) {
        eState = state;
    }

    public static PROGRAM_STATE getState() {
        return eState;
    }

    /**
     * main.Main edit routine. Loops until the end of the editing session.
     */
    public void edit() {
        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the editing session is over.
        this.console.update(LocaleManager.getInstance().getString("screen.splash"));
        eState = PROGRAM_STATE.RUN;
        while (eState.equals(PROGRAM_STATE.RUN)) {
           this.console.update("> ");     // print prompt
            Parser.ParsedInput parsedInput = parser.getCommand();
            switch (parsedInput.eState) {
                case VALID:
                    parsedInput.cmd.execute();
                    break;
                case INVALID:
                    String msg = LocaleManager.getInstance().getString("error.command.invalid");
                    msg = MessageFormat.format(msg, parsedInput.what);
                    this.console.update(msg);
                    break;
                case INVALID_ARG:
                    this.console.update(parsedInput.what);
                    break;
                case EMPTY:
                    break;
            }
        }
        this.console.update(LocaleManager.getInstance().getString("screen.end"));
    }
}
