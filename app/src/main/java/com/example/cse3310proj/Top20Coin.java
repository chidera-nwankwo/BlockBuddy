package com.example.cse3310proj;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Top20Coin extends AppCompatActivity{

    // creating variable for recycler view,
    // adapter, array list, progress bar
    private RecyclerView currencyRV;
    private EditText searchEdt;
    private ArrayList<CurrencyModal> currencyModalArrayList;
    private CurrencyRVAdapter currencyRVAdapter;
    private ProgressBar loadingPB;
    private CurrencyRVAdapter.RecyclerViewClickListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top20coin);
        searchEdt = findViewById(R.id.idEdtCurrency);

        setOnClickListener();

        // initializing all our variables and array list.
        loadingPB = findViewById(R.id.idPBLoading);
        currencyRV = findViewById(R.id.idRVcurrency);
        currencyModalArrayList = new ArrayList<>();

        // initializing our adapter class.
        currencyRVAdapter = new CurrencyRVAdapter(currencyModalArrayList, this, listener);

        // setting layout manager to recycler view.
        currencyRV.setLayoutManager(new LinearLayoutManager(this));

        // setting adapter to recycler view.
        currencyRV.setAdapter(currencyRVAdapter);

        // calling get data method to get data from API.
        getData();

        // on below line we are adding text watcher for our
        // edit text to check the data entered in edittext.
        searchEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                // on below line calling a
                // method to filter our array list
                filter(s.toString());
            }
        });
    }

    private void setOnClickListener() {
        listener = new CurrencyRVAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(getApplicationContext(), OpeningPage.class);
                intent.putExtra("symbol",currencyModalArrayList.get(position).getSymbol());
                intent.putExtra("price",currencyModalArrayList.get(position).getPrice());
                intent.putExtra("name", currencyModalArrayList.get(position).getName());
                intent.putExtra("change24",currencyModalArrayList.get(position).getChange24());
                startActivity(intent);
            }
        };
    }

    private void filter(String filter) {
        // on below line we are creating a new array list
        // for storing our filtered data.
        ArrayList<CurrencyModal> filteredlist = new ArrayList<>();
        // running a for loop to search the data from our array list.
        for (CurrencyModal item : currencyModalArrayList) {
            // on below line we are getting the item which are
            // filtered and adding it to filtered list.
            if (item.getName().toLowerCase().contains(filter.toLowerCase())) {
                filteredlist.add(item);
            }
        }
        // on below line we are checking
        // weather the list is empty or not.
        if (filteredlist.isEmpty()) {
            // if list is empty we are displaying a toast message.
            Toast.makeText(this, "No currency found..", Toast.LENGTH_SHORT).show();
        } else {
            // on below line we are calling a filter
            // list method to filter our list.
            currencyRVAdapter.filterList(filteredlist);
        }
    }

    private void getData() {
        // creating a variable for storing our string.
        String url = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest";
        // creating a variable for request queue.
        RequestQueue queue = Volley.newRequestQueue(this);
        // making a json object request to fetch data from API.
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                // inside on response method extracting data
                // from response and passing it to array list
                // on below line we are making our progress
                // bar visibility to gone.
                loadingPB.setVisibility(View.GONE);
                try {
                    // extracting data from json.
                    JSONArray dataArray = response.getJSONArray("data");
                    for (int i = 0; i < 20; i++) {
                        JSONObject dataObj = dataArray.getJSONObject(i);
                        String symbol = dataObj.getString("symbol");
                        String name = dataObj.getString("name");
                        JSONObject quote = dataObj.getJSONObject("quote");
                        JSONObject USD = quote.getJSONObject("USD");
                        double price = USD.getDouble("price");
                        double change = USD.getDouble("percent_change_24h");
                        // adding all data to our array list.
                        currencyModalArrayList.add(new CurrencyModal(name, symbol, price, change));
                    }
                    // notifying adapter on data change.
                    currencyRVAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    // handling json exception.
                    e.printStackTrace();
                    Toast.makeText(Top20Coin.this, "Something went amiss. Please try again later", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // displaying error response when received any error.
                Toast.makeText(Top20Coin.this, "Something went amiss. Please try again later", Toast.LENGTH_SHORT).show();
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
    }

}