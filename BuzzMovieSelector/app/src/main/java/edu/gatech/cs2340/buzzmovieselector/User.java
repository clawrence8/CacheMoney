package edu.gatech.cs2340.buzzmovieselector;

/**
 * Created by naudghebre on 2/5/16.
 */
public class User {
    private String mName;
    private String mGtid;
    private String mEmail;
    private String mPassword;
    private String mMajor;
    private String mGender;
    private String mInterests;
    private int loginAttempts;
    private String gtid;

    public User(String name, String gtid, String email, String password, String major,
                String gender, String interests) {
        mName = name;
        mGtid = gtid;
        mEmail = email;
        mPassword = password;
        mMajor = major;
        mGender = gender;
        mInterests = interests;
    }



    public boolean checkPassword(String pass) {
        return mPassword.equals(pass);
    }

    public void addLoginAttempt() {
        loginAttempts +=1;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getGtid() {
        return mGtid;
    }

    public void setGtid(String mGtid) {
        this.mGtid = mGtid;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    public String getMajor() {
        return mMajor;
    }

    public void setMajor(String mMajor) {
        this.mMajor = mMajor;
    }

    public String getGender() {
        return mGender;
    }

    public void setGender(String mGender) {
        this.mGender = mGender;
    }

    public String getInterests() {
        return mInterests;
    }

    public void setInterests(String mInterests) {
        this.mInterests = mInterests;
    }

    public int getLoginAttempts() {
        return loginAttempts;
    }

    public void setLoginAttempts(int loginAttempts) {
        this.loginAttempts = loginAttempts;
    }

}
