package main.io;

import javafx.scene.control.Alert;
import main.io.input.Input;
import main.io.ouput.Dialog;
import main.io.ouput.StatusBar;
import main.locale.LocaleManager;

public class IoGui extends IoHelper<Input, StatusBar, Dialog, Dialog> {
    public IoGui() {
        super(null, new StatusBar(), new Dialog(), new Dialog());
    }

    @Override
    public void initialize() {

        this.err.setI(() -> {
            Alert errAlert = new Alert(Alert.AlertType.ERROR);
            errAlert.setTitle(LocaleManager.getInstance().getUiBundle().getString("error"));
            errAlert.setHeaderText("");
            return errAlert;
        });

        this.alrt.setI(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(LocaleManager.getInstance().getUiBundle().getString("information"));
            alert.setHeaderText("");
            return alert;
        });
    }
}
