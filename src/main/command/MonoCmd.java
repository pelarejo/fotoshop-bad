package main.command;

import main.gui.ConsoleView;
import main.image.ColorImage;
import main.image.ImageManager;
import main.locale.LocaleManager;

import java.awt.*;

/**
 * Set the image as black and white
 */
public class MonoCmd extends CommandFactory.Command {

    public static final String TAG = "mono";

    private ConsoleView consoleView = new ConsoleView();

    public MonoCmd(String[] args) {
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
        ColorImage tmpImage = ei.getImage();
        //Graphics2D g2 = tmpImage.createGraphics();
        int height = tmpImage.getHeight();
        int width = tmpImage.getWidth();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color pix = tmpImage.getPixel(x, y);
                int lum = (int) Math.round(0.299 * pix.getRed()
                        + 0.587 * pix.getGreen()
                        + 0.114 * pix.getBlue());
                tmpImage.setPixel(x, y, new Color(lum, lum, lum));
            }
        }
        ImageManager.getInstance().edit(tmpImage, this);
        return true;
    }
}
