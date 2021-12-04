package com.example.cse3310proj;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class OpeningPage extends AppCompatActivity implements View.OnClickListener {
    private static DecimalFormat df2 = new DecimalFormat("#.##");

    private FloatingActionButton button;
    private TextView totalValue;
    private ArrayList<WatchlistCoin> Watchlist = new ArrayList<WatchlistCoin>();
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private WatchlistAdapter.RecyclerViewClickListener listener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opening_page);

        button = (FloatingActionButton) findViewById(R.id.top20search);
        button.setOnClickListener(this);

        TextView totalValue = (TextView) findViewById(R.id.total_value);
        TextView percentChange = (TextView) findViewById(R.id.percent_change);


        double price;
        double change;
        String name, symbol;
        double PortValue=0;
        double PortBasis=0;


        //Receive name,symbol,price from coin selected in top20list and add to watchlist
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            price = extras.getDouble("price");
            name = extras.getString("name");
            symbol = extras.getString("symbol");
            change = extras.getDouble("change24");
            Watchlist.add(new WatchlistCoin(name,symbol,price,change));
        }

        for(WatchlistCoin coin: Watchlist)
        {
            PortValue += coin.getMarketValue();
            PortBasis += coin.getCostbasis();

        }

        double PortChange = ((Double.parseDouble(df2.format(PortValue)) - Double.parseDouble(df2.format(PortBasis))) / Double.parseDouble(df2.format(PortBasis)) ) *100;
        totalValue.setText("$" + df2.format(PortValue));
        if(PortValue>=PortBasis) {percentChange.setTextColor(Color.GREEN); percentChange.setText("+" + df2.format(PortChange) + "%");}
        else {percentChange.setTextColor(Color.RED); percentChange.setText(df2.format(PortChange) + "%");}


        setOnClickListener();
        mRecyclerView = findViewById(R.id.watchlistRecycler);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager=  new LinearLayoutManager(this);
        mAdapter = new WatchlistAdapter(Watchlist,listener);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);


    }

    private void setOnClickListener() {
        listener = new WatchlistAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(getApplicationContext(), CoinMetric.class);
                intent.putExtra("name", Watchlist.get(position).getName());
                intent.putExtra("symbol", Watchlist.get(position).getSymbol());
                intent.putExtra("holdings",Watchlist.get(position).getHoldings());
                intent.putExtra("price",Watchlist.get(position).getPrice());
                intent.putExtra("marketvalue",Watchlist.get(position).getMarketValue());
                intent.putExtra("costbasis",Watchlist.get(position).getCostbasis());
                startActivity(intent);
            }
        };
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