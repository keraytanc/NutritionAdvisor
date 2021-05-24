package keray.ui;

import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import keray.domain.FoodSearchResult;

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

        //creating new table
        TableView table = new TableView();
        //creating a table that will display search results and its columns
        TableColumn nameColumn = new TableColumn("Users");


        //defining data type for the column
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));


        //sizing columns
        nameColumn.prefWidthProperty().bind(table.widthProperty().add(-15.5));


        //adding ability to pick user by double click
        table.setRowFactory((rowFunction) -> {
            TableRow<String> row = new TableRow<>();
            row.setOnMouseClicked((click) -> {
                if (click.getClickCount() == 2 && !row.isEmpty()) {
                    //this.chosenFood = (Food) table.getSelectionModel().getSelectedItem();
                    //////////CREATE A FUNCTIONALITY
                }
            });
            return row;
        });

        //wrapping text inside columns
        nameColumn.setCellFactory((row) -> {
            TableCell<FoodSearchResult, String> cell = new TableCell<>();
            Text text = new Text();
            cell.setGraphic(text);
            text.wrappingWidthProperty().bind(nameColumn.widthProperty());
            text.textProperty().bind(cell.itemProperty());
            return cell;
        });


        //adding columns to the the table
        table.getColumns().add(nameColumn);

        return table;
    }


}
