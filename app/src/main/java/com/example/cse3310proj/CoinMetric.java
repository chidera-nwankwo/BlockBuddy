package com.example.cse3310proj;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;

public class CoinMetric extends AppCompatActivity {
    private static DecimalFormat df2 = new DecimalFormat("#.##");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coinmetrics);

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

            double profitLoss = Double.parseDouble(df2.format(extras.getDouble("marketvalue"))) - Double.parseDouble(df2.format(extras.getDouble("costbasis")));
            if(profitLoss<0) {ProfitLoss.setTextColor(Color.RED); ProfitLoss.setText("$" + df2.format(profitLoss));}
            else {ProfitLoss.setTextColor(Color.GREEN);ProfitLoss.setText("+$" +df2.format(profitLoss));}

            double PercentChangeD = (profitLoss / Double.parseDouble(df2.format(extras.getDouble("costbasis")))) * 100 ;
            if(PercentChangeD<0) {ProfitLossPercent.setTextColor(Color.RED); ProfitLossPercent.setText(df2.format(PercentChangeD) + "%");}
            else {ProfitLossPercent.setTextColor(Color.GREEN); ProfitLossPercent.setText("+" + df2.format(PercentChangeD) + "%");}

        }
    }
}
