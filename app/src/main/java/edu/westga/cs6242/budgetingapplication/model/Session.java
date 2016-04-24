package edu.westga.cs6242.budgetingapplication.model;

/**
 * Created by Patrick on 4/23/2016.
 */
public class Session {
    private static Session ourInstance = new Session();
    private static User user = new User();
    private static MonthlyBudget budget_1 = new MonthlyBudget();
    private static MonthlyBudget budget_2 = new MonthlyBudget();
    public static Session getInstance() {
        return ourInstance;
    }
    private Session() {
    }
    public static void setUser(User user) {
        Session.user = user;
    }
    public static void setMonthlyBudget1(MonthlyBudget budget) {
        Session.budget_1 = budget;
    }
    public static void setMonthlyBudget2(MonthlyBudget budget) {
        Session.budget_2 = budget;
    }

    public static User getUser() {
        return Session.user;
    }
    public static MonthlyBudget getMonthlyBudget1() {
        return Session.budget_1;
    }
    public static MonthlyBudget getMonthlyBudget2() {
        return Session.budget_2;
    }
}
