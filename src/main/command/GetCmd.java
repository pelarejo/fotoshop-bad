package main.command;

import main.gui.ConsoleView;
import main.image.ImageManager;
import main.locale.LocaleManager;

/**
 * Gets a copy of a cache image as the current image
 */
public class GetCmd extends CommandFactory.Command {

    public static String TAG = "get";

    private ConsoleView consoleView = new ConsoleView();

    public GetCmd(String[] args) {
        super(args);
        if (args.length < 1) {
            throw new ArgumentException(LocaleManager.getInstance().getString("error.command.get.argument.none"));
        }
    }

    @Override
    public String getTag() {
        return TAG;
    }

    @Override
    public boolean execute() {
        ImageManager.EditableImage ei = ImageManager.getInstance().getCachedImage(this.args[0]);
        if (ei == null) {
            this.consoleView.update(LocaleManager.getInstance().getString("error.command.get.no.img"));
            return false;
        }
        ImageManager.getInstance().newImage(ei);
        return true;
    }
}
