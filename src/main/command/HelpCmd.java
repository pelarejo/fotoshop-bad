package main.command;

import main.locale.LocaleManager;

public class HelpCmd extends CommandFactory.Command {

    public static final String TAG = "help";

    public HelpCmd(String[] args) {
        super(args);
    }

    @Override
    public String getTag() {
        return TAG;
    }

    @Override
    public boolean execute() {
        //TODO: replace all prints
        System.out.print(LocaleManager.getInstance().getString("help"));
        return true;
    }
}
