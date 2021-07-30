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
import javafx.util.Duration;
import keray.domain.Person;
import keray.logic.DbUsers;

//UI representing user information.
public class UserUI {

    //Person variable
    private final Person chosenPerson = MainUI.getUser();

    //Layout
    private final VBox layout = new VBox();

    //Label variables which will be constantly updated in the menu
    private final Label kcalLabel = new Label("Max kcal: " + chosenPerson.getKcalDemand());
    private final Label proteinLabel = new Label("Min prot: " + chosenPerson.getMinProtein());
    private final Label fatLabel = new Label("Min fats: " + chosenPerson.getMinFats());
    private final Label carbLabel = new Label("Min carbs: " + chosenPerson.getMinCarbs());
    private final Label bodyFatLabel = new Label("Your current bodyfat: " + chosenPerson.getBodyFat() + "%");

    public VBox getUserUI() {

        //formatting the layout into proper form
        this.formatLayout();

        //creating top menu and adding basic constant information about user
        VBox nameLabelMenu = new VBox();
        this.formatNameLabelMenu(nameLabelMenu);

        //Labels with basic updatable user information's
        Label weightLabel = new Label("Weight: " + chosenPerson.getWeight() + "kg");
        Label waistLabel = new Label("Waist circumference: " + chosenPerson.getWaistCircumference() + "cm");
        nameLabelMenu.getChildren().addAll(weightLabel, waistLabel);

        //creating update menu
        GridPane updateMenu = new GridPane();
        this.formatUpdateMenu(updateMenu);

        //creating and formatting update TextFields
        TextField weightField = new TextField();
        TextField waistField = new TextField();
        this.formatTextFields(weightField, waistField);

        //creating and formatting update buttons
        Button updateWeightButton = new Button("Update");
        Button updateWaistButton = new Button("Update");
        this.formatUpdateButtons(updateWeightButton, updateWaistButton);

        //adding nodes to the update menu in proper position
        this.positionNodesInUpdateMenu(updateMenu, weightField, waistField, updateWeightButton, updateWaistButton);

        //creating the menu with nutritional advice
        VBox adviceMenu = new VBox();
        ScrollPane adviceField = new ScrollPane();
        this.formatAndAddNodesToAdviceMenu(adviceMenu, adviceField);

        //updating advice field with proper verbal advice
        this.updateAdviceField(adviceField);

        //Creating and formatting menu for choosing dietary plans
        VBox plansMenu = new VBox();
        this.formatPlansMenu(plansMenu);

        //creating a choicebox for the menu
        ChoiceBox<String> choosePlansList = new ChoiceBox<>();
        this.formatPlansChoiceBox(choosePlansList);

        //adding ChoiceBox to the menu
        plansMenu.getChildren().add(choosePlansList);


        //Creating menu depicting nutritional requirements
        VBox nutritionalNeedsMenu = createNutritionMenu();

        //creating buttons to adjust basic maintenance calories demand
        VBox adjustKcalMenu = new VBox();

        //Label with description of the menu
        Label manualLabel = new Label("Manual adjustment:");

        //creating adjustment buttons
        Button lowerDemandButton = new Button("lower kcal demand");
        Button resetDemandButton = new Button("Reset kcal demand");
        Button raiseDemandButton = new Button("raise kcal demand");

        //formatting and positioning adjustment buttons
        HBox adjustKcalButtons = this.formatAndPositionAdjustmentButtons(lowerDemandButton, resetDemandButton, raiseDemandButton);

        //adding label and buttons to the adjustment menu
        adjustKcalMenu.getChildren().addAll(manualLabel, adjustKcalButtons);

        //adding all elements to the layout
        layout.getChildren().addAll(nameLabelMenu, updateMenu, adviceMenu, plansMenu,
                nutritionalNeedsMenu, adjustKcalMenu);


        ///////ANIMATING UI//////////////////////////////////////////////////////////

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

        //adding action to the buttons responsible for total nutritional demand
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

        //adding actions to the buttons updating weight and waist circumference
        updateWeightButton.setOnAction((event) -> {

            try {
                int weight = Integer.parseInt(weightField.getText());
                chosenPerson.setWeight(weight);
                this.updateNutritionLabels();
                weightLabel.setText("Weight: " + chosenPerson.getWeight() + "kg");
                DbUsers.updateWeight(chosenPerson, weight);
            } catch (Exception e) {
                MainUI.errorDialogBox();
            }
        });

        updateWaistButton.setOnAction((event) -> {

            try {
                int waist = Integer.parseInt(waistField.getText());
                chosenPerson.setWaist(waist);
                this.updateBodyFatLabel();
                waistLabel.setText("Waist circumference: " + chosenPerson.getWaistCircumference() + "cm");
                DbUsers.updateWaist(chosenPerson, waist);
                this.updateAdviceField(adviceField);
            } catch (Exception e) {
                MainUI.errorDialogBox();
            }
        });

        return layout;
    }

