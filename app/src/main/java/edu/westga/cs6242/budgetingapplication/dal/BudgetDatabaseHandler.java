package edu.westga.cs6242.budgetingapplication.dal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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
        User userA = new User();
        userA.setUserName("pdean1");
        userA.setPassword("password");
        this.addUser(userA);
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

    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(BudgetDatabase.Users.C2_USER_NAME, user.getUserName());
        values.put(BudgetDatabase.Users.C3_PASSWORD, user.getPassword());
        long result = db.insert(BudgetDatabase.Users.TABLE_NAME, null, values);
        db.close();
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
}
