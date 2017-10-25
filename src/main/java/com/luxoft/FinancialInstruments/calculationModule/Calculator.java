package com.luxoft.FinancialInstruments.calculationModule;

import com.luxoft.FinancialInstruments.model.Instrument;

//Base calculator
public abstract class Calculator {

    protected double sum = 0.0;
    protected long counter = 0;

    protected double getMean() {
        return sum/counter;
    }

    public abstract void addValue(Instrument instrument);

    public abstract double calculate();

}
