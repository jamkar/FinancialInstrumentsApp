package com.luxoft.FinancialInstruments.model;

import java.util.Date;

public class Instrument {

    private String name;
    private Date date;
    private double value;

    public Instrument(String name, Date date, double value) {
        this.name = name;
        this.date = date;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
