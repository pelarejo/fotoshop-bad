package main;

import javafx.beans.property.SimpleObjectProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import main.command.CommandFactory;
import main.io.IoHelper;
import main.io.input.Input;
import main.io.ouput.Output;
import main.io.input.Parser;
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

public class Workbench extends Service<Boolean> {

    private static PROGRAM_STATE eState;

    public final IoHelper ios;

    public enum PROGRAM_STATE {
        RUN, QUIT
    }

    /**
     * Create the editor and initialize its parser.
     */
    public Workbench(IoHelper ios) {
        this.ios = ios;
    }

    public static void setPgrState(PROGRAM_STATE state) {
        eState = state;
    }

    public static PROGRAM_STATE getPgrState() {
        return eState;
    }

    /**
     * Run routine. Loops until the end of the editing session.
     */
    public void run() {
        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the editing session is over.
        this.ios.out.update(LocaleManager.getInstance().getString("screen.splash"));
        eState = PROGRAM_STATE.RUN;
        while (eState.equals(PROGRAM_STATE.RUN)) {
            Parser.ParsedInput parsedInput = this.ios.in.getCommand();
            switch (parsedInput.eState) {
                case VALID:
                    parsedInput.cmd.execute();
                    break;
                case INVALID:
                    String msg = LocaleManager.getInstance().getString("error.command.invalid");
                    msg = MessageFormat.format(msg, parsedInput.what);
                    this.ios.err.update(msg);
                    break;
                case INVALID_ARG:
                    this.ios.err.update(parsedInput.what);
                    break;
                case IGNORE:
                    break;
            }
        }
        this.ios.out.update(LocaleManager.getInstance().getString("screen.end"));
    }


    // SERVICE IMPLEMENTATION

    private SimpleObjectProperty<CommandFactory.Command> cmdPty = new SimpleObjectProperty<>();

    public void runCommand(CommandFactory.Command cmd){
        this.setCommand(cmd);
        this.start();
    }

    public void setCommand(CommandFactory.Command cmd) {
        this.cmdPty.set(cmd);
    }

    public CommandFactory.Command getCommand() {
        return this.cmdPty.get();
    }

    @Override
    protected Task<Boolean> createTask() {
        final CommandFactory.Command cmd = getCommand();
        return new Task<Boolean>() {
            @Override
            protected Boolean call() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return cmd.execute();
            }
        };
    }
}
