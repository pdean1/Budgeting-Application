package edu.westga.cs6242.budgetingapplication.view.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;

import edu.westga.cs6242.budgetingapplication.R;
import edu.westga.cs6242.budgetingapplication.dal.BudgetDatabaseHandler;
import edu.westga.cs6242.budgetingapplication.model.Bill;
import edu.westga.cs6242.budgetingapplication.model.Session;

public class CreateBillActivity extends AppCompatActivity {

    private EditText etTitle, etAmount, etDateDue;
    private CheckBox cbIsRecurring, cbIsPaid;

    private BudgetDatabaseHandler dbh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_bill);
        this.dbh = new BudgetDatabaseHandler(getApplicationContext(), null);
        this.etTitle = (EditText) findViewById(R.id.etBillTitle);
        this.etAmount = (EditText) findViewById(R.id.etBillAmount);
        this.etDateDue = (EditText) findViewById(R.id.etDateDue);
        this.cbIsPaid = (CheckBox) findViewById(R.id.cbIsPaid);
        this.cbIsRecurring = (CheckBox) findViewById(R.id.cbIsRecurring);
    }


    public void btnAddBill_Click(View v) {
        Bill bill = new Bill();
        bill.setTitle(this.etTitle.getText().toString());
        bill.setAmount(Double.parseDouble(this.etAmount.getText().toString()));
        bill.setDateDue(new Date());
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
    }

    private void ToastMessage(String text) {
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(getApplicationContext(), text, duration);
        toast.show();
    }

}
