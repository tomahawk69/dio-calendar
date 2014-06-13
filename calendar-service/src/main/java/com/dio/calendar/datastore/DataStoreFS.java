package com.dio.calendar.datastore;

import com.dio.calendar.Entry;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

/**
 * Created by iovchynnikov on 5/20/14.
 */
public interface DataStoreFS {
    Boolean write(Entry entry) throws DataStoreFSException;
    Boolean delete(UUID id) throws DataStoreFSException;
    Boolean delete(Path file) throws DataStoreFSException;
    Entry read(UUID id) throws DataStoreFSException;
    Entry read(Path file) throws DataStoreFSException;
    void clear() throws DataStoreFSException;
    List<Path> getListFiles() throws DataStoreFSException, IOException;
}
