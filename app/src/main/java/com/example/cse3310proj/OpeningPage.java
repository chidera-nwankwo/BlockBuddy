package com.example.cse3310proj;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class OpeningPage extends AppCompatActivity implements View.OnClickListener {

    private FloatingActionButton button;
    private TextView totalValue;
    private ArrayList<WatchlistCoin> Watchlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opening_page);

        button = (FloatingActionButton) findViewById(R.id.top20search);
        button.setOnClickListener(this);
        Watchlist = new ArrayList<>();

        TextView totalValue = (TextView) findViewById(R.id.total_value);

        double price = 0;
        String name, symbol;

        //Receive name,symbol,price from coin selected in top20list and add to watchlist
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            price = extras.getDouble("price");
            name = extras.getString("name");
            symbol = extras.getString("symbol");
            Watchlist.add(new WatchlistCoin(name,symbol,price));
        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top20search:
                startActivity(new Intent(this, Top20Coin.class));
                break;
        }
    }
}