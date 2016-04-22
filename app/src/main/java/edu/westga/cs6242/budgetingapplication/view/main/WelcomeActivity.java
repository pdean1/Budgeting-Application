package edu.westga.cs6242.budgetingapplication.view.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import edu.westga.cs6242.budgetingapplication.R;
import edu.westga.cs6242.budgetingapplication.dal.BudgetDatabaseHandler;
import edu.westga.cs6242.budgetingapplication.model.User;

public class WelcomeActivity extends AppCompatActivity {

    private BudgetDatabaseHandler dbh;

    private User user;

    private EditText inUserName;
    private EditText inPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        this.dbh = new BudgetDatabaseHandler(this, null);
        this.inUserName = (EditText) findViewById(R.id.etUserName);
        this.inPassword = (EditText) findViewById(R.id.etPassword);
        this.user = new User();
    }

    public void attemptSignIn(View v) {
        this.user = new User();
        this.user.setUserName(this.inUserName.getText().toString());
        this.user.setPassword(this.inPassword.getText().toString());
        this.user = this.dbh.attemptLogIn(this.user);
        if (this.user == null) {
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(getApplicationContext(), "Sign In Failed", duration);
            toast.show();
            return;
        }
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(getApplicationContext(), this.user.getUserName() + " signed in", duration);
        toast.show();
    }

}
