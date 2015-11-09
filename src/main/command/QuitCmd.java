package main.command;

import main.Editor;
import main.gui.ConsoleView;
import main.locale.LocaleManager;

public class QuitCmd extends CommandFactory.Command {

    public static final String TAG = "quit";

    private ConsoleView console = new ConsoleView();

    public QuitCmd(String[] args) {
        super(args);
    }

    @Override
    public String getTag() {
        return TAG;
    }

    @Override
    public boolean execute() {
        if (this.args.length > 1) {
            this.console.update(LocaleManager.getInstance().getString("error.command.quit.argument"));
            return false;
        } else {
            Editor.setState(Editor.PROGRAM_STATE.QUIT);
            return true;
        }
    }
}
