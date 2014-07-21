package com.dio.calendar.datastore;

import com.dio.calendar.Entry;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.UUID;

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

    }

    @Test
    public void testRead() throws Exception {

    }

    @Test
    public void testClear() throws Exception {

    }

    @Test
    public void testGetEntries() throws Exception {

    }

    @Test
    public void testGetListEntries() throws Exception {

    }
}