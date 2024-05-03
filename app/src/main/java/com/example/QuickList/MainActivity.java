package com.example.QuickList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Button button_login;
    Button button_register;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        PreferenceManager preferenceManager = new PreferenceManager(this);
        if (preferenceManager.isLoggedIn()){
            Intent i = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(i);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_login = findViewById(R.id.btnToLogin);
        button_register = findViewById(R.id.btnToRegister);

        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });

        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });
    }
}