package com.dio.calendar.datastore;

import com.dio.calendar.Entry;
import com.dio.calendar.EntryWrapper;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.lang.String;

/**
 * Created by yur on 18.07.2014.
 */
public class DataStoreJDBCImpl implements DataStore {
    private final DataSource dataSource;
    private static Logger logger = Logger.getLogger(DataStoreJDBCImpl.class);

    private final String sqlDelete = "delete from entries where f_entry_id = ?";
    private final String sqlDeleteAttenders = "delete from entry_attenders where f_entry_id = ?";

    public DataStoreJDBCImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Boolean write(Entry entry) throws DataStoreFSException {
        logger.debug("write " + entry);

        Boolean result = false;

        String sql = "INSERT INTO entries " +
                "(f_entry_id, f_subject, f_description, f_dateFrom, f_dateTo) VALUES (?, ?, ?, ?, ?)";
        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, entry.getId().toString());
            preparedStatement.setString(2, entry.getSubject());
            preparedStatement.setString(3, entry.getDescription());
            preparedStatement.setDate(4, new java.sql.Date(entry.getStartDate().getTime()));
            preparedStatement.setDate(5, new java.sql.Date(entry.getEndDate().getTime()));
            preparedStatement.executeUpdate();
            preparedStatement.close();

            writeAttenders(conn, entry.getAttenders(), entry.getId());

            result = true;
        } catch (SQLException e) {
            logger.error(e);
            throw new RuntimeException(e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                }
            }
        }
        return result;
    }

    public void writeAttenders(Connection conn, List<String> attenders, UUID id) {
        logger.debug("write attenders for " + id);

        String sqlAdd = "insert into entry_attenders (f_entry_id, f_attender) values (?, ?)";

        try {

            PreparedStatement preparedStatement = conn.prepareStatement(sqlDelete);
            preparedStatement.setString(1, id.toString());
            preparedStatement.executeUpdate();
            preparedStatement.close();

            preparedStatement = conn.prepareStatement(sqlAdd);
            for (String attender : attenders) {
                preparedStatement.setString(1, id.toString());
                preparedStatement.setString(2, attender);
                preparedStatement.executeUpdate();
            }

            preparedStatement.close();

        } catch (SQLException e) {
            logger.error(e);
            throw new RuntimeException(e);
        } finally {

        }
    }

    @Override
    public Boolean delete(UUID id) throws DataStoreFSException {
        logger.debug("delete " + id);

        Boolean result = false;

        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(sqlDeleteAttenders);
            preparedStatement.setString(1, id.toString());
            preparedStatement.executeUpdate();
            preparedStatement.close();

            preparedStatement = conn.prepareStatement(sqlDelete);
            preparedStatement.setString(1, id.toString());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            result = true;
        } catch (SQLException e) {
            logger.error(e);
            throw new RuntimeException(e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                }
            }
        }
        return result;
    }

    @Override
    public Entry read(UUID id) throws DataStoreFSException {
        logger.debug("read " + id);

        Entry entry = null;

        String sql = "select * from entries where f_entry_id = ?";
        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, id.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                entry = readEntryFromResultSet(conn, resultSet);
            } else {
                logger.warn(String.format("Entry with is '%s' is not found", id));
            }
            preparedStatement.close();
        } catch (SQLException e) {
            logger.error(e);
            throw new RuntimeException(e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                }
            }
        }
        return entry;
    }

    @Override
    public void clear() throws DataStoreFSException {

        logger.debug("clear");

        String sql1 = "delete from entries";
        String sql2 = "delete from attenders";
        String sql3 = "delete from notifications";

        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(sql3);
            preparedStatement.execute();
            preparedStatement = conn.prepareStatement(sql2);
            preparedStatement.execute();
            preparedStatement = conn.prepareStatement(sql1);
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException e) {
            logger.error(e);
            throw new RuntimeException(e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                }
            }
        }
    }

    @Override
    public List<Entry> getEntries() throws DataStoreFSException {

        logger.debug("getEntries");

        List<Entry> entries = new ArrayList<>();

        String sql = "select * from entries";
        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                entries.add(readEntryFromResultSet(conn, resultSet));
            }
            preparedStatement.close();
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {

                }
            }
        }
        return entries;
    }

    public Entry readEntryFromResultSet(Connection conn, ResultSet resultSet) {

        logger.debug("readEntryFromResultSet");

        EntryWrapper wrapper = new EntryWrapper();

        try {
            wrapper.setId(UUID.fromString(resultSet.getString("f_entry_id")));
            wrapper.setSubject(resultSet.getString("f_subject"));
            wrapper.setDescription(resultSet.getString("f_description"));
            wrapper.setStartDate(resultSet.getDate("f_dateFrom"));
            wrapper.setEndDate(resultSet.getDate("f_dateTo"));
            wrapper.setAttenders(loadAttenders(conn, wrapper.getId()));
        } catch (SQLException e) {
            logger.error(e);
        }

        return wrapper.createEntry();
    }

    public List<String> loadAttenders(Connection conn, UUID id) {
        List<String> attenders = new ArrayList<>();

        logger.debug("read attenders for " + id);

        String sql = "select f_attender from entry_attenders where f_entry_id = ?";

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, id.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                attenders.add(resultSet.getString(1));
            }
            preparedStatement.close();
        } catch (SQLException e) {
            logger.error(e);
            throw new RuntimeException(e);
        } finally {}
        return attenders;
    }

    @Override
    public List<UUID> getListEntries() {

        logger.debug("getListEntries");

        List<UUID> entries = new ArrayList<>();

        String sql = "select f_entry_id from entries";
        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                entries.add(UUID.fromString(resultSet.getString("f_entry_id")));
            }
            preparedStatement.close();
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {

                }
            }
        }
        return entries;
    }

    @Override
    public LoadEntry createLoader(CalendarDataStore calendarDataStore, UUID id) {
        logger.debug("createLoader");
        return new LoadEntryImpl(this, calendarDataStore, id);
    }
}
