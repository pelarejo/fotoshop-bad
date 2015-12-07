package main.gui;

import javafx.beans.binding.Bindings;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import main.Main;
import main.Workbench;
import main.command.MonoCmd;
import main.command.PutCmd;
import main.command.Rot90Cmd;
import main.command.ScriptCmd;
import main.image.ImageManager;
import main.io.IoGui;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class WorkbenchController implements Initializable {
    //Top
    @FXML
    private MenuBar mainMenuBar;
    @FXML
    private MainMenuBarController mainMenuBarController;
    //Bottom
    @FXML
    private Label statusBarLbl;
    //Center
    @FXML
    public ScrollPane scrollImgHolder;
    @FXML
    private StackPane imgViewHolder;
    @FXML
    private ImageView currentImage;
    //Left panel
    @FXML
    private VBox cmdList;
    @FXML
    public Button rotBtn;
    @FXML
    public Button monoBtn;
    @FXML
    public Button scriptBtn;
    //Right panel
    @FXML
    public VBox rightPanel;
    @FXML
    private Button putBtn;
    @FXML
    private Accordion img_cache;

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
            Main.stage.getScene().setCursor(Cursor.DEFAULT);
            Main.stage.removeEventFilter(MouseEvent.MOUSE_PRESSED, mouseClickFilter);
            Main.stage.removeEventFilter(MouseEvent.MOUSE_RELEASED, mouseClickFilter);
            this.wb.reset();
        });

        this.wb.setOnFailed(event -> {
            Main.stage.getScene().setCursor(Cursor.DEFAULT);
            Main.stage.removeEventFilter(MouseEvent.MOUSE_PRESSED, mouseClickFilter);
            Main.stage.removeEventFilter(MouseEvent.MOUSE_RELEASED, mouseClickFilter);
            //noinspection ThrowableResultOfMethodCallIgnored
            Throwable exception = this.wb.getException();
            this.ioGui.err.update(exception.getMessage());
            this.wb.reset();
        });

        this.ioGui.initialize();
        this.ioGui.out.bind(statusBarLbl.textProperty());

        ImageManager.getInstance().bind(this.currentImage, this.img_cache);

        // Logic

        this.scriptBtn.setOnAction(event -> onScriptBtn(resources));
        this.monoBtn.setOnAction(event -> Main.wb.runCommand(new MonoCmd(null)));
        this.rotBtn.setOnAction(event -> Main.wb.runCommand(new Rot90Cmd(null)));
        this.putBtn.setOnMouseClicked(event -> onPutBtn(resources));

        // Design

        this.mainMenuBar.getStyleClass().add("menu");
        this.statusBarLbl.getStyleClass().add("status");

        this.imgViewHolder.minWidthProperty().bind(Bindings.createDoubleBinding(() ->
                scrollImgHolder.getViewportBounds().getWidth(), scrollImgHolder.viewportBoundsProperty()));

        this.imgViewHolder.minHeightProperty().bind(Bindings.createDoubleBinding(() ->
                scrollImgHolder.getViewportBounds().getHeight(), scrollImgHolder.viewportBoundsProperty()));

        this.cmdList.getStyleClass().add("panel");
        this.scriptBtn.getStyleClass().add("btn-script");
        this.rotBtn.getStyleClass().add("btn-rot");
        this.monoBtn.getStyleClass().add("btn-mono");
        this.scriptBtn.maxWidthProperty().bind(this.cmdList.widthProperty());
        this.rotBtn.maxWidthProperty().bind(this.cmdList.widthProperty());
        this.monoBtn.maxWidthProperty().bind(this.cmdList.widthProperty());

        this.rightPanel.getStyleClass().add("panel");
        this.putBtn.setMaxWidth(Double.MAX_VALUE);
    }

    private void onPutBtn(ResourceBundle resources) {
        TextInputDialog td = new TextInputDialog();
        td.setTitle(resources.getString("dialog.input.put"));
        td.setHeaderText("");
        Optional<String> s = td.showAndWait();
        if (s.isPresent() && !s.get().isEmpty()) {
            Main.wb.runCommand(new PutCmd(new String[]{s.get()}));
        }
    }

    private void onScriptBtn(ResourceBundle resources) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter txt;
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter(resources.getString("file.chooser.ext.all"), "*.*"),
                (txt = new FileChooser.ExtensionFilter("TEXT", "*.txt"))
        );
        fileChooser.setSelectedExtensionFilter(txt);
        fileChooser.setTitle(resources.getString("file.chooser.title.script"));
        File file = fileChooser.showOpenDialog(Main.stage.getScene().getWindow());
        if (file != null) Main.wb.runCommand(new ScriptCmd(new String[]{file.getAbsolutePath()}));
    }
}
