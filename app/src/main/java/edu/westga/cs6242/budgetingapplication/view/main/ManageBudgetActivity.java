package edu.westga.cs6242.budgetingapplication.view.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;

import edu.westga.cs6242.budgetingapplication.R;
import edu.westga.cs6242.budgetingapplication.model.MonthlyBudget;
import edu.westga.cs6242.budgetingapplication.model.Session;
import edu.westga.cs6242.budgetingapplication.model.User;

public class ManageBudgetActivity extends AppCompatActivity {

    private User user;

    private MonthlyBudget budget;

    TextView titleLabel, descriptionLabel;
    EditText dateLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_budget);
        this.user = Session.getUser();
        this.budget = Session.getMonthlyBudget1();
        updateBudgetInformation();
        updateSessiontText();
    }

    private void updateBudgetInformation() {
        this.titleLabel = (TextView) findViewById(R.id.tvBudgetTitleLbl);
        this.descriptionLabel = (TextView) findViewById(R.id.etDescriptionLbl);
        this.dateLabel = (EditText) findViewById(R.id.etDateCreatedLbl);
        titleLabel.setText(this.budget.getTitle());
        descriptionLabel.setText(this.budget.getDescription());
        dateLabel.setEnabled(false);
        dateLabel.setText(this.budget.getDateCreated().toString());
    }

    private void updateSessiontText() {
        TextView txtSessionInfo = (TextView) findViewById(R.id.tvUserInformation);
        assert txtSessionInfo != null;
        String sessionString = "Signed in as: " + this.user.getUserName();
        txtSessionInfo.setText(sessionString);
    }
}
