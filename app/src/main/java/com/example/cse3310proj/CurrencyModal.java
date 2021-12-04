package com.example.cse3310proj;

public class CurrencyModal {
    // variable for currency name,
    // currency symbol and price.
    private String name;
    private String symbol;
    private double price;
    private double change24;

    public CurrencyModal(String name, String symbol, double price,double change24) {
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

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getPrice() {
        return price;
    }

    public double getChange24() {return change24;}

    public void setPrice(double price) {
        this.price = price;
    }
}