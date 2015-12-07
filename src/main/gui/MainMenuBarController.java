package main.gui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import main.Main;
import main.command.OpenCmd;
import main.command.UndoCmd;
import main.locale.LocaleManager;

import java.io.File;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class MainMenuBarController extends MenuBar implements Initializable {

    static final private Map<String, String[]> MENU_FIELDS = new LinkedHashMap<>();

    @FXML
    private MenuBar menuBar;

    static {
        // IDs are generated as follow: key.value
        // Order is respected by the LinkedHashMap
        MENU_FIELDS.put("file", new String[]{"open", "close"});
        MENU_FIELDS.put("edit", new String[]{"undo"});
        MENU_FIELDS.put("help", new String[]{"about"});
    }

    public MainMenuBarController() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for (Map.Entry<String, String[]> kv : MENU_FIELDS.entrySet()) {
            Menu m = new Menu(resources.getString("menu.item." + kv.getKey()));
            for (String subItems : kv.getValue()) {
                MenuItem mi = new MenuItem(resources.getString("menu.item." + kv.getKey() + "." + subItems));
                mi.setId(kv.getKey() + "." + subItems);
                mi.setOnAction((event -> onMenuBarAction(mi.getId(), event)));
                m.getItems().add(mi);
            }
            this.menuBar.getMenus().add(m);
        }
    }

    private void onMenuBarAction(String id, ActionEvent event) {
        // IDs are generated as follow: key.value
        switch (id) {
            case "file.open":
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Open Resource File");
                File img = fileChooser.showOpenDialog(this.menuBar.getScene().getWindow());
                if (img != null) Main.wb.runCommand(new OpenCmd(new String[]{img.getPath()}));
                break;
            case "file.close":
                Platform.exit();
                break;
            case "edit.undo":
                Main.wb.runCommand(new UndoCmd(null));
                break;
            case "help.about":
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(LocaleManager.getInstance().getUiBundle().getString("error"));
                alert.setHeaderText("Look, an Information Dialog");
                alert.setContentText("I have a great message for you!");

                alert.showAndWait();

                break;
            default:
                throw new RuntimeException();
        }
    }
}
