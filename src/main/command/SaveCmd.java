package main.command;

import main.gui.ConsoleView;
import main.image.ColorImage;
import main.image.ImageManager;
import main.locale.LocaleManager;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;

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
        ColorImage img = ImageManager.getInstance().getCurrentImage().getImage();
        if (img == null) {
            this.consoleView.update(LocaleManager.getInstance().getString("error.command.save.no.img"));
            return false;
        }
        String outputName = this.args[0];
        try {
            File outputFile = new File(outputName);
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
