package keray.ui;

import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import keray.domain.Person;
import keray.logic.DbUsers;

public class AddUserUI {

    public VBox addUserUI() {

        //creating new add user layout
        VBox layout = new VBox();

        //size and configuration for the layout
        layout.setPrefSize(360, 640);
        layout.setSpacing(3);

        //enter the name field
        Label enterNameLabel = new Label("Enter your name:");
        TextField enterNameField = new TextField();
        enterNameLabel.setStyle("-fx-font-size: 16");

        //enter the height field
        Label enterHeightLabel = new Label("Enter your height(cm):");
        TextField enterHeightField = new TextField();
        enterHeightLabel.setStyle("-fx-font-size: 16");

        //enter the weight field
        Label enterWeightLabel = new Label("Enter your weight(kg):");
        TextField enterWeightField = new TextField();
        enterWeightLabel.setStyle("-fx-font-size: 16");

        //enter the waist circumference field
        Label enterWaistLabel = new Label("Enter your waist circumference(cm):");
        TextField enterWaistField = new TextField();
        enterWaistLabel.setStyle("-fx-font-size: 16");

        //Choose your lifestyle field
        Label chooseLifestyleLabel = new Label("Choose your lifestyle:");
        ChoiceBox chooseLifestyleList = new ChoiceBox();
        chooseLifestyleList.prefHeight(25);
        chooseLifestyleList.prefWidthProperty().bind(layout.prefWidthProperty().add(-40));
        chooseLifestyleLabel.setStyle("-fx-font-size: 16");

        //Adding option to the ChoiceBox
        chooseLifestyleList.setItems(FXCollections.observableArrayList("Sedentary", "Normal", "Active"));

        //Choose your plans
        Label choosePlansLabel = new Label("Choose your plans:");
        ChoiceBox choosePlansList = new ChoiceBox();
        choosePlansList.prefHeight(25);
        choosePlansList.prefWidthProperty().bind(layout.prefWidthProperty().add(-40));
        choosePlansLabel.setStyle("-fx-font-size: 16");

        //Adding option to the ChoiceBox
        choosePlansList.setItems(FXCollections.observableArrayList("Reduce weight", "Maintain weight", "Gain weight"));

        //Add button
        Button addButton = new Button("Add");
        addButton.setPrefHeight(30);
        addButton.prefWidthProperty().bind(layout.prefWidthProperty().add(-40));
        addButton.setStyle("-fx-font-size: 16");

        //Region object to separate fields on the layout
        Region region1 = new Region();
        region1.prefHeightProperty().bind(layout.prefHeightProperty());

        Region region2 = new Region();
        region2.prefHeightProperty().bind(layout.prefHeightProperty());

        Region region3 = new Region();
        region3.prefHeightProperty().bind(layout.prefHeightProperty());

        Region region4 = new Region();
        region4.prefHeightProperty().bind(layout.prefHeightProperty());

        Region region5 = new Region();
        region5.prefHeightProperty().bind(layout.prefHeightProperty());

        Region region6 = new Region();
        region6.prefHeightProperty().bind(layout.prefHeightProperty().add(30));


        //adding action to a button: adding user
        addButton.setOnAction((event) -> {
            try {
                String name = enterNameField.getText();
                int height = Integer.parseInt(enterHeightField.getText());
                int weight = Integer.parseInt(enterWeightField.getText());
                int waist = Integer.parseInt(enterWaistField.getText());

                int multiplier = convertLifeStyleToMultiplier(chooseLifestyleList.getValue().toString());
                double caloryRate = convertPlansToCaloryRate(choosePlansList.getValue().toString());

                Person newPerson = new Person(name, height, weight, waist, multiplier, caloryRate);
                DbUsers.addUserToDb(newPerson);

                MainUI mainUI = new MainUI(newPerson);
                ParentUI.setInsideParentLayout(mainUI.getMainUI());
            } catch (Exception e) {
                //e.printStackTrace();
                this.errorDialogBox();
            }
        });


        //adding all the elements
        layout.getChildren().addAll(enterNameLabel, enterNameField, region1, enterHeightLabel,
                enterHeightField, region2, enterWeightLabel, enterWeightField, region3, enterWaistLabel,
                enterWaistField, region4, chooseLifestyleLabel, chooseLifestyleList, region5,
                choosePlansLabel, choosePlansList, region6, addButton);


        return layout;
    }

    public int convertLifeStyleToMultiplier(String lifestyle) {
        int multiplier = 32;
        if (lifestyle.equals("Sedentary")) {
            multiplier = 31;
        } else if (lifestyle.equals("Active")) {
            multiplier = 33;
        }

        return multiplier;
    }

    public double convertPlansToCaloryRate(String plans) {
        double caloryRate = 1.0;

        if (plans.equals("Reduce weight")) {
            caloryRate = 0.8;
        } else if (plans.equals("Gain weight")) {
            caloryRate = 1.1;
        }

        return caloryRate;
    }

    //Method shows dialog in case of incorrect input
    private void errorDialogBox() {

        Dialog<String> dialog = new Dialog<String>();
        dialog.setTitle("Incorrect input");
        ButtonType okButton = new ButtonType("OK");
        dialog.setContentText("Input is incorrect. Make sure height, weight, waist circumference " +
                "is in numeric format and all the gaps are filled");
        dialog.getDialogPane().getButtonTypes().add(okButton);

        dialog.showAndWait();
    }

}
