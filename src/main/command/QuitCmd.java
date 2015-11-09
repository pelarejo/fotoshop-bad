package main.command;

import main.Editor;
import main.gui.ConsoleView;
import main.locale.LocaleManager;

public class QuitCmd extends CommandFactory.Command {

    public static final String TAG = "quit";

    private ConsoleView console = new ConsoleView();

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
        Editor.setState(Editor.PROGRAM_STATE.QUIT);
        return true;
    }
}
