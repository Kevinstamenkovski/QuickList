package com.example.QuickList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements ListInterface{
    PreferenceManager preferenceManager;
    DatabaseHelper databaseHelper;
    Intent intent;

    List<String> list_titles;
    List<Integer> list_number_items;
    RecyclerView recyclerView;
    ListAdapter adapter;
    Button button_add_list, btnDialogAddList,logoutBtn;
    Dialog dialog;
    EditText etDialogListName;
    public int userID;
    public int listID;

//    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        preferenceManager = new PreferenceManager(this);
        databaseHelper = new DatabaseHelper(this);
        intent = getIntent();
        userID = intent.getIntExtra("id", -1);

        list_titles = new ArrayList<>();
        list_number_items = new ArrayList<>();



        Cursor cursor = databaseHelper.getListsByUserID(userID);
        @SuppressLint("Range")
        int listID = cursor.getInt(cursor.getColumnIndex("listID"));

        Log.e(null, "CURSOR COUNT: "+ String.valueOf(cursor.getCount()));
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range")
                int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.LIST_TABLE_COLUMN_ID));
                @SuppressLint("Range")
                String title = cursor.getString(cursor.getColumnIndex(DatabaseHelper.LIST_TABLE_COLUMN_LIST_NAME));
                int number_items = databaseHelper.getProductNumbers(id);
                list_titles.add(title);
                list_number_items.add(number_items);
                Log.e(null, "GETTING DATA...");
            } while (cursor.moveToNext());
        }
        Log.e(null, "FINISHED GETTING LIST");
        adapter = new ListAdapter(this, list_titles, list_number_items, this);


        recyclerView = findViewById(R.id.rvLists);
        button_add_list = findViewById(R.id.btnAddProduct);
        logoutBtn = findViewById(R.id.logoutBtn);

        dialog = new Dialog(HomeActivity.this);
        dialog.setContentView(R.layout.dialog_add_list);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);

        btnDialogAddList = dialog.findViewById(R.id.btnDialogAddList);
        etDialogListName = dialog.findViewById(R.id.etDialogListName);



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
                databaseHelper.createList(name, userID);
                list_titles.add(name);
                list_number_items.add(0);
                adapter.notifyDataSetChanged();

                dialog.dismiss();
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
        intent.putExtra("userID", userID);
        intent.putExtra("listID", listID);
        startActivity(intent);
    }

}