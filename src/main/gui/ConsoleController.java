package main.gui;

import main.Command;
import main.Parser;
import main.locale.LocaleManager;

public class ConsoleController extends AGuiController {
    
    // Create a model for this that fetch depending of language
    private static final String SPLASH_MSG = LocaleManager.getInstance().getString("screen.splash");
    
    //
    
    private IView consoleView;
    private PromptView promptView;

    public ConsoleController() {
        this.consoleView = new ConsoleView();
        this.consoleView.updateView(SPLASH_MSG);
        this.promptView = new PromptView();
    }
    
    @Override
    public void updateView() {  
    }
  
    public void run() {
        Parser parser = new Parser();
        boolean finished = false;
        do {
            promptView.updateView();
            Command command = parser.getCommand();
//            finished = processCommand(command);
        } while (!finished);
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
 /*       if (commandWord.equals("help")) {
            printHelp();
        } else if (commandWord.equals("open")) {
            open(command);
        } else if (commandWord.equals("save")) {
            save(command);
        } else if (commandWord.equals("mono")) {
            mono(command);
        } else if (commandWord.equals("rot90")) {
            rot90(command);
        } else if (commandWord.equals("look")) {
            look(command);
        } else if (commandWord.equals("script")) {
            wantToQuit = script(command);
        } else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }*/
        return wantToQuit;
    }

}
