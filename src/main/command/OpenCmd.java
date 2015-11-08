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
    public boolean execute() {
        if (this.args.length < 1) {
            // if there is no second word, we don't know what to open...
            System.out.println("open what?");
            return false;
        }
        String inputName = this.args[0];
        ColorImage img = loadImage(inputName);
        if (img != null) {
            ImageManager.getInstance().setCurrentImage(img);
            //TODO: There must be better
            System.out.println("Loaded " + img.getName());
        }
        return true;
    }

    /**
     * Load an image from a file.
     *
     * @param name The name of the image file
     * @return a main.image.ColorImage containing the image
     */
    private ColorImage loadImage(String name) {
        ColorImage img = null;
        try {
            img = new ColorImage(name, ImageIO.read(new File(name)));
        } catch (IOException e) {
            System.out.println("Cannot find image file, " + name);
            System.out.println("cwd is " + System.getProperty("user.dir"));
        }
        return img;
    }
}
