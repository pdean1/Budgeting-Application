package edu.westga.cs6242.budgetingapplication.view.budget_management.manage;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import edu.westga.cs6242.budgetingapplication.R;
import edu.westga.cs6242.budgetingapplication.dal.BudgetDatabaseHandler;
import edu.westga.cs6242.budgetingapplication.model.Bill;
import edu.westga.cs6242.budgetingapplication.model.Earning;
import edu.westga.cs6242.budgetingapplication.model.MonthlyBudget;
import edu.westga.cs6242.budgetingapplication.view.abstract_views.PortraitOnlyActivity;
import edu.westga.cs6242.budgetingapplication.view.budget_management.manage.create.CreateBillActivity;
import edu.westga.cs6242.budgetingapplication.view.budget_management.manage.create.CreateEarningActivity;

import static edu.westga.cs6242.budgetingapplication.util.session.Session.getMonthlyBudget1;
import static edu.westga.cs6242.budgetingapplication.util.session.Session.getUser;
import static edu.westga.cs6242.budgetingapplication.util.session.Session.numberFormat;
import static edu.westga.cs6242.budgetingapplication.util.session.Session.setMonthlyBudget1;

public class ManageBudgetActivity extends PortraitOnlyActivity {

    private BudgetDatabaseHandler dbh;

    private ArrayAdapter<Bill> billArrayAdapter;
    private ArrayAdapter<Earning> earningArrayAdapter;

    private TabHost tabHost;

    private ArrayList<Bill> bills;
    private ArrayList<Earning> earnings;

