package com.luxoft.FinancialInstruments.dao;

import com.luxoft.FinancialInstruments.model.InstrumentModel;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Repository
public class InstrumentDaoImpl extends BaseDao implements InstrumentDao {

    public InstrumentModel getByName(String name) {
        Map<String, Object> params = new HashMap<>();
        params.put("name", name);

        String sql = "SELECT * FROM INSTRUMENT_PRICE_MODIFIER WHERE NAME=:name";

        InstrumentModel result;
        try {
            result = getNamedParameterJdbcTemplate().queryForObject(
                    sql,
                    params,
                    new InstrumentDtoMapper());
        }
        catch (EmptyResultDataAccessException ex) {
            return null;
        }

        return result;
    }

    private static final class InstrumentDtoMapper implements RowMapper<InstrumentModel> {

        public InstrumentModel mapRow(ResultSet rs, int rowNum) throws SQLException {
            InstrumentModel instrumentModel = new InstrumentModel();
            instrumentModel.setId(rs.getInt("ID"));
            instrumentModel.setName(rs.getString("NAME"));
            instrumentModel.setMultiplier(rs.getDouble("MULTIPLIER"));
            return instrumentModel;
        }
    }

}
