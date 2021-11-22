package com.example.cse3310proj;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class OpeningPage extends AppCompatActivity implements View.OnClickListener {

    private FloatingActionButton button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opening_page);

        button = (FloatingActionButton) findViewById(R.id.top20search);
        button.setOnClickListener(this);
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