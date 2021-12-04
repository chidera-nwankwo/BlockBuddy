package com.example.cse3310proj;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import java.util.Iterator;

public class addRemTransaction extends AppCompatActivity implements View.OnClickListener {
    private EditText PerCoin, Quantity, Date, Status;
    private Button Save;
    private TextView Title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        Title = (TextView) findViewById(R.id.coinTitle);

        Bundle extras = getIntent().getExtras();
        if(extras!=null) {
            String symbol = extras.getString("symbol");
            Title.setText(symbol);
        }

        PerCoin = (EditText) findViewById(R.id.perCoin);
        Quantity = (EditText) findViewById(R.id.Quantity);
        Date = (EditText) findViewById(R.id.Date);
        Status = (EditText) findViewById(R.id.Status);

        Save = (Button) findViewById(R.id.Save);
        Save.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        addTransaction();
    }

    private void addTransaction() {
        Bundle extras = getIntent().getExtras();
        String symbol = extras.getString("symbol");
        double PerCoinD = Double.parseDouble(PerCoin.getText().toString().trim());
        double QuantityD = Double.parseDouble(Quantity.getText().toString().trim());

        Transaction tx = new Transaction(symbol,PerCoinD, QuantityD, Date.getText().toString(), Integer.parseInt(Status.getText().toString()));
        for (Iterator<WatchlistCoin> iterator = Login.Watchlist.iterator(); iterator.hasNext(); ) {
            WatchlistCoin coin = iterator.next();
            if(coin.getSymbol().equalsIgnoreCase(symbol)) {
                coin.TransactionLog.add(tx);
                if(Integer.parseInt(Status.getText().toString().trim()) == 1) {
                    coin.costbasis -= tx.PerCoin * tx.quantity;
                    coin.holdings -= tx.quantity;
                    startActivity(new Intent(this, OpeningPage.class));
                }
                if(Integer.parseInt(Status.getText().toString().trim()) == 0) {
                    coin.costbasis += tx.PerCoin * tx.quantity;
                    coin.holdings += tx.quantity;
                    startActivity(new Intent(this, OpeningPage.class));
                }

            }
        }
        //for(WatchlistCoin coin: Login.Watchlist) {
            //if(coin.getSymbol().equalsIgnoreCase(symbol)) {
                //coin.TransactionLog.add(tx);
                //if(Integer.parseInt(Status.getText().toString()) == 0 ) {
                    //coin.costbasis += tx.PerCoin*tx.quantity;
                //}
            //}
        //}
    }


}
