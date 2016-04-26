package edu.westga.cs6242.budgetingapplication.model.base_classes;

/**
 * BaseBudgetRecord class that should be extended if you need too interact with the budgeting database
 */
public abstract class BaseBudgetRecord extends BaseRecord {

    private double amount;
    private boolean isRecurring;
    private int budgetId;

    public BaseBudgetRecord() {
        super(0, "");
        this.amount = 0.0;
        this.isRecurring = false;
        this.budgetId = 0;
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
