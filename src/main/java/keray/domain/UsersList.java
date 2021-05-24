package keray.domain;

import java.util.ArrayList;

public class UsersList {
    private ArrayList<Person> list;

    public UsersList() {
        this.list = new ArrayList<>();
    }

    public ArrayList<Person> getUsersList() {
        return this.list;
    }
}
