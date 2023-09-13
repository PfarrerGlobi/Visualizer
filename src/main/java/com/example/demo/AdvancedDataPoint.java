package com.example.demo;

import java.time.LocalDateTime;

public class AdvancedDataPoint extends DataPoint {

    private String ticker;
    private double entryEquity;

    public double getEntryEquity() {
        return entryEquity;
    }

    public void setEntryEquity(double entryEquity) {
        this.entryEquity = entryEquity;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public AdvancedDataPoint(LocalDateTime date, double capital, double entryEquity, String ticker){
        super(date, capital);

        this.ticker = ticker;
        this.entryEquity = entryEquity;

    }
    
}
