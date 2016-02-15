package edu.gatech.cs2340.buzzmovieselector;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by naudghebre on 2/5/16.
 */
public class UserManager implements AuthenticationMeasure {

    private static Map<String, User> usersList = new HashMap<>();
    private static UserManager manager;
    private User currentUser;
    private int userCount;

    public void addUser(User newUser) {
        usersList.put(newUser.getName(), newUser);
        userCount++;
        setCurrentUser(newUser);
    }

    public void UserManager(User currentUser) {
        this.currentUser = currentUser;
    }

    public void removeUser(User user) {
        usersList.remove(user);
        userCount--;
    }

    private void setCurrentUser(User user) {
        this.currentUser = user;
    }

    public User findUserById(String id) {
        return usersList.get(id);
    }

    public int userListCount() {
        return userCount;
    }

    public User retrieveCurrentUser() {
        return currentUser;
    }

    public boolean handleLoginAttempt(String name, String pass) {

        User u = findUserById(name);
        if (u == null) return false;
        return u.checkPassword(pass);
    }
}
