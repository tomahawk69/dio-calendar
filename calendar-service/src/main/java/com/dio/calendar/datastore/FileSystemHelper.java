package com.dio.calendar.datastore;

import com.dio.calendar.Entry;
import com.dio.calendar.EntryWrapper;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

/**
 * Created by iovchynnikov on 5/26/14.
 */
public interface FileSystemHelper {
    boolean exists(Path fileName);

    Path getPath(UUID id, Path dbPath);
    Path getPath(String dbPath);
    List<Path> getListFiles(Path dbPath) throws IOException;

    boolean delete(Path file) throws IOException;

    EntryWrapper getEntryWrapper(Entry entry);

}
