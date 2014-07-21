package com.dio.calendar.datastore;

import com.dio.calendar.Entry;
import com.dio.calendar.EntryRowMapper;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class DataStoreJDBCImplTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testWrite() throws Exception {
        Entry entry = new Entry.Builder().id(UUID.randomUUID()).build();

        JdbcTemplate jdbcTemplate = mock(JdbcTemplate.class);

        DataStoreJDBCImpl dataStore = spy(new DataStoreJDBCImpl(jdbcTemplate));

        Boolean result = dataStore.write(entry);

        assertTrue(result);

        verify(jdbcTemplate).update(dataStore.sqlWrite,
                entry.getId().toString(),
                entry.getSubject(),
                entry.getDescription(),
                entry.getStartDate(),
                entry.getEndDate());
    }

    @Test
    public void testWriteNull() throws Exception {
        Entry entry = null;

        JdbcTemplate jdbcTemplate = mock(JdbcTemplate.class);

        DataStoreJDBCImpl dataStore = spy(new DataStoreJDBCImpl(jdbcTemplate));

        thrown.expect(NullPointerException.class);

        dataStore.write(entry);
    }

    @Test
    public void testDelete() throws Exception {
        UUID id = UUID.randomUUID();

        JdbcTemplate jdbcTemplate = mock(JdbcTemplate.class);

        DataStoreJDBCImpl dataStore = spy(new DataStoreJDBCImpl(jdbcTemplate));

        Boolean result = dataStore.delete(id);

        assertTrue(result);

        verify(jdbcTemplate).update(dataStore.sqlDelete,
                id.toString());
    }

    @Test
    public void testDeleteNull() throws Exception {
        UUID id = null;

        JdbcTemplate jdbcTemplate = mock(JdbcTemplate.class);

        DataStoreJDBCImpl dataStore = spy(new DataStoreJDBCImpl(jdbcTemplate));

        thrown.expect(NullPointerException.class);

        dataStore.delete(id);

    }

    @Test
    public void testRead() throws Exception {

        UUID id = UUID.randomUUID();
        Entry expectedEntry = new Entry.Builder().id(UUID.randomUUID()).build();
        Object[] params = new Object[1];
        params[0] = id.toString();

        JdbcTemplate jdbcTemplate = mock(JdbcTemplate.class);

        DataStoreJDBCImpl dataStore = spy(new DataStoreJDBCImpl(jdbcTemplate));

        when(jdbcTemplate.queryForObject(dataStore.sqlRead, params, new EntryRowMapper())).thenReturn(expectedEntry);

        Entry resultEntry = dataStore.read(id);

        assertEquals(expectedEntry, resultEntry);

    }

    @Test
    public void testReadNull() throws Exception {

        UUID id = null;

        JdbcTemplate jdbcTemplate = mock(JdbcTemplate.class);

        DataStoreJDBCImpl dataStore = spy(new DataStoreJDBCImpl(jdbcTemplate));

        thrown.expect(NullPointerException.class);

        Entry resultEntry = dataStore.read(id);
    }

    @Test
    public void testClear() throws Exception {

        JdbcTemplate jdbcTemplate = mock(JdbcTemplate.class);

        DataStoreJDBCImpl dataStore = spy(new DataStoreJDBCImpl(jdbcTemplate));

        dataStore.clear();

        verify(jdbcTemplate).update(dataStore.sqlDeleteAll);

    }

    @Test
    public void testGetEntries() throws Exception {
        Entry entry = new Entry.Builder().id(UUID.randomUUID()).build();
        List<Entry> expectedEntries = Arrays.asList(entry);
        List<Map<String, Object>> rows = new ArrayList<>();
        Map<String, Object> recordMap = new HashMap<>();

        recordMap.put("f_entry_id", entry.getId().toString());
        recordMap.put("f_subject", entry.getSubject());
        recordMap.put("f_description", entry.getDescription());
        recordMap.put("f_dateFrom", entry.getStartDate());
        recordMap.put("f_dateTo", entry.getEndDate());
        rows.add(recordMap);

        JdbcTemplate jdbcTemplate = mock(JdbcTemplate.class);

        DataStoreJDBCImpl dataStore = spy(new DataStoreJDBCImpl(jdbcTemplate));

        when(jdbcTemplate.queryForList(dataStore.sqlReadEntries)).thenReturn(rows);

        List<Entry> resultEntries = dataStore.getEntries();

        assertEquals(expectedEntries, resultEntries);

    }

    @Test
    public void testGetListEntries() throws Exception {
        Entry entry = new Entry.Builder().id(UUID.randomUUID()).build();
        UUID id = entry.getId();
        List<UUID> expectedEntries = Arrays.asList(id);
        List<Map<String, Object>> rows = new ArrayList<>();
        Map<String, Object> recordMap = new HashMap<>();

        recordMap.put("f_entry_id", entry.getId().toString());
        rows.add(recordMap);

        JdbcTemplate jdbcTemplate = mock(JdbcTemplate.class);

        DataStoreJDBCImpl dataStore = spy(new DataStoreJDBCImpl(jdbcTemplate));

        when(jdbcTemplate.queryForList(dataStore.sqlReadEntriesList)).thenReturn(rows);

        List<UUID> resultEntries = dataStore.getListEntries();

        assertEquals(expectedEntries, resultEntries);
    }
}