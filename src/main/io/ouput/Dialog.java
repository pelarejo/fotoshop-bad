package main.io.ouput;

import javafx.application.Platform;
import javafx.scene.control.Alert;

public class Dialog extends Output {

    private Alert alert;

    @Override
    public void update(String txt) {
        if (this.alert == null) throw new RuntimeException("Alert Not Set");
        Platform.runLater(() -> {
            alert.setHeaderText(txt);
            alert.showAndWait();
        });
    }

    public void setAlert(Alert alert) {
        this.alert = alert;
    }
}
