package com.example.cse3310proj;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView register;

    //ListView listView;
    //ArrayList<String> stringArrayList = new ArrayList<>();
    //ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
/*
        listView = findViewById(R.id.listView);

        // Add items in array list
        stringArrayList.set(0, "Bitcoin");
        stringArrayList.set(1, "Ethereum");
        stringArrayList.set(2, "Binance Coin");
        stringArrayList.set(3, "Tether");
        stringArrayList.set(4, "Solana");

        adapter = new ArrayAdapter<>( MainActivity.this, android.R.layout.simple_list_item_1,stringArrayList);
        // Set adapter on listView
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(),adapter.getItem(i),Toast.LENGTH_SHORT).show();
            }


        });

 */
    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.me);
        return super.onCreateOptionsMenu(menu);
    }

 */
}