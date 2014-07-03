package com.dio.calendar.datastore;

import org.apache.log4j.Logger;

import java.nio.file.Path;

/**
 * Created by yur on 01.06.2014.
 */
public class LoadEntryFileImpl implements LoadEntry {

    private final DataStoreFSImpl fs;
    private final CalendarDataStore data;
    private final Path path;
    private static Logger logger = Logger.getLogger(LoadEntryFileImpl.class);

    public LoadEntryFileImpl(DataStoreFSImpl fs, CalendarDataStore data, Path path) {
        this.fs = fs;
        this.data = data;
        this.path = path;
    }

    @Override
    public String toString() {
        return "LoadFileImpl{" +
                "fs=" + fs +
                ", data=" + data +
                ", path=" + path +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LoadEntryFileImpl loadFile = (LoadEntryFileImpl) o;

        if (!data.equals(loadFile.data)) return false;
        if (!fs.equals(loadFile.fs)) return false;
        if (!path.equals(loadFile.path)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = fs.hashCode();
        result = 31 * result + data.hashCode();
        result = 31 * result + path.hashCode();
        return result;
    }

    @Override
    public Boolean call() {
        try {
            logger.debug("Run loading of " + path);
            data.addEntryToEntries(fs.read(path));
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return true;
        } catch (DataStoreFSException e) {
            e.printStackTrace();
        }
        return false;
    }
}
