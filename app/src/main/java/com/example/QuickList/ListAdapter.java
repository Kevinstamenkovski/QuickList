package com.example.QuickList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private final ListInterface listInterface;
    List<String> titles;
    List<Integer> number_items, id;
    LayoutInflater inflater;

    public ListAdapter(Context context, List<String> titles, List<Integer> number_items, List<Integer> id, ListInterface listInterface) {
        this.titles = titles;
        this.number_items = number_items;
        this.id = id;
        this.inflater = LayoutInflater.from(context);
        this.listInterface = listInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_layout, parent, false);
        return new ViewHolder(view, listInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(titles.get(position));
        holder.number.setText(number_items.get(position).toString());
        holder.list_id = id.get(position);
    }

    @Override
    public int getItemCount() {
        return titles.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView number;
        int list_id;
        public ViewHolder(@NonNull View itemView, ListInterface listInterface) {
            super(itemView);
            title = itemView.findViewById(R.id.tvListNameCard);
            number = itemView.findViewById(R.id.tvNumerItems);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listInterface != null){
                        int position = getAdapterPosition();

                        if(position != RecyclerView.NO_POSITION) {
                            listInterface.onItemClick(position, list_id, title.getText().toString());
                        }
                    }
                }
            });
        }
    }
}
