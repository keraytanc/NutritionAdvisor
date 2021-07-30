package keray.ui;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

//the class creates the Parent UI that will shuffle between user interfaces
public class ParentUI {
    //parent UI which will contain all other
    private static BorderPane parentLayout;

    public ParentUI() {
        parentLayout = new BorderPane();
        parentLayout.setPrefSize(360, 640);
        parentLayout.setPadding(new Insets(15, 25, 20, 25));
    }

    public static BorderPane getParentLayout() {
        return parentLayout;
    }

    //method will return a Scene that will host the program to the the main class
    public Scene getScene() {
        PickUserUI pickUserUI = new PickUserUI();
        BorderPane mainDisplay = pickUserUI.getPickUserUI();
        parentLayout.setCenter(mainDisplay);
        return new Scene(parentLayout);
    }

    //method used to shuffle between different interfaces
    public static void setInsideParentLayout(Parent newLayout) {
        parentLayout.setCenter(newLayout);

    }

}
