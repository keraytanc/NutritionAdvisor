package keray.ui;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import keray.domain.Person;

//class is a representing a main UI: buttons at the top and mechanism to switch between windows
public class MainUI {
    private static Person user;

    public MainUI(Person person) {
        user = person;
    }

    public BorderPane getMainUI() {

        //creating main layout and top menu
        BorderPane layout = new BorderPane();
        HBox topMenu = new HBox();

        //size for layouts
        layout.setPrefSize(360, 640);

        topMenu.setPrefSize(360, 30);
        topMenu.spacingProperty().bind(topMenu.widthProperty().multiply(0.05));
        topMenu.setAlignment(Pos.CENTER);

        //creating buttons for top menu
        Button userButton = new Button("User");
        Button foodButton = new Button("Food");
        Button statsButton = new Button("Stats");

        //buttons size
        userButton.prefWidthProperty().bind(topMenu.widthProperty().multiply(0.3));
        foodButton.prefWidthProperty().bind(topMenu.widthProperty().multiply(0.3));
        statsButton.prefWidthProperty().bind(topMenu.widthProperty().multiply(0.3));

        //adding buttons to top menu
        topMenu.getChildren().addAll(userButton, foodButton, statsButton);

        //adding top menu to the layout
        layout.setTop(topMenu);

        //creating different UI object
        AddingFoodUI foodMenu = new AddingFoodUI();
        VBox foodMenuBox = foodMenu.getAddingFoodUI();
        UserUI userMenu = new UserUI();
        VBox userMenuBox = userMenu.getUserUI();
        StatsUI statsMenu = new StatsUI();
        VBox statsMenuBox = statsMenu.getStatsUI();

        //attaching window-switch action to each button
        userButton.setOnAction((event) -> layout.setCenter(userMenuBox));
        foodButton.setOnAction((event) -> layout.setCenter(foodMenuBox));
        statsButton.setOnAction((event) -> layout.setCenter(statsMenuBox));

        layout.setCenter(userMenuBox);

        //Creating new Scene and returning it
        return layout;
    }

    //method will provide the User to other layers of layout
    public static Person getUser() {
        return user;
    }
}