    //////////////METHODS///////////////////////////////////

    //formatting adjustment buttons
    private HBox formatAndPositionAdjustmentButtons(Button... buttons) {

        //creating HBox layout for buttons
        HBox buttonsMenu = new HBox();
        buttonsMenu.setSpacing(2);

        //creating tooltip with the instruction for the adjustment buttons
        Tooltip adjustmentTip = new Tooltip();
        this.formatTooltip(adjustmentTip);

        //editing buttons
        for (Button button : buttons) {
            button.setWrapText(true);
            button.prefWidthProperty().bind(layout.widthProperty().multiply(0.33));
            button.setTooltip(adjustmentTip);
            buttonsMenu.getChildren().add(button);
        }
        return buttonsMenu;
    }

    //formatting and adding text to the tooltip
    private void formatTooltip(Tooltip adjustmentTip) {
        adjustmentTip.setShowDelay(Duration.seconds(0));
        adjustmentTip.setHideDelay(Duration.seconds(.3));
        adjustmentTip.setText("Follow your weight for two weeks. If your \nweight doesn't follow your plans" +
                " it means \nthat your lifestyle has changed \nor your base level nutritional needs \nare inaccurate. " +
                "Adjust your needs with \nthe buttons. lower it if you need less food \nthan Advisor stipulate, " +
                "raise it if you need\n more and reset it to the standard value \nif you are not sure what should you do.");
    }

    //creating nutritional menu
    private VBox createNutritionMenu() {
        VBox nutritionMenu = new VBox();
        nutritionMenu.setAlignment(Pos.CENTER);

        //description label
        Label dietaryRequirements = new Label("Your total dietary requirements:");

        //separator to divide upper and bottom part of the menu
        Separator horizontalSeparator = new Separator();

        //formatting nutrition labels to proper format
        this.formatNutritionLabels();

        //creating bottom part of the menu(with fats, proteins and carbs)
        HBox macroDemandMenu = this.createBottomOfNutritionNeedsMenu();

        //Adding all elements to the menu
        nutritionMenu.getChildren().addAll(dietaryRequirements, this.kcalLabel, horizontalSeparator, macroDemandMenu);

        return nutritionMenu;
    }

    //creating and editing bottom nutritional menu
    private HBox createBottomOfNutritionNeedsMenu() {

        HBox bottomNeedsMenu = new HBox();

        //create separators
        Separator verticalSeparator1 = new Separator();
        verticalSeparator1.setOrientation(Orientation.VERTICAL);

        Separator verticalSeparator2 = new Separator();
        verticalSeparator2.setOrientation(Orientation.VERTICAL);

        //add labels and separators to Menu
        bottomNeedsMenu.getChildren().addAll(this.proteinLabel, verticalSeparator1, this.fatLabel, verticalSeparator2, this.carbLabel);

        return bottomNeedsMenu;
    }

    //formatting nutritional labels
    private void formatNutritionLabels() {
        this.kcalLabel.prefWidthProperty().bind(layout.widthProperty());
        this.kcalLabel.setAlignment(Pos.CENTER);

        this.proteinLabel.prefWidthProperty().bind(layout.widthProperty().multiply(0.33));
        this.proteinLabel.setAlignment(Pos.CENTER);

        this.fatLabel.prefWidthProperty().bind(layout.widthProperty().multiply(0.33));
        this.fatLabel.setAlignment(Pos.CENTER);

        this.carbLabel.prefWidthProperty().bind(layout.widthProperty().multiply(0.33));
        this.carbLabel.setAlignment(Pos.CENTER);
    }

    //formatting dietary plans choicebox
    private void formatPlansChoiceBox(ChoiceBox<String> plansChoices) {

        //formatting choiceBox
        plansChoices.prefHeight(25);
        plansChoices.prefWidthProperty().bind(layout.widthProperty());

        //Adding options to the ChoiceBox
        plansChoices.setItems(FXCollections.observableArrayList("Reduce weight", "Maintain weight", "Gain weight"));

        //view of the initial choice according to the data in Person object
        String initialChoice = "Reduce weight";
        if (chosenPerson.getCaloryRate() == 1) {
            initialChoice = "Maintain weight";
        } else if (chosenPerson.getCaloryRate() > 1) {
            initialChoice = "Gain weight";
        }
        plansChoices.setValue(initialChoice);
    }

    //formatting plans menu
    private void formatPlansMenu(VBox plansMenu) {
        //alignment
        plansMenu.setAlignment(Pos.CENTER);

        //label
        Label choosePlansLabel = new Label("Choose your plans:");
        plansMenu.getChildren().add(choosePlansLabel);

    }

