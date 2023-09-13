package com.example.demo;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

public  class DataPoint {
    private LocalDateTime date;
    private double capital;
    public boolean isWeekday;
    public boolean connectedToWeekend;


    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public double getCapital() {
        return capital;
    }

    public void setCapital(double capital) {
        this.capital = capital;
    }

    public DataPoint(LocalDateTime date, double capital) {
        this.date = date;
        this.capital = capital;

        DayOfWeek dayOfWeek = date.getDayOfWeek();

        isWeekday = !(dayOfWeek.name().equals("SATURDAY") || dayOfWeek.name().equals("SUNDAY"));
        connectedToWeekend = dayOfWeek.name().equals("MONDAY") || dayOfWeek.name().equals("FRIDAY");

    }

    // getters and setters

    @Override
    public String toString() {
        return "DataPoint{" +
                "date=" + date +
                ", capital=" + capital +
                '}';
    }
}
