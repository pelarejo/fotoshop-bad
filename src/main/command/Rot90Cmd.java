package main.command;

import main.gui.ConsoleView;
import main.image.ColorImage;
import main.image.ImageManager;
import main.locale.LocaleManager;

import java.awt.*;

/**
 * Rotates the image 90°
 * This class should handle every rotation possible,
 * using args to pass the angle
 */
public class Rot90Cmd extends CommandFactory.UndoableCommand {

    public static final String TAG = "rot90";

    private ConsoleView consoleView = new ConsoleView();

    public Rot90Cmd(String[] args) {
        super(args);
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
        ColorImage img = ei.getImage();
        // R90 = [0 -1, 1 0] rotates around origin
        // (x,y) -> (-y,x)
        // then transate -> (height-y, x)
        int height = img.getHeight();
        int width = img.getWidth();
        ColorImage rotImage = new ColorImage(img.getOriginalPath(), height, width);
        for (int y = 0; y < height; y++) { // in the rotated image
            for (int x = 0; x < width; x++) {
                Color pix = img.getPixel(x, y);
                rotImage.setPixel(height - y - 1, x, pix);
            }
        }
        ImageManager.getInstance().edit(rotImage, this);
        return true;
    }

    @Override
    public boolean undo() {
        ColorImage img = ImageManager.getInstance().getCurrentImage().getImage();
        int height = img.getHeight();
        int width = img.getWidth();
        ColorImage rotImage = new ColorImage(img.getOriginalPath(), height, width);
        for (int y = 0; y < height; y++) { // in the rotated image
            for (int x = 0; x < width; x++) {
                Color pix = img.getPixel(x, y);
                rotImage.setPixel(y, width - x - 1, pix);
            }
        }
        ImageManager.getInstance().undoEdit(rotImage);
        return true;
    }
}
