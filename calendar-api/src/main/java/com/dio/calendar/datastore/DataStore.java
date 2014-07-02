package com.dio.calendar.datastore;

import com.dio.calendar.Entry;

import java.util.List;
import java.util.UUID;

/**
 * Created by yur on 02.07.2014.
 */
public interface DataStore {
    Boolean write(Entry entry) throws DataStoreFSException;
    Boolean delete(UUID id) throws DataStoreFSException;
    Entry read(UUID id) throws DataStoreFSException;
    void clear() throws DataStoreFSException;
    List<Entry> getEntries() throws DataStoreFSException;
}
