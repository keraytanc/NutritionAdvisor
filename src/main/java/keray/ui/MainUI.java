package keray.ui;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import keray.domain.Person;

//class is a representing a main UI: buttons at the top and mechanism to switch between windows
public class MainUI {
    private static Person user;
    public BorderPane layout;

    //user object is the user selected in previous UI. All the actions and edition will be attributed to selected user
    public MainUI(Person person) {
        user = person;
        this.layout = new BorderPane();
        layout.setPrefSize(360, 640);
    }

    public BorderPane getMainUI() {

        //creating and formatting top menu
        HBox topMenu = new HBox();
        this.formatTopMenu(topMenu);

        //creating buttons for top menu
        Button userButton = new Button("User");
        Button foodButton = new Button("Food");
        this.formatAndAddButtonsToTheMenu(topMenu, userButton, foodButton);

        //creating other User UI's
        AddingFoodUI foodMenu = new AddingFoodUI();
        VBox foodMenuBox = foodMenu.getAddingFoodUI();
        UserUI userMenu = new UserUI();
        VBox userMenuBox = userMenu.getUserUI();

        //adding elements to the layout
        layout.setTop(topMenu);
        layout.setCenter(userMenuBox);


        //////////////////ANIMATING UI/////////////////////

        //attaching layout-switch action to each button
        userButton.setOnAction((event) -> layout.setCenter(userMenuBox));
        foodButton.setOnAction((event) -> {

            layout.setCenter(foodMenuBox);
            foodMenu.formatAndUpdateProgress();
        });

        return layout;
    }

    //////////////////////METHODS//////////////////////////////

    private void formatAndAddButtonsToTheMenu(HBox topMenu, Button button1, Button button2) {
        //format
        button1.prefWidthProperty().bind(topMenu.widthProperty().multiply(0.5));
        button2.prefWidthProperty().bind(topMenu.widthProperty().multiply(0.5));

        //add
        topMenu.getChildren().addAll(button1, button2);
    }

    private void formatTopMenu(HBox topMenu) {
        topMenu.setPrefSize(360, 30);
        topMenu.spacingProperty().bind(topMenu.widthProperty().multiply(0.05));
        topMenu.setAlignment(Pos.CENTER);
    }

    //method will provide the User to other layers of layout
    public static Person getUser() {
        return user;
    }

    //Method shows dialog in case of incorrect input
    public static void errorDialogBox() {

        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Incorrect input");
        ButtonType okButton = new ButtonType("OK");
        dialog.setContentText("Input is incorrect. Make sure the input is numerical");
        dialog.getDialogPane().getButtonTypes().add(okButton);

        dialog.showAndWait();
    }
}

