package com.example.cse3310proj;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;
import java.util.Iterator;

public class CoinMetric extends AppCompatActivity implements View.OnClickListener {
    private static DecimalFormat df2 = new DecimalFormat("#.##");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coinmetrics);

        TextView transactionLog = (TextView) findViewById(R.id.TransactionLog);
        transactionLog.setOnClickListener(this);
        Button AddTransaction = (Button) findViewById(R.id.AddTransaction);
        AddTransaction.setOnClickListener(this);
        Button Unwatch = (Button) findViewById(R.id.removeFromList);
        Unwatch.setOnClickListener(this);

        TextView coinTitle = findViewById(R.id.coinTitle);
        TextView MarketValue = findViewById(R.id.MarketValue);
        TextView Holdings = findViewById(R.id.Holdings);
        TextView ProfitLoss = findViewById(R.id.ProfitLoss);
        TextView NetCost = findViewById(R.id.NetCost);
        TextView Symbol = findViewById(R.id.Symbol);
        TextView ProfitLossPercent = findViewById(R.id.PercentageChange);
        TextView Price = findViewById(R.id.Price);


        Bundle extras = getIntent().getExtras();
        if(extras!=null) {
            coinTitle.setText(extras.getString("name"));
            Price.setText("$ " + df2.format(extras.getDouble("price")));
            Symbol.setText(extras.getString("symbol"));
            MarketValue.setText("$ " + df2.format(extras.getDouble("marketvalue")));
            Holdings.setText(df2.format(extras.getDouble("holdings")) + " " + extras.getString("symbol"));
            NetCost.setText("$ " + df2.format(extras.getDouble("costbasis")));
            String symbol = extras.getString("symbol");

            double profitLoss = Double.parseDouble(df2.format(extras.getDouble("marketvalue"))) - Double.parseDouble(df2.format(extras.getDouble("costbasis")));
            if(profitLoss<0) {ProfitLoss.setTextColor(Color.RED); ProfitLoss.setText("$" + df2.format(profitLoss));}
            else {ProfitLoss.setTextColor(Color.GREEN);ProfitLoss.setText("+$" +df2.format(profitLoss));}

            double PercentChangeD;
            if (extras.getDouble("costbasis") > 0) {
                PercentChangeD = (profitLoss / Double.parseDouble(df2.format(extras.getDouble("costbasis")))) * 100;
            } else {
                PercentChangeD = 0;
            }
            if(PercentChangeD<0) {ProfitLossPercent.setTextColor(Color.RED); ProfitLossPercent.setText(df2.format(PercentChangeD) + "%");}
            else {ProfitLossPercent.setTextColor(Color.GREEN); ProfitLossPercent.setText("+" + df2.format(PercentChangeD) + "%");}


        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.TransactionLog:
                viewLog();
                break;
            case R.id.AddTransaction:
                addTransaction();
                break;
            case R.id.removeFromList:
                deleteCoin();
                startActivity(new Intent(this, OpeningPage.class));
                break;
        }
    }

    private void viewLog() {
        Bundle extras = getIntent().getExtras();
        String symbol = extras.getString("symbol");
        Intent intent2 = new Intent(getApplicationContext(), transactionLog.class);
        intent2.putExtra("symbol",symbol);
        startActivity(intent2);
    }

    private void addTransaction() {
        Bundle extras = getIntent().getExtras();
        String symbol = extras.getString("symbol");
        Intent intent2 = new Intent(getApplicationContext(), addRemTransaction.class);
        intent2.putExtra("symbol",symbol);
        startActivity(intent2);
    }

    private void deleteCoin() {
        Bundle extras = getIntent().getExtras();
        String symbol = extras.getString("symbol");
        for (Iterator<WatchlistCoin> iterator = Login.Watchlist.iterator(); iterator.hasNext(); ) {
            WatchlistCoin coin = iterator.next();
            if(coin.getSymbol().equalsIgnoreCase(symbol)) {
                iterator.remove();
                startActivity(new Intent(this, OpeningPage.class));
            }
        }
    }


}
