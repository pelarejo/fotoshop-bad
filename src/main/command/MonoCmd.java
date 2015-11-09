package main.command;

import main.image.ColorImage;
import main.image.ImageManager;

import java.awt.*;

public class MonoCmd extends CommandFactory.Command {

    public static final String TAG = "mono";

    public MonoCmd(String[] args) {
        super(args);
    }

    @Override
    public String getTag() {
        return TAG;
    }

    @Override
    public boolean execute() {
        // TODO: check if image exists
        ColorImage tmpImage = ImageManager.getInstance().getCurrentImage().getImage();
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
