package main.command;

import main.image.ColorImage;
import main.image.ImageManager;
import main.locale.LocaleManager;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;

/**
 * Opens an image to set as a current image.
 * Will override any current image.
 */
public class OpenCmd extends CommandFactory.Command {

    public static final String TAG = "open";

    public OpenCmd(String[] args) {
        super(args);
        if (this.args.length < 1) {
            // if there is no second word, we don't know what to open...
            throw new ArgumentException(LocaleManager.getInstance().getString("error.command.open.argument.none"));
        }
    }

    @Override
    public String getTag() {
        return TAG;
    }


    @Override
    public boolean execute() {
        String path = this.args[0];
        ColorImage img = loadImage(path);
        if (img != null) {
            ImageManager.getInstance().newImage(path, img);
            String msg = LocaleManager.getInstance().getString("command.open.loaded");
            this.ios.out.update(MessageFormat.format(msg, img.getOriginalPath()));
            return true;
        }
        return false;
    }

    /**
     * Load an image from a file.
     *
     * @param path The path of the image file
     * @return a main.image.ColorImage containing the image
     */
    private ColorImage loadImage(String path) {
        try {
            return new ColorImage(path, ImageIO.read(new File(path)));
        } catch (IOException e) {
            String msg = LocaleManager.getInstance().getString("error.command.open.file.not.found");
            this.ios.err.update(MessageFormat.format(msg, path, System.getProperty("user.dir")));
            return null;
        }
    }
}
