package edu.westga.cs6242.budgetingapplication.dal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import edu.westga.cs6242.budgetingapplication.model.Bill;
import edu.westga.cs6242.budgetingapplication.model.Earning;
import edu.westga.cs6242.budgetingapplication.model.MonthlyBudget;
import edu.westga.cs6242.budgetingapplication.model.User;
import edu.westga.cs6242.budgetingapplication.util.database.BudgetDatabase;
import edu.westga.cs6242.budgetingapplication.util.session.Session;

/**
 * Database handler for the Budget Database.
 *
 * @author Patrick Dean
 * @version 1
 */
public class BudgetDatabaseHandler extends SQLiteOpenHelper {

    // To call simply pass getApplicationContext, null
    public BudgetDatabaseHandler(Context context, SQLiteDatabase.CursorFactory cursorFactory) {
        super(context, BudgetDatabase.DATABASE_NAME, cursorFactory,
                BudgetDatabase.DATABASE_VERSION);
    }

    /**
     * Called when the database is created for the first time. This is where the
     * creation of tables and the initial population of the tables should happen.
     *
     * @param db The database.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        BudgetDatabase.createDatabase(db);
    }

    /**
     * Called when the database needs to be upgraded. The implementation
     * should use this method to drop tables, add tables, or do anything else it
     * needs to upgrade to the new schema version.
     * <p/>
     * <p>
     * The SQLite ALTER TABLE documentation can be found
     * <a href="http://sqlite.org/lang_altertable.html">here</a>. If you add new columns
     * you can use ALTER TABLE to insert them into a live table. If you rename or remove columns
     * you can use ALTER TABLE to rename the old table, then create the new table and then
     * populate the new table with the contents of the old table.
     * </p><p>
     * This method executes within a transaction.  If an exception is thrown, all changes
     * will automatically be rolled back.
     * </p>
     *
     * @param db         The database.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        BudgetDatabase.deleteDatabase(db);
        this.onCreate(db);
    }

    /**
     * Attempts to find a user in the database based on a user name
     *
     * @param userName the username of the user to find
     * @return a valid user if one was found, null otherwise
     */
    public User findUser(String userName) {
        String strQuery = "SELECT * FROM " + BudgetDatabase.Users.TABLE_NAME + " WHERE " + BudgetDatabase.Users
                .C2_USER_NAME + " = \"" + userName + "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(strQuery, null);
        User user = new User();
        if (cursor.moveToFirst()) {
            user.setId(Integer.parseInt(cursor.getString(0)));
            user.setUserName(cursor.getString(1));
            user.setPassword(cursor.getString(2));
        } else {
            user = null;
        }
        cursor.close();
        db.close();
        return user;
    }

    /**
     * Attempts to log a user in
     *
     * @param user user to log in
     * @return signed in user or null
     */
    public User attemptLogIn(User user) {
        User foundUser = findUser(user.getUserName());
        if (foundUser == null)
            return null;
        if (foundUser.getPassword().equals(user.getPassword())) {
            return foundUser;
        }
        return null;
    }

