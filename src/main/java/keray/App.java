package keray;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import keray.ui.MotherUI;


/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {


        //testing layout
        MotherUI motherUI = new MotherUI();
        Scene mainScene = motherUI.getScene();
        stage.setScene(mainScene);

        stage.show();

    }
    public static void main(String[] args) {
        launch();
    }

}