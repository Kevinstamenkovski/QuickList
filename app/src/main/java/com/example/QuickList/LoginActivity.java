package com.example.QuickList;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginActivity extends AppCompatActivity {
    DatabaseHelper databaseHelper = new DatabaseHelper(this);
    EditText etEmail, etPassword;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.etEmailLogin);
        etPassword = findViewById(R.id.etPassLogin);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(v -> {
//            Log.e(null, etEmail.getText().toString() + " e " + etPassword.getText().toString());
            Cursor cursor = databaseHelper.getUser(etEmail.getText().toString(), etPassword.getText().toString());
            cursor.moveToFirst();
//            Log.e(null, cursor.getCount() + "");
            try {
                @SuppressLint("Range")
                int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.USER_TABLE_COLUMN_ID));
                @SuppressLint("Range")
                String fullName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.USER_TABLE_COLUMN_FULLNAME));
                @SuppressLint("Range")
                String username = cursor.getString(cursor.getColumnIndex(DatabaseHelper.USER_TABLE_COLUMN_USERNAME));
                @SuppressLint("Range")
                String email = cursor.getString(cursor.getColumnIndex(DatabaseHelper.USER_TABLE_COLUMN_EMAIL));

                User loggedUser = new User(id, fullName, email, username);
//                Log.e(null, fullName + " " + username + " " + email);
                PreferenceManager preferenceManager = new PreferenceManager(this);
                preferenceManager.setLoggedIn(true);

                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("username", username);
                intent.putExtra("email", email);
                startActivity(intent);
                finish();
            }catch (CursorIndexOutOfBoundsException e){
                Toast.makeText(this, "User Not Found", Toast.LENGTH_SHORT).show();
//                Log.e(null, e.getMessage().toString());
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