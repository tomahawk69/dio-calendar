package com.dio.calendar.datastore;

import org.apache.log4j.Logger;

import java.util.UUID;

/**
 * Created by yur on 04.07.2014.
 */
public class LoadEntryHibernateImpl implements LoadEntry {
    private final DataStoreHibernateImpl fs;
    private final CalendarDataStore data;
    private final UUID id;
    private static Logger logger = Logger.getLogger(LoadEntryHibernateImpl.class);

    public LoadEntryHibernateImpl(DataStoreHibernateImpl fs, CalendarDataStore data, UUID id) {
        this.fs = fs;
        this.data = data;
        this.id = id;
    }


    @Override
    public Boolean call() {
        try {
            logger.debug("Run loading of " + id);
            data.addEntryToEntries(fs.read(id));
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                logger.error(e);
            }
            return true;
        } catch (DataStoreFSException e) {
            logger.error(e);
        }
        return false;
    }

    @Override
    public String toString() {
        return "LoadEntryHibernateImpl{" +
                "fs=" + fs +
                ", data=" + data +
                ", id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LoadEntryHibernateImpl that = (LoadEntryHibernateImpl) o;

        if (!data.equals(that.data)) return false;
        if (!fs.equals(that.fs)) return false;
        if (!id.equals(that.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = fs.hashCode();
        result = 31 * result + data.hashCode();
        result = 31 * result + id.hashCode();
        return result;
    }


}
