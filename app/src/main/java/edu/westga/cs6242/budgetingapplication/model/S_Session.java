package edu.westga.cs6242.budgetingapplication.model;

/**
 * Session Object to be passed around by the application
 */
public class S_Session {
    private static S_Session ourInstance = new S_Session();

    public static S_Session getInstance() {
        return ourInstance;
    }

    public static User user = new User();

    public static MonthlyBudget monthlyBudgetA = new MonthlyBudget();

    private S_Session() {

    }



}
