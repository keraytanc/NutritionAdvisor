package keray.ui;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Dialog;
import javafx.scene.layout.BorderPane;

public class ParentUI {

    //mother UI which will contain all other
    private static BorderPane parentLayout = new BorderPane();

    public ParentUI() {

        parentLayout = new BorderPane();
        //size for layouts
        parentLayout.setPrefSize(360, 640);
        parentLayout.setPadding(new Insets(15, 25, 20, 25));
    }

    public static BorderPane getParentLayout() {
        return parentLayout;
    }

    //method will return a Scene that will host the program to the the main class
    public Scene getScene() {
        PickUserUI welcome = new PickUserUI();
        BorderPane mainDisplay = welcome.getPickUserUI();
        parentLayout.setCenter(mainDisplay);
        Scene view = new Scene(parentLayout);

        return view;
    }

    //method used to shuffle between different interfaces
    public static void setInsideParentLayout(Parent newLayout) {
        parentLayout.setCenter(newLayout);

    }

}
