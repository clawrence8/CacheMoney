package edu.gatech.cs2340.buzzmovieselector;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by SysAdmin on 2/16/16.
 */
public class UserManager implements AuthenticationMeasure {
    private static UserManager ourInstance = null;
    private static Map<String, User> usersList = new HashMap<>();
    private User currentUser;
    private int userCount;

    /**
     *Single usermanager for the entire application
     * @return user manager
     */

    public static UserManager getInstance() {
        if (ourInstance == null) {
            ourInstance = new UserManager();
        }
        return ourInstance;
    }

    /**
     * Creates new user manager object
     */

    private UserManager() {
    }

    /**
     *Sets the current user of the application
     * @param user current user
     */

    private UserManager(User user) {
        this.currentUser = user;
    }

    /**
     *Adds user to userlist
     * @param newUser new user of the application
     */

    public void addUser(User newUser) {
        usersList.put(newUser.getName(), newUser);
        userCount++;
        setCurrentUser(newUser);
    }

    /**
     *Removes user from userlist
     * @param user user to be removed
     */


    public void removeUser(User user) {
        usersList.remove(user);
        userCount--;
    }

    /**Sets the current user of the application
     *
     * @param user current user
     */

    private void setCurrentUser(User user) {
        this.currentUser = user;
    }

    /**
     *
     * @param id id for username text field
     * @return user from userlist
     */

    public User findUserById(String id) {
        return usersList.get(id);
    }

    /**
     *
     * @return count of how many users we have on the userlist
     */

    public int userListCount() {
        return userCount;
    }

    /**
     *
     * @return current user of the application
     */

    public User retrieveCurrentUser() {
        return currentUser;
    }

    /**
     *
     * @param name user's name
     * @param pass user's password
     * @return boolean of if user input is valid and matches a user from the userlist
     */

    public boolean handleLoginAttempt(String name, String pass) {

        User u = findUserById(name);
        if (u == null) return false;
        return u.checkPassword(pass);
    }
}
