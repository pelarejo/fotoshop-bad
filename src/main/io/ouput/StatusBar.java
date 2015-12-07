package main.io.ouput;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.util.Duration;

public class StatusBar extends Output {

    private Timeline resetTimeline;
    private StringProperty txtPty;

    public StatusBar() {
        this.txtPty = new SimpleStringProperty();
        this.resetTimeline = new Timeline(new KeyFrame(Duration.millis(2500), a -> this.txtPty.set("")));
    }

    @Override
    public void update(String txt) {
        Platform.runLater(() -> {
            txtPty.set(txt);
            this.resetTimeline.stop();
            this.resetTimeline.play();
        });
    }

    public void bind(StringProperty txt) {
        txt.bind(txtPty);
    }
}
