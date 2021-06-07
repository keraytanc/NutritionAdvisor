package keray;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import keray.ui.MainUI;
import keray.ui.MotherUI;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {


        //testing layout

        //ponizej test dodawania uzytkownika
        //DbConnector.addUserToDb("INSERT INTO users(username, userheight, userweight, userwaist, usermultiplier, usercaloryrate) VALUES ('Tomek', 172, 85, 100, 31, 1.1)");

        /*ArrayList<Person> listToRead = DbUsers.getUsersFromDb();

        for (Person person: listToRead) {
            System.out.println(person);
        }


         */




        MotherUI motherUI = new MotherUI();
        Scene mainScene = motherUI.getScene();
        stage.setScene(mainScene);

        stage.show();

    }
    public static void main(String[] args) {
        launch();
    }

}