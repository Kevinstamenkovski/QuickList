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

public class ListActivity extends AppCompatActivity implements ProductInterface {
    DatabaseHelper databaseHelper;
    Cursor cursor;
    Button backButton, btnAddProduct, btnDialogAddProduct, btnDialogEditProduct;
    ProductAdapter adapter;
    RecyclerView recyclerView;
    List<String> product_names;
    List<Integer> product_amounts, product_ids;
    Dialog dialogAddItem, dialogEditItem;
    EditText etDialogProductName, etDialogEditProductName, etDialogEditProductAmount;
    TextView tvListName, tvDialogEditProductName;

    Intent intent;
    int listID;
    int userID;

    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        backButton = findViewById(R.id.backButton);
        databaseHelper = new DatabaseHelper(this);
        backButton.setOnClickListener(v -> {
//            Intent intent = new Intent(ListActivity.this, HomePage.class);
            finish();
        });

        product_names = new ArrayList<>();
        product_amounts = new ArrayList<>();
        product_ids = new ArrayList<>();

        tvListName = findViewById(R.id.tvListName);
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

        intent = getIntent();
        userID = intent.getIntExtra("userID", -1);
        listID = intent.getIntExtra("listID", -1);
        String listName = intent.getStringExtra("listName");
        Log.e(null, userID + ", "+ listID);
        cursor = databaseHelper.getProducts(listID, userID);
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range")
                String name = cursor.getString(cursor.getColumnIndex("name"));
                @SuppressLint("Range")
                int amount = cursor.getInt(cursor.getColumnIndex("amount"));
                @SuppressLint("Range")
                int productID = cursor.getInt(cursor.getColumnIndex("productID"));
                product_names.add(name);
                product_amounts.add(amount);
                product_ids.add(productID);
            } while (cursor.moveToNext());
        }
        tvListName.setText(listName);

        adapter = new ProductAdapter(this, product_names, product_amounts, product_ids, this);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);

        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogAddItem.show();
            }
        });

        btnDialogAddProduct.setOnClickListener(v ->  {
            String name = etDialogProductName.getText().toString();
            databaseHelper.createProduct(name, 1, listID);
            Log.e(null, name +", "+ listID);
            etDialogProductName.setText("");
            product_names.add(name);
            product_amounts.add(1);
            Cursor c = databaseHelper.getListsByUserID(userID);
            c.moveToLast();
            product_ids.add(c.getInt(c.getColumnIndex(DatabaseHelper.PRODUCT_TABLE_COLUMN_LIST_ID)));
            adapter.notifyDataSetChanged();
            dialogAddItem.dismiss();
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
    public void onButtonClick(int position, int listID) {
        product_names.remove(position);
        product_amounts.remove(position);
        Cursor c = databaseHelper.getProducts(listID, userID);
        // c.getInt(c.getColumnIndex(databaseHelper.PRODUCT_TABLE_COLUMN_LIST_ID))
        // databaseHelper.removeProduct(productID, listID);
        adapter.notifyDataSetChanged();
    }
}