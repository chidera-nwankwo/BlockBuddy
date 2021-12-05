package com.example.cse3310proj;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class OpeningPage extends AppCompatActivity implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {
    private static DecimalFormat df2 = new DecimalFormat("#.##");

    private FloatingActionButton button;
    private TextView totalValue;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private WatchlistAdapter.RecyclerViewClickListener listener;
    private SwipeRefreshLayout swipeRefreshLayout;
    public double PortValue=0;
    public double PortBasis=0;

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


        //Receive name,symbol,price from coin selected in top20list and add to watchlist
        Bundle extras = getIntent().getExtras();
        if(extras!= null) {
            price = extras.getDouble("price");
            name = extras.getString("name");
            symbol = extras.getString("symbol");
            change = extras.getDouble("change24");
            Login.Watchlist.add(new WatchlistCoin(name,symbol,price,change));
        }

        for(WatchlistCoin coin: Login.Watchlist)
        {
            PortValue += coin.getMarketValue();
            PortBasis += coin.getCostbasis();

        }

        double PortChange = ((Double.parseDouble(df2.format(PortValue)) - Double.parseDouble(df2.format(PortBasis))) / Double.parseDouble(df2.format(PortBasis)) ) *100;
        totalValue.setText("$" + df2.format(PortValue));
        if(PortValue>=PortBasis) {percentChange.setTextColor(Color.GREEN); percentChange.setText("+" + df2.format(PortChange) + "%");}
        else {percentChange.setTextColor(Color.RED); percentChange.setText(df2.format(PortChange) + "%");}

        extras=null;


        setOnClickListener();
        mRecyclerView = findViewById(R.id.watchlistRecycler);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager=  new LinearLayoutManager(this);
        mAdapter = new WatchlistAdapter(Login.Watchlist,listener);


        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        swipeRefreshLayout = findViewById(R.id.Refreshlayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                // creating a variable for storing our string.
                String url = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest";
                // creating a variable for request queue.
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                // making a json object request to fetch data from API.
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // extracting data from json.
                            JSONArray dataArray = response.getJSONArray("data");
                            for (int i = 0; i < 25; i++) {
                                JSONObject dataObj = dataArray.getJSONObject(i);
                                String symbol = dataObj.getString("symbol");
                                String name = dataObj.getString("name");
                                JSONObject quote = dataObj.getJSONObject("quote");
                                JSONObject USD = quote.getJSONObject("USD");
                                double price = USD.getDouble("price");
                                double change = USD.getDouble("percent_change_24h");



                                for (Iterator<WatchlistCoin> iterator = Login.Watchlist.iterator(); iterator.hasNext(); ) {
                                    WatchlistCoin coin = iterator.next();
                                    if(coin.getSymbol().equalsIgnoreCase(symbol)) {
                                        coin.setPrice(price);
                                        coin.setChange24(change);
                                    }
                                }

                            }

                            PortValue=0;
                            PortBasis=0;

                            for(WatchlistCoin coin: Login.Watchlist)
                            {
                                PortValue += coin.getMarketValue();
                                PortBasis += coin.getCostbasis();

                            }

                            double PortChange = ((Double.parseDouble(df2.format(PortValue)) - Double.parseDouble(df2.format(PortBasis))) / Double.parseDouble(df2.format(PortBasis)) ) *100;
                            totalValue.setText("$" + df2.format(PortValue));
                            if(PortValue>=PortBasis) {percentChange.setTextColor(Color.GREEN); percentChange.setText("+" + df2.format(PortChange) + "%");}
                            else {percentChange.setTextColor(Color.RED); percentChange.setText(df2.format(PortChange) + "%");}



                            mAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            // handling json exception.
                            e.printStackTrace();
                            Toast.makeText(OpeningPage.this, "Something went amiss. Please try again later", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // displaying error response when received any error.
                        Toast.makeText(OpeningPage.this, "Something went amiss. Please try again later", Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @Override
                    public Map<String, String> getHeaders() {
                        // in this method passing headers as
                        // key along with value as API keys.
                        HashMap<String, String> headers = new HashMap<>();
                        headers.put("X-CMC_PRO_API_KEY", "bcb74507-0431-4f42-8a7b-53de7111970e");
                        // at last returning headers
                        return headers;
                    }
                };
                // calling a method to add our
                // json object request to our queue.
                queue.add(jsonObjectRequest);



                //finish refresh
                swipeRefreshLayout.setRefreshing(false);
            }

        });

    }

    private void setOnClickListener() {
        listener = new WatchlistAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(getApplicationContext(), CoinMetric.class);
                intent.putExtra("name", Login.Watchlist.get(position).getName());
                intent.putExtra("symbol", Login.Watchlist.get(position).getSymbol());
                intent.putExtra("holdings",Login.Watchlist.get(position).getHoldings());
                intent.putExtra("price",Login.Watchlist.get(position).getPrice());
                intent.putExtra("marketvalue",Login.Watchlist.get(position).getMarketValue());
                intent.putExtra("costbasis",Login.Watchlist.get(position).getCostbasis());
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

    public void showSettings(View v1){
        PopupMenu popup = new PopupMenu(this, v1);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.popup_menu);
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch(menuItem.getItemId()){
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(OpeningPage.this, Login.class));
                Toast.makeText(this,"Logout Successfully", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.changePassword:
                
                Toast.makeText(this,"Change Password Successfully", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return false;
        }
    }

    private void passwordResetDialog(EditText resetPassword) {

    }

}

