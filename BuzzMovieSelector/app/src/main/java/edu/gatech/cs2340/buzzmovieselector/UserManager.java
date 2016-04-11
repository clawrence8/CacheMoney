package edu.gatech.cs2340.buzzmovieselector;

import android.app.Activity;
import android.app.Application;
import android.app.Instrumentation;
import android.app.Instrumentation;
import android.app.Service;
import android.util.Log;
import android.widget.Toast;
import android.content.Context;

import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.snapshot.IndexedNode;
import com.firebase.client.snapshot.Node;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by SysAdmin on 2/16/16.
 */
public class UserManager {
    private static UserManager ourInstance = null;
    private Map<String, User> usersList = new HashMap<>();
    private User currentUser;
    private int userCount;
    private Context managerContext;
    private Firebase database = new Firebase("https://buzz-movie-selector5.firebaseio.com/");
    private Firebase userTable = new Firebase("https://buzz-movie-selector5.firebaseio.com/Users/");


    /**
     *Single usermanager for the entire application
     * @return user manager
     */

    public static UserManager getInstance() {
        if (ourInstance == null) {
            ourInstance = new UserManager();
        }
        Context d = new Instrumentation().getTargetContext();
        ourInstance.setContext(d);
        return ourInstance;
    }

    public static UserManager getInstance(Context c) {
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
        if (newUser != null) {
            usersList.put(newUser.getName(), newUser);
            userCount++;
            setCurrentUser(newUser);
            userTable.child(newUser.getUsername()).setValue(newUser);
        }

    }

    /*
     * Sets an Android context for the manager
     * @param c the new context for the manager
     */
    public void setContext(Context c) {
        managerContext = c;
    }

    /**
     *
     *
     * returns reference to the database
     * @return reference to the database
     */
    public Firebase getDatabase() {
        return database;
    }

    /**
     * returns reference to the user table
     * @return reference to the user table
     */
    public Firebase getUserTable() {
        return userTable;
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

    public void setCurrentUser(User user) {
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

    public User getCurrentUser() {
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

    public Map<String, User> getUserList() {
        return usersList;
    }

}
