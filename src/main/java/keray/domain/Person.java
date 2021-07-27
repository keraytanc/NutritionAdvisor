package keray.domain;

import keray.logic.DbUsers;

import java.util.ArrayList;
import java.util.HashMap;

//the class will analyze person according to a dietary needs. Through the variable "multiplier" the maintanance
//amount of nutritients will be adjusted(standard value is a 32 for most) and through variable "caloryRate"
//this nutritional demand will be multiplied according to a plans(loosing or gaining weight)

public class Person {
    private int id;
    private String name;
    private int height;
    private int weight;
    private int waistCircumference;
    private double caloryRate;
    private int multiplier;
    private int kcalDemand;
    private int minProtein;
    private int minCarbs;
    private int minFats;
    private ArrayList<EatenFoodData> eatenToday;

    public Person(String name, int height, int weight, int waistCircumference, int multiplier, double caloryRate) {
        this.id = 0;
        this.name = name;

        if (weight > 700) { weight = 700; }
        if (weight < 30) { weight = 30; }
        this.weight = weight;
        this.waistCircumference = waistCircumference;

        if (height > 300) { height = 300; }
        if (height < 40) { height = 40; }
        this.height = height;
        this.multiplier = multiplier;
        this.caloryRate = caloryRate;
        this.kcalDemand = Math.toIntExact(Math.round(weight * this.multiplier * caloryRate));
        this.minProtein = Math.toIntExact(Math.round(weight * 1.8));
        this.minFats = Math.toIntExact(Math.round(weight * this.multiplier * 0.0234375 * caloryRate));
        this.minCarbs = Math.toIntExact(Math.round(weight * this.multiplier * 0.05 * caloryRate));
        this.eatenToday = new ArrayList<>();
    }

    //Getters
    public String getName() {
        return name;
    }
    public double getWeight() {
        return weight;
    }
    public double getWaistCircumference() {
        return waistCircumference;
    }
    public int getHeight() {
        return height;
    }
    public int getKcalDemand() {
        return kcalDemand;
    }
    public int getMinProtein() {
        return minProtein;
    }
    public int getMinCarbs() {
        return minCarbs;
    }
    public int getMinFats() {
        return minFats;
    }
    public double getCaloryRate() { return caloryRate; }
    public int getMultiplier() { return multiplier; }
    public int getId() { return id; }
    public ArrayList<EatenFoodData> getEatenToday() { return eatenToday; }

    //ID setter
    public void setId(int id) { this.id = id; }

    //updating weight
    public void setWeight(int weight) {
        this.weight = weight;
        this.changeIntake();
    }

    //updating waist circumference
    public void setWaist(int waist) {
        this.waistCircumference = waist;
    }

    //method returning bodyfat of a person according to a formula developed by academics
    public double getBodyFat() {
        double bf = 64 - (20* (1.0 * this.height/this.waistCircumference));

        //rounding bodyfat value into value with one decimal place
        long roundedBf = Math.round(10 * bf);
        bf = (1.0 * roundedBf) / 10;

        return bf;
    }

    //adding to a kcal intake
    public void addIntake(int percent) {
        this.caloryRate = 1 + (1.0 * percent/100);
        this.changeIntake();
    }

    //method will be used to set multiplier according to lifestyle(31 for sedentary, 33 for active and 32 for average)
    public void setMultiplier(int number) {
        this.multiplier = number;
        this.changeIntake();
    }

    //standard variable's value needed to calculate calories demand
    public void resetMultiplier() {
        this.multiplier = 32;
        this.changeIntake();
    }

    //adjusting variable needed to calculate calories demand
    public void lowerMultiplier() {
        this.multiplier = this.multiplier - 1;
        this.changeIntake();
    }

    //adjusting variable needed to calculate calories demand
    public void raiseMultiplier() {
        this.multiplier = this.multiplier + 1;
        this.changeIntake();
    }

    //internal method to adjust intake according to changing conditions
    private void changeIntake() {
        this.kcalDemand = Math.toIntExact(Math.round(weight * this.multiplier * this.caloryRate));
        this.minProtein = Math.toIntExact(Math.round(weight * 1.8));
        this.minFats = Math.toIntExact(Math.round(weight * this.multiplier * 0.0234375 * this.caloryRate));
        this.minCarbs = Math.toIntExact(Math.round(weight * this.multiplier * 0.05 * this.caloryRate));
    }

    //adding to the list of Foods eaten today
    public void addEatenFood(int id, int weight) {
        EatenFoodData eatenFoodToAdd = new EatenFoodData(id, weight);
        this.eatenToday.add(eatenFoodToAdd);
    }

    //deleting food from the list
    public void deleteFoodFromList(EatenFoodData food) {
        for (EatenFoodData foodFromList: this.eatenToday) {
            if (food.equals(foodFromList)) {
                this.eatenToday.remove(foodFromList);
                break;
            }
        }
    }

    //String representation of the eaten foods list for the database
    public String listAsAString() {

        StringBuilder list = new StringBuilder();
        for (EatenFoodData food: this.eatenToday){
            if (list.toString().isEmpty()) {
                list.append(food);
            } else {
                list.append(" " + food);
            }
        }
        return list.toString();
    }

    //amount of micronutrients eaten today
    public int getKcalEatenToday() {
        int kcalEaten = 0;
        for (EatenFoodData food: this.eatenToday) {
            int eaten = (int) Math.round(food.getFood().getKcal() * (1.0 * food.getWeight() / 100));
            kcalEaten = kcalEaten + eaten;
        }
        return kcalEaten;
    }

    public int getProtEatenToday() {
        int protEaten = 0;
        for (EatenFoodData food: this.eatenToday) {
            int eaten = (int) Math.round(food.getFood().getProteins() * (1.0 * food.getWeight() / 100));
            protEaten = protEaten + eaten;
        }
        return protEaten;
    }

    public int getFatEatenToday() {
        int fatEaten = 0;
        for (EatenFoodData food: this.eatenToday) {
            int eaten = (int) Math.round(food.getFood().getFats() * (1.0 * food.getWeight() / 100));
            fatEaten = fatEaten + eaten;
        }
        return fatEaten;
    }

    public int getCarbEatenToday() {
        int carbEaten = 0;
        for (EatenFoodData food: this.eatenToday) {
            int eaten = (int) Math.round(food.getFood().getCarbs() * (1.0 * food.getWeight() / 100));
            carbEaten = carbEaten + eaten;
        }
        return carbEaten;
    }


    //toString method to check users for testing
    @Override
    public String toString() {
        return this.name + this.weight + this.waistCircumference + this.height + this.multiplier + " " +
                this.caloryRate + " " + this.kcalDemand + " " + this.minProtein + " " + this.minFats + " " + this.minCarbs;
    }

}
