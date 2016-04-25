package edu.westga.cs6242.budgetingapplication.view.main_menu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

import edu.westga.cs6242.budgetingapplication.R;
import edu.westga.cs6242.budgetingapplication.dal.BudgetDatabaseHandler;
import edu.westga.cs6242.budgetingapplication.model.MonthlyBudget;
import edu.westga.cs6242.budgetingapplication.model.session.Session;
import edu.westga.cs6242.budgetingapplication.view.abstract_views.PortraitOnlyActivity;
import edu.westga.cs6242.budgetingapplication.view.budget_management.ViewBudgetsActivity;

/**
 * Main Menu Java Class. This class allows a user to add a budget
 * to their user id and manage it.
 *
 * @author Patrick Dean
 * @version 1
 */
public class MainMenuActivity extends PortraitOnlyActivity {

    private BudgetDatabaseHandler dbh;
    private EditText etBudgetTitle;
    private TextView txtSessionInfo;
    private EditText etBudgetDescription;
    private CheckBox cbIsRecurring;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        this.txtSessionInfo = (TextView) findViewById(R.id.tvUser);
        this.etBudgetTitle = (EditText) findViewById(R.id.etTitle);
        this.etBudgetDescription = (EditText) findViewById(R.id.etDescription);
        this.cbIsRecurring = (CheckBox) findViewById(R.id.cbRecurringBillsAndEarnings);

        this.dbh = new BudgetDatabaseHandler(getApplicationContext(), null);
        updateSessiontText();
    }

    private void updateSessiontText() {
        txtSessionInfo = (TextView) findViewById(R.id.tvUser);
        String sessionString = "Signed in as: " + Session.getUser().getUserName();
        assert txtSessionInfo != null;
        txtSessionInfo.setText(sessionString);
    }

    public void btnManageBudgets_Click(View v) {
        Intent intent = new Intent(v.getContext(), ViewBudgetsActivity.class);
        startActivity(intent);
    }

    public void btnAddBudget_Click(View v) {
        if (this.etBudgetTitle.getText().toString().length() < 5 ||
                this.etBudgetDescription.getText().toString().length() < 10) {
            ToastMessage("Please provide a proper title and description!");
            return;
        }
        MonthlyBudget monthlyBudgetForRecurringAdd = new MonthlyBudget();
        if (this.cbIsRecurring.isChecked()) {
            // if the is recurring check box is checked... set the monthly budget 2 session variable
            monthlyBudgetForRecurringAdd = this.dbh.getLastMonthlyBudgetAddedByUser(Session.getUser().getId());
        }

        MonthlyBudget monthlyBudget = new MonthlyBudget();

        monthlyBudget.setTitle(this.etBudgetTitle.getText().toString());
        monthlyBudget.setDescription(this.etBudgetDescription.getText().toString());
        monthlyBudget.setDateCreated(new Date(Calendar.getInstance().getTimeInMillis()));
        monthlyBudget.setDateUpdated(monthlyBudget.getDateCreated());
        monthlyBudget.setUserId(Session.getUser().getId());

        long result = this.dbh.addMonthlyBudget(monthlyBudget);

        if (result == -1) {
            ToastMessage("Unable to add Budget to Database, duplicates present.");
            return;
        }

        if (this.cbIsRecurring.isChecked()) {
            if (!this.dbh.addRecurringBillsAndEarningsFromPreviousMonth(result, monthlyBudgetForRecurringAdd.getId())) {
                ToastMessage("Budget Added, but unable to add recurring fields!");
                return;
            }
        }

        ToastMessage("Budget Added!");
    }

    private void ToastMessage(String text) {
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(getApplicationContext(), text,
                duration);
        toast.show();
    }
}