    private TextView titleLabel; //
    private TextView tvDescription;
    private TextView tvDateCreated;
    private TextView statsBillsLabel; //
    private TextView statsEarningsLabel; //
    private ListView
            lvBills;
    private ListView lvEarnings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_budget);
        this.dbh = new BudgetDatabaseHandler(getApplicationContext());
        this.tabHost = (TabHost) findViewById(R.id.tabHost);
        getViewsById();
        updateBudgetInformation();
        updateSessionText();
        setUpTabs();
    }

    private void getViewsById() {
        this.titleLabel         = (TextView) findViewById(R.id.tvBudgetTitleLbl);
        this.statsBillsLabel    = (TextView) findViewById(R.id.tvStatBills);
        this.statsEarningsLabel = (TextView) findViewById(R.id.tvStatEarnings);
        this.tvDateCreated = (TextView) findViewById(R.id.tvDateCreated);
        this.tvDescription = (TextView) findViewById(R.id.tvDescr);
        this.lvBills            = (ListView) findViewById(R.id.lvBills);
        this.lvEarnings         = (ListView) findViewById(R.id.lvEarnings);
    }

    private void updateBudgetInformation() {
        titleLabel.setText(getMonthlyBudget1().getTitle());
        this.tvDescription.setText(getMonthlyBudget1().getDescription());
        this.tvDateCreated.setText(getMonthlyBudget1().getDateCreated());
        updateList();
    }

    private void updateList() {
        setUpListViews();
        addOnClickListenerToBillsListView();
        addOnClickListenerToEarningsListView();
    }

    private void setUpListViews() {
        /**
        * All Bills in the Database
        * */
        this.bills    = this.dbh.getBillsByBudgetId(getMonthlyBudget1().getId());
        /**
         * All Earnings in the Database
         * */
        this.earnings = this.dbh.getEarningsByBudgetId(getMonthlyBudget1().getId());

        this.billArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, bills);
        this.earningArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, earnings);
        this.lvBills.setAdapter(billArrayAdapter);
        this.lvEarnings.setAdapter(earningArrayAdapter);
        refreshStatsTab();
    }

    private void refreshStatsTab() {
        double
                billsAmount = displayStatBillAmount(),
                earningsAmount = displayStatEarningAmount();

        double EarningsLessBills = earningsAmount + ((billsAmount > 0) ?
                billsAmount * -1.0 :
                billsAmount);
        TextView tvEarningsLessBills = (TextView) findViewById(R.id.tvStatsEarningsLessBills);
        assert tvEarningsLessBills != null;
        tvEarningsLessBills.setText(String.format("%s%s", getString(R.string.txt_stats_elb),
                numberFormat.format(EarningsLessBills)));
        TextView tvSumOfBillsNotPaid = (TextView) findViewById(R.id.tvSumOfBillsNotPaid);

        double billsNotPaidAmount = 0.0;
        for (Bill bill : this.bills) {
            if (bill.isPaid())
                continue;
            billsNotPaidAmount += bill.getAmount();
        }
        assert tvSumOfBillsNotPaid != null;
        tvSumOfBillsNotPaid.setText(String.format("%s%s", getString(R.string.txt_sum_of_bills_not_paid),
                numberFormat.format(billsNotPaidAmount)));

        double earningsToBillsRatio;
        try {
            earningsToBillsRatio = earningsAmount / billsAmount;
        } catch (Exception e) {
            earningsToBillsRatio = 0.0;
        }
        TextView tvStatEarningsToBillsRatio = (TextView) findViewById(R.id.tvStatEarningsToBillsRatio);
        assert tvStatEarningsToBillsRatio != null;
        tvStatEarningsToBillsRatio.setText((!Double.isNaN(earningsToBillsRatio)) ?
                String.format(getString(R.string.txt_ratio),earningsToBillsRatio) :
                getString(R.string.txt_ratio) + getString(R.string.txt_0));
    }

    private void addOnClickListenerToEarningsListView() {
        this.lvEarnings.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Earning earning = earnings.get(position);
                ToastMessage(earning.getTitle());
                final Dialog dialog = new Dialog(view.getContext());
                dialog.setContentView(R.layout.dialog_earnings);
                dialog.setTitle("View " + earning.getTitle());
                TextView tvTitle = (TextView) dialog.findViewById(R.id.tvEarningTitle);
                TextView tvAmount = (TextView) dialog.findViewById(R.id.tvEarningAmount);
                TextView tvDateEarned = (TextView) dialog.findViewById(R.id.tvDateEarned);
                TextView tvIsRecurring = (TextView) dialog.findViewById(R.id.tvIsRecurring);
                Button btnDelete = (Button) dialog.findViewById(R.id.btnDeleteEarning);
                btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (dbh.deleteEarningById(earning.getId())) {
                            ToastMessage("Deleted");
                            earnings.remove(earning);
                            dialog.hide();
                            finish();
                            startActivity(getIntent());
                        }
                    }
                });
                tvTitle.setText(earning.getTitle());
                tvAmount.setText(numberFormat.format(earning.getAmount()));
                tvDateEarned.setText(earning.getDateEarned());
                tvIsRecurring.setText((earning.isRecurring()) ? "Recurring" : "Not Recurring");
                dialog.show();
            }
        });
    }

    private void addOnClickListenerToBillsListView() {
        this.lvBills.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Bill bill = bills.get(position);
                final Dialog dialog = new Dialog(view.getContext());

                dialog.setContentView(R.layout.dialog_bills);
                dialog.setTitle("View " + bill.getTitle());

                TextView tvBillTitle = (TextView) dialog.findViewById(R.id.tvBillTitle);
                TextView tvBillAmount = (TextView) dialog.findViewById(R.id.tvBillAmount);
                TextView tvDateDue = (TextView) dialog.findViewById(R.id.tvDateDue);
                TextView tvBillDatePaid = (TextView) dialog.findViewById(R.id.tvBillDatePaid);

                final TextView tvBillIsRecurring = (TextView) dialog.findViewById(R.id.tvBillIsRecurring);
                final TextView tvBillIsPaid = (TextView) dialog.findViewById(R.id.tvBillIsPaid);
                tvBillIsPaid.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (bill.isPaid()) {
                            bill.setIsPaid(false);
                            tvBillIsPaid.setText(R.string.txt_not_paid);
                        } else {
                            bill.setIsPaid(true);
                            tvBillIsPaid.setText(R.string.txt_paid);
                        }
                        dbh.updateBill(bill);
                        // Much explain what happens
                        ToastMessage(bill.getTitle() + " updated to " + ((bill.isPaid()) ? "paid":"not paid"));
                        billArrayAdapter.notifyDataSetChanged();
                    }
                });

                TextView btnDelete = (TextView) dialog.findViewById(R.id.btnDeleteBill);
                btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (dbh.deleteBillById(bill.getId())) {
                            bills.remove(bill);
                            ToastMessage(bill.getTitle() + " deleted");
                            finish();
                            startActivity(getIntent());
                        }
                        dialog.hide();
                    }
                });

                tvBillTitle.setText(bill.getTitle());
                tvBillAmount.setText(numberFormat.format(bill.getAmount()));
                tvDateDue.setText(bill.getDateDue());
                tvBillDatePaid.setText(bill.getDatePaid());
                tvBillIsRecurring.setText((bill.isRecurring()) ? "Recurring" : "Not Recurring");
                // BUG FIX #1
                tvBillIsPaid.setText((bill.isPaid()) ? "Paid" : "Not Paid");

                dialog.show();
            }
        });
    }

    private double displayStatEarningAmount() {
        double earningsAmount = 0.0;
        for (Earning b : this.earnings) {
            earningsAmount += b.getAmount();
        }
        this.statsEarningsLabel.setText(String.format("Total earnings amount: %s", numberFormat.format
                (earningsAmount)));
        return earningsAmount;
    }

    private double displayStatBillAmount() {
        double billsAmount = 0.0;
        for (Bill b : this.bills) {
            billsAmount += b.getAmount();
        }
        this.statsBillsLabel.setText(String.format("Total bill amount: %s", numberFormat.format(billsAmount)));
        return billsAmount;
    }

    private void updateSessionText() {
        TextView txtSessionInfo = (TextView) findViewById(R.id.tvUserInformation);
        assert txtSessionInfo != null;
        String sessionString = "Signed in as: " + getUser().getUserName();
        txtSessionInfo.setText(sessionString);
    }

    private void setUpTabs() {
        this.tabHost.setup();

        TabHost.TabSpec spec = this.tabHost.newTabSpec("Bills");
        spec.setContent(R.id.tabBills);
        spec.setIndicator("Bills");
        tabHost.addTab(spec);

        spec = this.tabHost.newTabSpec("Earnings");
        spec.setContent(R.id.tabEarnings);
        spec.setIndicator("Earnings");
        tabHost.addTab(spec);

        spec = this.tabHost.newTabSpec("Statistics");
        spec.setContent(R.id.tabStatistics);
        spec.setIndicator("Statistics");
        tabHost.addTab(spec);
    }

    private void createNewBill() {
        Intent intent = new Intent(getApplicationContext(), CreateBillActivity.class);
        startActivity(intent);
        updateList();
    }

    private void createNewEarning() {
        Intent intent = new Intent(getApplicationContext(), CreateEarningActivity.class);
        startActivity(intent);
        updateList();
    }

    private void deleteBudget() {
        this.dbh.deleteMonthlyBudget(getMonthlyBudget1());
        ToastMessage("Budget Deleted");
        setMonthlyBudget1(new MonthlyBudget());
        this.finish();
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
            case R.id.deleteThisBudget:
                this.deleteBudget();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onResume() {  // After a pause OR at startup
        super.onResume();
        //Refresh your stuff here
        this.updateBudgetInformation();
    }

    private void ToastMessage(String text) {
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(getApplicationContext(), text, duration);
        toast.show();
    }
}
