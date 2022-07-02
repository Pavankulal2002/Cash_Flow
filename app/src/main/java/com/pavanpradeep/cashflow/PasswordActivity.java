package com.pavanpradeep.cashflow;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        final EditText username = findViewById(R.id.username_reset);
        Button reset = findViewById(R.id.btnReset);
        final UsersDb db = new UsersDb(this);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getText().toString();
                Boolean usercheck=db.chkUserName(user);
                if(usercheck ==true){
                    Toast.makeText(PasswordActivity.this, "successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),ResetActivity.class);
                    intent.putExtra("username",user);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(PasswordActivity.this, "User doesn't exists", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}