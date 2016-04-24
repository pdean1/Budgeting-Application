package edu.westga.cs6242.budgetingapplication.view.main_menu;

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
import edu.westga.cs6242.budgetingapplication.model.Session;
import edu.westga.cs6242.budgetingapplication.model.User;
import edu.westga.cs6242.budgetingapplication.view.budget_management.ViewBudgetsActivity;

public class MainMenuActivity extends AppCompatActivity {

    private BudgetDatabaseHandler dbh;

    private User user;

    private EditText etBudgetTitle;
    private EditText etBudgetDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        this.user = Session.getUser();
        this.etBudgetTitle = (EditText) findViewById(R.id.etTitle);
        this.etBudgetDescription = (EditText) findViewById(R.id.etDescription);
        this.dbh = new BudgetDatabaseHandler(getApplicationContext(), null);
        updateSessiontText();
    }

    private void updateSessiontText() {
        TextView txtSessionInfo = (TextView) findViewById(R.id.tvUser);
        assert txtSessionInfo != null;
        String sessionString = "Signed in as: " + this.user.getUserName();
        txtSessionInfo.setText(sessionString);
    }

    public void btnManageBudgets_Click(View v) {
        Intent intent = new Intent(v.getContext(), ViewBudgetsActivity.class);
        startActivity(intent);
    }

    public void btnAddBudget_Click(View v) {
        if (this.etBudgetTitle.getText().toString().equals("") ||
                this.etBudgetDescription.getText().toString().equals(""))
        {
            ToastMessage("Please provide a proper title and description!");
            return;
        }
        MonthlyBudget monthlyBudget = new MonthlyBudget();
        monthlyBudget.setTitle(this.etBudgetTitle.getText().toString());
        monthlyBudget.setDescription(this.etBudgetDescription.getText().toString());
        monthlyBudget.setDateCreated(new Date(System.currentTimeMillis()));
        monthlyBudget.setDateUpdated(monthlyBudget.getDateCreated());
        monthlyBudget.setUserId(this.user.getId());
        long result = this.dbh.addMonthlyBudget(monthlyBudget);
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
