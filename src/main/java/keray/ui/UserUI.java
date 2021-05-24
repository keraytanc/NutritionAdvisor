package keray.ui;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

//window representing user information.
public class UserUI {

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
        Label nameLabel = new Label("Kamil");
        nameLabel.setStyle("-fx-font-size: 24");
        Label heightLabel = new Label("Height: 186cm");
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

        updateWeightButton.prefWidthProperty().bind(layout.widthProperty().multiply(0.5).add(-5));
        updateWaistButton.prefWidthProperty().bind(layout.widthProperty().multiply(0.5).add(-5));

        updateMenu.add(weightField, 0, 0);
        updateMenu.add(waistField, 1, 0);
        updateMenu.add(updateWeightButton, 0, 1);
        updateMenu.add(updateWaistButton, 1, 1);

        layout.getChildren().add(updateMenu);

         //creating buttons to adjust basic maintanance calories demand
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

        //creating the label depicting current body fat and nutritional advice
        VBox adviceMenu = new VBox();
        adviceMenu.setAlignment(Pos.CENTER);

        Label bodyFatLabel = new Label("Your current bodyfat: 0%");
        bodyFatLabel.setStyle("-fx-font-size: 20");

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

        adviceMenu.getChildren().addAll(bodyFatLabel, adviceField);
        layout.getChildren().add(adviceMenu);

        //choosing dietry plans Menu
        VBox plansMenu = new VBox();
        plansMenu.setAlignment(Pos.CENTER);

        Label choosePlansLabel = new Label("Choose your plans:");

        ChoiceBox choosePlansList = new ChoiceBox();
        choosePlansList.prefHeight(25);
        choosePlansList.prefWidthProperty().bind(layout.widthProperty());

        plansMenu.getChildren().addAll(choosePlansLabel, choosePlansList);
        layout.getChildren().add(plansMenu);


        //TOTAL REQUIREMENTS STATS
        VBox kcalDemandMenu = new VBox();
        kcalDemandMenu.setAlignment(Pos.CENTER);
        HBox macroDemandMenu = new HBox();

        //TOTAL REQUIREMENTS STATS: labels depicting statistics
        Label dietaryRequirements = new Label("Your total dietary requirements:");
        Label kcalLabel = new Label("Max kcal: 0");
        kcalLabel.prefWidthProperty().bind(layout.widthProperty());
        kcalLabel.setAlignment(Pos.CENTER);

        Label proteinLabel = new Label("Min prot: 0");
        proteinLabel.prefWidthProperty().bind(layout.widthProperty().multiply(0.33));
        proteinLabel.setAlignment(Pos.CENTER);

        Label fatLabel = new Label("Min fats: 0");
        fatLabel.prefWidthProperty().bind(layout.widthProperty().multiply(0.33));
        fatLabel.setAlignment(Pos.CENTER);

        Label carbLabel = new Label("Min carbs: 0");
        carbLabel.prefWidthProperty().bind(layout.widthProperty().multiply(0.33));
        carbLabel.setAlignment(Pos.CENTER);

        //TOTAL REQUIREMENTS STATS: separators
        Separator horizontalSeparator = new Separator();

        Separator verticalSeparator1 = new Separator();
        verticalSeparator1.setOrientation(Orientation.VERTICAL);

        Separator verticalSeparator2 = new Separator();
        verticalSeparator2.setOrientation(Orientation.VERTICAL);

        //TOTAL REQUIREMENTS STATS adding elements to the bottom stats menu
        macroDemandMenu.getChildren().addAll(proteinLabel, verticalSeparator1, fatLabel, verticalSeparator2, carbLabel);

        //TOTAL REQUIREMENTS STATS: adding all the elements
        kcalDemandMenu.getChildren().addAll(dietaryRequirements,kcalLabel, horizontalSeparator, macroDemandMenu);

        layout.getChildren().add(kcalDemandMenu);


        return layout;
    }
}
