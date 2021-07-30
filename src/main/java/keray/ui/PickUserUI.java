package keray.ui;

import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import keray.logic.DbUsers;
import keray.logic.UsersTable;

import java.util.Optional;

//UI with the menu to pick user
public class PickUserUI {

    public BorderPane getPickUserUI() {

        BorderPane mainPickUserUI = new BorderPane();

        //top menu
        VBox topMenu = new VBox();

        //command
        Label commandLabel = new Label("Pick user:");

        //new table to pick users
        UsersTable usersTable = new UsersTable();
        TableView table = usersTable.createNewTable();

        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        table.prefWidthProperty().bind(ParentUI.getParentLayout().widthProperty().add(-50));
        table.prefHeightProperty().bind(ParentUI.getParentLayout().heightProperty().multiply(0.35));

        //Region object to make a space between table and delete button
        Region space = new Region();
        space.setPrefHeight(10);

        //Delete user button
        Button deleteButton = new Button("Delete User");
        deleteButton.setPrefHeight(40);
        deleteButton.prefWidthProperty().bind(ParentUI.getParentLayout().widthProperty().add(-50));

        //adding elements to the top menu
        topMenu.getChildren().addAll(commandLabel, table, space, deleteButton);

        //Button to add new user
        Button addUserButton = new Button("Add new user");
        addUserButton.setPrefHeight(40);
        addUserButton.prefWidthProperty().bind(ParentUI.getParentLayout().widthProperty().add(-50));

        //adding all elements to the layout
        mainPickUserUI.setTop(topMenu);
        mainPickUserUI.setBottom(addUserButton);

        //Deleting user action
        deleteButton.setOnAction((event) -> {
            if (usersTable.toDelete != null) {
                this.userDeleteConfirmationDialog(usersTable, table);
            } else {
                this.userNotPickedDialog();
            }
        });

        //Adding new user action
        addUserButton.setOnAction((event) -> {
            AddUserUI addingUserLayout = new AddUserUI();
            VBox addingUser = addingUserLayout.addUserUI();
            ParentUI.getParentLayout().getChildren().removeAll(topMenu, addUserButton);
            ParentUI.setInsideParentLayout(addingUser);
        });

        return mainPickUserUI;
    }


    public void userDeleteConfirmationDialog(UsersTable userstable, TableView table) {
        Dialog<ButtonType> dialog = new Dialog<ButtonType>();
        dialog.setTitle("Confirm");
        ButtonType yesButton = new ButtonType("Yes");
        ButtonType noButton = new ButtonType("No");
        dialog.setContentText("Are you sure you want to delete user?");
        dialog.getDialogPane().getButtonTypes().add(yesButton);
        dialog.getDialogPane().getButtonTypes().add(noButton);

        Optional<ButtonType> result = dialog.showAndWait();

        //deleting record after user confirms
        if (result.get() == yesButton && userstable.toDelete != null) {
            DbUsers.deleteUserFromDb(userstable.toDelete);
            userstable.toDelete = null;
            userstable.updateTable(table);
        } else {
            userstable.toDelete = null;
        }
    }

    //dialog informing about user not being picked
    public void userNotPickedDialog() {
        Dialog<ButtonType> dialog = new Dialog<ButtonType>();
        dialog.setTitle("Select user to delete");
        ButtonType yesButton = new ButtonType("Ok");
        dialog.setContentText("User to delete is not selected");
        dialog.getDialogPane().getButtonTypes().add(yesButton);

        Optional<ButtonType> result = dialog.showAndWait();
    }


}
