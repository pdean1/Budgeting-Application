package edu.westga.cs6242.budgetingapplication.view.budget_management.manage.create;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import edu.westga.cs6242.budgetingapplication.R;
import edu.westga.cs6242.budgetingapplication.dal.BudgetDatabaseHandler;
import edu.westga.cs6242.budgetingapplication.model.Earning;
import edu.westga.cs6242.budgetingapplication.util.session.Session;
import edu.westga.cs6242.budgetingapplication.view.abstract_views.PortraitOnlyActivity;

public class CreateEarningActivity extends PortraitOnlyActivity implements View.OnClickListener {

    private TextView
            tvDateEarned;
    private EditText
            etTitle,
            etAmount;
    private CheckBox
            cbIsRecurring;

    private DatePickerDialog datePickerDialogDateEarned;

    private BudgetDatabaseHandler dbh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_earning);

        this.findViewsById();

        this.getDatabase();

        this.setUpDatePicker();

        this.updateSessionText();

    }

    private void updateSessionText() {
        TextView txtSessionInfo = (TextView) findViewById(R.id.tvUserName);
        assert txtSessionInfo != null;
        String sessionString = "Signed in as: " + Session.getUser().getUserName();
        txtSessionInfo.setText(sessionString);
    }

    public void btnAddEarning_Click(View v) {
        if (validateFields()) {
            Earning earning = new Earning();
            earning.setTitle(this.etTitle.getText().toString());
            String amount = this.etAmount.getText().toString().replaceAll("[^\\d.]+", "");
            earning.setAmount(Double.parseDouble(amount));
            earning.setDateEarned(this.tvDateEarned.getText().toString());
            earning.setIsRecurring(this.cbIsRecurring.isChecked());
            earning.setBudgetId(Session.getMonthlyBudget1().getId());
            if (this.dbh.addEarning(earning) == -1) {
                ToastMessage("Unable to add Earning");
                return;
            }
            ToastMessage("Earning Added");
            this.finish();
        }
    }

    private boolean validateFields() {
        if (this.etTitle.getText().toString().length() < 5) {
            ToastMessage("Add more to the title");
            return false;
        }
        if (this.etAmount.getText().toString().equals("")) {
            ToastMessage("Add an amount");
            return false;
        }
        try {
            Session.dateFormatView.parse(this.tvDateEarned.getText().toString());
        } catch (Exception e) {
            ToastMessage("Invalid Date");
            return false;
        }
        return true;
    }

    private void findViewsById() {
        this.etTitle = (EditText) findViewById(R.id.etEarningTitle);
        this.etAmount = (EditText) findViewById(R.id.etEarningAmount);
        this.etAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            private String current = "";

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals(current)) {
                    etAmount.removeTextChangedListener(this);

                    String cleanString = s.toString().replaceAll("[$,.]", "");

                    double parsed = Double.parseDouble(cleanString);
                    String formatted = Session.numberFormat.format((parsed / 100));

                    current = formatted;
                    etAmount.setText(formatted);
                    etAmount.setSelection(formatted.length());

                    etAmount.addTextChangedListener(this);
                }
            }
        });
        this.tvDateEarned = (TextView) findViewById(R.id.tvDateEarned);
        this.cbIsRecurring = (CheckBox) findViewById(R.id.cbIsRecurring);
    }

    private void getDatabase() {
        this.dbh = new BudgetDatabaseHandler(getApplicationContext());
    }

    private void setUpDatePicker() {
        this.tvDateEarned.setOnClickListener(this);
        Calendar calendar = Calendar.getInstance();
        this.datePickerDialogDateEarned = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                tvDateEarned.setText(Session.dateFormatView.format(newDate.getTime()));
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v == this.tvDateEarned)
            datePickerDialogDateEarned.show();
    }

    private void ToastMessage(String text) {
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(getApplicationContext(), text, duration);
        toast.show();
    }
}
