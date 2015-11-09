package main.command;

import main.image.ColorImage;
import main.image.ImageManager;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class OpenCmd extends CommandFactory.Command {

    public static final String TAG = "open";

    public OpenCmd(String[] args) {
        super(args);
    }

    @Override
    public String getTag() {
        return TAG;
    }


    @Override
    public boolean execute() {
        if (this.args.length < 1) {
            // if there is no second word, we don't know what to open...
            System.out.println("open what?");
            return false;
        }
        String path = this.args[0];
        ColorImage img = loadImage(path);
        if (img != null) {
            ImageManager.getInstance().newImage(path, img);
            //TODO: There must be better
            System.out.println("Loaded " + img.getOriginalPath());
        }
        return true;
    }

    /**
     * Load an image from a file.
     *
     * @param path The path of the image file
     * @return a main.image.ColorImage containing the image
     */
    private ColorImage loadImage(String path) {
        ColorImage img = null;
        try {
            img = new ColorImage(path, ImageIO.read(new File(path)));
        } catch (IOException e) {
            System.out.println("Cannot find image file, " + path);
            System.out.println("cwd is " + System.getProperty("user.dir"));
        }
        return img;
    }
}
