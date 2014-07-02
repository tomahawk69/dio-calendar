package com.dio.calendar.database;

/**
 * Created by iovchynnikov on 7/2/2014.
 */
import com.dio.calendar.Entry;
import com.dio.calendar.EntryWrapper;

import java.util.Collection;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public interface EntryDAO {
    public void addEntry(Entry entry) throws SQLException;
    public void addEntry(EntryWrapper entry) throws SQLException;
    public void updateEntry(Entry entry) throws SQLException;
    public void updateEntry(Entry entry, Entry oldEntry) throws SQLException;
    public Entry getEntryById(UUID id) throws SQLException;
    public List getEntries() throws SQLException;
    public void deleteEntry(Entry bus) throws SQLException;
    public void deleteEntryById(UUID id) throws SQLException;
    public Collection getEntriesBySubject(String subject) throws SQLException;

}