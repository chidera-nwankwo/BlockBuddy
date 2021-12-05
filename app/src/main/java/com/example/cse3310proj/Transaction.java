package com.example.cse3310proj;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Date;

public class Transaction {
    private String symbol;
    public double PerCoin;
    public double quantity;
    private String date;
    public int status;

    public Transaction(String symbol, double PerCoin, double quantity, String date, int status) {
        this.symbol = symbol;
        this.PerCoin = PerCoin;
        this.quantity = quantity;
        this.date = date;
        this.status = status;
    }

    public String getDate() {return date;}
    public double getPercoin() {return PerCoin;}
    public double getQuantity() {return quantity;}
    public int getStatus() {return status;}

}
