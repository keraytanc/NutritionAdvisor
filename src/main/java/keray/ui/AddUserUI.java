package keray.ui;

import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import keray.domain.Person;
import keray.logic.DbUsers;

import java.util.HashMap;

//class will be responsible for the UI of User-adding functionality
public class AddUserUI {
    public VBox layout;

    public AddUserUI() {
        this.layout = new VBox();
        this.layout.setPrefSize(360, 640);
        this.layout.setSpacing(3);
    }

    public VBox getAddUserUI() {

        //enter the name field
        Label enterNameLabel = new Label("Enter your name:");
        TextField enterNameField = new TextField();

        //enter the height field
        Label enterHeightLabel = new Label("Enter your height(cm):");
        TextField enterHeightField = new TextField();

        //enter the weight field
        Label enterWeightLabel = new Label("Enter your weight(kg):");
        TextField enterWeightField = new TextField();

        //enter the waist circumference field
        Label enterWaistLabel = new Label("Enter your waist circumference(cm):");
        TextField enterWaistField = new TextField();


        //Choose your lifestyle field
        Label chooseLifestyleLabel = new Label("Choose your lifestyle:");
        ChoiceBox<String> chooseLifestyleList = new ChoiceBox<>();
        this.formatChoiceBoxes(chooseLifestyleList);
        //Adding option to the ChoiceBox
        chooseLifestyleList.setItems(FXCollections.observableArrayList("Sedentary", "Normal", "Active"));


        //Choose your plans
        Label choosePlansLabel = new Label("Choose your plans:");
        ChoiceBox<String> choosePlansList = new ChoiceBox<>();
        this.formatChoiceBoxes(choosePlansList);
        //Adding option to the ChoiceBox
        choosePlansList.setItems(FXCollections.observableArrayList("Reduce weight", "Maintain weight", "Gain weight"));

        //Add button
        Button addButton = new Button("Add");
        this.formatButton(addButton);

        //Region objects to separate fields on the layout will be stored in HashMap
        HashMap<Integer, Region> regions = this.createRegionObjectsStorage();

        //Formatting font in all the labels in UI
        this.formatLabels(enterNameLabel, enterHeightLabel, enterWeightLabel,
                enterWaistLabel, chooseLifestyleLabel, choosePlansLabel);

        //adding all the elements
        this.layout.getChildren().addAll(enterNameLabel, enterNameField, regions.get(0), enterHeightLabel,
                enterHeightField, regions.get(1), enterWeightLabel, enterWeightField, regions.get(2), enterWaistLabel,
                enterWaistField, regions.get(3), chooseLifestyleLabel, chooseLifestyleList, regions.get(4),
                choosePlansLabel, choosePlansList, regions.get(5), addButton);


        ////////////////ANIMATING UI//////////////////////////////

        //adding action to a button: adding user
        addButton.setOnAction((event) -> {
            try {
                String name = enterNameField.getText();
                int height = Integer.parseInt(enterHeightField.getText());
                int weight = Integer.parseInt(enterWeightField.getText());
                int waist = Integer.parseInt(enterWaistField.getText());

                int multiplier = convertLifeStyleToMultiplier(chooseLifestyleList.getValue());
                double caloriesRate = convertPlansToCaloriesRate(choosePlansList.getValue());

                Person newPerson = new Person(name, height, weight, waist, multiplier, caloriesRate);
                DbUsers.addUserToDb(newPerson);

                MainUI mainUI = new MainUI(newPerson);
                ParentUI.setInsideParentLayout(mainUI.getMainUI());
            } catch (Exception e) {
                this.errorDialogBox();
            }
        });

        return this.layout;
    }

    //////////////METHODS////////////////////////////

    //method will create region objects(necessary for the clarity of UI) and store them in a HashMap
    private HashMap<Integer, Region> createRegionObjectsStorage() {

        //HashMap as a storage
        HashMap<Integer, Region> storage = new HashMap<>();

        //loop creating 6 Region objects
        for (int i = 0; i < 6; i++) {
            Region newRegion = new Region();
            if (i == 5) {
                newRegion.prefHeightProperty().bind(this.layout.prefHeightProperty().add(30));
            } else {
                newRegion.prefHeightProperty().bind(this.layout.prefHeightProperty());
            }
            storage.put(i,newRegion);
        }
        return storage;
    }

    private void formatButton(Button button) {
        button.setPrefHeight(30);
        button.prefWidthProperty().bind(this.layout.prefWidthProperty().add(-40));
        button.setStyle("-fx-font-size: 16");
    }

    private void formatLabels(Label... labels) {
        for (Label label : labels) {
            label.setStyle("-fx-font-size: 16");
        }
    }

    private void formatChoiceBoxes(ChoiceBox<String> box) {
        box.prefHeight(25);
        box.prefWidthProperty().bind(this.layout.prefWidthProperty().add(-40));
    }

    private int convertLifeStyleToMultiplier(String lifestyle) {
        int multiplier = 32;
        if (lifestyle.equals("Sedentary")) {
            multiplier = 31;
        } else if (lifestyle.equals("Active")) {
            multiplier = 33;
        }
        return multiplier;
    }

    private double convertPlansToCaloriesRate(String plans) {
        double caloriesRate = 1.0;

        if (plans.equals("Reduce weight")) {
            caloriesRate = 0.8;
        } else if (plans.equals("Gain weight")) {
            caloriesRate = 1.1;
        }
        return caloriesRate;
    }

    //Method shows dialog in case of incorrect input
    private void errorDialogBox() {

        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Incorrect input");
        ButtonType okButton = new ButtonType("OK");
        dialog.setContentText("Input is incorrect. Make sure height, weight, waist circumference " +
                "is in numeric format and all the gaps are filled");
        dialog.getDialogPane().getButtonTypes().add(okButton);

        dialog.showAndWait();
    }

}
