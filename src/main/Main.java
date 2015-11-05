package main;

import main.locale.LocaleManager;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * This is the main class for the Fotoshop application
 * 
 * @author Richard Jones
 * @version 2013.09.05
 */
public class Main {

    public static void main(String[] args) {
        if (args.length > 0) {
            LocaleManager.getInstance().setLocale(args[0]);
        }
        new Editor().edit();
    }
}
