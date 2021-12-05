package com.example.cse3310proj;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Iterator;

public class transactionLog extends AppCompatActivity {
    private RecyclerView mRecyclerview;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactionlog);

        Bundle extras = getIntent().getExtras();
        String symbol = extras.getString("symbol");


        for (Iterator<WatchlistCoin> iterator = Login.Watchlist.iterator(); iterator.hasNext(); ) {
            WatchlistCoin coin = iterator.next();
            if(coin.getSymbol().equalsIgnoreCase(symbol)) {
                mRecyclerview = findViewById(R.id.transactionLog);
                mRecyclerview.setHasFixedSize(true);
                mLayoutManager = new LinearLayoutManager(this);
                mAdapter = new TransactionLogAdapter(coin.TransactionLog);

                mRecyclerview.setLayoutManager(mLayoutManager);
                mRecyclerview.setAdapter(mAdapter);
            }
        }
    }
}
