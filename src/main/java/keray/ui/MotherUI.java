package keray.ui;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class MotherUI {

    //mother UI which will contain all other
    private static BorderPane motherLayout = new BorderPane();

    public MotherUI() {

        motherLayout = new BorderPane();
        //size for layouts
        motherLayout.setPrefSize(360, 640);
        motherLayout.setPadding(new Insets(15, 25, 20, 25));
    }

    public static BorderPane getMotherLayout() {
        return motherLayout;
    }

    //method will return a Scene that will host the program to the the main class
    public Scene getScene() {
        WelcomeUI welcome = new WelcomeUI();
        BorderPane mainDisplay = welcome.getWelcomeUI();
        motherLayout.setCenter(mainDisplay);
        Scene view = new Scene(motherLayout);

        return view;
    }

    //method used to shuffle between different interfaces
    public static void setInsideMotherLayout(Parent newLayout) {
        motherLayout.setCenter(newLayout);

    }

}
