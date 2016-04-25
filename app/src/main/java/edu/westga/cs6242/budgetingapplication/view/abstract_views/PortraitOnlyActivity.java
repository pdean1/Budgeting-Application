package edu.westga.cs6242.budgetingapplication.view.abstract_views;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

/**
 * Abstract class that prevents users from having a portrait view
 * @author Patrick Dean
 * @version 1
 */
public class PortraitOnlyActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }
}
