package com.example.QuickList;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

public class ListActivity extends AppCompatActivity {
    Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        backButton = findViewById(R.id.backButton);

        backButton.setOnClickListener(v -> {
//            Intent intent = new Intent(ListActivity.this, HomePage.class);
            finish();
        });
        Button backButton;
    }
}