    //formatting advice menu and advice field
    private void formatAndAddNodesToAdviceMenu(VBox adviceMenu, ScrollPane adviceField) {

        //formatting menu and bodyFatLabel
        adviceMenu.setAlignment(Pos.CENTER);
        this.bodyFatLabel.setStyle("-fx-font-size: 20");

        //formatting advice field
        adviceField.setStyle("-fx-padding: 2;" +
                "-fx-border-style: solid inside;" +
                "-fx-border-width: 1;" +
                "-fx-border-color: #D3D3D3;" +
                "-fx-background-color: white;" +
                "-fx-effect: dropshadow(three-pass-box, gray, 2, 0, 0, 0)");
        adviceField.setPrefHeight(60);
        adviceField.prefWidthProperty().bind(layout.widthProperty());

        //adding nodes to the menu
        adviceMenu.getChildren().addAll(this.bodyFatLabel, adviceField);
    }

    //formatting User UI layout
    private void formatLayout() {
        this.layout.setPadding(new Insets(10, 0, 0, 0));
        this.layout.setPrefSize(360, 610);
        this.layout.setMinSize(200, 420);
        this.layout.spacingProperty().bind(layout.heightProperty().add(-400).multiply(0.20));
        this.layout.setAlignment(Pos.TOP_CENTER);
    }

    //method positions all the nodes of update menu in correct places
    private void positionNodesInUpdateMenu(GridPane updateMenu, TextField weightField, TextField waistField,
                                           Button updateWeightButton, Button updateWaistButton) {
        updateMenu.add(weightField, 0, 0);
        updateMenu.add(waistField, 1, 0);
        updateMenu.add(updateWeightButton, 0, 1);
        updateMenu.add(updateWaistButton, 1, 1);
    }

    //formatting update buttons
    private void formatUpdateButtons(Button... buttons) {
        for (Button button : buttons) {
            button.prefWidthProperty().bind(layout.widthProperty().multiply(0.5).add(-5));
        }
    }

    //formatting update text fields
    private void formatTextFields(TextField weightField, TextField waistField) {
        weightField.setPromptText("Update your weight:");
        waistField.setPromptText("Update waist measure");
    }

    //formatting GridPane of update menu
    private void formatUpdateMenu(GridPane updateMenu) {
        updateMenu.setHgap(15);
        updateMenu.setVgap(5);
    }

    //method will format upper menu with constant basic information's about the user
    private void formatNameLabelMenu(VBox nameLabelMenu) {
        nameLabelMenu.setAlignment(Pos.CENTER);

        Label nameLabel = new Label(chosenPerson.getName());
        nameLabel.setStyle("-fx-font-size: 24");

        Label heightLabel = new Label("Height: " + chosenPerson.getHeight() + "cm");

        nameLabelMenu.getChildren().addAll(nameLabel, heightLabel);
    }

    private void updateNutritionLabels() {
        this.kcalLabel.setText("Max kcal: " + chosenPerson.getKcalDemand());
        this.proteinLabel.setText("Min prot: " + chosenPerson.getMinProtein());
        this.fatLabel.setText("Min fats: " + chosenPerson.getMinFats());
        this.carbLabel.setText("Min carbs: " + chosenPerson.getMinCarbs());
    }

    private void updateBodyFatLabel() {
        this.bodyFatLabel.setText("Your current bodyfat: " + chosenPerson.getBodyFat() + "%");
    }

    //method will update Advice field with current nutritional advice
    private void updateAdviceField(ScrollPane adviceField) {
        Text advice = new Text(this.formulateAdvice());
        advice.wrappingWidthProperty().bind(adviceField.widthProperty().add(-20));
        adviceField.setContent(advice);
    }

    //method will formulate a nutritional advice according to body fat level
    private String formulateAdvice() {

        double bodyFat = this.chosenPerson.getBodyFat();

        String advice = "You are obese. Your fat level is very unhealthy and might lead to various diseases. " +
                "loose your weight urgently";

        if (bodyFat < 12) {
            advice = "Your fat level is much below healthy levels. You should urgently gain weight and contact a doctor";
        } else if (bodyFat < 16.5) {
            advice = "Your fat level is too low and might be unhealthy if kept for extended periods of time. " +
                    "Gain weight.";
        } else if (bodyFat < 20) {
            advice = "You are a fit person. Fat level between 16.5% and 20% is optimal for health " +
             "and aesthetics. Your aim should be to maintain current fat level.";
        } else if (bodyFat < 26) {
            advice = "You are overweight. You should loose weight. Start your diet.";
        }
        return advice;
    }
}
