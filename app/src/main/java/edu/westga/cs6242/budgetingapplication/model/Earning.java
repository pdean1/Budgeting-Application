package edu.westga.cs6242.budgetingapplication.model;

import java.util.Date;

import edu.westga.cs6242.budgetingapplication.model.base_classes.BaseBudgetRecord;
import edu.westga.cs6242.budgetingapplication.util.session.Session;

/**
 * Represents earnings
 *
 * @author Patrick Dean
 * @version 1
 */
public class Earning extends BaseBudgetRecord {
    // NumberFormat formatter = NumberFormat.getCurrencyInstance();
    // System.out.println(formatter.format(amt));
    private Date dateEarned;

    public Earning() {
        this(0, "", 0.0, new Date(), false, 0);
    }

    public Earning(int id, String title, double amount, Date dateEarned, boolean isRecurring, int budgetId) {
        super(id, title, amount, isRecurring, budgetId);
        this.dateEarned = dateEarned;
    }

    @Override
    public String getInformation() {
        String recurring = this.isRecurring() ? "Recurring" : "Not recurring";
        return this.getTitle() + "\n" +
                Session.numberFormat.format(this.getAmount()) + "\n" +
                Session.dateFormatView.format(this.getDateEarned()) + "\n" +
                recurring;
    }

    public Date getDateEarned() {
        return dateEarned;
    }

    public void setDateEarned(Date dateEarned) {
        this.dateEarned = dateEarned;
    }

    @Override
    public String toString() {
        return this.getInformation();
    }
}
