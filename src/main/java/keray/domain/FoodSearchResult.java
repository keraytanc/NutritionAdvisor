package keray.domain;

import com.google.gson.JsonArray;

import java.util.ArrayList;

public class FoodSearchResult {
    private ArrayList<Food> foods;

    public ArrayList<Food> getFoodList() {
        return this.foods;
    }
}
