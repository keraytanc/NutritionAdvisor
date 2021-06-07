package keray.ui;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import keray.domain.Person;
import keray.logic.DbUsers;

//window representing user information.
public class UserUI {

    //Person variable
    private Person chosenPerson = MainUI.getUser();

    //Label variables which will be constantly updated in the menu
    private Label kcalLabel = new Label("Max kcal: " + chosenPerson.getKcalDemand());
    private Label proteinLabel = new Label("Min prot: " + chosenPerson.getMinProtein());
    private Label fatLabel = new Label("Min fats: " + chosenPerson.getMinFats());
    private Label carbLabel = new Label("Min carbs: " + chosenPerson.getMinCarbs());
    private Label bodyFatLabel = new Label("Your current bodyfat: " + chosenPerson.getBodyFat() + "%");

    public VBox getUserUI() {


        //creating new layout for the User window
        VBox layout = new VBox();
        layout.setPadding(new Insets(10, 0, 0, 0));
        layout.setPrefSize(360, 610);
        layout.setMinSize(200, 420);
        layout.spacingProperty().bind(layout.heightProperty().add(-400).multiply(0.22));
        layout.setAlignment(Pos.TOP_CENTER);

        //creating top Labels
        VBox nameLabelMenu = new VBox();
        Label nameLabel = new Label(chosenPerson.getName());
        nameLabel.setStyle("-fx-font-size: 24");
        Label heightLabel = new Label("Height: " + chosenPerson.getHeight() + "cm");
        nameLabelMenu.getChildren().addAll(nameLabel, heightLabel);
        nameLabelMenu.setAlignment(Pos.CENTER);

        layout.getChildren().add(nameLabelMenu);

        //creating Textfields to update waist and weight information and buttons for them
        GridPane updateMenu = new GridPane();
        updateMenu.setHgap(15);
        updateMenu.setVgap(5);

        TextField weightField = new TextField();
        weightField.setPromptText("Update your weight:");
        TextField waistField = new TextField();
        waistField.setPromptText("Update waist measure");

        Button updateWeightButton = new Button("Update");
        Button updateWaistButton = new Button("Update");

        //adding actions to the buttons
        updateWeightButton.setOnAction((event) -> {

            int weight = Integer.parseInt(weightField.getText());
            chosenPerson.setWeight(weight);
            this.updateNutritionLabels();

            DbUsers.updateWeight(chosenPerson, weight);

        });

        updateWaistButton.setOnAction((event) -> {

            int waist = Integer.parseInt(waistField.getText());

            chosenPerson.setWaist(waist);
            this.updateBodyFatLabel();

            DbUsers.updateWaist(chosenPerson, waist);


        });

        updateWeightButton.prefWidthProperty().bind(layout.widthProperty().multiply(0.5).add(-5));
        updateWaistButton.prefWidthProperty().bind(layout.widthProperty().multiply(0.5).add(-5));

        updateMenu.add(weightField, 0, 0);
        updateMenu.add(waistField, 1, 0);
        updateMenu.add(updateWeightButton, 0, 1);
        updateMenu.add(updateWaistButton, 1, 1);

        layout.getChildren().add(updateMenu);

         //creating buttons to adjust basic maintenance calories demand
        HBox adjustKcalMenu = new HBox();
        adjustKcalMenu.setSpacing(2);

        Button lowerDemandButton = new Button("lower kcal demand");
        Button resetDemandButton = new Button("Reset kcal demand");
        Button raiseDemandButton = new Button("raise kcal demand");

        lowerDemandButton.setWrapText(true);
        resetDemandButton.setWrapText(true);
        raiseDemandButton.setWrapText(true);

        lowerDemandButton.prefWidthProperty().bind(layout.widthProperty().multiply(0.33));
        resetDemandButton.prefWidthProperty().bind(layout.widthProperty().multiply(0.33));
        raiseDemandButton.prefWidthProperty().bind(layout.widthProperty().multiply(0.33));

        adjustKcalMenu.getChildren().addAll(lowerDemandButton, resetDemandButton, raiseDemandButton);
        layout.getChildren().add(adjustKcalMenu);

        //adding action to the buttons
        lowerDemandButton.setOnAction((event) -> {
            chosenPerson.lowerMultiplier();

            int multiplier = chosenPerson.getMultiplier();
            DbUsers.updateMultiplier(chosenPerson, multiplier);
            this.updateNutritionLabels();
        });

        resetDemandButton.setOnAction((event) -> {
            chosenPerson.resetMultiplier();

            int multiplier = 32;
            DbUsers.updateMultiplier(chosenPerson, multiplier);
            this.updateNutritionLabels();
        });

        raiseDemandButton.setOnAction((event) -> {
            chosenPerson.raiseMultiplier();

            int multiplier = chosenPerson.getMultiplier();
            DbUsers.updateMultiplier(chosenPerson, multiplier);
            this.updateNutritionLabels();
        });

        //creating the label depicting current body fat and nutritional advice
        VBox adviceMenu = new VBox();
        adviceMenu.setAlignment(Pos.CENTER);

        this.bodyFatLabel.setStyle("-fx-font-size: 20");

         //creating the advice field
        ScrollPane adviceField = new ScrollPane();
        adviceField.setStyle("-fx-padding: 2;" +
                "-fx-border-style: solid inside;" +
                "-fx-border-width: 1;" +
                "-fx-border-color: #D3D3D3;" +
                "-fx-background-color: white;" +
                "-fx-effect: dropshadow(three-pass-box, gray, 2, 0, 0, 0)");
        adviceField.setPrefHeight(80);
        adviceField.prefWidthProperty().bind(layout.widthProperty());
        Text adviceText = new Text();
        adviceText.wrappingWidthProperty().bind(adviceField.widthProperty().add(-20));
        adviceField.setContent(adviceText);

        adviceMenu.getChildren().addAll(this.bodyFatLabel, adviceField);
        layout.getChildren().add(adviceMenu);

        //choosing dietary plans Menu
        VBox plansMenu = new VBox();
        plansMenu.setAlignment(Pos.CENTER);

        Label choosePlansLabel = new Label("Choose your plans:");

        //creating a choicebox for the menu
        ChoiceBox choosePlansList = new ChoiceBox();
        choosePlansList.prefHeight(25);
        choosePlansList.prefWidthProperty().bind(layout.widthProperty());

        //Adding option to the ChoiceBox
        choosePlansList.setItems(FXCollections.observableArrayList("Reduce weight", "Maintain weight", "Gain weight"));

        //view of the initial choice according to the data in Person object
        String initialChoice = "Reduce weight";
        if (chosenPerson.getCaloryRate() == 1) {
            initialChoice = "Maintain weight";
        } else if (chosenPerson.getCaloryRate() > 1) {
            initialChoice = "Gain weight";
        }
        choosePlansList.setValue(initialChoice);

        //adding to the menu option of changing the plans
        choosePlansList.getSelectionModel().selectedItemProperty().addListener((change, oldValue, newValue) -> {
            int kcalChangeInPercent = 0;
            if (newValue.equals("Reduce weight")) {
                kcalChangeInPercent = -20;
            } else if (newValue.equals("Gain weight")) {
                kcalChangeInPercent = 10;
            }
            chosenPerson.addIntake(kcalChangeInPercent);
            this.updateNutritionLabels();

            DbUsers.updateCaloryRate(chosenPerson);
        });


        plansMenu.getChildren().addAll(choosePlansLabel, choosePlansList);
        layout.getChildren().add(plansMenu);


        //TOTAL REQUIREMENTS STATS
        VBox kcalDemandMenu = new VBox();
        kcalDemandMenu.setAlignment(Pos.CENTER);
        HBox macroDemandMenu = new HBox();

        //TOTAL REQUIREMENTS STATS: labels depicting statistics
        Label dietaryRequirements = new Label("Your total dietary requirements:");
        kcalLabel.prefWidthProperty().bind(layout.widthProperty());
        kcalLabel.setAlignment(Pos.CENTER);

        proteinLabel.prefWidthProperty().bind(layout.widthProperty().multiply(0.33));
        proteinLabel.setAlignment(Pos.CENTER);

        fatLabel.prefWidthProperty().bind(layout.widthProperty().multiply(0.33));
        fatLabel.setAlignment(Pos.CENTER);

        carbLabel.prefWidthProperty().bind(layout.widthProperty().multiply(0.33));
        carbLabel.setAlignment(Pos.CENTER);

        //TOTAL REQUIREMENTS STATS: separators
        Separator horizontalSeparator = new Separator();

        Separator verticalSeparator1 = new Separator();
        verticalSeparator1.setOrientation(Orientation.VERTICAL);

        Separator verticalSeparator2 = new Separator();
        verticalSeparator2.setOrientation(Orientation.VERTICAL);

        //TOTAL REQUIREMENTS STATS adding elements to the bottom stats menu
        macroDemandMenu.getChildren().addAll(this.proteinLabel, verticalSeparator1, this.fatLabel, verticalSeparator2, this.carbLabel);

        //TOTAL REQUIREMENTS STATS: adding all the elements
        kcalDemandMenu.getChildren().addAll(dietaryRequirements, this.kcalLabel, horizontalSeparator, macroDemandMenu);

        layout.getChildren().add(kcalDemandMenu);


        return layout;
    }

    public void updateNutritionLabels() {
        this.kcalLabel.setText("Max kcal: " + chosenPerson.getKcalDemand());
        this.proteinLabel.setText("Min prot: " + chosenPerson.getMinProtein());
        this.fatLabel.setText("Min fats: " + chosenPerson.getMinFats());
        this.carbLabel.setText("Min carbs: " + chosenPerson.getMinCarbs());
    }

    public void updateBodyFatLabel() {
        this.bodyFatLabel.setText("Your current bodyfat: " + chosenPerson.getBodyFat() + "%");
    }
}
