package main.command;

import main.image.ImageManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class LookCmd extends CommandFactory.Command {
    public final static String TAG = "look";

    /**
     * Always implement this constructor in inherited class.
     *
     * @param args is a list of arguments
     */
    public LookCmd(String[] args) {
        super(args);
    }

    @Override
    public String getTag() {
        return TAG;
    }

    @Override
    public boolean execute() {
        //TODO: check null
        ImageManager.EditableImage curImg = ImageManager.getInstance().getCurrentImage();
        System.out.println("The current image name and input file is " + curImg.getTag());
        lookHistory(curImg.getHistory());
        System.out.println();

        Map<String, ImageManager.EditableImage> cache = ImageManager.getInstance().getCache();
        if (cache.size() > 0) {
            System.out.println("Cached image include:");
            for (ImageManager.EditableImage img : cache.values()) {
                System.out.println("name: " + img.getTag() + "input file: " + img.getImage().getOriginalPath());
                lookHistory(img.getHistory());
                System.out.println();
            }
        }
        return true;
    }

    private void lookHistory(Collection<CommandFactory.Command> history) {
        System.out.print("Filters applied:\t");
        ArrayList<String> out = new ArrayList<>();
        for (CommandFactory.Command cmd : history) {
            out.add(cmd.getTag());
        }
        System.out.println(String.join(" ", out));
    }
}
