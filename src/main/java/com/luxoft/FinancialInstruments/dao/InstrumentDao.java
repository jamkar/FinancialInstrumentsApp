package com.luxoft.FinancialInstruments.dao;

import com.luxoft.FinancialInstruments.model.InstrumentModel;

public interface InstrumentDao {

    InstrumentModel getByName(String name);
}
