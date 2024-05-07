package com.example.QuickList;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class RegisterActivity extends AppCompatActivity {
    DatabaseHelper databaseHelper = new DatabaseHelper(this);;
    EditText etFirstName, etLastName, etUserName, etEmail, etPassword;
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etFirstName = findViewById(R.id.etNameRegister);
        etLastName = findViewById(R.id.etSurnameRegister);
        etUserName = findViewById(R.id.etUserName);
        etEmail = findViewById(R.id.etEmailRegister);
        etPassword = findViewById(R.id.etPassRegister);
        btnRegister = findViewById(R.id.btnRegister);

        /*String hashedPassword = hashPassword(etPassword.getText().toString());
        Log.e(null, password.toString());
        Log.e(null, hashedPassword);*/

        btnRegister.setOnClickListener(v -> {
            try {
                Log.i(null, databaseHelper.createUser(etFirstName.getText().toString(),
                        etLastName.getText().toString(), etUserName.getText().toString(),
                        etEmail.getText().toString(), etPassword.getText().toString()));
            }catch (SQLiteException error){
                Log.e(null, "ERROR in SQLite: "+ error);
            }finally{
                PreferenceManager preferenceManager = new PreferenceManager(this);
                preferenceManager.setLoggedIn(true);
/*
                Log.i(null, etPassword.getText().toString());
                Log.i(null, "Block Executed");
*/
                Cursor cursor = databaseHelper.getUser(etEmail.getText().toString(), etPassword.getText().toString());
                cursor.moveToFirst();
                Log.e("cursor", cursor.getCount() + "");
                @SuppressLint("Range")
                int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.USER_TABLE_COLUMN_ID));

                Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
                finish();
            }
        });
    }
    private String hashPassword(String password){
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