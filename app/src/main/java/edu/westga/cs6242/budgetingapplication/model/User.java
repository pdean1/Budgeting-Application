package edu.westga.cs6242.budgetingapplication.model;

import edu.westga.cs6242.budgetingapplication.model.base_classes.BaseUser;

/**
 * Represents a User Stored in the Database
 *
 * @author Patrick Dean
 * @version 1
 */
public class User extends BaseUser {

    public User() {
        super();
    }

    @Override
    public String getLoginText() {
        return "Logged in as: " + super.getUserName();
    }

}
