package com.example.QuickList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    List<String> list_titles;
    List<Integer> list_number_items;
    RecyclerView recyclerView;
    ListAdapter adapter;
    TextView title;
    Button button_add_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        list_titles = new ArrayList<>();
        list_number_items = new ArrayList<>();

        list_titles.add("Ciao");
        list_titles.add("Ciao");
        list_titles.add("Ciao");

        list_number_items.add(1);
        list_number_items.add(1);
        list_number_items.add(1);

        recyclerView = findViewById(R.id.recyclerView);
        title = findViewById(R.id.tvHome);
        button_add_list = findViewById(R.id.btnAddProduct);

        //Prendo tutti i valori dal database e li metto in una array list

        adapter = new ListAdapter(this, list_titles, list_number_items);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    void call() {
        Intent intent = new Intent(HomeActivity.this, ListActivity.class);
        startActivity(intent);
    }
}