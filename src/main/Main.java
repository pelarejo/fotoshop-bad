package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.gui.FXMLHelper;
import main.locale.LocaleManager;

/**
 * This is the main class for the Fotoshop application
 *
 * @author Richard Jones
 * @version 2013.09.05
 */
public class Main extends Application {

    private static final String APPLICATION_NAME = "Fotoshop";

    public static void main(String[] args) {
        if (args.length > 0) {
            // Add language file setting loading here
            LocaleManager.getInstance().setLocale(args[0]);
        }
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Workbench workbench = new Workbench();
        //workbench.edit();
        Parent rootLayout = FXMLHelper.newLoader("workbench.fxml").load();

        //Add light workbench logic here
        //BorderPane workbench = FXMLHelper.newLoader("workbench.fxml").load();
        //rootLayout.getChildren().add(btn);

        // Build the entire scene from the layout.
        Scene rootScene = new Scene(rootLayout);

        primaryStage.setMinHeight(200);
        primaryStage.setMinWidth(400);
        primaryStage.setTitle(APPLICATION_NAME);
        primaryStage.setScene(rootScene);
        primaryStage.show();
    }
}