package main.command;

import main.gui.ConsoleView;
import main.image.ImageManager;
import main.locale.LocaleManager;

import java.text.MessageFormat;
import java.util.EmptyStackException;

public class UndoCmd extends CommandFactory.Command {

    public static String TAG = "undo";

    private ConsoleView consoleView = new ConsoleView();

    public UndoCmd(String[] args) {
        super(args);
    }

    @Override
    public String getTag() {
        return TAG;
    }

    @Override
    public boolean execute() {
        try {
            CommandFactory.Command cmd = ImageManager.getInstance().lastCommand();
            if (cmd instanceof CommandFactory.UndoableCommand) {
                return ((CommandFactory.UndoableCommand) cmd).undo();
            } else {
                String msg = LocaleManager.getInstance().getString("error.command.undo.not.undoable");
                this.consoleView.update(MessageFormat.format(msg, cmd.getTag()));
            }
        } catch (EmptyStackException e) {
            this.consoleView.update(LocaleManager.getInstance().getString("error.command.undo.nothing"));
        }
        return true;
    }
}
