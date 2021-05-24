package keray.domain;

import java.util.ArrayList;

//the class will analyze person according to a dietary needs. Through the variable "multiplier" the maintanance
//amount of nutritients will be adjusted(standard value is a 32 for most) and through variable "caloryRate"
//this nutritional demand will be multiplied according to a plans(loosing or gaining weight)

public class Person {
    private String name;
    private int height;
    private double weight;
    private double waistCircumference;
    private double caloryRate;
    private int multiplier;
    private int kcalDemand;
    private int minProtein;
    private int minCarbs;
    private int minFats;
    private ArrayList<Integer> eatenToday;

    public Person(String name, int height, double weight, double waistCircumference) {
        this.name = name;

        if (weight > 700) { weight = 700; }
        if (weight < 30) { weight = 30; }
        this.weight = weight;
        this.waistCircumference = waistCircumference;

        if (height > 300) { height = 300; }
        if (height < 40) { height = 40; }
        this.height = height;
        this.multiplier = 32;
        this.caloryRate = 1;
        this.kcalDemand = Math.toIntExact(Math.round(weight * this.multiplier));
        this.minProtein = Math.toIntExact(Math.round(weight * 1.8));
        this.minFats = Math.toIntExact(Math.round(weight * this.multiplier * 0.0234375));
        this.minCarbs = Math.toIntExact(Math.round(weight * this.multiplier * 0.05));
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

    //updating weight
    public void setWeight(double weight) {
        this.weight = weight;
        this.changeIntake();
    }

    //other methods required by application
    public double getBodyFat() {
        double bf = 64 - (20* (this.height/this.waistCircumference));
        return bf;
    }

    //cutting kcal intake
    public void cutIntake(int percent) {
        this.caloryRate = 1 - 1.0 * (percent/100);
        this.changeIntake();
    }

    //adding to a kcal intake
    public void addIntake(int percent) {
        this.caloryRate = 1 + 1.0 * (percent/100);
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

}
