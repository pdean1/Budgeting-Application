package edu.westga.cs6242.budgetingapplication.view.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;

import edu.westga.cs6242.budgetingapplication.R;
import edu.westga.cs6242.budgetingapplication.model.MonthlyBudget;
import edu.westga.cs6242.budgetingapplication.model.User;
import edu.westga.cs6242.budgetingapplication.util.ApplicationVariableStrings;

public class ManageBudgetActivity extends AppCompatActivity {

    private User user;

    private MonthlyBudget budget;

    TextView titleLabel;
    EditText descriptionLabel, dateLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_budget);
        this.user = getIntent().getParcelableExtra(ApplicationVariableStrings.SESSION_USER);
        this.budget = getIntent().getParcelableExtra(ApplicationVariableStrings.MANAGE_BUDGET);
        this.titleLabel = (TextView) findViewById(R.id.tvBudgetTitleLbl);
        this.descriptionLabel = (EditText) findViewById(R.id.etDescriptionLbl);
        this.dateLabel = (EditText) findViewById(R.id.etDateCreatedLbl);
        updateBudgetInformation();
    }

    private void updateBudgetInformation() {
        titleLabel.setText(this.budget.getTitle());
        descriptionLabel.setText(this.budget.getDescription());
        dateLabel.setEnabled(false);
        dateLabel.setText(this.budget.getDateCreated().toString());
    }
}
