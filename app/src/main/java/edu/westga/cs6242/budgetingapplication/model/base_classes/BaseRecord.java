package edu.westga.cs6242.budgetingapplication.model.base_classes;

/**
 * Base Record Class which all database model classes inherit
 * @author Patrick Dean
 * @version 1
 */
public abstract class BaseRecord {
    private int id;
    private String title;
    public BaseRecord() {
        this(0, "");
    }

    public BaseRecord(int id, String title) {
        this.id = id;
        this.title = title;
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

    public abstract String getInformation();
}
