package edu.westga.cs6242.budgetingapplication.view.main_menu;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

import edu.westga.cs6242.budgetingapplication.R;
import edu.westga.cs6242.budgetingapplication.dal.BudgetDatabaseHandler;
import edu.westga.cs6242.budgetingapplication.model.MonthlyBudget;
import edu.westga.cs6242.budgetingapplication.util.session.Session;
import edu.westga.cs6242.budgetingapplication.view.abstract_views.PortraitOnlyActivity;
import edu.westga.cs6242.budgetingapplication.view.budget_management.ViewBudgetsActivity;
import edu.westga.cs6242.budgetingapplication.view.fragments.dialog.PickMonthAndYearDialog;

/**
 * Main Menu Java Class. This class allows a user to add a budget
 * to their user id and manage it.
 *
 * @author Patrick Dean
 * @version 1
 */
public class MainMenuActivity extends PortraitOnlyActivity implements View.OnClickListener {

    private BudgetDatabaseHandler dbh;

    private EditText etBudgetTitle;
    private TextView txtSessionInfo, tvSelectMonth;
    private EditText etBudgetDescription;
    private CheckBox cbIsRecurring;

    private PickMonthAndYearDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget_menu);

        findViewsByIds();

        if (this.tvSelectMonth != null) {
            this.tvSelectMonth.setOnClickListener(this);
        } else {
            Log.d("E", "Error in setting on click listner: " + (R.id.tvSelectMonth));
        }
        this.dialog = new PickMonthAndYearDialog();
        this.dialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                try {
                    Date date = Session.dateFormatDatabase.parse(String.format("%d-%d-%d",
                            year, monthOfYear, dayOfMonth));
                    tvSelectMonth.setText(Session.dateFormatDatabase.format(date));
                } catch (Exception e) {
                    Log.d("E", "onDateSet: " + e.getMessage());
                }
            }
        });
        this.dbh = new BudgetDatabaseHandler(getApplicationContext());
        updateSessionText();
    }

    private void findViewsByIds() {
        this.txtSessionInfo = (TextView) findViewById(R.id.tvUser);
        this.etBudgetTitle = (EditText) findViewById(R.id.etTitle);
        this.etBudgetDescription = (EditText) findViewById(R.id.etDescription);
        this.cbIsRecurring = (CheckBox) findViewById(R.id.cbRecurringBillsAndEarnings);
        this.tvSelectMonth = (TextView) findViewById(R.id.tvSelectMonth);
    }

    public void btnManageBudgets_Click(View v) {
        Intent intent = new Intent(v.getContext(), ViewBudgetsActivity.class);
        startActivity(intent);
    }

    public void btnAddBudget_Click(View v) {
        // Validation Routine
        if (!validationRoutine()) {
            return;
        }
        MonthlyBudget monthlyBudgetForRecurringAdd = new MonthlyBudget();
        if (this.cbIsRecurring.isChecked()) {
            monthlyBudgetForRecurringAdd = this.dbh.getLastMonthlyBudgetAddedByUser(Session.getUser().getId());
            if (monthlyBudgetForRecurringAdd == null) {
                ToastMessage("Un-check is recurring. No previous budget.");
                return;
            }
            if (!this.dbh.checkForRecurringBudgetRecords(monthlyBudgetForRecurringAdd.getId())) {
                ToastMessage("Un-check is recurring. No previous recurring bills or earnings.");
                return;
            }
        }
        MonthlyBudget monthlyBudget = new MonthlyBudget();
        monthlyBudget.setTitle(this.etBudgetTitle.getText().toString());
        monthlyBudget.setDescription(this.etBudgetDescription.getText().toString());
        try {
            monthlyBudget.setDateCreated(this.tvSelectMonth.getText().toString());
        } catch (Exception e) {
            Log.d("I", monthlyBudget.getDateCreated());
            return;
        }
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

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v == this.tvSelectMonth)
            dialog.show(getFragmentManager(), "1");
    }

    private boolean validationRoutine() {
        // Audit Title
        if (this.etBudgetTitle.getText().toString().length() < 5) {
            ToastMessage("Please provide a proper title and description!");
            return false;
        }
        // Audit Description
        if (this.etBudgetDescription.getText().toString().length() < 10) {
            ToastMessage("Please provide a proper title and description!");
            return false;
        }
        // Audit Date
        try {
            Session.dateFormatView.parse(this.tvSelectMonth.getText().toString());
        } catch (Exception e) {
            this.tvSelectMonth.setTextColor(Color.RED);
            ToastMessage("Provide a valid date");
            return false;
        }
        return true;
    }
    private void updateSessionText() {
        txtSessionInfo = (TextView) findViewById(R.id.tvUser);
        String sessionString = "Signed in as: " + Session.getUser().getUserName();
        assert txtSessionInfo != null;
        txtSessionInfo.setText(sessionString);
    }
    private void ToastMessage(String text) {
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(getApplicationContext(), text, duration);
        toast.show();
    }
}
