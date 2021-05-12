package keray.domain;


import java.util.ArrayList;

public class Food {
    private int fdcId;
    private String description;
    private String foodCategory;
    private ArrayList<FoodNutrients> foodNutrients;

    public String getDescription() {
        return this.description;
    }

    public String getFoodCategory() {
        return this.foodCategory;
    }

    public double getKcal() {
        return getValue(1008);
    }

    public double getProteins() {
        return getValue(1003);
    }

    public double getFats() {
        return getValue(1004);
    }

    public double getCarbs() {
        return getValue(1005);
    }




    //method used to return the value of either kcal, fats, carbs or protein based on nutrientID
    public double getValue(int number) {
        for (FoodNutrients nutrient: foodNutrients) {
            if (nutrient.returnNutrientId() == number) {
                return nutrient.returnValue();
            }
        }
        return -1;
    }


    @Override
    public String toString() {
        return this.fdcId + " " + this.description + " " + this.foodCategory + " " + this.getKcal();
    }
}
