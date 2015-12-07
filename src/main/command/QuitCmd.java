package main.command;

import main.locale.LocaleManager;

/**
 * Quits the program
 */
public class QuitCmd extends CommandFactory.Command {

    public static final String TAG = "quit";

    public QuitCmd(String[] args) {
        super(args);
        if (this.args.length > 0) {
            throw new ArgumentException(LocaleManager.getInstance().getString("error.command.quit.argument"));
        }
    }

    @Override
    public String getTag() {
        return TAG;
    }

    @Override
    public boolean execute() {
        System.exit(0);
        //Workbench.setPgrState(Workbench.PROGRAM_STATE.QUIT);
        return true;
    }
}
