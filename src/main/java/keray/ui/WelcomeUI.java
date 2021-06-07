package keray.ui;

import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import keray.domain.FoodSearchResult;
import keray.logic.UsersTable;

//the class creates the mother UI that will shuffle between user interfaces as well as the welcome interface
public class WelcomeUI {

    public BorderPane getWelcomeUI() {

        BorderPane mainWelcomeUI = new BorderPane();

        //top menu
        VBox topMenu = new VBox();

        //command
        Label commandLabel = new Label("Pick user:");

        //new table to pick users
        TableView table = this.createUserTable();
        table.prefWidthProperty().bind(MotherUI.getMotherLayout().widthProperty().add(-50));
        table.prefHeightProperty().bind(MotherUI.getMotherLayout().heightProperty().multiply(0.35));

        //adding elements to the top menu
        topMenu.getChildren().addAll(commandLabel, table);

        //Button to add new user
        Button addUserButton = new Button("Add new user");
        addUserButton.setPrefHeight(40);
        addUserButton.prefWidthProperty().bind(MotherUI.getMotherLayout().widthProperty().add(-50));

        //adding all elements to the layout
        mainWelcomeUI.setTop(topMenu);
        mainWelcomeUI.setBottom(addUserButton);

        addUserButton.setOnAction((event) -> {
            AddUserUI addingUserLayout = new AddUserUI();
            VBox addingUser = addingUserLayout.addUserUI();
            MotherUI.getMotherLayout().getChildren().removeAll(topMenu, addUserButton);
            MotherUI.setInsideMotherLayout(addingUser);
        });
        return mainWelcomeUI;
    }

    public TableView createUserTable() {

        UsersTable usersTable = new UsersTable();
        TableView table = usersTable.createNewTable();

        return table;
    }


}
