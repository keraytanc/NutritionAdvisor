package keray.domain;

import keray.logic.FoodDataConnection;

//class will represent objects with informations suitable for a list of foods eaten at the given day
public class EatenFoodData {

    private int fdcId;
    private int weight;
    private FoodDataConnection connect;
    private Food food;
    private String description;

    //constructor to retrieve weight and id data from database. Using ID it creates Food object by
    //connecting to external API
    public EatenFoodData(int id, int weight) {
        this.fdcId = id;
        this.weight = weight;
        this.connect = new FoodDataConnection();
        this.food = connect.searchFood(String.valueOf(id)).getFoodList().get(0);
        this.description = this.food.getDescription();
    }

    //constructor to create object from the Food object
    public EatenFoodData(Food food, int weight) {
        this.fdcId = food.getId();
        this.weight = weight;
        this.food = food;
        this.description = food.getDescription();
    }

    //getters
    public int getFdcId() { return fdcId; }
    public int getWeight() { return weight; }
    public Food getFood() { return food; }
    public String getDescription() { return description; }

    //String representation of the object: "id,weight" necessary for the database
    @Override
    public String toString() {
        return this.fdcId + "," + this.weight;
    }

}
