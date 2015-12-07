package main.gui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.stage.FileChooser;
import main.Main;
import main.command.*;
import main.image.ImageManager;
import main.locale.LocaleManager;

import java.io.File;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainMenuBarController implements Initializable {

    static final private Map<String, String[]> MENU_FIELDS = new LinkedHashMap<>();

    @FXML
    private MenuBar menuBar;

    private FileChooser fileChooser;
    private ResourceBundle res;

    static {
        // IDs are generated as follow: key.value
        // Order is respected by the LinkedHashMap
        MENU_FIELDS.put("file", new String[]{"open", "save", "close"});
        MENU_FIELDS.put("edit", new String[]{"undo"});
        MENU_FIELDS.put("help", new String[]{"about"});
    }

    public MainMenuBarController() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.res = resources;
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

        this.fileChooser = new FileChooser();
        this.fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter(resources.getString("file.chooser.ext.all"), "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png"),
                new FileChooser.ExtensionFilter("BMP", "*.bmp")
        );
    }

    private void onMenuBarAction(String id, ActionEvent event) {
        // IDs are generated as follow: key.value
        switch (id) {
            case "file.open":
                this.fileChooser.setTitle(this.res.getString("file.chooser.title"));
                File img = this.fileChooser.showOpenDialog(this.menuBar.getScene().getWindow());
                if (img != null) Main.wb.runCommand(new OpenCmd(new String[]{img.getPath()}));
                break;
            case "file.save":
                String path = "C://";
                if (ImageManager.getInstance().getCurrentImage() != null) {
                    this.fileChooser.setTitle(this.res.getString("file.saver.title"));
                    img = this.fileChooser.showSaveDialog(this.menuBar.getScene().getWindow());
                    if (img != null) path = img.getAbsolutePath();
                }
                Main.wb.runCommand(new SaveCmd(new String[]{path}));
                break;
            case "file.close":
                Platform.exit();
                break;
            case "edit.undo":
                Main.wb.runCommand(new UndoCmd(null));
                break;
            case "help.about":
                Main.wb.runCommand(new HelpCmd(null));
                break;
            default:
                throw new RuntimeException("Unknown Command");
        }
    }
}
