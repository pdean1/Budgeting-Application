package edu.westga.cs6242.budgetingapplication.util.database;

/**
 * This class provides constant strings used to reference the
 * budget data base and it's tables
 *
 * @author Patrick Dean
 * @version 1
 */
public class BudgetDatabase {

    /**
     * Public inner class Users defines strings for the user table
     */
    public class Users {
        public static final String TABLE_NAME   = "Users";
        public static final String C1_PK_ID     = "id";
        public static final String C2_USER_NAME = "user_name";
        public static final String C3_PASSWORD  = "password";
        public static final String C4_EMAIL     = "email";
    }

    /**
     * Public inner class AccountType defines strings for the AccountType table
     */
    public class AccountType {
        public static final String TABLE_NAME     = "AccountType";
        public static final String C1_PK_ID       = "id";
        public static final String C2_TITLE       = "title";
        public static final String C3_DESCRIPTION = "description";
    }

    /**
     * Public inner class Accounts defines strings for the Accounts table
     */
    public class Accounts {
        public static final String TABLE_NAME             = "Accounts";
        public static final String C1_PK_ID               = "id";
        public static final String C2_DESCRIPTION         = "description";
        public static final String C3_DATE_CREATED        = "date_created";
        public static final String C4_DATE_UPDATED        = "date_updated";
        public static final String C5_FK1_USER_ID         = "user_id";
        public static final String C6_FK2_ACCOUNT_TYPE_ID = "account_type_id";
    }

    /**
     * Public inner class MonthlyBudget defines strings for the MonthlyBudget table
     */
    public class MonthlyBudget {
        public static final String TABLE_NAME      = "MonthlyBudget";
        public static final String C1_PK_ID        = "id";
        public static final String C2_TITLE        = "title";
        public static final String C3_DESCRIPTION  = "description";
        public static final String C4_DATE_CREATED = "date_created";
        public static final String C5_DATE_UPDATED = "date_updated";
        public static final String C6_FK1_USER_ID  = "user_id";
    }

    /**
     * Public inner class Transfers defines strings for the Transfers table
     */
    public class Transfers {
        public static final String TABLE_NAME             = "Transfers";
        public static final String C1_PK_ID               = "id";
        public static final String C2_TITLE               = "title";
        public static final String C3_AMOUNT              = "amount";
        public static final String C4_DATE_OF_TRANSFER    = "date_of_transfer";
        public static final String C5_IS_RECURRING        = "is_recurring";
        public static final String C6_FK1_ACCOUNT_FROM_ID = "account_from_id";
        public static final String C7_FK2_ACCOUNT_TO_ID   = "account_to_id";
        public static final String C8_FK3_BUDGET_ID       = "budget_id";
        public static final String C9_FK4_USER_ID         = "user_id";
    }

    /**
     * Public inner class Bills defines strings for the Bills table
     */
    public class Bills {
        public static final String TABLE_NAME             = "Transfers";
        public static final String C1_PK_ID               = "id";
        public static final String C2_TITLE               = "title";
        public static final String C3_AMOUNT              = "amount";
        public static final String C4_DATE_DUE            = "date_of_transfer";
        public static final String C5_DATE_PAID           = "is_recurring";
        public static final String C6_IS_PAID             = "is_paid";
        public static final String C7_IS_RECURRING        = "is_recurring";
        public static final String C8_FK1_BUDGET_ID       = "budget_id";
    }

    /**
     * Public inner class Earnings defines strings for the Earnings table
     */
    public class Earnings {
        public static final String TABLE_NAME             = "Earnings";
        public static final String C1_PK_ID               = "id";
        public static final String C2_TITLE               = "title";
        public static final String C3_AMOUNT              = "amount";
        public static final String C4_DATE_EARNED         = "date_earned";
        public static final String C5_IS_RECURRING        = "is_recurring";
        public static final String C6_FK1_BUDGET_ID       = "budget_id";
    }
}