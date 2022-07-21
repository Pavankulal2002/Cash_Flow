package com.pavanpradeep.cashflow;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final EditText username = findViewById(R.id.usernameLogin);
        final EditText password = findViewById(R.id.passwordLogin);
        Button signIn = findViewById(R.id.btnSign);
        final UsersDb db = new UsersDb(this);
        TextView newMember = findViewById(R.id.LoginReg);
        newMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getText().toString();
                String pass = password.getText().toString();
                if (!isValidLogUsername(user)) {
                    username.setError("Username can not be empty!");
                }
                if (!isValidLogPasword(pass)) {
                    password.setError("Password can not be empty!");
                }
                if (user.equals("") || pass.equals("")) {
                    Toast.makeText(LoginActivity.this, "Please enter Login Details!", Toast.LENGTH_SHORT).show();
                } else {
                    Boolean result = db.chkUserNamePassword(user, pass);
                    if (result == true) {
                        Toast.makeText(LoginActivity.this, "Successful Login", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), BudgetPieChart.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(LoginActivity.this, "Invalid Login credentials!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private boolean isValidLogUsername(String user) {
        return user != null;
    }

    private boolean isValidLogPasword(String pass) {
        return pass != null;
    }
}