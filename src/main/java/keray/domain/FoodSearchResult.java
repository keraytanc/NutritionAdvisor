package keray.domain;

import java.util.ArrayList;

public class FoodSearchResult {
    private final ArrayList<Food> foods = new ArrayList<>();

    public ArrayList<Food> getFoodList() {
        return this.foods;
    }
}
