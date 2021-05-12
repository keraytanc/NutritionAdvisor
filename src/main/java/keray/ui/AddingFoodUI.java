package keray.ui;

import keray.logic.SearchBox;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class AddingFoodUI {

    public Scene searchScreen() {

        //creating main layout
        VBox layout = new VBox();
        layout.setPrefSize(360, 640);
        layout.setPadding(new Insets(25, 25, 25, 25));

        //creating "Enter the food" label above search menu
        Label enterTheFoodLabel = new Label("Enter the food:");

        //creating top search menu
        HBox searchMenu = new HBox();
        searchMenu.setSpacing(10);
        searchMenu.prefWidthProperty().bind(layout.widthProperty());

        //TOP SEARCH MENU: creating elements
        TextField searchField = new TextField();
        searchField.prefWidthProperty().bind(layout.widthProperty().add(-120));
        searchField.setPrefHeight(20);
        searchField.setPromptText("Enter the food");

        Button searchButton = new Button("search");
        searchButton.setMinSize(60, 20);

        //TOP SEARCH MENU: adding the elements
        searchMenu.getChildren().add(searchField);
        searchMenu.getChildren().add(searchButton);
        searchMenu.setPadding(new Insets(0, 0, 20, 0));

        //creating food choice frame below top search menu
        //ListView<String> searchResultList = new ListView<>();
        TableView searchResultList = new SearchBox().returnSearchBox("tomato");
        searchResultList.prefWidthProperty().bind(layout.widthProperty());
        searchResultList.prefHeightProperty().bind(layout.heightProperty().multiply(0.35));

        //creating add Menu
        HBox addMenu = new HBox();
        addMenu.prefWidthProperty().bind(layout.widthProperty());
        addMenu.setSpacing(5);

        addMenu.setPadding(new Insets(5, 0, 5, 0));

        //ADD MENU: creating elements
        ListView<String> chosenFood = new ListView<>();

        chosenFood.setPrefHeight(20);
        chosenFood.prefWidthProperty().bind(layout.widthProperty().multiply(0.5));


        TextField enterFoodWeight = new TextField();
        enterFoodWeight.setPromptText("weight in grams");
        enterFoodWeight.setPrefHeight(20);
        enterFoodWeight.prefWidthProperty().bind(layout.widthProperty().multiply(0.5).add(-50));

        Button addButton = new Button("Add");
        addButton.setMinSize(50, 20);

        //ADD MENU: adding the elements to the add menu
        addMenu.getChildren().add(chosenFood);
        addMenu.getChildren().add(enterFoodWeight);
        addMenu.getChildren().add(addButton);

        //STATS MENU of the chosen food
        VBox statsMenu = new VBox();
        HBox bottomStatsMenu = new HBox();

        //STATS MENU: labels depicting statistics
        Label kcalLabel = new Label("Kcal: 0");
        kcalLabel.prefWidthProperty().bind(layout.widthProperty());
        kcalLabel.setAlignment(Pos.CENTER);

        Label proteinLabel = new Label("Proteins: 0");
        proteinLabel.prefWidthProperty().bind(layout.widthProperty().multiply(0.33));
        proteinLabel.setAlignment(Pos.CENTER);

        Label fatLabel = new Label("Fats: 0");
        fatLabel.prefWidthProperty().bind(layout.widthProperty().multiply(0.33));
        fatLabel.setAlignment(Pos.CENTER);

        Label carbLabel = new Label("Carbs: 0");
        carbLabel.prefWidthProperty().bind(layout.widthProperty().multiply(0.33));
        carbLabel.setAlignment(Pos.CENTER);

        //STATS MENU: separators
        Separator horizontalSeparator = new Separator();

        Separator verticalSeparator1 = new Separator();
        verticalSeparator1.setOrientation(Orientation.VERTICAL);

        Separator verticalSeparator2 = new Separator();
        verticalSeparator2.setOrientation(Orientation.VERTICAL);

        //STATS MENU: adding elements to the bottom stats menu
        bottomStatsMenu.getChildren().addAll(proteinLabel, verticalSeparator1, fatLabel, verticalSeparator2, carbLabel);

        //STATS MENU: adding all the elements
        statsMenu.getChildren().addAll(kcalLabel, horizontalSeparator, bottomStatsMenu);

        //adding everything to the layout
        layout.getChildren().add(enterTheFoodLabel);
        layout.getChildren().add(searchMenu);
        layout.getChildren().add(searchResultList);
        layout.getChildren().add(addMenu);
        layout.getChildren().add(statsMenu);

        //creating a scene
        Scene view = new Scene(layout);

        return view;
    }

}
