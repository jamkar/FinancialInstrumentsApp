package com.luxoft.FinancialInstruments;

import com.luxoft.FinancialInstruments.dao.InstrumentDaoImpl;
import com.luxoft.FinancialInstruments.model.Instrument;
import com.luxoft.FinancialInstruments.model.InstrumentModel;
import com.luxoft.FinancialInstruments.calculationModule.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@SpringBootApplication
public class FinancialInstrumentsApplication implements CommandLineRunner {

	@Autowired
	InstrumentDaoImpl instrumentDao;

	private static final String filePath = "example_input.txt";

	private static final String instrument1 = "INSTRUMENT1";
	private static final String instrument2 = "INSTRUMENT2";
	private static final String instrument3 = "INSTRUMENT3";

	private Map<CalculatorType, Calculator> calculators = new HashMap<>();
	private Map<String, Double> multipliers = new HashMap<>();

	@Bean
	@Qualifier("derbyDatasource")
	public DataSource dataSource() {

		EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
		EmbeddedDatabase db = builder
				.setType(EmbeddedDatabaseType.DERBY)
				.addScript("db/create-db.sql")
				.addScript("db/insert-data.sql")
				.build();
		return db;
	}

	@Bean
	public NamedParameterJdbcTemplate getJdbcTemplate(@Qualifier("derbyDatasource") DataSource dataSource) {
		return new NamedParameterJdbcTemplate(dataSource);
	}

	public static void main(String[] args) {
		SpringApplication.run(FinancialInstrumentsApplication.class, args);
	}

	@Override
	public void run(String... args) {

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 2014);
		cal.set(Calendar.MONTH, Calendar.NOVEMBER);

		calculators.put(CalculatorType.MEAN, new MeanCalculator());
		calculators.put(CalculatorType.MEAN_BY_DATE, new MeanByDateCalculator(cal.getTime()));
		calculators.put(CalculatorType.DEVIATION, new StandardDeviationCalculator());
		calculators.put(CalculatorType.NEWEST, new NewestValuesCalculator(10));

		InstrumentModel iModel;
		for (int i=1; i<=3; i++) {
			if ((iModel = instrumentDao.getByName("INSTRUMENT" + i)) != null)
				multipliers.put("INSTRUMENT" + i, iModel.getMultiplier());
			else
				multipliers.put("INSTRUMENT" + i, 0.0);
		}

		readFile(filePath);

		for (Calculator calc : calculators.values()) {
			System.out.println(calc + ": " + calc.calculate());
		}

	}

	/**
	 * Reads file line by line
	 * @param path
	 */
	private void readFile(String path) {
		try(BufferedReader in = new BufferedReader(new FileReader(path))) {
			String line;
			while ((line = in.readLine()) != null) {
				Instrument instrument = parseInstrument(line);
				if(instrument != null) {
					add(instrument);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Parses strings and creates instruments
	 * @param line
	 * @return
	 */
	private Instrument parseInstrument(String line) {
		Instrument instrument = null;
		String[] arr = line.split("[,]");
		if (arr.length == 3) {
			String name = arr[0];
			Date date = getDate(arr[1]);
			float price = Float.parseFloat(arr[2]);
			instrument = new Instrument(name, date, price);
		}
		return instrument;

	}

	/**
	 * Get date object from text
	 * @param txtDate
	 * @return
	 */
	private Date getDate(String txtDate) {
		DateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);
		Date date = null;
		try {
			date = df.parse(txtDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * Add created instruments to calculators
	 * @param instrument
	 */
	private void add(Instrument instrument) {
		String name = instrument.getName();
		switch (name) {
			case instrument1:
				instrument.setValue(instrument.getValue() * multipliers.get(instrument1));
				calculators.get(CalculatorType.MEAN).addValue(instrument);
				break;
			case instrument2:
				instrument.setValue(instrument.getValue() * multipliers.get(instrument2));
				calculators.get(CalculatorType.MEAN_BY_DATE).addValue(instrument);
				break;
			case instrument3:
				instrument.setValue(instrument.getValue() * multipliers.get(instrument3));
				calculators.get(CalculatorType.DEVIATION).addValue(instrument);
				break;
			default:
				calculators.get(CalculatorType.NEWEST).addValue(instrument);
				break;
		}
	}

}
