package keray.ui;

import javafx.scene.text.Text;
import keray.logic.DbUsers;
import keray.logic.SearchTable;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import keray.logic.EatenFoodTable;

public class AddingFoodUI {
    private VBox layout;
    public HBox progressMenu;

    public AddingFoodUI() {
        this.layout = new VBox();
        this.progressMenu = new HBox();
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
        searchResultList.prefHeightProperty().bind(layout.heightProperty().multiply(0.27));

        //creating add Menu
        HBox addMenu = new HBox();
        addMenu.prefWidthProperty().bind(layout.widthProperty());
        addMenu.setSpacing(5);

        addMenu.setPadding(new Insets(10, 0, 5, 0));

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

        //Label depicting eaten food table
        Label eatenFoodLabel = new Label("Foods eaten today:");
        eatenFoodLabel.setPadding(new Insets(10, 0, 0, 0));

        //Menu managing food eaten today
        HBox eatenFoodsMenu = new HBox();
        eatenFoodsMenu.setSpacing(10);
        eatenFoodsMenu.setPadding(new Insets(0, 0, 10, 0));

        //Table of foods eaten today
        EatenFoodTable eatenFoodTableObject = new EatenFoodTable();
        TableView eatenFoodTable = eatenFoodTableObject.createNewTable();
        eatenFoodTable.prefWidthProperty().bind(layout.widthProperty().add(-60));
        eatenFoodTable.prefHeightProperty().bind(layout.heightProperty().multiply(0.25));
        eatenFoodTableObject.updateEatenFoodTable();

        //Button to delete a food from the list
        Button deleteFromEatenButton = new Button();
        deleteFromEatenButton.prefHeightProperty().bind(eatenFoodTable.heightProperty());
        deleteFromEatenButton.setPrefWidth(50);

        //text on the button
        Label textOnButton = new Label("DELETE");
        textOnButton.setWrapText(true);
        textOnButton.setMinWidth(1);
        textOnButton.setMaxWidth(1);

        //setting text on the button and adding elements to the foods menu
        deleteFromEatenButton.setGraphic(textOnButton);
        eatenFoodsMenu.getChildren().addAll(eatenFoodTable, deleteFromEatenButton);

        //Progress menu
        this.progressMenu.setSpacing(10);

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


        //adding everything to the layout
        this.layout.getChildren().addAll(enterTheFoodLabel, searchMenu, searchResultList, addMenu, statsMenu,
                eatenFoodLabel, eatenFoodsMenu, progressMenu);


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
                this.updateProgress(progressMenu);
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
                this.updateProgress(this.progressMenu);

            }
        });

        //creating a Scene object and returning it
        return this.layout;
    }

    //method will update progress menu
    public void updateProgress(HBox progressMenu) {

        //acessing internal VBox layouts
        VBox progressBars = (VBox) progressMenu.getChildren().get(1);
        VBox progressNumerical = (VBox) progressMenu.getChildren().get(2);

        //accesing progress bars from first internal layout
        ProgressBar kcalBar = (ProgressBar) progressBars.getChildren().get(0);
        ProgressBar protBar = (ProgressBar) progressBars.getChildren().get(1);
        ProgressBar fatBar = (ProgressBar) progressBars.getChildren().get(2);
        ProgressBar carbBar = (ProgressBar) progressBars.getChildren().get(3);

        //updating values for the bars
        kcalBar.setProgress(1.0 * MainUI.getUser().getKcalEatenToday() / MainUI.getUser().getKcalDemand());
        protBar.setProgress(1.0 * MainUI.getUser().getProtEatenToday() / MainUI.getUser().getMinProtein());
        fatBar.setProgress(1.0 * MainUI.getUser().getFatEatenToday() / MainUI.getUser().getMinFats());
        carbBar.setProgress(1.0 * MainUI.getUser().getCarbEatenToday() / MainUI.getUser().getMinCarbs());

        //accessing numerical progress labels
        Label kcalLabel = (Label) progressNumerical.getChildren().get(0);
        Label protLabel = (Label) progressNumerical.getChildren().get(1);
        Label fatLabel = (Label) progressNumerical.getChildren().get(2);
        Label carbLabel = (Label) progressNumerical.getChildren().get(3);

        //updating values for the labels
        kcalLabel.setText(MainUI.getUser().getKcalEatenToday() + "/" + MainUI.getUser().getKcalDemand());
        protLabel.setText(MainUI.getUser().getProtEatenToday() + "/" + MainUI.getUser().getMinProtein());
        fatLabel.setText(MainUI.getUser().getFatEatenToday() + "/" + MainUI.getUser().getMinFats());
        carbLabel.setText(MainUI.getUser().getCarbEatenToday() + "/" + MainUI.getUser().getMinCarbs());
    }

}
