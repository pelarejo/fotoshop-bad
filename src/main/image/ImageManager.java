package main.image;

import main.command.CommandFactory;

import java.util.*;

public class ImageManager {
    private static ImageManager instance = new ImageManager();

    private EditableImage currentImage;
    private Map<String, EditableImage> cache;

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
            this.image = original.image;
            //TODO: test this
            this.cmdHistory = original.cmdHistory;
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
        this.cache = new HashMap<>();
    }

    public EditableImage getCurrentImage() {
        return this.currentImage;
    }

    public Map<String, EditableImage> getCache() {
        return Collections.unmodifiableMap(this.cache);
    }

    /**
     * Replace current image with another one
     *
     * @param tag
     * @param img
     */
    public void newImage(String tag, ColorImage img) {
        if (this.currentImage != null) {
            //todo: warning overriding
        }
        this.currentImage = new EditableImage(tag, img);
    }

    /**
     * Save the current image to cache
     *
     * @param tag
     */
    public void cacheImage(String tag) {
        if (this.currentImage == null)
            return;
        if (this.cache.containsKey(tag)) {
            //TODO: better
            System.out.print("Erasing image of the same originalPath");
        }
        this.cache.put(tag, new EditableImage(tag, currentImage));
    }

    /**
     * Load a cache image as current image
     *
     * @param tag
     * @return
     */
    public boolean loadCacheImage(String tag) {
        EditableImage cachedImg = this.cache.get(tag);
        if (cachedImg == null) return false;
        this.currentImage = new EditableImage(tag, cachedImg);
        return true;
    }

    /**
     * Edit the current image
     *
     * @param img
     * @param cmd
     * @return
     */
    public boolean edit(ColorImage img, CommandFactory.Command cmd) {
        //TODO: better
        if (!(cmd instanceof CommandFactory.UndoableCommand) && this.currentImage.cmdHistory.size() > 0) {
            System.out.print("Trigger Warning");
        }
        this.currentImage.image = img;
        this.currentImage.cmdHistory.push(cmd);
        return true;
    }

    /**
     * Undo the current image's last filter
     */
    public void undoEdit() {
        try {
            CommandFactory.Command cmd = this.currentImage.cmdHistory.pop();
            if (cmd instanceof CommandFactory.UndoableCommand) {
                ((CommandFactory.UndoableCommand) cmd).undo();
            }
        } catch (EmptyStackException e) {
            // TODO: catch this
            System.out.print("No stack:" + e.getMessage());
        }
    }
}
