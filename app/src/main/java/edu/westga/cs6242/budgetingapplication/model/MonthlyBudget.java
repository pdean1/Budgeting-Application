package edu.westga.cs6242.budgetingapplication.model;

import java.util.ArrayList;
import java.util.Date;

/**
 * Represents a monthly budget. This class mainly 'holds' things
 * @author Patrick Dean
 * @version 1
 */
public class MonthlyBudget {
    // Fields in the database
    // id uniquely identifies this MonthlyBudget in the database
    private Integer id;
    // Title for this monthly budget
    private String  title;
    // Description the user can add to give extra meaning to the MonthlyBudget
    private String  description;
    // Date this Budget was created
    private Date    dateCreated;
    // Date this budget was updated
    private Date    dateUpdated;
    // User id this budget is associated to
    private Integer userID;

    // Bills associated with this budget
    private ArrayList<Bill> bills;
}
