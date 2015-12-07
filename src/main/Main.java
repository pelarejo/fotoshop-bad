package main;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.gui.FxmlHelper;
import main.io.IoCli;
import main.io.IoGui;
import main.io.input.ICommand;
import main.io.ouput.Dialog;
import main.locale.LocaleManager;

/**
 * This is the main class for the Fotoshop application
 *
 * @author Richard Jones
 * @version 2013.09.05
 */
public class Main extends Application {
    public static Workbench wb;
    public static IoGui ioGui;
    public static Stage stage;

    private static final String APPLICATION_NAME = "Fotoshop";

    public static void main(String[] args) {
        boolean cliApp = false;

        for (String arg : args) {
            if (arg.equals("-c")) cliApp = true;
            else LocaleManager.getInstance().setLocale(arg);
        }

        if (cliApp) {
            wb = new Workbench(new IoCli());
            wb.run();
        } else {
            ioGui = new IoGui();
            wb = new Workbench(ioGui);
            launch(args);
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        Parent rootLayout = FxmlHelper.newLoader("workbench.fxml").load();
        Scene rootScene = new Scene(rootLayout);

        primaryStage.setMinHeight(200);
        primaryStage.setMinWidth(400);
        primaryStage.setTitle(APPLICATION_NAME);
        primaryStage.setScene(rootScene);
        primaryStage.show();
    }
}