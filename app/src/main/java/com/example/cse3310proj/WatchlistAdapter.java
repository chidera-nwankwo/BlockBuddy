package com.example.cse3310proj;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Set;

public class WatchlistAdapter extends RecyclerView.Adapter<WatchlistAdapter.WatchlistViewHolder> {
    private ArrayList<WatchlistCoin> mWatchlist;
    private static DecimalFormat df2 = new DecimalFormat("#.##");
    private RecyclerViewClickListener listener;

    public class WatchlistViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView mPrice;
        public TextView mSymbol;
        public TextView change24;
        public TextView holdingsUSD;
        public TextView holdings;
        public TextView name;
        public TextView MarketValue;

        public WatchlistViewHolder(@NonNull View itemView) {
            super(itemView);
            mPrice = itemView.findViewById(R.id.idTVRate);
            mSymbol = itemView.findViewById(R.id.idTVSymbol);
            change24 = itemView.findViewById(R.id.change24);
            holdings = itemView.findViewById(R.id.holdings);
            holdingsUSD = itemView.findViewById(R.id.MarketValue);
            name = itemView.findViewById(R.id.idTVName);
            MarketValue = itemView.findViewById(R.id.MarketValue);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onClick(view, getAdapterPosition());
        }
    }

    public WatchlistAdapter(ArrayList<WatchlistCoin> Watchlist, RecyclerViewClickListener listener){
        mWatchlist = Watchlist;
        this.listener = listener;
    }

    @NonNull
    @Override
    public WatchlistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item_watchlist, parent,false);
        WatchlistViewHolder Wvh = new WatchlistViewHolder(v);
        return Wvh;
    }

    @Override
    public void onBindViewHolder(@NonNull WatchlistViewHolder holder, int position) {
        WatchlistCoin currentItem = mWatchlist.get(position);

        holder.mSymbol.setText(currentItem.getSymbol());

        holder.name.setText(currentItem.getName());

        holder.change24.setText(df2.format(currentItem.getChange24()) + "%");
        if(currentItem.getChange24() <0) {
            holder.change24.setTextColor(Color.RED);
        }
        else {
            holder.change24.setTextColor(Color.GREEN);
        }

        String mHoldings = String.valueOf(currentItem.getHoldings());
        holder.holdings.setText(mHoldings);

        holder.holdingsUSD.setText("$ " + df2.format(currentItem.getHoldingsUSD()));

        holder.mPrice.setText("$ " + df2.format(currentItem.getPrice()));

        holder.MarketValue.setText("$ " + df2.format(currentItem.getMarketValue()));
    }

    @Override
    public int getItemCount() {
        return mWatchlist.size();
    }

    public interface RecyclerViewClickListener {
        void onClick(View v, int position);
    }
}
