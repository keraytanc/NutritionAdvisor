package keray.ui;

import javafx.scene.text.Text;
import keray.logic.SearchTable;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class AddingFoodUI {
    private VBox layout;

    public AddingFoodUI() {
        this.layout = new VBox();
    }

    public VBox getAddingFoodUI() {

        //creating main layout for the AddingFood window
        this.layout.setPrefSize(360, 610);
        this.layout.setPadding(new Insets(10, 0, 0, 0));

        //creating "Enter the food" label above search menu
        Label enterTheFoodLabel = new Label("Enter the food:");

        //creating top search menu
        HBox searchMenu = new HBox();
        searchMenu.setSpacing(10);
        searchMenu.prefWidthProperty().bind(layout.widthProperty());

        //TOP SEARCH MENU: creating elements
        TextField searchField = new TextField();
        searchField.prefWidthProperty().bind(layout.widthProperty().add(-60));
        searchField.setPrefHeight(20);
        searchField.setPromptText("Enter the food");

        Button searchButton = new Button("search");
        searchButton.setMinSize(60, 20);

        //TOP SEARCH MENU: adding the elements
        searchMenu.getChildren().add(searchField);
        searchMenu.getChildren().add(searchButton);
        searchMenu.setPadding(new Insets(0, 0, 20, 0));

        //creating food choice frame below top search menu
        SearchTable tableMechanism = new SearchTable();
        TableView searchResultList = tableMechanism.createNewTable();
        searchResultList.prefWidthProperty().bind(layout.widthProperty());
        searchResultList.prefHeightProperty().bind(layout.heightProperty().multiply(0.35));

        //creating add Menu
        HBox addMenu = new HBox();
        addMenu.prefWidthProperty().bind(layout.widthProperty());
        addMenu.setSpacing(5);

        addMenu.setPadding(new Insets(5, 0, 5, 0));

        //ADD MENU: creating elements
        ScrollPane chosenFoodView = new ScrollPane();
        chosenFoodView.setStyle("-fx-padding: 2;" +
                "-fx-border-style: solid inside;" +
                "-fx-border-width: 1;" +
                "-fx-border-color: #D3D3D3;" +
                "-fx-background-color: white;" +
                "-fx-effect: dropshadow(three-pass-box, gray, 2, 0, 0, 0)");
        chosenFoodView.setPrefHeight(40);
        chosenFoodView.prefWidthProperty().bind(layout.widthProperty().multiply(0.62));
        Text chosenFoodText = new Text();
        chosenFoodText.wrappingWidthProperty().bind(chosenFoodView.widthProperty().add(-20));
        chosenFoodView.setContent(chosenFoodText);


        TextArea enterFoodWeight = new TextArea();
        enterFoodWeight.setPromptText("grams");
        enterFoodWeight.setPrefHeight(40);
        enterFoodWeight.prefWidthProperty().bind(layout.widthProperty().multiply(0.38).add(-40));

        Button addButton = new Button("Add");
        addButton.setMinSize(40, 40);


        //ADD MENU: adding the elements to the add menu
        addMenu.getChildren().add(chosenFoodView);
        addMenu.getChildren().add(enterFoodWeight);
        addMenu.getChildren().add(addButton);

        //STATS MENU of the chosen food
        VBox statsMenu = new VBox();
        HBox bottomStatsMenu = new HBox();

        //FOOD DATA MENU: labels depicting statistics
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

        //FOOD DATA MENU: separators
        Separator horizontalSeparator = new Separator();

        Separator verticalSeparator1 = new Separator();
        verticalSeparator1.setOrientation(Orientation.VERTICAL);

        Separator verticalSeparator2 = new Separator();
        verticalSeparator2.setOrientation(Orientation.VERTICAL);

        //FOOD DATA MENU: adding elements to the bottom stats menu
        bottomStatsMenu.getChildren().addAll(proteinLabel, verticalSeparator1, fatLabel, verticalSeparator2, carbLabel);

        //FOOD DATA MENU: adding all the elements
        statsMenu.getChildren().addAll(kcalLabel, horizontalSeparator, bottomStatsMenu);

        //adding everything to the layout
        this.layout.getChildren().addAll(enterTheFoodLabel, searchMenu, searchResultList, addMenu, statsMenu);


        //adding action to a search food button
        searchButton.setOnAction((event) -> {

            //creating a new String according to a query written in a search field
            String newSearch = searchField.getText();

            //searching for a query and inputing data into table
            tableMechanism.updateSearchBoxResults(newSearch);

        });


        //adding action to picking up a food from list
        searchResultList.setOnMouseClicked((click) -> {
            if (tableMechanism.getChosenFood() != null) {

                Text newChosenFood = chosenFoodText;
                newChosenFood.setText(tableMechanism.getChosenFood().getDescription());
                chosenFoodView.setContent(newChosenFood);
            }
        });


        //adding action to the weight text field. It automatically show macronutrients of the chosen food
        enterFoodWeight.setOnKeyTyped((type) -> {
            try {
                double weight = 0;
                if (!enterFoodWeight.getText().isEmpty()) {
                    weight = Double.parseDouble(enterFoodWeight.getText());
                }

                double kcal = tableMechanism.getChosenFood().getKcal() * (weight/100);
                double proteins = tableMechanism.getChosenFood().getProteins() * (weight/100);
                double fats = tableMechanism.getChosenFood().getFats() * (weight/100);
                double carbs = tableMechanism.getChosenFood().getCarbs() * (weight/100);

                kcalLabel.setText("Kcal: " + Math.round(kcal));
                proteinLabel.setText("Proteins: " + Math.round(proteins));
                fatLabel.setText("Fats: " + Math.round(fats));
                carbLabel.setText("Carbs: " + Math.round(carbs));
            } catch (NumberFormatException e) {
            }
        });

        //adding chosen food to the list of eaten today
        addButton.setOnAction((event) -> {



            int eatenFoodsId = tableMechanism.getChosenFood().getId();
            int eatenFoodsWeight = 0;

            if (!enterFoodWeight.getText().isEmpty()) {
                eatenFoodsWeight = Integer.parseInt(enterFoodWeight.getText());

                //adding food to the user's list
                if (MainUI.getUser().getEatenToday().containsKey(eatenFoodsId)) {
                    int eatenAlready = MainUI.getUser().getEatenToday().get(eatenFoodsId);
                    int newAmount = eatenFoodsWeight + eatenAlready;

                    MainUI.getUser().getEatenToday().put(eatenFoodsId, newAmount);
                } else {
                    MainUI.getUser().getEatenToday().put(eatenFoodsId, eatenFoodsWeight);
                }
            }

        });

        //creating a Scene object and returning it
        return this.layout;
    }



    private void updateNutrients(double weight) {

    }


}
