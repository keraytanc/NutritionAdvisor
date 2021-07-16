package keray.domain;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

public class Food {
    private int fdcId;
    private String description;
    private String foodCategory;
    //leaving variable as a Json array for program speed
    private JsonArray foodNutrients;

    //Methods do get basic information about the food. Used in search table
    public String getDescription() {
        return this.description;
    }
    public String getFoodCategory() {
        return this.foodCategory;
    }

    //Different methods to get Nutritional values of the chosen food(each micronutrient type has ID).
    public double getKcal() {
        return this.getValue(1008);
    }
    public double getProteins() {
        return this.getValue(1003);
    }
    public double getFats() {
        return this.getValue(1004);
    }
    public double getCarbs() {
        return this.getValue(1005);
    }

    //getting ID
    public Integer getId() { return this.fdcId; }

    //method used to return the value of either kcal, fats, carbs or protein based on nutrientID
    private double getValue(int nutrientID) {

        for (FoodNutrients nutrient : this.getNutrientsList()) {
            if (nutrient.returnNutrientId() == nutrientID) {
                return nutrient.returnValue();
            }
        }
        return -1;
    }

    //method will process Json Array of the Food object to obtain Array of FoodNutritions objects
    private FoodNutrients[] getNutrientsList() {

        //Creating gson object to create FoodNutrients object from Json
        Gson gson = new Gson();
        JsonArray JsonResult = this.foodNutrients;

        //Returning results as an object
        return gson.fromJson(JsonResult, FoodNutrients[].class);
    }
}
