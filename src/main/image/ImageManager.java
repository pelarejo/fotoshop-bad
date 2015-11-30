package main.image;

import com.sun.istack.internal.NotNull;
import main.command.CommandFactory;

import java.util.*;

/**
 * Class used to apply any image manipulation.
 * Handles cache and image cloning.
 * Image manipulation will never be applied to the current image
 * unless using edit() and undoEdit() methods
 */
public class ImageManager {
    private static ImageManager instance = new ImageManager();

    private EditableImage currentImage;
    private Map<String, EditableImage> cache;

    /**
     * Wrapper used to handle cache
     */
    public class EditableImage {
        private String tag;
        private ColorImage image;
        private Stack<CommandFactory.Command> cmdHistory;

        private EditableImage(String tag, ColorImage image) {
            this.tag = tag;
            this.image = image;
            this.cmdHistory = new Stack<>();
        }

        private EditableImage(String tag, EditableImage original) {
            this.tag = tag;
            this.image = new ColorImage(original.image);
            //noinspection unchecked
            this.cmdHistory = (Stack<CommandFactory.Command>) original.cmdHistory.clone();
        }

        public ColorImage getImage() {
            return new ColorImage(this.image);
        }

        public String getTag() {
            return this.tag;
        }

        public Collection<CommandFactory.Command> getHistory() {
            return Collections.unmodifiableCollection(this.cmdHistory);
        }
    }

    public static ImageManager getInstance() {
        return instance;
    }

    public ImageManager() {
        this.cache = new TreeMap<>();
    }

    public EditableImage getCurrentImage() {
        return this.currentImage;
    }

    public Map<String, EditableImage> getCache() {
        return Collections.unmodifiableMap(this.cache);
    }

    /**
     * Get a cached image
     *
     * @param tag of the image
     * @return a reference of the image
     */
    public EditableImage getCachedImage(String tag) {
        return this.cache.get(tag);
    }

    /**
     * Replace current image with another one
     *
     * @param tag the image tag
     * @param img the raw image to manipulate
     */
    public void newImage(String tag, ColorImage img) {
        /*
        *   TODO: Future revision should ask before override
        * if (this.currentImage != null) {
        *   //ASK
        *   }
        */
        this.currentImage = new EditableImage(tag, img);
    }

    /**
     * Replace current image with another one
     *
     * @param cached the cached image
     */
    public void newImage(EditableImage cached) {
        this.currentImage = new EditableImage(cached.tag, cached);
    }

    /**
     * Save the current image to cache
     *
     * @param tag the image tag
     */
    public EditableImage cacheImage(String tag, @NotNull EditableImage ei) {
        return this.cache.put(tag, new EditableImage(tag, ei));
    }

    /**
     * Edit the current image
     *
     * @param img the manipulated raw image
     * @param cmd the command used
     * @return true if the image was edited, false otherwise
     */
    public boolean edit(ColorImage img, CommandFactory.Command cmd) {
        /*  TODO: Future revision should trigger warning when pushing a non-undoable command
        *   if (!(cmd instanceof CommandFactory.UndoableCommand) && this.currentImage.cmdHistory.size() > 0) {
        *       System.out.print("Warning");
        *   }
        */
        this.currentImage.image = img;
        this.currentImage.cmdHistory.push(cmd);
        return true;
    }

    /**
     * Undo the current image's last filter
     * @param img the raw image unedited
     */
    public boolean undoEdit(ColorImage img) {
        //TODO: Future revision could manipulate a list for a redo function
        this.currentImage.image = img;
        this.currentImage.cmdHistory.pop();
        return true;
    }

    /**
     * Get the last current image command
     *
     * @return the last command
     * @throws EmptyStackException when there's no last command
     */
    public CommandFactory.Command lastCommand() {
        if (this.currentImage == null) {
            throw new EmptyStackException();
        }
        return this.currentImage.cmdHistory.peek();
    }
}
