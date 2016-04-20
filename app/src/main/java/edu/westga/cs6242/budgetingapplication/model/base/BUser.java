package edu.westga.cs6242.budgetingapplication.model.base;

/**
 * Created by Patrick on 4/19/2016.
 */
public class BUser {

    private int id;
    private String userName;
    private String password;

    public BUser(int id, String userName, String password) {
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
