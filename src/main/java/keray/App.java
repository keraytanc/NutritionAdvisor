package keray;

import keray.ui.AddingFoodUI;

import javafx.application.Application;
import javafx.stage.Stage;



/**
 * JavaFX App
 */
public class App extends Application {


    @Override
    public void start(Stage stage) {

        //testing layout
        AddingFoodUI searchLayout = new AddingFoodUI();

        stage.setScene(searchLayout.searchScreen());

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}