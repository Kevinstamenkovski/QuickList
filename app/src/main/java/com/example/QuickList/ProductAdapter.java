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
    private final ProductInterface productInterface;
    List<String> prodNames;
    List<Integer> prodAmounts, id;
    List<Blob> prodImages;
    LayoutInflater inflater;

    public ProductAdapter(Context context, List<String> prodNames, List<Integer> prodAmounts, List<Integer> id, ProductInterface productInterface) {
        this.prodNames = prodNames;
        this.prodAmounts = prodAmounts;
//        this.prodImages = prodImages;
        this.id = id;
        this.inflater = LayoutInflater.from(context);
        this.productInterface = productInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.product_layout, parent, false);
        return new ViewHolder(view, productInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        todo:        holder.prodImage.setImage(prodImages.get(position));
        holder.productName.setText(prodNames.get(position));
        holder.productAmount.setText(prodAmounts.get(position).toString());
        holder.productID = id.get(position);
    }

    @Override
    public int getItemCount() {
        return prodNames.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
//        ImageView productImage;
        TextView productName;
        TextView productAmount;
        Button removeButton;
        int productID;

        public ViewHolder(@NonNull View itemView, ProductInterface productInterface){
            super(itemView);
//            productImage = itemView.findViewById(R.id.productImage);
            productName = itemView.findViewById(R.id.tvProductName);
            productAmount = itemView.findViewById(R.id.tvProductAmount);
            removeButton = itemView.findViewById(R.id.removeButton);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (productInterface != null){
                        int position = getAdapterPosition();

                        if(position != RecyclerView.NO_POSITION) {
                            productInterface.onItemClick(position);
                        }
                    }
                }
            });

            removeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (productInterface != null){
                        int position = getAdapterPosition();

                        if(position != RecyclerView.NO_POSITION) {
                            productInterface.onButtonClick(position, productID);
                        }
                    }
                }
            });
        }
    }







}
