package edu.gatech.cs2340.buzzmovieselector;

/**
 * Created by naudghebre on 2/5/16.
 */
public class User {
    String name;
    String password;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public boolean checkPassword(String pass) {
        return password.equals(pass);
    }
}
