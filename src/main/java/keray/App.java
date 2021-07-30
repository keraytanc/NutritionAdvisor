package keray;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import keray.ui.ParentUI;

/**
 * JavaFX App
 */
//Main class of the application
public class App extends Application {

    @Override
    public void start(Stage stage) {

        ParentUI parentUI = new ParentUI();
        Scene mainScene = parentUI.getScene();
        stage.setScene(mainScene);

        stage.show();

    }
    public static void main(String[] args) {
        launch();
    }

}