package edu.westga.cs6242.budgetingapplication.model;

import java.util.Date;

/**
 * Represents a MonthlyBudget
 * @author Patrick Dean
 * @version 1
 */
public class MonthlyBudget {
    private int id;
    private String title;
    private String description;
    private Date dateCreated;
    private Date dateUpdated;
    private int userId;

    public MonthlyBudget() {
        this(0, "", new Date(), new Date(), 0);
    }

    public MonthlyBudget(int id, String title, Date dateCreated, Date dateUpdated, int userId) {
        this.id = id;
        this.title = title;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    @Override
    public String toString() {
        return Integer.toString(this.getId()) + " - " +  this.getTitle();
    }

}
