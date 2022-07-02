package com.pavanpradeep.cashflow;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ResetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);
        final TextView username = findViewById(R.id.username_reset_text);
        final EditText pass = findViewById(R.id.password_reset);
        final EditText repass = findViewById(R.id.repassword_reset);
        Button confirm = findViewById(R.id.btnConfirm);

        final UsersDb db = new UsersDb(this);

        Intent intent = getIntent();
        username.setText(intent.getStringExtra("username"));

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getText().toString();
                String password = pass.getText().toString();
                String re_password = repass.getText().toString();

                if(password.equals(re_password)){
                    Boolean check_password =db.updatepassword(user,password);
                    if(check_password ==true){
                        Toast.makeText(ResetActivity.this, "Password updated successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(ResetActivity.this, "Password not updated", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(ResetActivity.this, "Passwords not matching", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}