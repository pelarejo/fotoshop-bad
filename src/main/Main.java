package main;

import main.locale.LocaleManager;

/**
 * This is the main class for the Fotoshop application
 * 
 * @author Richard Jones
 * @version 2013.09.05
 */
public class Main {

    public static void main(String[] args) {
        if (args.length > 0) {
            // Add language file setting loading here
            LocaleManager.getInstance().setLocale(args[0]);
        }
        new Editor().edit();
    }
}
