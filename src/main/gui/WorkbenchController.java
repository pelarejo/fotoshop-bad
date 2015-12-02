package main.gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import main.locale.LocaleManager;

import javafx.event.ActionEvent;

import java.io.File;
import java.net.URL;
import java.util.*;

public class WorkbenchController implements Initializable {

    @FXML private VBox favouriteCmdList;
    @FXML private ImageView currentImageView;
    @FXML private Accordion savedImagesView;
    @FXML private MenuBar menuBarView;

    static final private Map<String, String[]> MENU_FIELDS = new LinkedHashMap<>();
    static {
        // IDs are generated as follow: key.value
        MENU_FIELDS.put("file", new String[] {"open", "close"});
        MENU_FIELDS.put("edit", new String[] {"undo"});
        MENU_FIELDS.put("help", new String[] {"about"});
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for (Map.Entry<String, String[]> kv : MENU_FIELDS.entrySet()) {
            Menu m = new Menu(resources.getString("menu.item." + kv.getKey()));
            for (String subItems : kv.getValue()) {
                MenuItem mi = new MenuItem(resources.getString("menu.item." + kv.getKey() + "." + subItems));
                mi.setId(kv.getKey() + "." + subItems);
                mi.setOnAction((event -> {
                    onMenuBarAction(mi.getId(), event);
                }));
                m.getItems().add(mi);
            }
            this.menuBarView.getMenus().add(m);
        }
    }

    private void onMenuBarAction(String id, ActionEvent event) {
        // IDs are generated as follow: key.value
        switch (id) {
            case "file.open":
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Open Resource File");
                File picture = fileChooser.showOpenDialog(this.menuBarView.getScene().getWindow());
                break;
            case "file.close":
                System.exit(0);
                break;
            default:
                throw new RuntimeException();
        }
    }
}
