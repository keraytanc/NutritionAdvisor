package keray.ui;

import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import keray.domain.Person;
import keray.logic.DbUsers;
import keray.logic.UsersTable;

import java.util.Optional;

//UI with the menu to pick user
public class PickUserUI {
    BorderPane mainPickUserUI = new BorderPane();

    public BorderPane getPickUserUI() {

        //command/instruction
        Label commandLabel = new Label("Pick user:");

        //new table to pick users
        UsersTable usersTable = new UsersTable();
        TableView<Person> table = usersTable.createNewTable();
        this.formatUserTable(table);

        //Region object to make a space between table and delete button
        Region space = new Region();
        space.setPrefHeight(10);

        //Delete user button
        Button deleteButton = new Button("Delete User");
        this.formatButton(deleteButton);

        //Creating topMenu to segregate nodes at the top of UI
        VBox topMenu = new VBox();
        topMenu.getChildren().addAll(commandLabel, table, space, deleteButton);


        //Button to add new user
        Button addUserButton = new Button("Add new user");
        this.formatButton(addUserButton);

        //adding all elements to the layout
        this.mainPickUserUI.setTop(topMenu);
        this.mainPickUserUI.setBottom(addUserButton);

        ////////////ANIMATING UI///////////////////////////////////

        //Deleting user action
        deleteButton.setOnAction((event) -> {
            if (usersTable.toDelete != null) {
                this.userDeleteConfirmationDialog(usersTable, table);
            } else {
                this.userNotSelectedDialog();
            }
        });

        //Adding new user action
        addUserButton.setOnAction((event) -> {
            AddUserUI addingUserLayout = new AddUserUI();
            VBox addingUser = addingUserLayout.getAddUserUI();
            ParentUI.getParentLayout().getChildren().removeAll(topMenu, addUserButton);
            ParentUI.setInsideParentLayout(addingUser);
        });

        return this.mainPickUserUI;
    }

    ////////////////////METHODS///////////////////////////////

    //Method will format delete user button
    private void formatButton(Button button) {
        button.setPrefHeight(40);
        button.prefWidthProperty().bind(ParentUI.getParentLayout().widthProperty().add(-50));
    }

    //Method will adjust user table
    private void formatUserTable(TableView<Person> table) {
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        table.prefWidthProperty().bind(ParentUI.getParentLayout().widthProperty().add(-50));
        table.prefHeightProperty().bind(ParentUI.getParentLayout().heightProperty().multiply(0.35));
    }


    private void userDeleteConfirmationDialog(UsersTable userstable, TableView<Person> table) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Confirm");
        ButtonType yesButton = new ButtonType("Yes");
        ButtonType noButton = new ButtonType("No");
        dialog.setContentText("Are you sure you want to delete user?");
        dialog.getDialogPane().getButtonTypes().add(yesButton);
        dialog.getDialogPane().getButtonTypes().add(noButton);

        Optional<ButtonType> result = dialog.showAndWait();

        //deleting record after user confirms
        if (result.isPresent() && result.get() == yesButton && userstable.toDelete != null) {
            DbUsers.deleteUserFromDb(userstable.toDelete);
            userstable.toDelete = null;
            userstable.updateTable(table);
        } else {
            userstable.toDelete = null;
        }
    }

    //dialog informing about user not being picked
    private void userNotSelectedDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Select user to delete");
        ButtonType yesButton = new ButtonType("Ok");
        dialog.setContentText("User to delete is not selected");
        dialog.getDialogPane().getButtonTypes().add(yesButton);
        dialog.showAndWait();
    }


}
