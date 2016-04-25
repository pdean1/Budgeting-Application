package edu.westga.cs6242.budgetingapplication.model;

/**
 * Represents a User Stored in the Database
 *
 * @author Patrick Dean
 * @version 1
 */
public class User {
    private int id;
    private String userName;
    private String password;

    /**
     * Default User Constructor
     */
    public User() {
        this(0, "", "");
    }

    /**
     * Creates a User with custom values
     *
     * @param id       User's ID or 0
     * @param userName User's User Name
     * @param password User's Password
     */
    public User(int id, String userName, String password) {
        this.id = id;
        this.userName = userName;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
