package main.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import main.Main;
import main.locale.LocaleManager;

public class FxmlHelper {

    public static FXMLLoader newLoader(String resource) {
        return new FXMLLoader(Main.class.getResource("/res/layouts/" + resource), LocaleManager.getInstance().getUiBundle());
    }

    public static void applyStylesheet(Scene scene, String resource) {
        scene.getStylesheets().add(Main.class.getResource("/res/stylesheets/" +  resource).toExternalForm());
    }
}
