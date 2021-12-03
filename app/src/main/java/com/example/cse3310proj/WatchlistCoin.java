package com.example.cse3310proj;

public class WatchlistCoin {

    private String name;
    private String symbol;
    private double price;
    private double holdings = 0;
    private double holdingsUSD = 0;
    private double costbasis = 0;

    public WatchlistCoin(String name, String symbol, double price) {}

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
}
