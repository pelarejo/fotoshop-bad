package main.command;

import main.gui.ConsoleView;
import main.locale.LocaleManager;

public class HelpCmd extends CommandFactory.Command {

    public static final String TAG = "command.help.msg";

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
