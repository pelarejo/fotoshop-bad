package main.command;

public class Rot90Cmd extends CommandFactory.Command {

    public static final String TAG = "rot90";

    protected Rot90Cmd(String[] args) {
        super(args);
    }

    @Override
    public boolean execute() {
        return false;
    }
}
