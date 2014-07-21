package com.dio.calendar.datastore;

import com.dio.calendar.Entry;
import com.dio.calendar.EntryEntity;
import com.dio.calendar.EntryRowMapper;
import com.dio.calendar.EntryWrapper;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import java.util.*;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 * Created by yur on 19.07.2014.
 */
public class DataStoreJDBCImpl implements DataStore {

    public static final String sqlRead = "select * from entries where f_entry_id = ?";
    public static final String sqlWrite = "INSERT INTO entries " +
            "(f_entry_id, f_subject, f_description, f_dateFrom, f_dateTo) VALUES (?, ?, ?, ?, ?)";
    public static final String sqlDelete = "delete from entries where f_entry_id = ?";
    public static final String sqlDeleteAll = "delete from entries";
    public static final String sqlReadEntries = "select * from entries";
    public static final String sqlReadEntriesList = "select f_entry_id from entries";


    private final JdbcTemplate jdbcTemplate;
    private static Logger logger = Logger.getLogger(DataStoreDSImpl.class);

    public DataStoreJDBCImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public Boolean write(Entry entry) throws DataStoreFSException {
        logger.debug("Write entry " + entry);

        jdbcTemplate.update(sqlWrite,
                entry.getId().toString(),
                entry.getSubject(),
                entry.getDescription(),
                entry.getStartDate(),
                entry.getEndDate()
        );

        return true;
    }

    @Override
    public Boolean delete(UUID id) throws DataStoreFSException {

        jdbcTemplate.update(sqlDelete, id.toString());

        return true;
    }

    @Override
    public Entry read(UUID id) throws DataStoreFSException {
        logger.debug("get entry " + id);

        return (Entry) jdbcTemplate.queryForObject(
                sqlRead, new Object[] { id.toString() },
                new EntryRowMapper());
    }

    @Override
    public void clear() throws DataStoreFSException {

        jdbcTemplate.update(sqlDeleteAll);

    }

    @Override
    public List<Entry> getEntries() throws DataStoreFSException {
        logger.debug("get entries");

        List<Entry> entries = new ArrayList<>();

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sqlReadEntries);
        for (Map row : rows) {
            EntryWrapper wrapper = new EntryWrapper();
            wrapper.setId(UUID.fromString((String)row.get("f_entry_id")));
            wrapper.setSubject((String)row.get("f_subject"));
            wrapper.setDescription((String)row.get("f_description"));
            wrapper.setStartDate((Date)row.get("f_dateFrom"));
            wrapper.setEndDate((Date)row.get("f_dateTo"));
            entries.add(wrapper.createEntry());
        }

        return entries;
    }

    @Override
    public List<UUID> getListEntries() {
        logger.debug("get list of entries id");

        List<UUID> ids = new ArrayList<>();

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sqlReadEntriesList);

        for (Map row : rows) {
            ids.add(UUID.fromString((String) row.get("f_entry_id")));
        }

        return ids;
    }

    @Override
    public LoadEntry createLoader(CalendarDataStore calendarDataStore, UUID id) {
        return new LoadEntryImpl(this, calendarDataStore, id);
    }
}
