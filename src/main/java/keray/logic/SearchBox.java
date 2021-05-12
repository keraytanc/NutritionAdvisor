package keray.logic;

import com.google.gson.Gson;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import keray.domain.Food;
import keray.domain.FoodDataConnection;
import keray.domain.FoodSearchResult;


//class will be responsible for food searching mechanism
public class SearchBox {

    //method returns a table to UI that show searched food
    public TableView returnSearchBox(String searchedFood) {

        FoodSearchResult result = this.SearchFood(searchedFood);

        TableView table = this.createNewTable(result);


        return table;
    }

    //method will create a new table with search results
    private TableView createNewTable(FoodSearchResult foodData) {

        //creating a table that will display search results and its columns
        TableView table = new TableView();

        TableColumn nameColumn = new TableColumn("Food");
        TableColumn categoryColumn = new TableColumn("Category");

        //defining data type for each column
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("foodCategory"));

        //sizing columns
        nameColumn.prefWidthProperty().bind(table.widthProperty().multiply(0.6).add(-7.5));
        categoryColumn.prefWidthProperty().bind(table.widthProperty().multiply(0.4).add(-8));

        //wrapping text inside columns
        nameColumn.setCellFactory((row) -> {
            TableCell<FoodSearchResult, String> cell = new TableCell<>();
            Text text = new Text();
            cell.setGraphic(text);
            text.wrappingWidthProperty().bind(nameColumn.widthProperty());
            text.textProperty().bind(cell.itemProperty());
            return cell;
        });

        categoryColumn.setCellFactory((row) -> {
            TableCell<FoodSearchResult, String> cell = new TableCell<>();
            Text text = new Text();
            cell.setGraphic(text);
            text.wrappingWidthProperty().bind(categoryColumn.widthProperty());
            text.textProperty().bind(cell.itemProperty());
            return cell;
        });





        //adding columns to the the table
        table.getColumns().addAll(nameColumn, categoryColumn);

        //converting results into an observable list for
        ObservableList<Food> foodList = FXCollections.observableArrayList(foodData.getFoodList());


        //adding search results to the table
        table.setItems(foodList);


        return table;
    }

    //method connects to API and returns result as a FoodResultObject
    private FoodSearchResult SearchFood(String food) {

        //connects to API and returns search result in Json form.
        FoodDataConnection connect = new FoodDataConnection();
        String JsonSearchResult = connect.searchFood(food);

        //converting json data into Java objects
        Gson gson = new Gson();
        FoodSearchResult result = gson.fromJson(JsonSearchResult, FoodSearchResult.class);

        return result;
    }

}
