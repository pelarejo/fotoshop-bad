package main.command;

import main.locale.LocaleManager;

public class HelpCmd extends CommandFactory.Command {

    public static final String TAG = "help";

    protected HelpCmd(String[] args) {
        super(args);
    }

    @Override
    public boolean execute() {
        //TODO: replace all prints
        System.out.print(LocaleManager.getInstance().getString("help"));
        return true;
    }
}
