package main.command;

import main.image.ImageManager;
import main.locale.LocaleManager;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/**
 * Print the current image and cached image details
 */
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
        ImageManager.EditableImage curImg = ImageManager.getInstance().getCurrentImage();
        if (curImg == null) {
            this.ios.err.update(LocaleManager.getInstance().getString("error.no.image"));
            return true;
        }
        this.ios.out.update(LocaleManager.getInstance().getString("command.look.current.img"));
        String msg = LocaleManager.getInstance().getString("command.look.img.name");
        this.ios.out.update(MessageFormat.format(msg, curImg.getTag(), curImg.getImage().getOriginalPath()));
        lookHistory(curImg.getHistory());
        this.ios.out.update("\n");

        Map<String, ImageManager.EditableImage> cache = ImageManager.getInstance().getCache();
        if (cache.size() > 0) {
            this.ios.out.update(LocaleManager.getInstance().getString("command.look.cache.img"));
            msg = LocaleManager.getInstance().getString("command.look.img.name");
            for (ImageManager.EditableImage img : cache.values()) {
                this.ios.out.update(MessageFormat.format(msg, img.getTag(), img.getImage().getOriginalPath()));
                lookHistory(img.getHistory());
                this.ios.out.update("\n");
            }
        }
        return true;
    }

    private void lookHistory(Collection<CommandFactory.Command> history) {
        ArrayList<String> out = new ArrayList<>();
        for (CommandFactory.Command cmd : history) {
            out.add(cmd.getTag());
        }
        if (out.size() == 0) {
            this.ios.out.update(LocaleManager.getInstance().getString("command.look.no.filters"));
        } else {
            this.ios.out.update(LocaleManager.getInstance().getString("command.look.filters"));
            this.ios.out.update(String.join(" ", out));
            this.ios.out.update("\n");
        }
    }
}
