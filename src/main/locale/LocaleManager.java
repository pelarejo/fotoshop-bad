package main.locale;

import com.sun.istack.internal.NotNull;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public class LocaleManager {
    private static LocaleManager instance = new LocaleManager();

    private final Map<String, Locale> supportedLocales = new HashMap<>();
    private Locale currentLocale = Locale.ENGLISH;
    private ResourceBundle bundleStrings = ResourceBundle.getBundle("res.strings.messages", currentLocale);

    public static LocaleManager getInstance() { return instance; }

    public boolean setLocale(String loc) {
        String locUpp = loc.toUpperCase();
        if (!this.supportedLocales.containsKey(locUpp)) {
            //Using properties here for consistency purposes. Will use english.
            String msg = getString("error.not.found.locale");
            //Todo: use GUi
            System.out.println(MessageFormat.format(msg, loc));
            return false;
        } else {
            this.currentLocale = this.supportedLocales.get(locUpp);
            this.bundleStrings = ResourceBundle.getBundle("res.strings.messages", this.currentLocale);
            return true;
        }
    }

    public Locale getLocale() {
        return this.currentLocale;
    }

    public String getString(@NotNull String key) {
        return this.bundleStrings.getString(key);
    }

    private LocaleManager() {
        this.supportedLocales.put("EN", Locale.ENGLISH);
        this.supportedLocales.put("FR", Locale.FRENCH);
    }
}