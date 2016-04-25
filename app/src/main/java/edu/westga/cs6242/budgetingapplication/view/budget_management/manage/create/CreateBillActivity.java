package edu.westga.cs6242.budgetingapplication.view.budget_management.manage.create;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import edu.westga.cs6242.budgetingapplication.R;
import edu.westga.cs6242.budgetingapplication.dal.BudgetDatabaseHandler;
import edu.westga.cs6242.budgetingapplication.model.Bill;
import edu.westga.cs6242.budgetingapplication.util.session.Session;
import edu.westga.cs6242.budgetingapplication.view.abstract_views.PortraitOnlyActivity;

public class CreateBillActivity extends PortraitOnlyActivity implements View.OnClickListener {

    private EditText etTitle, etAmount;
    private TextView etDateDue;
    private CheckBox cbIsRecurring, cbIsPaid;

    private DatePickerDialog datePickerDialogDateDue;

    private SimpleDateFormat dateFormat;

    private BudgetDatabaseHandler dbh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_bill);
        dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        this.dbh = new BudgetDatabaseHandler(getApplicationContext(), null);
        this.findViewsById();
        this.setDateTimeField();
        this.updateSessionText();
    }

    private void findViewsById() {
        this.etTitle = (EditText) findViewById(R.id.etBillTitle);
        this.etAmount = (EditText) findViewById(R.id.etBillAmount);
        this.etDateDue = (TextView) findViewById(R.id.etDateDue);
        this.cbIsPaid = (CheckBox) findViewById(R.id.cbIsPaid);
        this.cbIsRecurring = (CheckBox) findViewById(R.id.cbIsRecurring);
    }

    private void setDateTimeField() {
        this.etDateDue.setOnClickListener(this);
        Calendar calendar = Calendar.getInstance();
        datePickerDialogDateDue = new DatePickerDialog(this, new OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                etDateDue.setText(dateFormat.format(newDate.getTime()));
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
    }

    public void btnAddBill_Click(View v) {
        if (!validateFields()) {
            return;
        }
        Bill bill = new Bill();
        bill.setTitle(this.etTitle.getText().toString());
        try {
            bill.setAmount(Double.parseDouble(this.etAmount.getText().toString()));
        } catch (Exception e) {
            ToastMessage("Provide an amount please");
            return;
        }
        try {
            bill.setDateDue(Session.dateFormatMMddddyyyy.parse(this.etDateDue.getText().toString()));
            if (this.cbIsPaid.isChecked()) {
                bill.setDatePaid(Session.dateFormatMMddddyyyy.parse(this.etDateDue.getText().toString()));
            } else {
                bill.setDatePaid(new Date());
            }
        } catch (Exception e) {
            ToastMessage("Provide a valid date");
            return;
        }
        bill.setDatePaid(new Date());
        bill.setIsRecurring(this.cbIsRecurring.isChecked());
        bill.setIsPaid(this.cbIsPaid.isChecked());
        bill.setBudgetId(Session.getMonthlyBudget1().getId());
        long result = this.dbh.addBill(bill);
        if (result == -1) {
            ToastMessage("Unable to add Bill");
            return;
        }
        ToastMessage("Bill Added");
        this.finish();
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
            dateFormat.parse(this.etDateDue.getText().toString());
        }
        catch (Exception e) {
            ToastMessage("Invalid Date");
            return false;
        }
        return true;
    }

    private void updateSessionText() {
        TextView txtSessionInfo = (TextView) findViewById(R.id.tvUserName);
        assert txtSessionInfo != null;
        String sessionString = "Signed in as: " + Session.getUser().getUserName();
        txtSessionInfo.setText(sessionString);
    }

    private void ToastMessage(String text) {
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(getApplicationContext(), text, duration);
        toast.show();
    }

    private boolean validateInput() {
        return false;
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v == this.etDateDue)
            datePickerDialogDateDue.show();
    }
}
