package edu.westga.cs6242.budgetingapplication.model;

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
    private String dateEarned;

    public Earning() {
        super();
        this.dateEarned = "";
    }

    @Override
    public String getInformation() {
        String recurring = this.isRecurring() ? "Recurring" : "Not recurring";
        return this.getTitle() + "\n" +
                Session.numberFormat.format(this.getAmount()) + "\n" +
                this.getDateEarned() + "\n" +
                recurring;
    }

    public String getDateEarned() {
        return dateEarned;
    }

    public void setDateEarned(String dateEarned) {
        this.dateEarned = dateEarned;
    }

    @Override
    public String toString() {
        return this.getInformation();
    }
}
