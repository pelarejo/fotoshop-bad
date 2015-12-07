package main.io.ouput;

import javafx.application.Platform;
import javafx.scene.control.Alert;

public class Dialog extends Output {

    private Initializer i;

    public interface Initializer {
        Alert initialize();
    }

    @Override
    public void update(String txt) {
        if (this.i == null) throw new RuntimeException("Init Not Set");
        Platform.runLater(() -> {
            Alert alert = this.i.initialize();
            alert.setContentText(txt);
            alert.show();
        });
    }

    public void setI(Initializer i) {
        this.i = i;
    }
}
