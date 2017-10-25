package com.luxoft.FinancialInstruments.calculationModule;

import com.luxoft.FinancialInstruments.model.Instrument;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

//Calculated from newest values
public class NewestValuesCalculator extends Calculator {

    private List<Instrument> instruments = new ArrayList<>();
    private Comparator<Instrument> comparator = new InstrumentByDateComparator();
    private int noOfElements;

    public NewestValuesCalculator(int noOfLastElements) {
        this.noOfElements = noOfLastElements;
    }

    @Override
    public void addValue(Instrument instrument) {
        if (instruments.size() == 11) {
            instruments.set(instruments.size() - 1, instrument);
            instruments.sort(comparator);
        }
        else
            instruments.add(instrument);
    }

    @Override
    public double calculate() {
        if(instruments.size() == noOfElements + 1) {
            for (int i=0; i<noOfElements; i++) {
                sum += instruments.get(i).getValue();
            }
        }
        else if (instruments.size() != 0){
            for (Instrument i : instruments) {
                sum += i.getValue();
            }
        }
        else
            return 0.0;

        return sum;
    }

    @Override
    public String toString() {
        return "Sum of the Newest Values ";
    }
}
