package keray.logic;

import com.google.gson.Gson;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import keray.domain.Food;
import keray.domain.FoodDataConnection;
import keray.domain.FoodSearchResult;

//class will be responsible for food searching mechanism
public class SearchBox {
    private final TableView table;
    private final FoodDataConnection connect;
    private Food chosenFood;

    //variablew 1) representing table with search results 2) object connecting with client
    public SearchBox() {
        this.table = new TableView();
        this.connect = new FoodDataConnection();
        this.chosenFood = null;
    }


    //method will format and return the table
    public TableView createNewTable() {

        //creating a table that will display search results and its columns
        TableColumn nameColumn = new TableColumn("Food");
        TableColumn categoryColumn = new TableColumn("Category");

        //defining data type for each column
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("foodCategory"));

        //sizing columns
        nameColumn.prefWidthProperty().bind(this.table.widthProperty().multiply(0.6).add(-7.5));
        categoryColumn.prefWidthProperty().bind(this.table.widthProperty().multiply(0.4).add(-8));

        //adding ability to pick a food(chosenFood variable) by double click
        this.table.setRowFactory((rowFunction) -> {
            TableRow<String> row = new TableRow<>();
            row.setOnMouseClicked((click) -> {
                if (click.getClickCount() == 2 && !row.isEmpty()) {
                    this.chosenFood = (Food) this.table.getSelectionModel().getSelectedItem();

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

        categoryColumn.setCellFactory((row) -> {
            TableCell<FoodSearchResult, String> cell = new TableCell<>();
            Text text = new Text();
            cell.setGraphic(text);
            text.wrappingWidthProperty().bind(categoryColumn.widthProperty());
            text.textProperty().bind(cell.itemProperty());
            return cell;
        });

        //adding columns to the the table
        this.table.getColumns().addAll(nameColumn, categoryColumn);

        return this.table;
    }



    //method will update the table with results of the search
    public void updateSearchBoxResults(String searchedFood) {

        //searching for a food and converting result into proper form
        FoodSearchResult result = this.getSearchedFoods(searchedFood);


        //converting results into an observable list
        ObservableList<Food> foodList = FXCollections.observableArrayList(result.getFoodList());

        //adding search results to the table
        this.table.setItems(foodList);
    }

    public Food getChosenFood() {
        return this.chosenFood;
    }

    //method connects to API and returns result as a FoodResultObject
    private FoodSearchResult getSearchedFoods(String food) {
        long startTime = System.nanoTime();

        //returns search result in Json form.
        String JsonSearchResult = this.connect.searchFood(food);

        //converting json data into Java objects
        Gson gson = new Gson();
        FoodSearchResult result = gson.fromJson(JsonSearchResult, FoodSearchResult.class);
        long endTime = System.nanoTime();
        long totalTime = (endTime - startTime) / 10000000;
        System.out.println(totalTime);

        return result;
    }

}
