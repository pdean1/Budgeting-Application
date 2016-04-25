package edu.westga.cs6242.budgetingapplication.model;

import java.util.Date;

/**
 * Represents a Bill
 *
 * @author Patrick Dean
 * @version 1
 */
public class Bill {

    private int id;
    private String title;
    private double amount;
    private Date dateDue;
    private Date datePaid;
    private boolean isPaid;
    private boolean isRecurring;
    private int budgetId;

    public Bill() {
        this(0, "", 0.0, new Date(), new Date(), false, false, 0);
    }

    public Bill(int id, String title, double amount, Date dateDue,
                Date datePaid, boolean isPaid, boolean isRecurring,
                int budgetId) {
        this.id = id;
        this.title = title;
        this.amount = amount;
        this.dateDue = dateDue;
        this.datePaid = datePaid;
        this.isPaid = isPaid;
        this.isRecurring = isRecurring;
        this.budgetId = budgetId;
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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getDateDue() {
        return dateDue;
    }

    public void setDateDue(Date dateDue) {
        this.dateDue = dateDue;
    }

    public Date getDatePaid() {
        return datePaid;
    }

    public void setDatePaid(Date datePaid) {
        this.datePaid = datePaid;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setIsPaid(boolean isPaid) {
        this.isPaid = isPaid;
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

    @Override
    public String toString() {
        String paid = this.isPaid() ? "Paid" : "Not paid";
        return this.getTitle() + "\n" +
                this.getAmount() + "\n" +
                this.getDateDue() + "\n" +
                paid;
    }
}
