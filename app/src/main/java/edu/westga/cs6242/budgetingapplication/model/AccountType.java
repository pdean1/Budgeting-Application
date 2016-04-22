package edu.westga.cs6242.budgetingapplication.model;

/**
 * Represents an Account Type
 * @author Patrick Dean
 * @version 1
 */
public class AccountType {
    private int id;
    private String title;
    private String description;

    public AccountType() {
        this(0, "", "");
    }

    public AccountType(int id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
