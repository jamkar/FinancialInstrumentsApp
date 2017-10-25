package com.luxoft.FinancialInstruments.calculationModule;

import com.luxoft.FinancialInstruments.model.Instrument;

//Calculates mean
public class MeanCalculator extends Calculator {

    @Override
    public void addValue(Instrument instrument) {
        sum += instrument.getValue();
        counter++;
    }

    @Override
    public double calculate() {
        return getMean();
    }

    @Override
    public String toString() {
        return "Mean Value";
    }
}
