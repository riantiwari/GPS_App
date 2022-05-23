package com.example.gps_distanceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.learnandroid.loginsqlite.databaseClass;

public class loginDatabase extends AppCompatActivity {

    EditText username, password, retypePassword;
    Button register, goTologin;
    databaseClass database;
    public static String user1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_database);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        retypePassword = findViewById(R.id.retypePassword);
        register = findViewById(R.id.register);
        goTologin = findViewById(R.id.goToLogin);
        database = new databaseClass(this);


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user1 = username.getText().toString();
                String pass1 = password.getText().toString();
                String retry= retypePassword.getText().toString();
                if(user1.equals("")|| pass1.equals("")|| retry.equals(""))
                    Toast.makeText(loginDatabase.this, "Please enter all the fields!", Toast.LENGTH_SHORT).show();
                else{
                    if(pass1.equals(retry))
                    {
                        Boolean checkUser = database.usernameCheck(user1);
                        if(checkUser == false)
                        {
                            if(database.input(user1.toString(),pass1)) {
                                Toast.makeText(loginDatabase.this, "Registered", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                            }
                            else
                                Toast.makeText(loginDatabase.this, "Registration failed", Toast.LENGTH_SHORT).show();
                        }
                        else
                            Toast.makeText(loginDatabase.this, "User already exists. Proceed to Sign in", Toast.LENGTH_SHORT).show();
                    }
                    else
                        Toast.makeText(loginDatabase.this, "Passwords do not match! Try again!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        goTologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(), loginPage2.class);
                startActivity(intent1);
            }
        });
    }
}