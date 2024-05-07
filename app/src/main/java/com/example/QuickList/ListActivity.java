package com.example.QuickList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity implements ProductInterface {
    DatabaseHelper databaseHelper;
    Cursor cursor;
    Button backButton, btnAddProduct, btnDialogAddProduct, btnDialogEditProduct;
    ProductAdapter adapter;
    RecyclerView recyclerView;
    List<String> product_names;
    List<Integer> product_amounts;
    Dialog dialogAddItem, dialogEditItem;
    EditText etDialogProductName, etDialogEditProductName, etDialogEditProductAmount;
    TextView tvDialogEditProductName;

    Intent intent = getIntent();
    int listID;
    int userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        backButton = findViewById(R.id.backButton);

        backButton.setOnClickListener(v -> {
//            Intent intent = new Intent(ListActivity.this, HomePage.class);
            finish();
        });

        product_names = new ArrayList<>();
        product_amounts = new ArrayList<>();

        recyclerView = findViewById(R.id.rvProducts);
        btnAddProduct = findViewById(R.id.btnAddProduct);

        dialogAddItem = new Dialog(ListActivity.this);
        dialogAddItem.setContentView(R.layout.dialog_add_product);
        dialogAddItem.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogAddItem.setCancelable(false);

        btnDialogAddProduct = dialogAddItem.findViewById(R.id.btnDialogAddProduct);
        etDialogProductName = dialogAddItem.findViewById(R.id.etDialogAddProductName);

        dialogEditItem = new Dialog(ListActivity.this);
        dialogEditItem.setContentView(R.layout.dialog_product_info);
        dialogEditItem.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogEditItem.setCancelable(false);

        etDialogEditProductName = dialogEditItem.findViewById(R.id.etDialogEditProductName);
        etDialogEditProductAmount = dialogEditItem.findViewById(R.id.etDialogEditProductAmount);
        tvDialogEditProductName = dialogEditItem.findViewById(R.id.tvDialogEditProductName);
        btnDialogEditProduct = dialogEditItem.findViewById(R.id.btnDialogEditProduct);
        userID = intent.getIntExtra("userID", -1);
        listID = intent.getIntExtra("listID", -1);

        cursor = databaseHelper.getProducts(listID, userID);
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range")
                String name = cursor.getString(cursor.getColumnIndex("name"));
                @SuppressLint("Range")
                int amount = cursor.getInt(cursor.getColumnIndex("amount"));
                product_names.add(name);
                product_amounts.add(amount);
            } while (cursor.moveToNext());
        }
        adapter = new ProductAdapter(this, product_names, product_amounts, this);

        //Prendo tutti i valori dal database e li metto in una array list

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);

        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogAddItem.show();
            }
        });

        btnDialogAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etDialogProductName.getText().toString();
                etDialogProductName.setText("");
                databaseHelper.createProduct(name, 1, listID);
                adapter.notifyDataSetChanged();
                dialogAddItem.dismiss();
            }
        });
    }

    @Override
    public void onItemClick(int position) {
        tvDialogEditProductName.setText(product_names.get(position));
        etDialogEditProductName.setText(product_names.get(position));
        etDialogEditProductAmount.setText(String.valueOf(product_amounts.get(position)));
        dialogEditItem.show();
        btnDialogEditProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                product_names.set(position, etDialogEditProductName.getText().toString());
                product_amounts.set(position, Integer.parseInt(etDialogEditProductAmount.getText().toString()));
                adapter.notifyDataSetChanged();
                dialogEditItem.dismiss();
            }
        });
    }

    @Override
    public void onButtonClick(int position) {
        product_names.remove(position);
        product_amounts.remove(position);
        adapter.notifyDataSetChanged();
    }
}