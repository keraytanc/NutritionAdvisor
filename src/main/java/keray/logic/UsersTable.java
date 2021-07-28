package keray.logic;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import keray.domain.FoodSearchResult;
import keray.domain.Person;
import keray.ui.MainUI;
import keray.ui.ParentUI;

public class UsersTable {

    //method will format and return the table
    public TableView createNewTable() {

        TableView table = new TableView();

        //creating a table that will display search results and its columns
        TableColumn nameColumn = new TableColumn("Choose User");

        //defining data type for each column
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));


        //sizing columns
        nameColumn.prefWidthProperty().bind(ParentUI.getParentLayout().widthProperty().add(-65));

        //adding ability to pick a user by double click
        table.setRowFactory((rowFunction) -> {
            TableRow<String> row = new TableRow<>();
            row.setOnMouseClicked((click) -> {
                if (click.getClickCount() == 2 && !row.isEmpty()) {

                    //opening the main User tab with chosen person
                    Person chosenPerson = (Person) table.getSelectionModel().getSelectedItem();
                    MainUI mainUI = new MainUI(chosenPerson);
                    ParentUI.setInsideParentLayout(mainUI.getMainUI());

                }
            });
            return row;
        });

        //wrapping text inside columns
        nameColumn.setCellFactory((row) -> {
            TableCell<FoodSearchResult, String> cell = new TableCell<>();
            Text text = new Text();
            cell.setGraphic(text);
            text.wrappingWidthProperty().bind(nameColumn.widthProperty());
            text.textProperty().bind(cell.itemProperty());
            return cell;
        });


        //adding columns to the the table
        table.getColumns().addAll(nameColumn);

        table.setItems(DbUsers.getUsersFromDb());

        return table;
    }

}
