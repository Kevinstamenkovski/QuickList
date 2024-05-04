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

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements ListInterface{

    List<String> list_titles;
    List<Integer> list_number_items;
    RecyclerView recyclerView;
    ListAdapter adapter;
    Button button_add_list, btnDialogAddList;
    Dialog dialog;
    EditText etDialogListName;

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

        recyclerView = findViewById(R.id.rvLists);
        button_add_list = findViewById(R.id.btnAddProduct);

        dialog = new Dialog(HomeActivity.this);
        dialog.setContentView(R.layout.popup_add_list);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);

        btnDialogAddList = dialog.findViewById(R.id.btnDialog);
        etDialogListName = dialog.findViewById(R.id.etDialog);

        adapter = new ListAdapter(this, list_titles, list_number_items, this);

        //Prendo tutti i valori dal database e li metto in una array list

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);

        button_add_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });

        btnDialogAddList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etDialogListName.getText().toString();
                etDialogListName.setText("");
                list_titles.add(name);
                list_number_items.add(0);
                adapter.notifyDataSetChanged();

                dialog.dismiss();
            }
        });
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(HomeActivity.this, ListActivity.class);
        startActivity(intent);
    }
}