package keray.logic;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import keray.domain.Person;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

//class will retieve and add users to DB
public class DbUsers {

    //method returns a list of users
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
                double caloryRate = result.getDouble("usercaloryrate");

                Person newPerson = new Person(name,height, weight, waist, multiplier, caloryRate);
                newPerson.setId(id);

                usersArrayList.add(newPerson);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ObservableList<Person> resultList = FXCollections.observableArrayList(usersArrayList);

        return resultList;
    }
    //method will add user to database
    public static void addUserToDb(Person person) {

        //converting person's data for SQL database
        String name = person.getName();
        int height = person.getHeight();
        double weight = person.getWeight();
        double waist = person.getWaistCircumference();
        int multiplier = person.getMultiplier();
        double caloryRate = person.getCaloryRate();

        //using data to create a query
        String dbQuery = "INSERT INTO users(username, userheight, userweight, userwaist, usermultiplier, usercaloryrate) " +
                "VALUES ('" + name + "', " + height + ", " + weight + ", " + waist + ", " +
                multiplier + ", " + caloryRate + ")";

        //sending a query and effectively adding user to the DB. Setting sql generated ID to the java object
        int personsId = DbConnector.addingNewRecordToDb(dbQuery);
        person.setId(personsId);
    }


    //method updates the calory rate(rate according to the plans)
    public static void updateCaloryRate(Person person) {

        double caloryRate = person.getCaloryRate();
        //int userId = person.getId();

        //String dbUpdateQuery = "UPDATE users SET usercaloryrate = " + caloryRate + " WHERE idusers = " + userId + ";";
        //DbConnector.updateValueInDb(dbUpdateQuery);
        updatePerson(person, "usercaloryrate", caloryRate);

    }

    //method updates the multiplier(basic calory demand)
    public static void updateMultiplier(Person person, int multiplier) {

        //int userId = person.getId();
        //String dbUpdateQuery = "UPDATE users SET usermultiplier = " + multiplier + " WHERE idusers = " + userId + ";";
        //DbConnector.updateValueInDb(dbUpdateQuery);
        updatePerson(person, "usermultiplier", multiplier);
    }

    //update user weight
    public static void updateWeight(Person person, int weight) {
        //int userId = person.getId();

        //String dbUpdateQuery = "UPDATE users SET userweight = " + weight + " WHERE idusers = " + userId + ";";
        //DbConnector.updateValueInDb(dbUpdateQuery);
        updatePerson(person, "userweight", weight);

    }

    //update user waist circumference
    public static void updateWaist(Person person, int waist) {
        //int userId = person.getId();

        //String dbUpdateQuery = "UPDATE users SET userwaist = " + waist + " WHERE idusers = " + userId + ";";
        //DbConnector.updateValueInDb(dbUpdateQuery);
        updatePerson(person, "userwaist", waist);
    }

    //updating table functionality
    private static void updatePerson(Person person, String column, Object value) {
        int userId = person.getId();

        String valueString = String.valueOf(value);

        String dbUpdateQuery = "UPDATE users SET " + column + " = " + valueString + " WHERE idusers = " + userId + ";";
        DbConnector.updateValueInDb(dbUpdateQuery);
    }
}
