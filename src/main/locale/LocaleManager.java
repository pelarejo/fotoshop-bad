package main.locale;

import com.sun.istack.internal.NotNull;
import main.io.ouput.Console;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Singleton use to recover locale specific properties
 */
public class LocaleManager {
    private static LocaleManager instance = new LocaleManager();

    private static final String MESSAGES_PATH = "res.strings.messages";
    private static final String UI_PATH = "res.strings.ui";
    private static final Map<String, Locale> SUPPORTED_LOCALES = new HashMap<>();
    static {
        SUPPORTED_LOCALES.put("EN", Locale.ENGLISH);
        SUPPORTED_LOCALES.put("FR", Locale.FRENCH);
    }

    private Locale currentLocale = Locale.ENGLISH;
    private ResourceBundle bundleStrings = ResourceBundle.getBundle(MESSAGES_PATH, currentLocale);
    private ResourceBundle uiStrings = ResourceBundle.getBundle(UI_PATH, currentLocale);

    public static LocaleManager getInstance() { return instance; }

    public String getString(@NotNull String key) {
        return this.bundleStrings.getString(key);
    }

    public Locale getLocale() {
        return this.currentLocale;
    }

    public ResourceBundle getUiBundle() {
        return this.uiStrings;
    }

    public boolean setLocale(String loc) {
        String locUpp = loc.toUpperCase();
        if (!SUPPORTED_LOCALES.containsKey(locUpp)) {
            //Using properties here for consistency purposes. Will use english.
            String msg = getString("error.not.found.locale");
            new Console().update(MessageFormat.format(msg, loc));
            return false;
        } else {
            this.currentLocale = SUPPORTED_LOCALES.get(locUpp);
            this.bundleStrings = ResourceBundle.getBundle(MESSAGES_PATH, this.currentLocale);
            this.uiStrings = ResourceBundle.getBundle(UI_PATH, currentLocale);
            return true;
        }
    }

    private LocaleManager() {}
}