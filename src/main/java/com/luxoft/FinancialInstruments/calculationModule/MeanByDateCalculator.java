package com.luxoft.FinancialInstruments.calculationModule;

import com.luxoft.FinancialInstruments.model.Instrument;

import java.util.Date;

//Calculates mean by given date
public class MeanByDateCalculator extends Calculator {

    private Date date;

    public MeanByDateCalculator(Date date) {
        this.date = date;
    }

    @Override
    public void addValue(Instrument instrument) {
        if (isCorrectDate(instrument.getDate())) {
            sum += instrument.getValue();
            counter++;
        }
    }

    @Override
    public double calculate() {
        return getMean();
    }

    private boolean isCorrectDate(Date date) {
        return this.date.getMonth() == date.getMonth() &&
                this.date.getYear() == date.getYear();
    }

    @Override
    public String toString() {
        return "Mean Value By Date: ";
    }
}
