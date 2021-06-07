package keray.logic;

import java.sql.*;

public class DbConnector {

    private static String URL = "jdbc:mysql://localhost:3306/nutrition";
    private static String user = "root";
    private static String password = "Qwerty1!";

    //method connects with database
    public static Connection connect() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, user, password);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return connection;
    }

    //methods sends SELECT query to database
    public static ResultSet selectQuery(String query) {
        ResultSet result = null;
        try {
            Connection connection = connect();
            Statement statement = connection.createStatement();
            result = statement.executeQuery(query);
        } catch (SQLException throwables) {
            System.out.println("failed select query");
            throwables.printStackTrace();
        }
        return result;
    }

    //method adds new record to the db and returns ID of new record
    public static Integer addingNewRecordToDb(String query) {
        Integer newId = 0;
        try {
            Connection connection = connect();
            Statement statement = connection.createStatement();
            statement.execute(query);
            ResultSet result = statement.executeQuery("SELECT last_insert_id();");
            result.next();
            newId = result.getInt("last_insert_id()");
        } catch (SQLException throwables) {
            System.out.println("failed query");
            throwables.printStackTrace();
        }
        return newId;
    }

    //method update chosen value in the database
    public static void updateValueInDb(String query) {

        try {
            Connection connection = connect();
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException throwables) {
            System.out.println("failed update query");
            throwables.printStackTrace();
        }

    }


}
