package com.dio.calendar.datastore;

import com.dio.calendar.Entry;
import com.dio.calendar.EntryWrapper;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import java.util.*;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 * Created by yur on 19.07.2014.
 */
public class DataStoreJDBCImpl extends JdbcDaoSupport implements DataStore {

    private final String sqlRead = "select * from entries where f_entry_id = ?";

    private static Logger logger = Logger.getLogger(DataStoreDSImpl.class);


    @Override
    public Boolean write(Entry entry) throws DataStoreFSException {
        String sql = "INSERT INTO entries " +
                "(f_entry_id, f_subject, f_description, f_dateFrom, f_dateTo) VALUES (?, ?, ?, ?, ?)";

        getJdbcTemplate().update(sql, new Object[] { entry.getId().toString(),
                entry.getSubject(), entry.getDescription(), entry.getStartDate(), entry.getEndDate()
        });
        return true;
    }

    @Override
    public Boolean delete(UUID id) throws DataStoreFSException {
        String sql = "delete from entries where f_entry_id = ?";
        getJdbcTemplate().update(sql, new Object[] { id.toString() });
        return true;
    }

    @Override
    public Entry read(UUID id) throws DataStoreFSException {
        return (Entry) getJdbcTemplate().queryForObject(
                sqlRead, new Object[] { id },
                new BeanPropertyRowMapper(Entry.class));
    }

    @Override
    public void clear() throws DataStoreFSException {

    }

    @Override
    public List<Entry> getEntries() throws DataStoreFSException {
        String sql = "select * from entries";

        List<Entry> entries = new ArrayList<>();

        List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sql);
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
        String sql = "select f_entry_id from entries";

        List<UUID> ids = new ArrayList<>();

        System.out.println(getJdbcTemplate());
        List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sql);
        for (Map row : rows) {
            ids.add(UUID.fromString((String) row.get(1)));
        }

        return ids;
    }

    @Override
    public LoadEntry createLoader(CalendarDataStore calendarDataStore, UUID id) {
        logger.debug("createLoader");
        return new LoadEntryImpl(this, calendarDataStore, id);
    }
}
