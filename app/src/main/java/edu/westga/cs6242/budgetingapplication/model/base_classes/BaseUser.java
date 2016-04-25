package edu.westga.cs6242.budgetingapplication.model.base_classes;

/**
 * BaseUser class that all Users must extend
 * @author Patrick Dean
 * @version 1
 */
public abstract class BaseUser {
    private int id;
    private String userName;
    private String password;
    /**
     * Default User Constructor
     */
    public BaseUser() {
        this(0, "", "");
    }

    /**
     * Creates a User with custom values
     *
     * @param id       User's ID or 0
     * @param userName User's User Name
     * @param password User's Password
     */
    public BaseUser(int id, String userName, String password) {
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

    public abstract String getLoginText();
}
