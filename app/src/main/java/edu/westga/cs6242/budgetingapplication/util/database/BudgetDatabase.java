package edu.westga.cs6242.budgetingapplication.util.database;

import android.database.sqlite.SQLiteDatabase;

/**
 * This class provides constant strings used to reference the
 * budget data base and it's tables
 *
 * @author Patrick Dean
 * @version 1
 */
public class BudgetDatabase {

    public static final String DATABASE_NAME    = "Budget.db";
    public static final int    DATABASE_VERSION = 1;
    public static final String INSERT_INTO = "INSERT INTO ";

    /**
     * Uses the passed database to create tables in it
     * @param db DATABASE to add tables too
     */
    public static void createDatabase(SQLiteDatabase db) {
        db.execSQL(Users.CREATE_TABLE);
        db.execSQL(AccountTypes.CREATE_TABLE);
        db.execSQL(Accounts.CREATE_TABLE);
        db.execSQL(MonthlyBudget.CREATE_TABLE);
        db.execSQL(Bills.CREATE_TABLE);
        db.execSQL(Earnings.CREATE_TABLE);
    }

    public static void deleteDatabase(SQLiteDatabase db) {
        db.execSQL(Earnings.DROP_TABLE);
        db.execSQL(Bills.DROP_TABLE);
        db.execSQL(MonthlyBudget.DROP_TABLE);
        db.execSQL(Accounts.DROP_TABLE);
        db.execSQL(Users.DROP_TABLE);
        db.execSQL(AccountTypes.DROP_TABLE);
    }

    /**
     * Public inner class Users defines strings for the user table
     */
    public class Users {
        public static final String TABLE_NAME   = "Users";
        public static final String C1_PK_ID     = "id";
        public static final String C2_USER_NAME = "user_name";
        public static final String C3_PASSWORD  = "password";
        public static final String CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " ( "
                + C1_PK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + C2_USER_NAME + " TEXT NOT NULL, "
                + C3_PASSWORD + " TEXT NOT NULL); ";
        public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    /**
     * Public inner class AccountType defines strings for the AccountType table
     */
    public class AccountTypes {
        public static final String TABLE_NAME     = "AccountTypes";
        public static final String C1_PK_ID       = "id";
        public static final String C2_TITLE       = "title";
        public static final String C3_DESCRIPTION = "description";
        public static final String CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " ( "
                + C1_PK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + C2_TITLE  + " TEXT NOT NULL, "
                + C3_DESCRIPTION + "TEXT);";
        public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
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
        public static final String CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " ( "
                        + C1_PK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + C2_DESCRIPTION + " TEXT NOT NULL "
                        + C3_DATE_CREATED + " NUMERIC NOT NULL, "
                        + C4_DATE_UPDATED + " NUMERIC, "
                        + C5_FK1_USER_ID + " INTEGER NOT NULL ,"
                        + C6_FK2_ACCOUNT_TYPE_ID + " INTEGER NOT NULL "
                + "FOREIGN KEY (" + C5_FK1_USER_ID + ") REFERENCES "
                        + Users.TABLE_NAME+" ( " + Users.C1_PK_ID + " ) "
                + "FOREIGN KEY (" + C6_FK2_ACCOUNT_TYPE_ID + ") REFERENCES "
                        + AccountTypes.TABLE_NAME+" ( " + AccountTypes.C1_PK_ID + " ) ";
        public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
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
        public static final String CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " ( "
                        + C1_PK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + C2_TITLE + " TEXT NOT NULL "
                        + C3_DESCRIPTION + " TEXT, "
                        + C4_DATE_CREATED + " NUMERIC NOT NULL, "
                        + C5_DATE_UPDATED + " NUMERIC,"
                        + C6_FK1_USER_ID + " INTEGER NOT NULL "
                        + "FOREIGN KEY (" + C6_FK1_USER_ID + ") REFERENCES "
                        + Users.TABLE_NAME+" ( " + Users.C1_PK_ID + " ) ";
        public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
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
        public static final String CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " ( "
                        + C1_PK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + C2_TITLE + " TEXT NOT NULL "
                        + C3_AMOUNT + " NUMERIC NOT NULL, "
                        + C4_DATE_DUE + " NUMERIC NOT NULL, "
                        + C5_DATE_PAID + " NUMERIC,"
                        + C6_IS_PAID + " INTEGER NOT NULL,"
                        + C7_IS_RECURRING + " INTEGER NOT NULL,"
                        + C8_FK1_BUDGET_ID + " INTEGER NOT NULL,"
                        + "FOREIGN KEY (" + C8_FK1_BUDGET_ID + ") REFERENCES "
                        + MonthlyBudget.TABLE_NAME + " ( " + MonthlyBudget.C1_PK_ID + " ) ";
        public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
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
        public static final String CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " ( "
                        + C1_PK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + C2_TITLE + " TEXT NOT NULL "
                        + C3_AMOUNT + " NUMERIC NOT NULL, "
                        + C4_DATE_EARNED + " NUMERIC NOT NULL, "
                        + C5_IS_RECURRING + " INTEGER NOT NULL,"
                        + C6_FK1_BUDGET_ID + " INTEGER NOT NULL,"
                        + "FOREIGN KEY (" + C6_FK1_BUDGET_ID + ") REFERENCES "
                        + MonthlyBudget.TABLE_NAME + " ( " + MonthlyBudget.C1_PK_ID + " ) ";
        public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
   }
}