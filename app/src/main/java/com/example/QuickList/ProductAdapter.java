package com.example.QuickList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.Blob;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    List<String> prodNames;
    List<Integer> prodAmounts;
    List<Blob> prodImages;
    LayoutInflater inflater;

    public ProductAdapter(Context context, List<String> prodNames, List<Integer> prodAmounts) {
        this.prodNames = prodNames;
        this.prodAmounts = prodAmounts;
//        this.prodImages = prodImages;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.product_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ViewHolder holder, int position) {
//        todo:        holder.prodImage.setImage(prodImages.get(position));
        holder.productName.setText(prodNames.get(position).toString());
        holder.productAmount.setText(prodAmounts.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return prodNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView productImage;
        TextView productName;
        TextView productAmount;
        Button removeButton;

        public ViewHolder(@NonNull View view){
            super(view);
//            productImage = itemView.findViewById(R.id.productImage);
            productName = itemView.findViewById(R.id.tvProductAmount);
            productAmount = itemView.findViewById(R.id.tvProductName);
            removeButton = itemView.findViewById(R.id.removeButton);

            removeButton.setOnClickListener(v -> {
//                todo: remove method
            });
        }
    }







}
