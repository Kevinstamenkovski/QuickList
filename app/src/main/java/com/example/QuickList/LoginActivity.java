package com.example.QuickList;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginActivity extends AppCompatActivity {
    DatabaseHelper databaseHelper = new DatabaseHelper(this);
    Button button_login;
    EditText et_email_login;
    EditText et_pass_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        button_login = findViewById(R.id.btnLogin);
        et_email_login = findViewById(R.id.etEmailLogin);
        et_pass_login = findViewById(R.id.etPassLogin);

        button_login.setOnClickListener(v -> {


                Log.e(null, et_email_login.getText().toString() + " porcodio " +et_pass_login.getText().toString());
                Cursor cursor = databaseHelper.getUser(et_email_login.getText().toString(),et_pass_login.getText().toString());
                cursor.moveToFirst();
                Log.e(null, cursor.getCount() + "");
                if (cursor != null) {
                    @SuppressLint("Range")
                    int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.USER_TABLE_COLUMN_ID));
                    @SuppressLint("Range")
                    String fullname = cursor.getString(cursor.getColumnIndex(DatabaseHelper.USER_TABLE_COLUMN_FULLNAME));
                    @SuppressLint("Range")
                    String username = cursor.getString(cursor.getColumnIndex(DatabaseHelper.USER_TABLE_COLUMN_USERNAME));
                    @SuppressLint("Range")
                    String email = cursor.getString(cursor.getColumnIndex(DatabaseHelper.USER_TABLE_COLUMN_EMAIL));
                    User loggedUser = new User(id, fullname, email, username);
                    Log.e(null, loggedUser.getUSERNAME());
                    Log.e(null, loggedUser.getFULLNAME());
                    Log.e(null, loggedUser.getEMAIL());
                    PreferenceManager preferenceManager = new PreferenceManager(this);
                    preferenceManager.setLoggedIn(true);
                    Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(i);
                    finish();
                }else {
                    Log.e("LoginActivity", "Error");
                }




        });
    }
    private String hashedPassword(String password){
        String hashedPassword = "";

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.update(password.getBytes());
            byte[] hashedBytes = digest.digest();
            StringBuilder stringBuilder = new StringBuilder();
            for (byte b : hashedBytes) {
                stringBuilder.append(String.format("%02x", b));
            }
            return stringBuilder.toString();
//            return password;
        }catch (NoSuchAlgorithmException error){
            error.printStackTrace();
            return null;
        }
    }
}