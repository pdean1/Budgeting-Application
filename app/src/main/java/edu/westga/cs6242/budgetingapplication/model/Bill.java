package edu.westga.cs6242.budgetingapplication.model;

import java.util.Date;

import edu.westga.cs6242.budgetingapplication.model.base_classes.BaseBudgetRecord;
import edu.westga.cs6242.budgetingapplication.util.session.Session;

/**
 * Represents a Bill
 *
 * @author Patrick Dean
 * @version 1
 */
public class Bill extends BaseBudgetRecord {

    private Date dateDue;
    private Date datePaid;
    private boolean isPaid;

    public Bill() {
        this(0, "", 0.0, new Date(), new Date(), false, false, 0);
    }

    public Bill(int id, String title, double amount, Date dateDue, Date datePaid, boolean isPaid,
                boolean isRecurring, int budgetId) {
        super(id, title, amount, isRecurring, budgetId);
        this.dateDue = dateDue;
        this.datePaid = datePaid;
        this.isPaid = isPaid;
    }

    @Override
    public String getInformation() {
        String paid = this.isPaid() ? "Paid" : "Not paid";
        String recurring = this.isRecurring() ? "Is Recurring" : "Not Recurring";
        return this.getTitle() + "\n" +
                Session.numberFormat.format(this.getAmount()) + "\n" +
                Session.dateFormatMMddddyyyy.format(this.getDateDue())  + "\n" +
                paid + "\n" +
                recurring;
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

    @Override
    public String toString() {
        return this.getInformation();
    }
}
