package edu.westga.cs6242.budgetingapplication.dal;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Patrick on 4/25/2016.
 */
public class StatisticsHandler extends BudgetDatabaseHandler {
    /**
     * @param context
     * @param cursorFactory
     */
    public StatisticsHandler(Context context, SQLiteDatabase.CursorFactory cursorFactory) {
        super(context, cursorFactory);
    }


}
