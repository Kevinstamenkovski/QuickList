package com.example.QuickList;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {
    Button button_login;
    EditText et_email_login;
    EditText et_pass_login;

    String db_email;
    String db_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        button_login = findViewById(R.id.btnLogin);
        et_email_login = findViewById(R.id.etEmailLogin);
        et_pass_login = findViewById(R.id.etPassLogin);

        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((et_email_login.getText().toString()).equals(db_email) && (et_pass_login.getText().toString()).equals(db_pass)) {
                    Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        });
    }
}