package main.image;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Extends standard BufferedImage class with convenience functions
 * for getting/setting pixel values using the standard Color class
 * and converting the raster to a standard 24-bit direct colour format.
 * <p>
 * Based on class OFImage described in chapter 11 of the book
 * "Objects First with Java" by David J Barnes and Michael Kolling
 * (from 2nd edition onwards).
 *
 * @author Michael Kolling, David J. Barnes and Peter Kenny
 * @version 1.0
 */

public class ColorImage extends BufferedImage {

    String originalPath;

    /**
     * Create a main.image.ColorImage copied from a BufferedImage
     * Convert to 24-bit direct colour
     *
     * @param image The image to copy
     */
    protected ColorImage(BufferedImage image) {
        super(image.getWidth(), image.getHeight(), TYPE_INT_RGB);
        int width = image.getWidth();
        int height = image.getHeight();
        for (int y = 0; y < height; y++)
            for (int x = 0; x < width; x++)
                setRGB(x, y, image.getRGB(x, y));
    }

    /**
     * Copy constructor
     * @param image image to copy
     */
    public ColorImage(ColorImage image) {
        this((BufferedImage) image);
        this.originalPath = image.getOriginalPath();
    }

    public ColorImage(String path, BufferedImage image) {
        this(image);
        this.originalPath = path;
    }

    /**
     * Create a main.image.ColorImage with specified size and 24-bit direct colour
     *
     * @param width  The width of the image
     * @param height The height of the image
     */
    public ColorImage(String path, int width, int height) {
        super(width, height, TYPE_INT_RGB);
        this.originalPath = path;
    }

    /**
     * Set a given pixel of this image to a specified color.
     * The color is represented as an (r,g,b) value.
     *
     * @param x   The x position of the pixel
     * @param y   The y position of the pixel
     * @param col The color of the pixel
     */
    public void setPixel(int x, int y, Color col) {
        int pixel = col.getRGB();
        setRGB(x, y, pixel);
    }

    /**
     * Get the color value at a specified pixel position
     *
     * @param x The x position of the pixel
     * @param y The y position of the pixel
     * @return The color of the pixel at the given position
     */
    public Color getPixel(int x, int y) {
        int pixel = getRGB(x, y);
        return new Color(pixel);
    }

    public String getOriginalPath() {
        return this.originalPath;
    }
}
