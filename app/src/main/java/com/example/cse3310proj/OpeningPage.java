package com.example.cse3310proj;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.LinkedList;
import java.util.List;

public class OpeningPage extends AppCompatActivity {
    // Change this array by data from Top 20 Coins later
    String []data = {"BTC", "ETH", "SRM", "TRX", "USDT"};
    int counter = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opening_page);

        List<String> items = new LinkedList<>();

        RecyclerView recyclerView = findViewById(R.id.recyclerViewWatchList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        WatchList adapter = new WatchList(items);
        recyclerView.setAdapter(adapter);

        findViewById(R.id.add).setOnClickListener(view -> {
            items.add(data[counter]);
            counter++;
            adapter.notifyItemInserted(items.size() - 1);
        });
    }
}
