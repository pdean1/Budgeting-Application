package edu.westga.cs6242.budgetingapplication.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Represents a Bill.
 * @author Patrick Dean
 * @version 1
 */
public class Bill {
    // if stored in the database, id is equal to the Bill's Primary Key
    private Integer id;
    // Title of the bill, i.e. "Rent"
    private String title;
    // Amount the bill is, i.e. "426.53"
    private BigDecimal amount;
    // Date the bill is due
    private Date dateDue;
    // Date the bill was paid
    private Date datePaid;
    // true if bill has been paid, false otherwise
    private Boolean isPaid;
    // true if bill comes every month, false if one time or infrequent bill
    private Boolean isRecurring;

    public Bill() {

    }

    public Integer getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getAmount() {
        return this.amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getDateDue() {
        return this.dateDue;
    }

    public void setDateDue(Date dateDue) {
        this.dateDue = new Date(dateDue.getTime());
    }

    public Date getDatePaid() {
        return this.datePaid;
    }

    public void setDatePaid(Date datePaid) {
        this.datePaid = new Date(datePaid.getTime());
    }

    public Boolean getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(Boolean isPaid) {
        this.isPaid = isPaid;
    }

    public Boolean getIsRecurring() {
        return this.isRecurring;
    }

    public void setIsRecurring(Boolean isRecurring) {
        this.isRecurring = isRecurring;
    }
}
