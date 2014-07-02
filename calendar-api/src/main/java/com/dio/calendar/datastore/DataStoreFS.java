package com.dio.calendar.datastore;

import com.dio.calendar.Entry;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

/**
 * Created by iovchynnikov on 5/20/14.
 */
public interface DataStoreFS extends DataStore {
    Boolean delete(Path file) throws DataStoreFSException;
    Entry read(Path file) throws DataStoreFSException;
    List<Path> getListFiles() throws DataStoreFSException;
}
