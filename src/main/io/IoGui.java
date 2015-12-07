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
        Alert errAlrt = new Alert(Alert.AlertType.ERROR);
        errAlrt.setTitle(LocaleManager.getInstance().getUiBundle().getString("error"));
        this.err.setAlert(errAlrt);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(LocaleManager.getInstance().getUiBundle().getString("information"));
        this.alrt.setAlert(alert);
    }
}
