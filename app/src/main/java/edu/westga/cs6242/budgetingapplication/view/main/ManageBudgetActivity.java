package edu.westga.cs6242.budgetingapplication.view.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TabHost;

import edu.westga.cs6242.budgetingapplication.R;
import edu.westga.cs6242.budgetingapplication.model.MonthlyBudget;
import edu.westga.cs6242.budgetingapplication.model.User;

public class ManageBudgetActivity extends AppCompatActivity {

    private User user;

    private MonthlyBudget budget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_budget);

        TabHost tabHost = (TabHost) findViewById(R.id.tabHost);
        assert tabHost != null;
        tabHost.setup();
        TabHost.TabSpec spec = tabHost.newTabSpec("Bills Tab");
        spec.setContent(R.id.bills_tab);
        spec.setIndicator("Bills Tab");
        tabHost.addTab(spec);
        spec = tabHost.newTabSpec("Earnings Tab");
        spec.setContent(R.id.earnings_tab);
        spec.setIndicator("Earnings Tab");
        tabHost.addTab(spec);
    }
}
