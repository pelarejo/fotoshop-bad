package main.io.ouput;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import main.locale.LocaleManager;

public class Dialog extends Output {

    @Override
    public void update(String txt) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(LocaleManager.getInstance().getUiBundle().getString("error"));
            alert.setHeaderText("Look, an Information Dialog");
            alert.setContentText("I have a great message for you!");

            alert.showAndWait();
        });
    }
}
