package main.command;

import main.gui.ConsoleView;
import main.locale.LocaleManager;

/**
 * Show generic help
 * Could be expanded as a per command manual
 */
public class HelpCmd extends CommandFactory.Command {

    public static final String TAG = "help";

    private ConsoleView console = new ConsoleView();

    public HelpCmd(String[] args) {
        super(args);
    }

    @Override
    public String getTag() {
        return TAG;
    }

    @Override
    public boolean execute() {
        this.console.update(LocaleManager.getInstance().getString("command.help.msg"));
        return true;
    }
}
