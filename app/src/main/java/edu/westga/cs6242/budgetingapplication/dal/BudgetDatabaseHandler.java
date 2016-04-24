package edu.westga.cs6242.budgetingapplication.dal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import edu.westga.cs6242.budgetingapplication.model.MonthlyBudget;
import edu.westga.cs6242.budgetingapplication.model.User;
import edu.westga.cs6242.budgetingapplication.util.database.BudgetDatabase;

/**
 * Database handler for the Budget Database.
 * @author Patrick Dean
 * @version 1
 */
public class BudgetDatabaseHandler extends SQLiteOpenHelper {

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

    /***********************************************************************************************
     * USERS TABLE QUERY FUNCTIONS
     **********************************************************************************************/
    public User findUser(String userName) {
        try {
            String strQuery = "SELECT * FROM " + BudgetDatabase.Users.TABLE_NAME +
                    " WHERE " + BudgetDatabase.Users.C2_USER_NAME + " = \"" + userName + "\"";
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(strQuery, null);
            User user = new User();
            if (cursor.moveToFirst()) {
                user.setId(Integer.parseInt(cursor.getString(0)));
                user.setUserName(cursor.getString(1));
                user.setPassword(cursor.getString(2));
            }
            else {
                user = null;
            }
            cursor.close();
            db.close();
            return user;
        } catch (Exception e) {
            Log.d("ERROR", e.getMessage());
            return null;
        }

    }

    public User attemptLogIn(User user) {
        User foundUser = findUser(user.getUserName());
        if (foundUser == null)
            return null;
        if (foundUser.getPassword().equals(user.getPassword())) {
            return foundUser;
        }
        return null;
    }

    public long addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(BudgetDatabase.Users.C2_USER_NAME, user.getUserName());
        values.put(BudgetDatabase.Users.C3_PASSWORD, user.getPassword());
        long id = db.insert(BudgetDatabase.Users.TABLE_NAME, null, values);
        db.close();
        return id;
    }

    public void updateUser(User user) {
        ContentValues values = new ContentValues();
        values.put(BudgetDatabase.Users.C2_USER_NAME, user.getUserName());
        values.put(BudgetDatabase.Users.C3_PASSWORD, user.getPassword());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(BudgetDatabase.Users.TABLE_NAME, null, values);
        db.close();
    }

    /**
     * Attempts to delete a user where user id == user.getId
     * @param user The user to delete from the database
     * @return true for successful detlete false otherwise
     */
    public boolean deleteUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(BudgetDatabase.Users.TABLE_NAME,
                BudgetDatabase.Users.C1_PK_ID + " = " + user.getId(), null);
        db.close();
        return result != 0;
    }
    /***********************************************************************************************
     * USERS TABLE QUERY FUNCTIONS
     **********************************************************************************************/
    public MonthlyBudget getMonthlyBudget(int id) {
        try {
            String strQuery = "SELECT * FROM " + BudgetDatabase.MonthlyBudget.TABLE_NAME +
                    " WHERE " + BudgetDatabase.MonthlyBudget.C1_PK_ID + " = " + id;
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(strQuery, null);
            MonthlyBudget monthlyBudget = new MonthlyBudget();
            if (cursor.moveToFirst()) {
                monthlyBudget.setId(cursor.getInt(0));
                monthlyBudget.setTitle(cursor.getString(1));
                monthlyBudget.setDescription(cursor.getString(2));
                monthlyBudget.setDateCreated(new Date(cursor.getLong(3)));
                monthlyBudget.setDateUpdated(new Date(cursor.getLong(4)));
                monthlyBudget.setUserId(cursor.getInt(5));
            }
            else {
                monthlyBudget = null;
            }
            cursor.close();
            db.close();
            return monthlyBudget;
        } catch (Exception e) {
            Log.d("ERROR", e.getMessage());
            return null;
        }

    }

    public ArrayList<MonthlyBudget> getMonthlyBudgetByUserId(int id) {
        ArrayList<MonthlyBudget> budgets = new ArrayList<>();
        String strQuery = "SELECT * FROM " + BudgetDatabase.MonthlyBudget.TABLE_NAME +
                " WHERE " + BudgetDatabase.MonthlyBudget.C6_FK1_USER_ID + " = " + id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(strQuery, null);
        if (cursor.moveToFirst())
        {
            while (cursor.isAfterLast() == false) {
                MonthlyBudget monthlyBudget = new MonthlyBudget();
                monthlyBudget.setId(cursor.getInt(0));
                monthlyBudget.setTitle(cursor.getString(1));
                monthlyBudget.setDescription(cursor.getString(2));
                monthlyBudget.setDateCreated(new Date(cursor.getLong(3)));
                monthlyBudget.setDateUpdated(new Date(cursor.getLong(4)));
                monthlyBudget.setUserId(cursor.getInt(5));
                budgets.add(monthlyBudget);
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();
        return budgets;
    }

    public long addMonthlyBudget(MonthlyBudget budget) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(BudgetDatabase.MonthlyBudget.C2_TITLE,
                budget.getTitle());
        values.put(BudgetDatabase.MonthlyBudget.C3_DESCRIPTION,
                budget.getDescription());
        values.put(BudgetDatabase.MonthlyBudget.C4_DATE_CREATED,
                new Date(Calendar.getInstance().getTimeInMillis()).toString());
        values.put(BudgetDatabase.MonthlyBudget.C5_DATE_UPDATED,
                new Date(Calendar.getInstance().getTimeInMillis()).toString());
        values.put(BudgetDatabase.MonthlyBudget.C6_FK1_USER_ID,
                budget.getUserId());
        long id = db.insert(BudgetDatabase.MonthlyBudget.TABLE_NAME,
                null, values);
        db.close();
        return id;
    }

    public boolean deleteMonthlyBudget(MonthlyBudget monthlyBudget) {
        SQLiteDatabase db = this.getWritableDatabase();
        this.deleteBillsByMonthlyBudgetID(db, monthlyBudget.getId());
        this.deleteEarningsByMonthlyBudgetID(db, monthlyBudget.getId());
        int result = db.delete(BudgetDatabase.MonthlyBudget.TABLE_NAME,
                BudgetDatabase.MonthlyBudget.C1_PK_ID + " = " + monthlyBudget.getId(), null);
        db.close();
        return result != 0;
    }

    public boolean deleteBillsByMonthlyBudgetID(SQLiteDatabase db, int id) {
        return db.delete(BudgetDatabase.Bills.TABLE_NAME, BudgetDatabase.Bills.C8_FK1_BUDGET_ID +
                " = " + id, null) != 0;
    }

    public boolean deleteEarningsByMonthlyBudgetID(SQLiteDatabase db, int id) {
        return db.delete(BudgetDatabase.Earnings.TABLE_NAME,
                BudgetDatabase.Earnings.C6_FK1_BUDGET_ID +
                " = " + id, null) != 0;
    }
}
