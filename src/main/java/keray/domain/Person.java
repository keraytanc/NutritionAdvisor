package keray.domain;

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
    private HashMap<Integer, Integer> eatenToday;

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
        this.eatenToday = new HashMap<>();
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
    public HashMap<Integer, Integer> getEatenToday() { return eatenToday; }

    //ID setter
    public void setId(int id) { this.id = id; }

    //updating weight
    public void setWeight(int weight) {
        this.weight = weight;
        this.changeIntake();
    }

    public void setWaist(int waist) {
        this.waistCircumference = waist;
    }

    //other methods required by application
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
        this.toString();
        this.changeIntake();
        this.toString();
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

    //toString method to check users for testing
    @Override
    public String toString() {
        return this.name + this.weight + this.waistCircumference + this.height + this.multiplier + " " +
                this.caloryRate + " " + this.kcalDemand + " " + this.minProtein + " " + this.minFats + " " + this.minCarbs;
    }

}
