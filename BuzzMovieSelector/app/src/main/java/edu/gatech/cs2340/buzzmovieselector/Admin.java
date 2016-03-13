package edu.gatech.cs2340.buzzmovieselector;

/**
 * Created by SysAdmin on 3/6/16.
 */
public class Admin {
    private String adminUserName;
    private String adminPassWord;


    /**
     * Creates an admin object
     */
    public Admin () {
        adminUserName = "admin";
        adminPassWord = "pass";
    }


    /**
     *
     * @return admin username
     */
    public String getAdminUserName() {
        return this.adminUserName;
    }


    /**
     *
     * @return admin password
     */
    public String getAdminPassWord() {
        return this.adminPassWord;
    }
}
