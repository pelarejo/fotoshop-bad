package main.command;

import main.gui.ConsoleView;
import main.image.ImageManager;
import main.locale.LocaleManager;

/**
 * Put the image in cache and assigned it a cache name
 */
public class PutCmd extends CommandFactory.Command {
    public static String TAG = "put";

    private ConsoleView consoleView = new ConsoleView();

    public PutCmd(String[] args) {
        super(args);
        if (args.length < 1) {
            throw new ArgumentException(LocaleManager.getInstance().getString("error.command.put.argument.none"));
        }
    }

    @Override
    public String getTag() {
        return TAG;
    }

    @Override
    public boolean execute() {
        ImageManager.EditableImage ei = ImageManager.getInstance().getCurrentImage();
        if (ei == null) {
            this.consoleView.update(LocaleManager.getInstance().getString("error.no.image"));
            return false;
        }
        ei = ImageManager.getInstance().cacheImage(this.args[0], ei);
        if (ei != null) {
            //TODO: Future revision should show a diff with both image properties and user confirmation
            String msg = LocaleManager.getInstance().getString("command.put.erasing.img");
            this.consoleView.update(msg);
        }
        return true;
    }
}
