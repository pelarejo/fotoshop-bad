package main.command;

import main.Workbench;
import main.Parser;
import main.gui.ConsoleView;
import main.locale.LocaleManager;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.MessageFormat;

/**
 * Read a script of all supported command.
 * No extra work needed here to add a Command.
 */
public class ScriptCmd extends CommandFactory.Command {

    public static final String TAG = "script";

    private ConsoleView console = new ConsoleView();
    private int currentLine = 1;

    public ScriptCmd(String[] args) {
        super(args);
        if (args.length < 1) {
            throw new ArgumentException(LocaleManager.getInstance().getString("error.command.script.argument.none"));
        }
    }

    @Override
    public String getTag() {
        return TAG;
    }

    @Override
    public boolean execute() {
        String scriptName = this.args[0];
        Parser scriptParser = new Parser();
        try (FileInputStream inputStream = new FileInputStream(scriptName)) {
            scriptParser.setInputStream(inputStream);
            loop:
            while (Workbench.getState().equals(Workbench.PROGRAM_STATE.RUN)) {
                Parser.ParsedInput pi = scriptParser.getCommand();
                switch (pi.eState) {
                    case VALID:
                        if (!pi.cmd.execute()) {
                            scriptErrorMessage(LocaleManager.getInstance().getString("error.command.script.stopped"));
                            break loop;
                        }
                        break;
                    case INVALID:
                        String msg = LocaleManager.getInstance().getString("error.command.invalid");
                        msg = MessageFormat.format(msg, pi.what);
                        scriptErrorMessage(msg);
                        break loop;
                    case INVALID_ARG:
                        scriptErrorMessage(pi.what);
                        break loop;
                    case EMPTY:
                        break;
                }
                this.currentLine++;
            }
        } catch (FileNotFoundException ex) {
            String msg = LocaleManager.getInstance().getString("error.command.script.bad.file");
            this.console.update(MessageFormat.format(msg, scriptName));
            return false;
        } catch (IOException ex) {
            throw new RuntimeException("Panic: script barfed!");
        }
        return true;
    }

    private void scriptErrorMessage(String message) {
        String msg = "{0}:{1} --> {2}";
        msg = MessageFormat.format(msg, this.args[0], Integer.toString(this.currentLine), message);
        this.console.update(msg);
    }
}
