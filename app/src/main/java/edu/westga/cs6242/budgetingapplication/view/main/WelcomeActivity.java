package edu.westga.cs6242.budgetingapplication.view.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import edu.westga.cs6242.budgetingapplication.R;
import edu.westga.cs6242.budgetingapplication.dal.BudgetDatabaseHandler;
import edu.westga.cs6242.budgetingapplication.model.session.Session;
import edu.westga.cs6242.budgetingapplication.model.User;
import edu.westga.cs6242.budgetingapplication.view.main_menu.MainMenuActivity;

public class WelcomeActivity extends AppCompatActivity {

    private BudgetDatabaseHandler dbh;
    // This variable will be passed around the application like a hot potato
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
            ToastMessage("Sign In Failed");
            return;
        }
        ToastMessage(this.user.getUserName() + " signed in");
        Session.setUser(this.user);
        Intent intent = new Intent(v.getContext(), MainMenuActivity.class);
        startActivity(intent);
    }

    public void btnAddUser_Click(View v) {
        this.user = new User();
        this.user.setUserName(this.inUserName.getText().toString());
        this.user.setPassword(this.inPassword.getText().toString());
        if (this.user.getUserName().equals("") || this.user.getPassword().equals("")) {
            ToastMessage("Please enter in information please");
            return;
        }
        try {
            long a = this.dbh.addUser(this.user);
            if (a == -1)
            {
                ToastMessage("Add Failed: User " +
                        this.user.getUserName() + " already exists!");
                return;
            }
            ToastMessage(this.user.getUserName() +
                    " Added: " + Long.toString(a));
        }
        catch (Exception e) {
            ToastMessage("Add Failed");
        }
    }

    private void ToastMessage(String text) {
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(getApplicationContext(), text, duration);
        toast.show();
    }

}
