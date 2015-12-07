package main.gui;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import main.Main;
import main.Workbench;
import main.io.IoGui;

import java.net.URL;
import java.util.ResourceBundle;

public class WorkbenchController implements Initializable {

    @FXML
    private VBox favouriteCmdList;
    @FXML
    private ImageView currentImageView;
    @FXML
    private Accordion savedImagesView;
    @FXML
    private MenuBar mainMenuBar;
    @FXML
    private MainMenuBarController mainMenuBarController;
    @FXML
    private Label statusBarLbl;

    private Workbench wb = Main.wb;
    private IoGui ioGui = Main.ioGui;
    private EventHandler<MouseEvent> mouseClickFilter = e -> {
        if (e.getButton().equals(MouseButton.SECONDARY) ||
                e.getButton().equals(MouseButton.PRIMARY)) {
            e.consume();
        }
    };

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.wb.setOnRunning(event -> {
            Main.stage.getScene().setCursor(Cursor.WAIT);
            Main.stage.addEventFilter(MouseEvent.MOUSE_PRESSED, mouseClickFilter);
            Main.stage.addEventFilter(MouseEvent.MOUSE_RELEASED, mouseClickFilter);
        });

        this.wb.setOnSucceeded(event -> {
            this.wb.reset();
            Main.stage.getScene().setCursor(Cursor.DEFAULT);
            Main.stage.removeEventFilter(MouseEvent.MOUSE_PRESSED, mouseClickFilter);
            Main.stage.removeEventFilter(MouseEvent.MOUSE_RELEASED, mouseClickFilter);
        });

        this.ioGui.initialize();
        this.ioGui.out.bind(statusBarLbl.textProperty());
    }
}
