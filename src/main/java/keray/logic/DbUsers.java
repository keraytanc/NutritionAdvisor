package keray.logic;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.converter.LocalDateStringConverter;
import keray.domain.EatenFoodData;
import keray.domain.Person;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//class will retrieve and add users to DB
public class DbUsers {


    //method returns a list of users stored in database
    public static ObservableList<Person> getUsersFromDb() {

        ArrayList<Person> usersArrayList = new ArrayList<>();

        String dbQuery = "SELECT * FROM users";

        ResultSet result = DbConnector.selectQuery(dbQuery);
        try {
            while (result.next()) {
                int id = result.getInt("idusers");
                String name = result.getString("username");
                int height = result.getInt("userheight");
                int weight = result.getInt("userweight");
                int waist = result.getInt("userwaist");
                int multiplier = result.getInt("usermultiplier");
                double calorieRate = result.getDouble("usercaloryrate");

                Person newPerson = new Person(id, name,height, weight, waist, multiplier, calorieRate);

                usersArrayList.add(newPerson);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return FXCollections.observableArrayList(usersArrayList);
    }


    //method will add user to database
    public static void addUserToDb(Person person) {

        //converting person's data for SQL database
        String name = person.getName();
        int height = person.getHeight();
        double weight = person.getWeight();
        double waist = person.getWaistCircumference();
        int multiplier = person.getMultiplier();
        double calorieRate = person.getCalorieRate();
        String lastDate = getTodaysDate();

        //using data to create a query
        String dbQuery = "INSERT INTO users(username, userheight, userweight, userwaist, usermultiplier, usercaloryrate, lastdate) " +
                "VALUES ('" + name + "', " + height + ", " + weight + ", " + waist + ", " +
                multiplier + ", " + calorieRate + ", '" + lastDate + "')";

        //sending a query and effectively adding user to the DB. Setting sql generated ID to the java object
        int personsId = DbConnector.addingNewRecordToDb(dbQuery);
        person.setId(personsId);
    }


    //method will delete user from DB
    public static void deleteUserFromDb(Person person) {
        int id = person.getId();

        String query = "DELETE FROM users WHERE idusers = " + id + ";";
        DbConnector.updateValueInDb(query);
    }


    //method updates user's calorie rate(rate according to the plans)
    public static void updateCalorieRate(Person person) {

        double calorieRate = person.getCalorieRate();
        updatePerson(person, "usercaloryrate", calorieRate);
    }


    //method updates user's multiplier(basic calorie demand)
    public static void updateMultiplier(Person person, int multiplier) {
        updatePerson(person, "usermultiplier", multiplier);
    }


    //update user's weight
    public static void updateWeight(Person person, int weight) {
        updatePerson(person, "userweight", weight);
    }


    //update user's waist circumference
    public static void updateWaist(Person person, int waist) {
        updatePerson(person, "userwaist", waist);
    }


    //update list of eatenFoods
    public static void updateEatenFoodsInDb(Person person, String eatenFoods) {
        updatePerson(person, "eatenfood", eatenFoods);
    }


    //method will retrieve an ArrayList of foods eaten today from the database
    public static ArrayList<EatenFoodData> retrieveEatenFoodsFromDb(int personsId) {

        String foodListAsString = "";

        //if personsId = 0 it means that new person is created in person hence empty ArrayList should be returned
        if (personsId != 0) {
            //Eaten foods String retrieved from Database
            foodListAsString = returnEatenFoodsAsString(personsId);
        }

        //new ArrayList
        ArrayList<EatenFoodData> eatenFood = new ArrayList<>();


        //if the String is empty, an empty Arraylist is returned. Otherwise list of EatenFoodData objects is returned
        if (!foodListAsString.isEmpty()) {

            //Getting a list of String convertible to the EatenFoodData object(each object is separated by space)
            List<String> eatenFoods = Arrays.asList(foodListAsString.split(" "));

            //turning each String object into EatenFoodData object(each object consist of id and its weight separated by comma)
            eatenFoods.stream()
                    .map(string -> string.split(","))
                    .map(array -> new EatenFoodData(Integer.parseInt(array[0]), Integer.parseInt(array[1])))
                    .forEach(eatenFood::add);

            //returning the updated list of eaten foods
        }
        return eatenFood;
    }


    //updating user's chosen value in database
    private static void updatePerson(Person person, String column, Object value) {
        int userId = person.getId();

        String valueString = String.valueOf(value);

        String dbUpdateQuery = "UPDATE users SET " + column + " = \"" + valueString + "\" WHERE idusers = " + userId + ";";
        DbConnector.updateValueInDb(dbUpdateQuery);
    }


    //method will check if food list in the database is up to date(reset it if not) and return list of foods eaten today
    private static String returnEatenFoodsAsString(int personsId) {

        String lastDate = getLastDateFromDb(personsId);
        String todaysDate = getTodaysDate();

        return getCurrentFoodList(personsId, lastDate, todaysDate);
    }


    //getting the date of the last update from User's database as String
    private static String getLastDateFromDb(int personsId) {
        String lastDate = "";
        String lastDateQuery = "SELECT lastdate FROM users WHERE idusers = " + personsId + ";";
        ResultSet resultDate = DbConnector.selectQuery(lastDateQuery);
        try {
            resultDate.next();
            lastDate = resultDate.getString("lastdate");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if (lastDate==null) {
            lastDate = "";
        }
        return lastDate;
    }


    //getting todays date as string
    private static String getTodaysDate() {

        LocalDateStringConverter dateToStringConverter = new LocalDateStringConverter();
        return dateToStringConverter.toString(LocalDate.now());
    }


    //method check if the last update is still valid and return foods list accordingly
    private static String getCurrentFoodList(int personsId, String lastDate, String todaysDate) {
        //comparing todays date with the last date in database
        if (lastDate.equals(todaysDate) && personsId != 0) {

            //if true method returns current food list
            String foodList = "";
            String dbQuery = "SELECT eatenfood FROM users WHERE idusers = " + personsId + ";";
            ResultSet resultFoodList = DbConnector.selectQuery(dbQuery);
            try {
                resultFoodList.next();
                foodList = resultFoodList.getString("eatenfood");

                //in case of null return empty String
                if (foodList == null) {
                    foodList = "";
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            return foodList;
        } else {
            //if false method erases current foodlist, changes last date and returns empty String
            String dbUpdateQuery = "UPDATE users SET lastdate = '" + todaysDate + "', eatenfood = '' WHERE idusers = " + personsId + ";";
            DbConnector.updateValueInDb(dbUpdateQuery);
            return "";
        }
    }
}
