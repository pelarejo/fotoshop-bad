package main.command;

import main.gui.ConsoleView;
import main.image.ImageManager;
import main.locale.LocaleManager;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class LookCmd extends CommandFactory.Command {
    public final static String TAG = "look";

    private ConsoleView consoleView = new ConsoleView();
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
            this.consoleView.update(LocaleManager.getInstance().getString("command.look.no.img"));
            return true;
        }
        String msg = LocaleManager.getInstance().getString("command.look.current.img");
        this.consoleView.update(MessageFormat.format(msg, curImg.getTag()));
        lookHistory(curImg.getHistory());
        this.consoleView.update("\n");

        Map<String, ImageManager.EditableImage> cache = ImageManager.getInstance().getCache();
        if (cache.size() > 0) {
            this.consoleView.update(LocaleManager.getInstance().getString("command.look.cache.img"));
            msg = LocaleManager.getInstance().getString("command.look.cache.img.name");
            for (ImageManager.EditableImage img : cache.values()) {
                this.consoleView.update(MessageFormat.format(msg, img.getTag(), img.getImage().getOriginalPath()));
                lookHistory(img.getHistory());
                this.consoleView.update("\n");
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
            this.consoleView.update(LocaleManager.getInstance().getString("command.look.no.filters"));
        } else {
            this.consoleView.update(LocaleManager.getInstance().getString("command.look.filters"));
            this.consoleView.update(String.join(" ", out));
            this.consoleView.update("\n");
        }
    }
}
