package keray;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.converter.LocalDateStringConverter;
import keray.ui.MotherUI;

import java.time.LocalDate;
import java.util.Date;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {

        MotherUI motherUI = new MotherUI();
        Scene mainScene = motherUI.getScene();
        stage.setScene(mainScene);

        stage.show();

    }
    public static void main(String[] args) {
        launch();
    }

}