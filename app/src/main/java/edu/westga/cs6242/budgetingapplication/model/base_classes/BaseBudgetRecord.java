package edu.westga.cs6242.budgetingapplication.model.base_classes;

/**
 * Created by Patrick on 4/25/2016.
 */
public abstract class BaseBudgetRecord extends BaseRecord {

    private double amount;
    private boolean isRecurring;
    private int budgetId;

    public BaseBudgetRecord() {
        this(0, "", 0.0, false, 0);
    }

    public BaseBudgetRecord(int id, String title, double amount, boolean isRecurring, int budgetId) {
        super(id, title);
        this.amount = amount;
        this.isRecurring = isRecurring;
        this.budgetId = budgetId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public boolean isRecurring() {
        return isRecurring;
    }

    public void setIsRecurring(boolean isRecurring) {
        this.isRecurring = isRecurring;
    }

    public int getBudgetId() {
        return budgetId;
    }

    public void setBudgetId(int budgetId) {
        this.budgetId = budgetId;
    }

}
