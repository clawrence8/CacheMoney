package edu.gatech.cs2340.buzzmovieselector;

import com.firebase.client.Firebase;

/**
 * Created by naudghebre on 2/5/16.
 */
public class User {
    private String mName;
    private String mUsername;
    private String mEmail;
    private String mPassword;
    private String mMajor;
    private String mGender;
    private String mInterests;
    private int loginAttempts;
    private String username;
    private String mUserStatus;
    /**
     *
     * @param name user name
     * @param username user username
     * @param email user email
     * @param password user password
     * @param major user major
     * @param gender user's gender
     * @param interests users' movie interest
     */

    public User(String name, String username, String email, String password, String major,
                String gender, String interests) {
        mName = name;
        mUsername = username;
        mEmail = email;
        mPassword = password;
        mMajor = major;
        mGender = gender;
        mInterests = interests;
        mUserStatus = "Active";
    }

    /**
     *
     * @param pass password input
     * @return boolean value if the password input matches a valid password in the user list
     */

    public boolean checkPassword(String pass) {
        return mPassword.equals(pass);
    }

    /**
     * Tracks number of times user attempts to log in
     */

    public void addLoginAttempt() {
        loginAttempts +=1;
    }

    /**
     *
     * @return user name
     */

    public String getName() {
        return mName;
    }

    /**
     *
     * @param mName user name
     */

    public void setName(String mName) {
        this.mName = mName;
    }

    /**
     *
     * @return returns user's username
     */

    public String getUsername() {
        return mUsername;
    }

    /**
     *
     * @param mUsername user's username
     */

    public void setUsername(String mUsername) {
        this.mUsername = mUsername;
    }

    /**
     *
     * @return user's email
     */

    public String getEmail() {
        return mEmail;
    }

    /**
     *
     * @param mEmail user's email
     */

    public void setEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    /**
     *
     * @return user's password
     */

    public String getPassword() {
        return mPassword;
    }

    /**
     *
     * @param mPassword user's password
     */

    public void setPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    /**
     *
     * @return user's major
     */

    public String getMajor() {
        return mMajor;
    }

    /**
     *
     * @param mMajor user's major
     */

    public void setMajor(String mMajor) {
        this.mMajor = mMajor;
    }

    /**
     *
     * @return user's gender
     */

    public String getGender() {
        return mGender;
    }

    /**
     *
     * @param mGender user's gender
     */

    public void setGender(String mGender) {
        this.mGender = mGender;
    }

    /**
     *
     * @return user's listed interests
     */

    public String getInterests() {
        return mInterests;
    }

    /**
     *
     * @param mInterests user's interests
     */

    public void setInterests(String mInterests) {
        this.mInterests = mInterests;
    }

    /**
     *
     * @return number of times user has attempted to log in
     */

    public int getLoginAttempts() {
        return loginAttempts;
    }

    /**
     *
     * @param loginAttempts sets the number of allowable attempts to log in
     */

    public void setLoginAttempts(int loginAttempts) {
        this.loginAttempts = loginAttempts;
    }

    /**
     *
     * @return the current users status
     */
    public String getStatus(){
        return mUserStatus;
    }

    /**
     *
     * @param status the account of status of the current user
     */
    public void setUserStatus(String status) {
        this.mUserStatus = status;
    }

}
