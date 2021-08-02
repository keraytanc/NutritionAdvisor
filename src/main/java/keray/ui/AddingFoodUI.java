package keray.ui;

import javafx.scene.text.Text;
import keray.domain.EatenFoodData;
import keray.domain.Food;
import keray.logic.DbUsers;
import keray.logic.SearchTable;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import keray.logic.EatenFoodTable;

public class AddingFoodUI {
    private final VBox layout;
    public final HBox progressMenu;

    public AddingFoodUI() {
        this.layout = new VBox();

        this.progressMenu = new HBox();
    }

    public VBox getAddingFoodUI() {


        this.formatLayout();


        //creating "Enter the food" label above search menu
        Label enterTheFoodLabel = new Label("Enter the food:");


        //creating top search menu
        HBox searchMenu = new HBox();

        TextField searchField = new TextField();
        Button searchButton = new Button("search");

        this.formatSearchMenuAndAddNodes(searchMenu, searchField, searchButton);


        //creating food choice frame below top search menu
        SearchTable tableMechanism = new SearchTable();
        TableView<Food> searchResultsList = tableMechanism.createNewTable();

        this.formatSearchResultsList(searchResultsList);


        //creating add Menu
        HBox addMenu = new HBox();

        ScrollPane chosenFoodView = new ScrollPane();
        Text chosenFoodText = new Text();
        TextArea enterFoodWeight = new TextArea();
        Button addButton = new Button("Add");

        this.formatAddMenuAndAddNodes(addMenu, chosenFoodView, chosenFoodText, enterFoodWeight, addButton);


        //STATS MENU of the chosen food
        VBox statsMenu = new VBox();

        Label kcalLabel = new Label("Kcal: 0");
        Label proteinLabel = new Label("Proteins: 0");
        Label fatLabel = new Label("Fats: 0");
        Label carbLabel = new Label("Carbs: 0");

        this.formatStatsMenuAndAddNodes(statsMenu, kcalLabel, proteinLabel, fatLabel, carbLabel);


        //Label describing eaten food table
        Label eatenFoodLabel = new Label("Foods eaten today:");
        eatenFoodLabel.setPadding(new Insets(10, 0, 0, 0));


        //Menu managing food eaten today
        HBox eatenFoodsMenu = new HBox();

        EatenFoodTable eatenFoodTableObject = new EatenFoodTable();
        Button deleteFromEatenButton = new Button();

        this.formatEatenFoodsMenuAndAddNodes(eatenFoodsMenu, eatenFoodTableObject, deleteFromEatenButton);


        //Updating progress menu with up-to-date info
        this.formatAndUpdateProgress();


        //adding everything to the layout
        this.layout.getChildren().addAll(enterTheFoodLabel, searchMenu, searchResultsList, addMenu, statsMenu,
                eatenFoodLabel, eatenFoodsMenu, progressMenu);


        ////////////////ANIMATING UI/////////////////////////////////////////


        //adding action to a search food button
        searchButton.setOnAction((event) -> {

            //creating a new String according to a query written in a search field
            String newSearch = searchField.getText();

            //searching for a query and inputting data into table
            tableMechanism.updateSearchBoxResults(newSearch);

        });


        //adding action to picking up a food from list
        searchResultsList.setOnMouseClicked((click) -> {
            if (tableMechanism.getChosenFood() != null) {

                chosenFoodText.setText(tableMechanism.getChosenFood().getDescription());
                chosenFoodView.setContent(chosenFoodText);
            }
        });


        //adding action to the weight text field. It automatically show macronutrients of the chosen food
        enterFoodWeight.setOnKeyTyped((type) -> {
            try {
                double weight;
                try {
                    weight = Double.parseDouble(enterFoodWeight.getText());
                } catch (Exception e) {
                    weight = 0;
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
                e.printStackTrace();
            }
        });

        //adding chosen food to the list of eaten today
        addButton.setOnAction((event) -> {

            int eatenFoodsId = tableMechanism.getChosenFood().getId();

            try {
                int eatenFoodsWeight = Integer.parseInt(enterFoodWeight.getText());

                //adding food to the list
                MainUI.getUser().addEatenFood(eatenFoodsId, eatenFoodsWeight);
                DbUsers.updateEatenFoodsInDb(MainUI.getUser(), MainUI.getUser().listAsAString());
                eatenFoodTableObject.updateEatenFoodTable();
                this.formatAndUpdateProgress();
            } catch (Exception e) {
                MainUI.errorDialogBox();
            }

        });

        //deleting food from the list of eaten today
        deleteFromEatenButton.setOnAction((event) -> {
            if (eatenFoodTableObject.getFoodToDelete() != null) {
                MainUI.getUser().deleteFoodFromList(eatenFoodTableObject.getFoodToDelete());
                DbUsers.updateEatenFoodsInDb(MainUI.getUser(), MainUI.getUser().listAsAString());
                eatenFoodTableObject.updateEatenFoodTable();
                eatenFoodTableObject.setFoodToDeleteAsNull();
                this.formatAndUpdateProgress();

            }
        });

        //creating a Scene object and returning it
        return this.layout;
    }

    ////////////METHODS///////////////////////////////////////////

    private void formatEatenFoodsMenuAndAddNodes(HBox eatenFoodsMenu, EatenFoodTable eatenFoodTableObject,
                                                 Button deleteFromEatenButton) {

        //formatting menu box
        eatenFoodsMenu.setSpacing(10);
        eatenFoodsMenu.setPadding(new Insets(0, 0, 10, 0));

        //formatting table for the eaten foods
        TableView<EatenFoodData> eatenFoodTable = eatenFoodTableObject.createNewTable();
        eatenFoodTable.prefWidthProperty().bind(layout.widthProperty().add(-60));
        eatenFoodTable.prefHeightProperty().bind(layout.heightProperty().multiply(0.25));
        eatenFoodTableObject.updateEatenFoodTable();

        //formatting button
        deleteFromEatenButton.prefHeightProperty().bind(eatenFoodTable.heightProperty());
        deleteFromEatenButton.setPrefWidth(50);

        Label textOnButton = new Label("DELETE");
        textOnButton.setWrapText(true);
        textOnButton.setMinWidth(1);
        textOnButton.setMaxWidth(1);
        deleteFromEatenButton.setGraphic(textOnButton);

        //Adding nodes
        eatenFoodsMenu.getChildren().addAll(eatenFoodTable, deleteFromEatenButton);

    }

    private void formatStatsMenuAndAddNodes(VBox statsMenu, Label kcalLabel,
                                            Label proteinLabel, Label fatLabel, Label carbLabel) {

        //formatting labels
        kcalLabel.prefWidthProperty().bind(layout.widthProperty());
        kcalLabel.setAlignment(Pos.CENTER);

        proteinLabel.prefWidthProperty().bind(layout.widthProperty().multiply(0.33));
        proteinLabel.setAlignment(Pos.CENTER);

        fatLabel.prefWidthProperty().bind(layout.widthProperty().multiply(0.33));
        fatLabel.setAlignment(Pos.CENTER);

        carbLabel.prefWidthProperty().bind(layout.widthProperty().multiply(0.33));
        carbLabel.setAlignment(Pos.CENTER);

        //creating separators
        Separator horizontalSeparator = new Separator();

        Separator verticalSeparator1 = new Separator();
        verticalSeparator1.setOrientation(Orientation.VERTICAL);

        Separator verticalSeparator2 = new Separator();
        verticalSeparator2.setOrientation(Orientation.VERTICAL);

        //Adding nodes together
        HBox bottomOfTheStatsMenu = new HBox();
        bottomOfTheStatsMenu.getChildren().addAll(proteinLabel, verticalSeparator1, fatLabel, verticalSeparator2, carbLabel);

        statsMenu.getChildren().addAll(kcalLabel, horizontalSeparator, bottomOfTheStatsMenu);
    }

    private void formatAddMenuAndAddNodes(HBox addMenu, ScrollPane chosenFoodView,
                                          Text chosenFoodText, TextArea enterFoodWeight, Button addButton) {

        //formatting layout box containing nodes
        addMenu.prefWidthProperty().bind(layout.widthProperty());
        addMenu.setSpacing(5);
        addMenu.setPadding(new Insets(10, 0, 5, 0));

        //formatting scrollpane with chosen food
        chosenFoodView.setStyle("-fx-padding: 2;" +
                "-fx-border-style: solid inside;" +
                "-fx-border-width: 1;" +
                "-fx-border-color: #D3D3D3;" +
                "-fx-background-color: white;" +
                "-fx-effect: dropshadow(three-pass-box, gray, 2, 0, 0, 0)");
        chosenFoodView.setPrefHeight(40);
        chosenFoodView.prefWidthProperty().bind(layout.widthProperty().multiply(0.62));

        //content of the scrollpane
        chosenFoodText.wrappingWidthProperty().bind(chosenFoodView.widthProperty().add(-20));
        chosenFoodView.setContent(chosenFoodText);

        //text area to enter weight
        enterFoodWeight.setPromptText("grams");
        enterFoodWeight.setPrefHeight(40);
        enterFoodWeight.prefWidthProperty().bind(layout.widthProperty().multiply(0.38).add(-40));

        //addButton
        addButton.setMinSize(40, 40);

        //adding nodes
        addMenu.getChildren().addAll(chosenFoodView, enterFoodWeight, addButton);
    }

    //format the tableview with the food search results
    private void formatSearchResultsList(TableView<Food> searchResultsList) {
        searchResultsList.prefWidthProperty().bind(layout.widthProperty());
        searchResultsList.prefHeightProperty().bind(layout.heightProperty().multiply(0.27));
    }

    //format search menu
    private void formatSearchMenuAndAddNodes(HBox searchMenu, TextField searchField, Button searchButton) {

        //formatting layout box containing nodes
        searchMenu.setSpacing(10);
        searchMenu.prefWidthProperty().bind(layout.widthProperty());
        searchMenu.setPadding(new Insets(0, 0, 20, 0));

        //formatting search textfield
        searchField.prefWidthProperty().bind(layout.widthProperty().add(-60));
        searchField.setPrefHeight(20);
        searchField.setPromptText("Enter the food");

        //formatting button
        searchButton.setMinSize(60, 20);

        //adding nodes to the menu
        searchMenu.getChildren().addAll(searchField, searchButton);
    }

    //formatting layout
    private void formatLayout() {
        this.layout.setPrefSize(360, 610);
        this.layout.setPadding(new Insets(10, 0, 0, 0));
    }

    //method will update progress menu
    public void formatAndUpdateProgress() {

        //formatting menu
        this.progressMenu.setSpacing(10);

        //clearing old records
        this.progressMenu.getChildren().clear();

        //creating sub-menus
        VBox progressLabels = new VBox();
        VBox progressBars = new VBox();
        VBox progressNumerical = new VBox();


        //Labels for the progress menu
        Label kcalProgressLabel = new Label("Kcal consumed: ");
        Label protProgressLabel = new Label("Proteins consumed: ");
        Label fatProgressLabel = new Label("Fats consumed: ");
        Label carbProgressLabel = new Label("Carbs consumed: ");

        //progress bars
        ProgressBar kcalBar = new ProgressBar(1.0 * MainUI.getUser().getKcalEatenToday() / MainUI.getUser().getKcalDemand());
        kcalBar.setStyle("-fx-accent: red");

        ProgressBar protBar = new ProgressBar(1.0 * MainUI.getUser().getProtEatenToday() / MainUI.getUser().getMinProtein());
        ProgressBar fatBar = new ProgressBar(1.0 * MainUI.getUser().getFatEatenToday() / MainUI.getUser().getMinFats());
        ProgressBar carbBar = new ProgressBar(1.0 * MainUI.getUser().getCarbEatenToday() / MainUI.getUser().getMinCarbs());


        //Labels with numerical progress
        Label kcalEatenLabel = new Label(MainUI.getUser().getKcalEatenToday() + "/" + MainUI.getUser().getKcalDemand());
        Label protEatenLabel = new Label(MainUI.getUser().getProtEatenToday() + "/" + MainUI.getUser().getMinProtein());
        Label fatEatenLabel = new Label(MainUI.getUser().getFatEatenToday() + "/" + MainUI.getUser().getMinFats());
        Label carbEatenLabel = new Label(MainUI.getUser().getCarbEatenToday() + "/" + MainUI.getUser().getMinCarbs());

        //Adding elements to the menu
        progressLabels.getChildren().addAll(kcalProgressLabel, protProgressLabel, fatProgressLabel, carbProgressLabel);
        progressBars.getChildren().addAll(kcalBar, protBar, fatBar, carbBar);
        progressNumerical.getChildren().addAll(kcalEatenLabel, protEatenLabel, fatEatenLabel, carbEatenLabel);

        progressMenu.getChildren().addAll(progressLabels, progressBars, progressNumerical);
    }

}
