package main.gui;

import javafx.fxml.FXMLLoader;
import main.Main;
import main.locale.LocaleManager;

public class FxmlHelper {

    public static FXMLLoader newLoader(String resource) {
        return new FXMLLoader(Main.class.getResource("/res/layouts/" + resource), LocaleManager.getInstance().getUiBundle());
    }
}
