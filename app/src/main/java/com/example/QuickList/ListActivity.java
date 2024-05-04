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

public class ListActivity extends AppCompatActivity implements ProductInterface {
    Button backButton, btnAddProduct, btnDialogAddProduct;
    ProductAdapter adapter;
    RecyclerView recyclerView;
    List<String> product_names;
    List<Integer> prod_amounts;
    Dialog dialog;
    EditText etDialogProductName;

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
        prod_amounts = new ArrayList<>();
//        prod_images = new ArrayList<>();

        product_names.add("Ciao");
        product_names.add("Ciao");
        product_names.add("Ciao");

        prod_amounts.add(1);
        prod_amounts.add(1);
        prod_amounts.add(1);

        recyclerView = findViewById(R.id.rvProducts);
        btnAddProduct = findViewById(R.id.btnAddProduct);

        dialog = new Dialog(ListActivity.this);
        dialog.setContentView(R.layout.dialog_add_list);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);

        btnDialogAddProduct = dialog.findViewById(R.id.btnDialogAddList);
        etDialogProductName = dialog.findViewById(R.id.etDialogListName);

        adapter = new ProductAdapter(this, product_names, prod_amounts, this);

        //Prendo tutti i valori dal database e li metto in una array list

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);

        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });

        btnDialogAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etDialogProductName.getText().toString();
                etDialogProductName.setText("");
                product_names.add(name);
                prod_amounts.add(0);
                adapter.notifyDataSetChanged();

                dialog.dismiss();
            }
        });
    }

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onButtonClick(int position) {

    }
}