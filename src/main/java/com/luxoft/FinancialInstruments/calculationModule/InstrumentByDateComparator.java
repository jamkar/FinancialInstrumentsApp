package com.luxoft.FinancialInstruments.calculationModule;

import com.luxoft.FinancialInstruments.model.Instrument;

import java.util.Comparator;

public class InstrumentByDateComparator implements Comparator<Instrument> {

    @Override
    public int compare(Instrument i1, Instrument i2) {
        if (i1.getDate().getTime() == i2.getDate().getTime())
            return 0;
        else
            return (i1.getDate().getTime() < i2.getDate().getTime() ? 1 : -1);
    }
}
