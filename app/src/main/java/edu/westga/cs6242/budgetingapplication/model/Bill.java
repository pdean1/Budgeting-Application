package edu.westga.cs6242.budgetingapplication.model;

import edu.westga.cs6242.budgetingapplication.model.base_classes.BaseBudgetRecord;
import edu.westga.cs6242.budgetingapplication.util.session.Session;

/**
 * Represents a Bill
 *
 * @author Patrick Dean
 * @version 1
 */
public class Bill extends BaseBudgetRecord {

    private String dateDue;
    private String datePaid;
    private boolean isPaid;

    public Bill() {
        super();
        this.dateDue = "";
        this.datePaid = "";
        this.isPaid = false;
    }

    @Override
    public String getInformation() {
        String paid = this.isPaid() ? "Paid" : "Not paid";
        String recurring = this.isRecurring() ? "Recurring" : "Not Recurring";
        return this.getTitle() + "\n" +
                Session.numberFormat.format(this.getAmount()) + "\n" +
                this.getDateDue()  + "\n" +
                paid + "\n" +
                recurring;
    }

    public String getDateDue() {
        return dateDue;
    }

    public void setDateDue(String dateDue) {
        this.dateDue = dateDue;
    }

    public String getDatePaid() {
        return datePaid;
    }

    public void setDatePaid(String datePaid) {
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
