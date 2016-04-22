package edu.westga.cs6242.budgetingapplication.view.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import edu.westga.cs6242.budgetingapplication.R;
import edu.westga.cs6242.budgetingapplication.model.User;
import edu.westga.cs6242.budgetingapplication.util.ApplicationVariableStrings;
import edu.westga.cs6242.budgetingapplication.view.UpdateBudgetActivity;

public class MainMenuActivity extends AppCompatActivity {

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        this.user = getIntent().getParcelableExtra(ApplicationVariableStrings.SESSION_USER);
    }

    private void btnAddBudget_Click(View v) {
        Intent intent = new Intent(v.getContext(), UpdateBudgetActivity.class);
        startActivity(intent);
    }
}
