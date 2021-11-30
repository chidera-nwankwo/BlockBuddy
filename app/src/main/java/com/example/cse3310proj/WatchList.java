package com.example.cse3310proj;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class WatchList extends RecyclerView.Adapter<watchListVH>{
    List<String> items;

    public WatchList(List<String> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public watchListVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_watch_list, parent, false);
        return new watchListVH(view).linkAdapter(this);
    }


    @Override
    public void onBindViewHolder(@NonNull watchListVH holder, int position) {
        holder.textView.setText(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}

class watchListVH extends RecyclerView.ViewHolder{

    TextView textView;
    private WatchList adapter;

    public watchListVH(@NonNull View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.text);
        itemView.findViewById(R.id.delete).setOnClickListener(view -> {
            adapter.items.remove(getAdapterPosition());
            adapter.notifyItemRemoved(getAdapterPosition());
        });
    }
    public watchListVH linkAdapter(WatchList adapter){
        this.adapter = adapter;
        return this;
    }
}