package main.command;

import main.gui.ConsoleView;
import main.image.ColorImage;
import main.image.ImageManager;
import main.locale.LocaleManager;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.MessageFormat;

/**
 * Save the image to the desire name.
 * Will override any existing image.
 */
public class SaveCmd extends CommandFactory.Command {

    public static final String TAG = "save";

    private ConsoleView consoleView = new ConsoleView();

    public SaveCmd(String[] args) {
        super(args);
        if (args.length < 1) {
            throw new ArgumentException(LocaleManager.getInstance().getString("error.command.save.argument.none"));
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
            this.consoleView.update(LocaleManager.getInstance().getString("error.command.save.no.img"));
            return false;
        }
        ColorImage img = ei.getImage();
        String outputName = this.args[0];
        try {
            File outputFile = new File(outputName);
            if (!outputFile.getParentFile().exists()) {
                // Necessary because ImageIO.write throw NullPointerException, which is dumb
                throw new FileNotFoundException(MessageFormat.format("Folder {0} doesn't exists", outputFile.getParentFile()));
            }
            ImageIO.write(img, "jpg", outputFile);
            String msg = LocaleManager.getInstance().getString("command.save.saved");
            this.consoleView.update(MessageFormat.format(msg, outputName));
        } catch (IOException e) {
            this.consoleView.update(LocaleManager.getInstance().getString("error.command.save.error"));
            return false;
        }
        return true;
    }
}
