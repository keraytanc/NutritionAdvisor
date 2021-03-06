package keray.logic;

import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import keray.domain.Person;
import keray.ui.MainUI;
import keray.ui.ParentUI;

//Class creates the table in the initial screen where user can edit(choose, add or delete) user profiles
public class UsersTable {
    public Person toDelete = null;

    //method will format and return the table
    public TableView<Person> createNewTable() {

        //Create table
        TableView<Person> table = new TableView<>();
        this.allowTableToEditUsers(table);

        //creating a column and formatting it
        TableColumn<Person, String> nameColumn = new TableColumn<>("Choose User");
        this.formatColumn(nameColumn);

        //adding column to the the table
        table.getColumns().add(nameColumn);

        //updating table with content
        this.updateTable(table);

        return table;
    }

    //////////METHODS////////////////////

    //method to update content of the table
    public void updateTable(TableView<Person> table) {
        table.setItems(DbUsers.getUsersFromDb());
    }

    //method edit column according to the needs of the application
    private void formatColumn(TableColumn<Person, String> column) {
        //sizing columns
        column.prefWidthProperty().bind(ParentUI.getParentLayout().widthProperty().add(-65));

        //defining data type for each column
        column.setCellValueFactory(new PropertyValueFactory<>("name"));

        //wrapping text inside columns
        column.setCellFactory((row) -> {
            TableCell<Person, String> cell = new TableCell<>();
            Text text = new Text();
            cell.setGraphic(text);
            text.wrappingWidthProperty().bind(column.widthProperty());
            text.textProperty().bind(cell.itemProperty());
            return cell;
        });
    }

    //methods add to the table functionality of choosing or deleting an user
    private void allowTableToEditUsers(TableView<Person> table) {
        //adding ability to pick a user by double click or to pick user to delete
        table.setRowFactory((rowFunction) -> {
            TableRow<Person> row = new TableRow<>();
            row.setOnMouseClicked((click) -> {

                //choosing user
                if (click.getClickCount() == 2 && !row.isEmpty()) {
                    //opening the main User tab with chosen person
                    Person chosenPerson = table.getSelectionModel().getSelectedItem();
                    MainUI mainUI = new MainUI(chosenPerson);
                    ParentUI.setInsideParentLayout(mainUI.getMainUI());

                //selecting user to delete
                } else if (!(click.getClickCount() > 1) && !row.isEmpty()) {
                    //picking user to delete
                    this.toDelete = table.getSelectionModel().getSelectedItem();
                }
            });
            return row;
        });
    }
}
