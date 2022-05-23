package com.example.gps_distanceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.learnandroid.loginsqlite.databaseClass;

public class loginPage2 extends AppCompatActivity {
    EditText u2, p2;
    Button login, goToSignIn;
    databaseClass data2;
    public static String user2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page2);

        u2 = findViewById(R.id.username2);
        p2 = findViewById(R.id.password2);
        login = findViewById(R.id.login);
        data2 = new databaseClass(this);
        goToSignIn = findViewById(R.id.goToSignIn);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user2 = u2.getText().toString();
                String pass2 = p2.getText().toString();
                if(user2.equals("")|| pass2.equals(""))
                    Toast.makeText(loginPage2.this, "Please enter all the fields!", Toast.LENGTH_SHORT).show();
                else {
                    if (data2.checkusernamepassword(user2, pass2)) {
                        Toast.makeText(loginPage2.this, "Log In successful", Toast.LENGTH_SHORT).show();
                        Intent intent2 = new Intent(loginPage2.this, MainActivity.class);
                        startActivity(intent2);
                    }
                    else
                        Toast.makeText(loginPage2.this, "Invalid Info", Toast.LENGTH_SHORT).show();
                }

            }
        });

        goToSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(loginPage2.this, loginDatabase.class);
                startActivity(intent);
            }
        });
    }
}