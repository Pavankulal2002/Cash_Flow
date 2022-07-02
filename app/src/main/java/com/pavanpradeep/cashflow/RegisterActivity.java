package com.pavanpradeep.cashflow;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        final EditText username = findViewById(R.id.username);
        final EditText password = findViewById(R.id.password);
        final EditText re_password = findViewById(R.id.repassword);
        Button register=findViewById(R.id.btnRegister);
        TextView login =findViewById(R.id.btnLogin);
        TextView forgot = findViewById(R.id.btnForget);

        final UsersDb db = new UsersDb(this );
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getText().toString();
                String pass = password.getText().toString();

                String repass = re_password.getText().toString();

                if(!isValidPassword(pass)){
                    password.setError("Password too short! 6 characters Required");
                }
                if(!isValidConfirmPassword(pass,repass)){
                    re_password.setError("Passwords don't match!");
                }
                if (user.equals("")){
                    username.setError("Username can not be empty!");
                }
                if(user.equals("")|| pass.equals("")||repass.equals("")){
                    Toast.makeText(RegisterActivity.this, "Please Fill in all the Fileds!", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(pass.equals(repass)){
                        Boolean usercheckResullt =db.chkUserName(user);
                        if(usercheckResullt ==false){
                            Boolean regResult = db.insertData(user,pass);
                            if(regResult ==true){
                                Toast.makeText(RegisterActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                                startActivity(intent);
                            }
                            else{
                                Toast.makeText(RegisterActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(RegisterActivity.this, "User already exists!\n Please Sign In", Toast.LENGTH_SHORT).show();
                        }

                    }
                    else{
                        Toast.makeText(RegisterActivity.this, "Passwords don't match", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
            }
        });
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),PasswordActivity.class);
                startActivity(intent);
            }
        });
    }
    private boolean isValidPassword(String pass){
        if(pass != null && pass.length()>=6){
            return true;
        }
        else{
            return false;
        }
    }
    private boolean isValidConfirmPassword(String pass,String repass){
        if(pass.equals(repass)){
            return true;
        }
        else{
            return false;
        }
    }
}