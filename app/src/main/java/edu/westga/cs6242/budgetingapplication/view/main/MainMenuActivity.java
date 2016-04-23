package edu.westga.cs6242.budgetingapplication.view.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

import edu.westga.cs6242.budgetingapplication.R;
import edu.westga.cs6242.budgetingapplication.dal.BudgetDatabaseHandler;
import edu.westga.cs6242.budgetingapplication.model.MonthlyBudget;
import edu.westga.cs6242.budgetingapplication.model.User;
import edu.westga.cs6242.budgetingapplication.util.ApplicationVariableStrings;

public class MainMenuActivity extends AppCompatActivity {

    private BudgetDatabaseHandler dbh;

    private User user;

    private TextView txtSessionInfo;

    private EditText etBudgetTitle;
    private EditText etBudgetDescription;

    private MonthlyBudget monthlyBudget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        this.user = getIntent().getParcelableExtra(ApplicationVariableStrings.SESSION_USER);
        this.etBudgetTitle = (EditText) findViewById(R.id.etTitle);
        this.etBudgetDescription = (EditText) findViewById(R.id.etDescription);
        this.dbh = new BudgetDatabaseHandler(getApplicationContext(), null);
        this.txtSessionInfo = (TextView) findViewById(R.id.tvUser);
        assert this.txtSessionInfo != null;
        String sessionString = "Signed in as: " + this.user.getUserName();
        this.txtSessionInfo.setText(sessionString);
    }

    public void btnManageBudgets_Click(View v) {
        Bundle bundleUser = new Bundle();
        bundleUser.putParcelable(ApplicationVariableStrings.SESSION_USER, this.user);
        Intent intent = new Intent(v.getContext(), ManageBudgetsActivity.class);
        intent.putExtras(bundleUser);
        startActivity(intent);
    }

    public void btnAddBudget_Click(View v) {
        this.monthlyBudget = new MonthlyBudget();
        this.monthlyBudget.setTitle(this.etBudgetTitle.getText().toString());
        this.monthlyBudget.setDescription(this.etBudgetDescription.getText().toString());
        this.monthlyBudget.setDateCreated(new Date(System.currentTimeMillis()));
        this.monthlyBudget.setDateUpdated(this.monthlyBudget.getDateCreated());
        this.monthlyBudget.setUserId(this.user.getId());
        long result = this.dbh.addMonthlyBudget(this.monthlyBudget);
        if (result == -1) {
            ToastMessage("Unable to add Budget to Database");
            return;
        }
        ToastMessage("Budget Added!");
    }

    private void ToastMessage(String text) {
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(getApplicationContext(), text, duration);
        toast.show();
    }
}
