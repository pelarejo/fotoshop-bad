package main.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import main.Main;
import main.command.GetCmd;

import java.io.IOException;

public class ImageCache extends TitledPane {
    @FXML
    private ImageView img;
    @FXML
    private Label lbl;

    public ImageCache(String tag, Image img) {
        FXMLLoader fxmlLoader = FxmlHelper.newLoader("image_cache.fxml");
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        setText(tag);
        setDetails("Not done yet");
        setImg(img);

        this.img.setOnMouseClicked(event -> {
            Main.wb.runCommand(new GetCmd(new String[]{getText()}));
        });
    }

    public void setDetails(String tag) {
        this.lbl.setText(tag);
    }

    public void setImg(Image img) {
        this.img.setImage(img);
    }
}
