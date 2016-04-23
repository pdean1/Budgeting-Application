package edu.westga.cs6242.budgetingapplication.view.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import edu.westga.cs6242.budgetingapplication.R;
import edu.westga.cs6242.budgetingapplication.dal.BudgetDatabaseHandler;
import edu.westga.cs6242.budgetingapplication.model.MonthlyBudget;
import edu.westga.cs6242.budgetingapplication.model.User;
import edu.westga.cs6242.budgetingapplication.util.ApplicationVariableStrings;

public class ManageBudgetsActivity extends AppCompatActivity {

    private Spinner spinnerBudgets;
    private TextView lblTitle, lblDescription, lblDateCreated;

    private ArrayList<MonthlyBudget> monthlyBudgets;

    private BudgetDatabaseHandler dbh;

    private User user;

    private ArrayAdapter<MonthlyBudget> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_manage_budgets);
            this.user = getIntent().getParcelableExtra(ApplicationVariableStrings.SESSION_USER);
            this.spinnerBudgets = (Spinner) findViewById(R.id.spinBudgets);
            this.lblTitle = (TextView) findViewById(R.id.tvBudgetTitle);
            this.lblDescription = (TextView) findViewById(R.id.tvDescription);
            this.lblDateCreated = (TextView) findViewById(R.id.tvDateCreatedLbl);
            this.dbh = new BudgetDatabaseHandler(getApplicationContext(), null);
            this.monthlyBudgets = this.dbh.getMonthlyBudgetByUserId(this.user.getId());
            this.arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,
                    this.monthlyBudgets);
            this.spinnerBudgets.setAdapter(this.arrayAdapter);
            this.spinnerBudgets.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view,
                                           int position, long id) {
                    RefreshView(position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    RefreshView(-1);
                }
            });
        } catch (Exception e) {
            ToastMessage("Fail");
        }
        updateSessiontText();
    }

    public void btnManageBudget_Click(View v) {
        MonthlyBudget budget = this.monthlyBudgets.get(spinnerBudgets.getSelectedItemPosition());
        Bundle bundle = new Bundle();
        bundle.putParcelable(ApplicationVariableStrings.SESSION_USER, this.user);
        bundle.putParcelable(ApplicationVariableStrings.MANAGE_BUDGET, budget);
        Intent intent = new Intent(v.getContext(), ManageBudgetActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }



    private void updateSessiontText() {
        TextView txtSessionInfo = (TextView) findViewById(R.id.tvSessionLbl);
        assert txtSessionInfo != null;
        String sessionString = "Signed in as: " + this.user.getUserName();
        txtSessionInfo.setText(sessionString);
    }

    private void RefreshView(int spinnerIndex) {
        if (spinnerIndex == -1) {
            this.lblTitle.setText("");
            this.lblDescription.setText("");
            this.lblDateCreated.setText("");
            return;
        }
        MonthlyBudget budget = this.monthlyBudgets.get(spinnerIndex);
        this.lblTitle.setText(budget.getTitle());
        this.lblDescription.setText(budget.getDescription());
        this.lblDateCreated.setText(budget.getDateCreated().toString());
    }


    private void ToastMessage(String text) {
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(getApplicationContext(), text, duration);
        toast.show();
    }
}
