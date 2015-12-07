package main.command;

import main.Main;
import main.Workbench;
import main.io.IoHelper;
import main.io.input.Parser;
import main.locale.LocaleManager;

import java.io.*;
import java.text.MessageFormat;
import java.util.NoSuchElementException;

/**
 * Read a script of all supported command.
 * No extra work needed here to add a Command.
 */
public class ScriptCmd extends CommandFactory.Command {

    public static final String TAG = "script";

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
            while (true) {
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
                    case IGNORE:
                        break;
                }
                this.currentLine++;
            }
        } catch (NoSuchElementException | EOFException e) {
            return true;
        } catch (FileNotFoundException ex) {
            String msg = LocaleManager.getInstance().getString("error.command.script.bad.file");
            this.ios.err.update(MessageFormat.format(msg, scriptName));
            return false;
        } catch (IOException ex) {
            throw new RuntimeException("Panic: script barfed!");
        }
        return true;
    }

    private void scriptErrorMessage(String message) {
        String msg = "{0}:{1} --> {2}";
        msg = MessageFormat.format(msg, this.args[0], Integer.toString(this.currentLine), message);
        this.ios.err.update(msg);
    }
}
