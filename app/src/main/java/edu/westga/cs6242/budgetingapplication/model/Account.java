package edu.westga.cs6242.budgetingapplication.model;

import java.util.Date;

/**
 * Represents an account
 * @author Patrick Dean
 * @version 1
 */
public class Account {
    private int id;
    private String description;
    private Date dateCreated;
    private Date dateUpdated;
    private int userId;
    private int accountTypeId;

    public Account() {
        this(0, "", new Date(), new Date(), 0, 0);
    }

    public Account(int id, String description, Date dateCreated,
                   Date dateUpdated, int userId, int accountTypeId)
    {
        this.id = id;
        this.description = description;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;
        this.userId = userId;
        this.accountTypeId = accountTypeId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(Date dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getAccountTypeId() {
        return accountTypeId;
    }

    public void setAccountTypeId(int accountTypeId) {
        this.accountTypeId = accountTypeId;
    }
}
