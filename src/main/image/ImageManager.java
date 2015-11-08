package main.image;

import main.command.CommandFactory;

import java.util.*;

public class ImageManager {
    private static ImageManager instance = new ImageManager();

    private ImageWrapper currentImage;
    private Map<String, ImageWrapper> cache;

    private class ImageWrapper {
        private ColorImage image;
        private Stack<CommandFactory.Undoable> cmdHistory;

        private ImageWrapper(ColorImage image) {
            this.image = image;
            this.cmdHistory = new Stack<>();
        }

        private ImageWrapper(ImageWrapper original) {
            this.image = original.image;
            //TODO: test this
            cmdHistory = original.cmdHistory;
        }
    }

    public static ImageManager getInstance() {
        return instance;
    }

    public ImageManager() {
        this.cache = new HashMap<>();
    }

    public ColorImage getCurrentImage() {
        return currentImage.image;
    }

    public void setCurrentImage(ColorImage img) {
        if (this.currentImage != null) {
            //todo: warning
        }
        this.currentImage = new ImageWrapper(img);
    }

    public void cacheImage(String tag) {
        if (currentImage == null)
            return;
        if (cache.containsKey(tag)) {
            //TODO: better
            System.out.print("Erasing image of the same name");
        }
        cache.put(tag, new ImageWrapper(currentImage));
    }

    public boolean edit(CommandFactory.Command cmd) {
        //TODO: better
        boolean undoable = cmd instanceof CommandFactory.Undoable;
        if (!undoable) {
            System.out.print("Trigger Warning");
            this.currentImage.cmdHistory.clear();
        }
        if (!cmd.execute()) {
            System.out.print("problem");
            return false;
        }
        if (undoable) {
            this.currentImage.cmdHistory.push((CommandFactory.Undoable) cmd);
        }
        return true;
    }

    public void undoEdit() {
        try {
            CommandFactory.Undoable cmd = this.currentImage.cmdHistory.pop();
            cmd.undo();
        } catch (EmptyStackException e) {
            // TODO: catch this
            System.out.print("No stack:" + e.getMessage());
        }
    }

    public void peekCommand() {

    }
}
