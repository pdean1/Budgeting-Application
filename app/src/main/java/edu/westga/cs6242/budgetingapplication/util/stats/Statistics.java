package edu.westga.cs6242.budgetingapplication.util.stats;

import java.util.ArrayList;

import edu.westga.cs6242.budgetingapplication.model.Bill;
import edu.westga.cs6242.budgetingapplication.model.Earning;

/**
 * Gives us useful statistics about this month's budget
 * @author Patrick Dean
 * @version 1
 */
public class Statistics {
    private ArrayList<Bill> bills;
    private ArrayList<Earning> earnings;
    private Double billTotal;
    private Double earningTotal;
    private int billCount;
    private int earningCount;

    public Statistics(ArrayList<Bill> bills, ArrayList<Earning> earnings) {
        this.bills = bills;
        this.earnings = earnings;
        this.billCount = bills.size();
        this.earningCount = earnings.size();
        this.billTotal = 0.0;
        for (Bill bill : bills) {
            this.billTotal += bill.getAmount();
        }
        this.earningTotal = 0.0;
        for (Earning earning : earnings) {
            this.earningTotal += earning.getAmount();
        }
    }

    public ArrayList<Bill> getBills() {
        return bills;
    }

    public ArrayList<Earning> getEarnings() {
        return earnings;
    }

    public Double getBillTotal() {
        return billTotal;
    }

    public Double getEarningTotal() {
        return earningTotal;
    }

    public int getBillCount() {
        return billCount;
    }

    public int getEarningCount() {
        return earningCount;
    }
}
