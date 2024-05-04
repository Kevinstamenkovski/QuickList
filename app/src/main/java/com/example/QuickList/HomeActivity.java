package com.example.QuickList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements ListInterface{
    PreferenceManager preferenceManager;

    List<String> list_titles;
    List<Integer> list_number_items;
    RecyclerView recyclerView;
    ListAdapter adapter;
    TextView title;
    Button button_add_list;
    Button logoutBtn;

    Dialog dialog;
    Button btnDialogAddList;
    EditText etDialogListName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        preferenceManager = new PreferenceManager(this);
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
        logoutBtn = findViewById(R.id.logoutBtn);

        dialog = new Dialog(HomeActivity.this);
        dialog.setContentView(R.layout.popup_add_list);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);

        btnDialogAddList = dialog.findViewById(R.id.btnDialog);
        etDialogListName = dialog.findViewById(R.id.etDialog);
        btnDialogAddList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etDialogListName.getText().toString();
                dialog.dismiss();
            }
        });
        //Prendo tutti i valori dal database e li metto in una array list

        adapter = new ListAdapter(this, list_titles, list_number_items, this);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);

        button_add_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });

        logoutBtn.setOnClickListener(v -> {
            preferenceManager.setLoggedIn(false);
            Intent intent = new Intent(HomeActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(HomeActivity.this, ListActivity.class);
        startActivity(intent);
    }
}