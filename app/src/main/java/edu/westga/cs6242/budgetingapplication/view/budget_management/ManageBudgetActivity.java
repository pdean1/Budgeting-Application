package edu.westga.cs6242.budgetingapplication.view.budget_management;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import edu.westga.cs6242.budgetingapplication.R;
import edu.westga.cs6242.budgetingapplication.dal.BudgetDatabaseHandler;
import edu.westga.cs6242.budgetingapplication.model.MonthlyBudget;
import edu.westga.cs6242.budgetingapplication.model.Session;

public class ManageBudgetActivity extends AppCompatActivity {

    private BudgetDatabaseHandler dbh;
    private ArrayList<MonthlyBudget> budgets;
    private TabHost tabHost;
    TextView titleLabel, descriptionLabel;
    EditText dateLabel;
    private ListView lvBills, lvEarnings;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_budget);
        this.dbh = new BudgetDatabaseHandler(getApplicationContext(), null);
        this.budgets = this.dbh.getMonthlyBudgetByUserId(Session.getMonthlyBudget1().getId());
        this.tabHost = (TabHost) findViewById(R.id.tabHost);
        updateBudgetInformation();
        updateSessiontText();
        setUpTabs();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.manage_budget_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.new_bill:
                this.createNewBill();
                return true;
            case R.id.new_earning:
                this.createNewEarning();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateBudgetInformation() {
        this.titleLabel = (TextView) findViewById(R.id.tvBudgetTitleLbl);
        this.descriptionLabel = (TextView) findViewById(R.id.etDescriptionLbl);
        this.dateLabel = (EditText) findViewById(R.id.etDateCreatedLbl);
        this.lvBills = (ListView) findViewById(R.id.lvBills);
        this.lvEarnings = (ListView) findViewById(R.id.lvEarnings);
        titleLabel.setText(Session.getMonthlyBudget1().getTitle());
        descriptionLabel.setText(Session.getMonthlyBudget1().getDescription());
        dateLabel.setEnabled(false);
        dateLabel.setText(Session.getMonthlyBudget1().getDateCreated().toString());
    }

    private void updateSessiontText() {
        TextView txtSessionInfo = (TextView) findViewById(R.id.tvUserInformation);
        assert txtSessionInfo != null;
        String sessionString = "Signed in as: " + Session.getUser().getUserName();
        txtSessionInfo.setText(sessionString);
    }

    private void setUpTabs() {
        this.tabHost.setup();

        TabHost.TabSpec spec = this.tabHost.newTabSpec("Bills Tab");
        spec.setContent(R.id.tabBills);
        spec.setIndicator("Bills Tab");
        tabHost.addTab(spec);

        spec = this.tabHost.newTabSpec("Earnings Tab");
        spec.setContent(R.id.tabEarnings);
        spec.setIndicator("Earnings Tab");
        tabHost.addTab(spec);

    }

    private void createNewBill() {
        Intent intent = new Intent(getApplicationContext(), CreateBillActivity.class);
        startActivity(intent);
    }

    private void createNewEarning() {
        Intent intent = new Intent(getApplicationContext(), CreateBillActivity.class);
        startActivity(intent);
    }

    private void ToastMessage(String text) {
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(getApplicationContext(), text, duration);
        toast.show();
    }
}