package com.luxoft.FinancialInstruments.calculationModule;

import com.luxoft.FinancialInstruments.model.Instrument;

//Calculates standard deviation on the fly
public class StandardDeviationCalculator extends Calculator {

    private double deviation = 0.0, result = 0.0;

    @Override
    public void addValue(Instrument instrument) {
        sum += instrument.getValue();
        counter++;

        double mean = getMean();

        deviation += Math.pow(instrument.getValue() - mean, 2);

        result = Math.sqrt(deviation/counter);
    }

    @Override
    public double calculate() {
        return result;
    }

    @Override
    public String toString() {
        return "Standard Deviation";
    }
}
