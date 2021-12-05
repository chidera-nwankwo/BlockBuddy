package com.example.cse3310proj;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.collection.LLRBNode;

import java.util.ArrayList;

public class TransactionLogAdapter extends RecyclerView.Adapter<TransactionLogAdapter.TXViewHolder> {
    private ArrayList<Transaction> mLog;

    public static class TXViewHolder extends RecyclerView.ViewHolder {
        public TextView Date;
        public TextView Amount_Price;
        public TextView Status;

        public TXViewHolder(@NonNull View itemView) {
            super(itemView);
            Date = itemView.findViewById(R.id.Date);
            Amount_Price = itemView.findViewById(R.id.amount);
            Status = itemView.findViewById(R.id.Status);
        }
    }

    public TransactionLogAdapter(ArrayList<Transaction> Log) {
        mLog = Log;
    }

    @NonNull
    @Override
    public TXViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item_transaction, parent, false);
        TXViewHolder vh = new TXViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull TXViewHolder holder, int position) {
        Transaction tx = mLog.get(position);

        holder.Date.setText(tx.getDate());
        holder.Amount_Price.setText(tx.getQuantity() + " for "+tx.getPercoin() +" each");
        if(tx.getStatus() == 1) {
            holder.Status.setText("SELL");
            holder.Status.setTextColor(Color.RED);
        }
        else {
            holder.Status.setText("BUY");
            holder.Status.setTextColor(Color.GREEN);
        }
    }

    @Override
    public int getItemCount() {
        return mLog.size();
    }
}
