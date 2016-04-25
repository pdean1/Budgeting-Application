package edu.westga.cs6242.budgetingapplication.model;

import edu.westga.cs6242.budgetingapplication.model.base_classes.BaseUser;

/**
 * Represents a User Stored in the Database
 *
 * @author Patrick Dean
 * @version 1
 */
public class User extends BaseUser {
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
        super(id, userName, password);
    }

    @Override
    public String getLoginText() {
        return "Logged in as: " + super.getUserName();
    }

}
