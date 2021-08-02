package keray.logic;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import keray.domain.EatenFoodData;
import keray.ui.MainUI;

//the class will control the mechanism of the list of the foods eaten by the user
public class EatenFoodTable {
    TableView<EatenFoodData> table = new TableView<>();
    EatenFoodData foodToDelete = null;

    //method returns table
    public TableView<EatenFoodData> createNewTable() {

        //Adding columns
        TableColumn<EatenFoodData, String> foodColumn = new TableColumn<>("Food");
        TableColumn<EatenFoodData, String> weightColumn = new TableColumn<>("Weight");

        this.formatColumns(foodColumn, weightColumn);


        /////////ANIMATING UI//////////////////////////////////


        //adding ability to pick food to delete
        this.table.setRowFactory((rowFunction) -> {
            TableRow<EatenFoodData> row = new TableRow<>();
            row.setOnMouseClicked((click) ->{
                if (!row.isEmpty()) {
                    this.foodToDelete = this.table.getSelectionModel().getSelectedItem();
                }
            });
            return row;
        });


        return this.table;
    }

    ////////////////////METHODS//////////////////////

    //formatting columns
    private void formatColumns(TableColumn<EatenFoodData, String> foodColumn, TableColumn<EatenFoodData, String> weightColumn) {

        //defining data type for each column
        foodColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        weightColumn.setCellValueFactory(new PropertyValueFactory<>("weight"));

        //sizing columns
        foodColumn.prefWidthProperty().bind(this.table.widthProperty().multiply(0.7));
        weightColumn.minWidthProperty().bind(this.table.widthProperty().multiply(0.25));
        weightColumn.maxWidthProperty().bind(this.table.widthProperty().multiply(0.3));
        this.table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        //wrapping text inside columns
        foodColumn.setCellFactory((row) -> {
            TableCell<EatenFoodData, String> cell = new TableCell<>();
            Text text = new Text();
            cell.setGraphic(text);
            text.wrappingWidthProperty().bind(foodColumn.widthProperty());
            text.textProperty().bind(cell.itemProperty());
            return cell;
        });

        //adding columns to the table
        this.table.getColumns().add(foodColumn);
        this.table.getColumns().add(weightColumn);


    }

    //updates table with a list of food eaten today
    public void updateEatenFoodTable() {

        //Creating observable list out of the persons list of eaten foods
        ObservableList<EatenFoodData> eatenFoodList = FXCollections.observableArrayList(MainUI.getUser().getEatenToday());

        //updating the table
        this.table.setItems(eatenFoodList);

    }

    //method provides food picked to delete outside of the method
    public EatenFoodData getFoodToDelete() {
        return this.foodToDelete;
    }

    //after deleting variable should be again set to null
    public void setFoodToDeleteAsNull() {
        this.foodToDelete = null;
    }
}
