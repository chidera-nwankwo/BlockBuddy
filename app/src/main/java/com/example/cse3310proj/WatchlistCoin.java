package com.example.cse3310proj;

import java.util.ArrayList;

public class WatchlistCoin {

    private String name;
    private String symbol;
    private double price;
    private double holdings = 4.288;
    private double holdingsUSD = 0;
    private double costbasis = 1621.97;
    private double change24;
    private double marketValue = holdings*price;

    private ArrayList<Transaction> TransactionLog;

    public WatchlistCoin(String name, String symbol, double price, double change24) {
        this.name = name;
        this.symbol = symbol;
        this.price = price;
        this.change24 = change24;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getChange24() {
        return change24;
    }

    public double getHoldings() {
        return holdings;
    }

    public double getHoldingsUSD() {
        return holdingsUSD;
    }

    public double getCostbasis() {
        return costbasis;
    }

    public double getMarketValue() {
        this.marketValue=this.holdings*this.price;
        return marketValue;
    }
}
