package com.example.QuickList;

import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class RegisterActivity extends AppCompatActivity {
    DatabaseHelper db;
    EditText firstName;
    EditText lastName;
    EditText userName;
    EditText email;
    EditText password;
    Button register;
    Button DoB;
    EditText DoBoutput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db = new DatabaseHelper(this);
        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.LastName);
        userName = findViewById(R.id.userName);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        register = findViewById(R.id.register);
        DoB = findViewById(R.id.DoB);
        DoBoutput = findViewById(R.id.DateOfBirthOUTPUT);
        String hashedPassword = hashPassword(password.getText().toString());
        Log.e(null, password.toString());
        Log.e(null, hashedPassword);
        register.setOnClickListener(v -> {
            try {
                Log.i(null, db.createUser(firstName.getText().toString(), lastName.getText().toString(), userName.getText().toString(), email.getText().toString(), hashedPassword, "test BLOB File", "test Date of Birth"));
            }catch (SQLiteException error){
                Log.e(null, "ERROR in SQLite: "+ error);
            }finally{
                Log.i(null, "Block Executed");
                Log.i(null, password.getText().toString());
                Log.i(null, hashedPassword);
                Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
//                startActivity(intent);
//                finish();
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
        }catch (NoSuchAlgorithmException error){
            error.printStackTrace();
            return null;
        }
    }
}