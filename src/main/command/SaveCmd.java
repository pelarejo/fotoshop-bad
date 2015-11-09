package main.command;

import main.image.ColorImage;
import main.image.ImageManager;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class SaveCmd extends CommandFactory.Command {

    public static final String TAG = "save";

    public SaveCmd(String[] args) {
        super(args);
        //TODO: create exception
        if (args.length < 1) throw new RuntimeException("Need one argument");
    }

    @Override
    public String getTag() {
        return TAG;
    }

    @Override
    public boolean execute() {
        ColorImage img = ImageManager.getInstance().getCurrentImage().getImage();
        if (img == null) {
            //TODO: error msg
            return false;
        }
        String outputName = this.args[0];
        try {
            File outputFile = new File(outputName);
            ImageIO.write(img, "jpg", outputFile);
            System.out.println("Image saved to " + outputName);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            //TODO: printHelp();
            return false;
        }
        return true;
    }
}
