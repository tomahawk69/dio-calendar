package com.dio.calendar;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

/**
 * Created by yur on 19.07.2014.
 */
public class EntryRowMapper implements RowMapper {

    @Override
    public Object mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        EntryWrapper wrapper = new EntryWrapper();
        wrapper.setId(UUID.fromString(resultSet.getString("f_entry_id")));
        wrapper.setSubject(resultSet.getString("f_subject"));
        wrapper.setDescription(resultSet.getString("f_description"));
        wrapper.setStartDate(resultSet.getDate("f_dateFrom"));
        wrapper.setEndDate(resultSet.getDate("f_dateTo"));
        return wrapper.createEntry();
    }
}