    /**
     * Attempts to add a user to the database
     *
     * @param user user to add
     * @return id of user or -1
     */
    public long addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(BudgetDatabase.Users.C2_USER_NAME, user.getUserName());
        values.put(BudgetDatabase.Users.C3_PASSWORD, user.getPassword());
        long id = db.insert(BudgetDatabase.Users.TABLE_NAME, null, values);
        db.close();
        return id;
    }

    /**
     * Gets a single monthly budget from the database based on the id of the monthly budget to retrieve
     *
     * @param id Id of the budget to retrieve
     * @return The found monthly budget or null
     */
    public MonthlyBudget getMonthlyBudget(int id) {
        String strQuery = "SELECT * FROM " + BudgetDatabase.MonthlyBudget.TABLE_NAME +
                " WHERE " + BudgetDatabase.MonthlyBudget.C1_PK_ID + " = " + id;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(strQuery, null);

        MonthlyBudget monthlyBudget = new MonthlyBudget();

        if (cursor.moveToFirst()) {
            monthlyBudget.setId(cursor.getInt(0));
            monthlyBudget.setTitle(cursor.getString(1));
            monthlyBudget.setDescription(cursor.getString(2));
            try {
                monthlyBudget.setDateCreated(Session.dateFormatDatabase.parse(cursor.getString(3)));
            } catch (ParseException ex) {
                Log.d("W", "Unable to parse: " + cursor.getString(3));
            }
            try {
                monthlyBudget.setDateUpdated(Session.dateFormatDatabase.parse(cursor.getString(4)));
            } catch (ParseException ex) {
                Log.d("W", "Unable to parse: " + cursor.getString(4));
            }
            monthlyBudget.setUserId(cursor.getInt(5));
        } else {
            monthlyBudget = null; // No budget found
        }

        cursor.close();
        db.close();
        return monthlyBudget;
    }

    /**
     * Gets a list of budgets associated to a user's id
     *
     * @param id the id of the user to search for
     * @return A list of budgets or an empty list
     */
    public ArrayList<MonthlyBudget> getMonthlyBudgetsByUserId(int id) {
        ArrayList<MonthlyBudget> budgets = new ArrayList<>();
        String strQuery = "SELECT * FROM " + BudgetDatabase.MonthlyBudget.TABLE_NAME + " WHERE " +
                BudgetDatabase.MonthlyBudget.C6_FK1_USER_ID + " = " + id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(strQuery, null);

        if (cursor.moveToFirst()) {

            while (!cursor.isAfterLast()) {
                MonthlyBudget budget = new MonthlyBudget();

                budget.setId(cursor.getInt(0));
                budget.setTitle(cursor.getString(1));
                budget.setDescription(cursor.getString(2));
                // TODO still a problem???
                try {
                    budget.setDateCreated(Session.dateFormatDatabase.parse(cursor.getString(3)));
                    budget.setDateUpdated(Session.dateFormatDatabase.parse(cursor.getString(4)));
                } catch (Exception e) {
                    Log.d("E", e.getMessage());
                }
                budget.setUserId(cursor.getInt(5));

                budgets.add(budget);

                cursor.moveToNext();
            }

        }

        cursor.close();
        db.close();
        return budgets;
    }

    /**
     * Adds a budget to the database
     *
     * @param budget The budget to add
     * @return Id of the inserted budget or -1 on failure
     */
    public long addMonthlyBudget(MonthlyBudget budget) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(BudgetDatabase.MonthlyBudget.C2_TITLE, budget.getTitle());
        values.put(BudgetDatabase.MonthlyBudget.C3_DESCRIPTION, budget.getDescription());
        values.put(BudgetDatabase.MonthlyBudget.C4_DATE_CREATED, budget.getDateCreated().toString());
        values.put(BudgetDatabase.MonthlyBudget.C5_DATE_UPDATED, budget.getDateUpdated().toString());
        values.put(BudgetDatabase.MonthlyBudget.C6_FK1_USER_ID, budget.getUserId());

        long id = db.insert(BudgetDatabase.MonthlyBudget.TABLE_NAME, null, values);

        db.close();
        return id;
    }

    /**
     * Deletes a monthly budget first by deleting the bills and earnigs associated with it then deleting the budget
     * itself
     *
     * @param monthlyBudget budget to delete
     * @return true if deleted false otherwise
     */
    public boolean deleteMonthlyBudget(MonthlyBudget monthlyBudget) {
        SQLiteDatabase db = this.getWritableDatabase();
        this.deleteBillsByMonthlyBudgetID(db, monthlyBudget.getId());
        this.deleteEarningsByMonthlyBudgetID(db, monthlyBudget.getId());
        int result = db.delete(BudgetDatabase.MonthlyBudget.TABLE_NAME, BudgetDatabase.MonthlyBudget.C1_PK_ID + " = "
                + monthlyBudget.getId(), null);
        db.close();
        return result != 0;
    }

    /**
     * Deletes a bill according to the bills id
     *
     * @param id id of bill to delete
     * @return true if deleted false otherwise
     */
    public boolean deleteBillById(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(BudgetDatabase.Bills.TABLE_NAME,
                BudgetDatabase.Bills.C1_PK_ID + " = " + id, null);
        return result != 0;
    }

    /**
     * Gets a list of bills that have the same budget id
     *
     * @param id id of the budget to match against
     * @return a list of bills associated with the passed budget id
     */
    public ArrayList<Bill> getBillsByBudgetId(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String strQuery = "SELECT * FROM " + BudgetDatabase.Bills.TABLE_NAME + " WHERE " + BudgetDatabase.Bills
                .C8_FK1_BUDGET_ID + " = " + id;

        ArrayList<Bill> bills = new ArrayList<>();

        Cursor cursor = db.rawQuery(strQuery, null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Bill bill = new Bill();
                bill.setId(cursor.getInt(0));
                bill.setTitle(cursor.getString(1));
                bill.setAmount(cursor.getDouble(2));
                try {
                    bill.setDateDue(Session.dateFormatDatabase.parse(cursor.getString(3)));
                } catch (Exception e) {
                    Log.d("W", "Unable to parse date: " + cursor.getString(3));
                }
                try {
                    bill.setDatePaid(Session.dateFormatDatabase.parse(cursor.getString(4)));
                } catch (Exception e) {
                    Log.d("W", "Unable to parse date: " + cursor.getString(4));
                }
                bill.setIsPaid(cursor.getInt(5) == 1);
                bill.setIsRecurring(cursor.getInt(6) == 1);
                bill.setBudgetId(cursor.getInt(7));

                bills.add(bill);

                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();
        return bills;
    }

    /**
     * Gets a list of earnings by budget id
     *
     * @param id id of budget
     * @return a list of earnings
     */
    public ArrayList<Earning> getEarningsByBudgetId(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String strQuery = "SELECT * FROM " + BudgetDatabase.Earnings.TABLE_NAME +
                " WHERE " + BudgetDatabase.Earnings.C6_FK1_BUDGET_ID + " = " + id;
        ArrayList<Earning> earnings = new ArrayList<>();
        Cursor cursor = db.rawQuery(strQuery, null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Earning earning = new Earning();
                earning.setId(cursor.getInt(0));
                earning.setTitle(cursor.getString(1));
                earning.setAmount(cursor.getDouble(2));
                try {
                    earning.setDateEarned(Session.dateFormatDatabase.parse(cursor.getString(3)));
                } catch (Exception e) {
                    Log.d("E", "getEarningsByBudgetId: " + e.getMessage());
                }
                earning.setIsRecurring(cursor.getInt(4) == 1);
                earning.setBudgetId(cursor.getInt(5));
                earnings.add(earning);
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();
        return earnings;
    }

    /**
     * Gets a list of bills that are recurring by budget id
     *
     * @param id id of budget to use as a filter
     * @return a list of budgets
     */
    public ArrayList<Bill> getBillsByBudgetIdThatAreRecurring(int id) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMddyyyy", Locale.US);
        SQLiteDatabase db = this.getReadableDatabase();
        String strQuery = "SELECT * FROM " + BudgetDatabase.Bills.TABLE_NAME +
                " WHERE " + BudgetDatabase.Bills.C8_FK1_BUDGET_ID + " = " + id + " AND " + BudgetDatabase.Bills
                .C7_IS_RECURRING + " = 1";
        ArrayList<Bill> bills = new ArrayList<>();
        Cursor cursor = db.rawQuery(strQuery, null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Bill bill = new Bill();
                bill.setId(cursor.getInt(0));
                bill.setTitle(cursor.getString(1));
                bill.setAmount(cursor.getDouble(2));
                try {
                    bill.setDateDue(dateFormat.parse(cursor.getString(3)));
                } catch (Exception e) {
                    Log.d("E", "getBillsByBudgetIdThatAreRecurring: " + e.getMessage());
                }
                try {
                    bill.setDatePaid(dateFormat.parse(cursor.getString(4)));
                } catch (Exception e) {
                    Log.d("E", "getBillsByBudgetIdThatAreRecurring: " + e.getMessage());
                }
                bill.setIsPaid(cursor.getInt(5) == 1);
                bill.setIsRecurring(cursor.getInt(6) == 1);
                bill.setBudgetId(cursor.getInt(7));
                bills.add(bill);
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();
        return bills;
    }

    /**
     * Gets a list of earnings that a recurring from the previous months budget based on id
     *
     * @param id id of budget to use as a filter
     * @return a list of earnings
     */
    public ArrayList<Earning> getEarningsByBudgetIdThatAreRecurring(int id) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMddyyyy", Locale.US);
        SQLiteDatabase db = this.getReadableDatabase();
        String strQuery = "SELECT * FROM " + BudgetDatabase.Earnings.TABLE_NAME +
                " WHERE " + BudgetDatabase.Earnings.C6_FK1_BUDGET_ID + " = " + id + " AND " +
                BudgetDatabase.Earnings.C5_IS_RECURRING + " = 1";
        ArrayList<Earning> earnings = new ArrayList<>();
        Cursor cursor = db.rawQuery(strQuery, null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Earning earning = new Earning();
                earning.setId(cursor.getInt(0));
                earning.setTitle(cursor.getString(1));
                earning.setAmount(cursor.getDouble(2));
                try {
                    earning.setDateEarned(dateFormat.parse(cursor.getString(3)));
                } catch (Exception e) {
                    Log.d("E", "getEarningsByBudgetIdThatAreRecurring: " + e.getMessage());
                }
                earning.setIsRecurring(cursor.getInt(4) == 1);
                earning.setBudgetId(cursor.getInt(5));
                earnings.add(earning);
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();
        return earnings;
    }

    /**
     * Adds a bill to the database
     *
     * @param bill bill to add to the database
     * @return id of the bill entered or -1 for error
     */
    public long addBill(Bill bill) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(BudgetDatabase.Bills.C2_TITLE, bill.getTitle());
        values.put(BudgetDatabase.Bills.C3_AMOUNT, bill.getAmount());
        values.put(BudgetDatabase.Bills.C4_DATE_DUE, bill.getDateDue().toString());
        values.put(BudgetDatabase.Bills.C5_DATE_PAID, bill.getDatePaid().toString());
        values.put(BudgetDatabase.Bills.C6_IS_PAID, Integer.toString((bill.isPaid()) ? 1 : 0));
        values.put(BudgetDatabase.Bills.C7_IS_RECURRING,
                Integer.toString((bill.isRecurring()) ? 1 : 0));
        values.put(BudgetDatabase.Bills.C8_FK1_BUDGET_ID, bill.getBudgetId());
        long id = db.insert(BudgetDatabase.Bills.TABLE_NAME, null, values);
        db.close();
        return id;
    }

    /**
     * Adds an Earning to the database
     *
     * @param earnings Earning to add to the database
     * @return id of added record or -1
     */
    public long addEarning(Earning earnings) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(BudgetDatabase.Earnings.C2_TITLE, earnings.getTitle());
        values.put(BudgetDatabase.Earnings.C3_AMOUNT, earnings.getAmount());
        values.put(BudgetDatabase.Earnings.C4_DATE_EARNED, earnings.getDateEarned().toString());
        values.put(BudgetDatabase.Earnings.C5_IS_RECURRING, (earnings.isRecurring()) ? 1 : 0);
        values.put(BudgetDatabase.Earnings.C6_FK1_BUDGET_ID, earnings.getBudgetId());

        long id = db.insert(BudgetDatabase.Earnings.TABLE_NAME, null,
                values);

        db.close();
        return id;
    }

    /**
     * Deletes bills based on a budget id = id
     *
     * @param db database to use
     * @param id id of budget to use as a filter
     * @return true if deleted false otherwise
     */
    public boolean deleteBillsByMonthlyBudgetID(SQLiteDatabase db, int id) {
        return db.delete(BudgetDatabase.Bills.TABLE_NAME, BudgetDatabase.Bills.C8_FK1_BUDGET_ID +
                " = " + id, null) != 0;
    }

    /**
     * Used by delete monthly budget query... deletes all earnings
     * associated with a budget
     *
     * @param db database to use
     * @param id id of the budget to filter by
     * @return true if deleted false otherwise
     */
    public boolean deleteEarningsByMonthlyBudgetID(SQLiteDatabase db, int id) {
        return db.delete(BudgetDatabase.Earnings.TABLE_NAME,
                BudgetDatabase.Earnings.C6_FK1_BUDGET_ID +
                        " = " + id, null) != 0;
    }

    /**
     * Deletes an earning associated with an id and returns true if
     * deleted false otherwise
     *
     * @param id id of earning to delete
     * @return true if deleted false otherwise
     */
    public boolean deleteEarningById(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(BudgetDatabase.Earnings.TABLE_NAME,
                BudgetDatabase.Earnings.C1_PK_ID + " = " + id, null);
        return result != 0;
    }

    /**
     * Gets the last budget added by a user based on id == userid
     *
     * @param id id of user
     * @return the last monthly budget added by the user
     */
    public MonthlyBudget getLastMonthlyBudgetAddedByUser(int id) {
        MonthlyBudget budget = new MonthlyBudget();
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + BudgetDatabase.MonthlyBudget.TABLE_NAME + " WHERE " + BudgetDatabase
                .MonthlyBudget.C6_FK1_USER_ID + " = " + id;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToLast()) {
            budget.setId(cursor.getInt(0));
            budget.setTitle(cursor.getString(1));
            budget.setDescription(cursor.getString(2));
            try {
                budget.setDateCreated(Session.dateFormatDatabase.parse(cursor.getString(3)));
            } catch (Exception e) {
                Log.d("E", "getLastMonthlyBudgetAddedByUser: ");
            }
            try {
                budget.setDateUpdated(Session.dateFormatDatabase.parse(cursor.getString(4)));
            } catch (Exception e) {
                Log.d("E", "getLastMonthlyBudgetAddedByUser: ");
            }
            budget.setUserId(cursor.getInt(5));
        } else {
            return null;
        }
        cursor.close();
        db.close();
        return budget;
    }

    public boolean checkForRecurringBudgetRecords(int budgetID) {
        ArrayList<Bill> bills = this.getBillsByBudgetIdThatAreRecurring(budgetID);
        if (bills.size() != 0) {
            return true;
        }
        ArrayList<Earning> earnings = this.getEarningsByBudgetIdThatAreRecurring(budgetID);
        if (earnings.size() != 0) {
            return true;
        }
        return false;
    }

    /**
     * Adds any recurring bills and earnings from the previous month to the new one
     *
     * @param newId id of the new budget
     * @param id    id of the old one
     * @return true if success false error
     */
    public boolean addRecurringBillsAndEarningsFromPreviousMonth(long newId, int id) {
        Boolean ret = true;
        ArrayList<Bill> bills = this.getBillsByBudgetIdThatAreRecurring(id);
        ArrayList<Earning> earnings = this.getEarningsByBudgetIdThatAreRecurring(id);
        for (Bill b : bills) {
            b.setBudgetId((int) newId);
            long result = this.addBill(b);
            if (result == -1 && ret) {
                ret = false;
            }
        }
        for (Earning e : earnings) {
            e.setBudgetId((int) newId);
            long result = this.addEarning(e);
            if (result == -1 && ret)
                ret = false;
        }
        return ret;
    }
}
