package edu.westga.cs6242.budgetingapplication.view.abstract_views;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Abstract class that prevents users from having a portrait view
 * @author Patrick Dean
 * @version 1
 */
public class PortraitOnlyActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }
}
