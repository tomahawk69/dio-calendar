package com.dio.calendar.datastore;

import com.dio.calendar.Entry;
import com.dio.calendar.EntryWrapper;
import org.apache.log4j.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by iovchynnikov on 5/21/14.
 */
public class DataStoreFSImpl implements DataStoreFS {
    private static Logger logger = Logger.getLogger(DataStoreFSImpl.class);
    private final Path dbPath;
    private final FileSystemHelper helper;

    public DataStoreFSImpl(String path, FileSystemHelper helper) {
        this.helper = helper;
        this.dbPath = helper.getPath(path);
        logger.info("dataStoreFS created with path " + dbPath);
    }

    @Override
    public Boolean write(Entry entry) throws DataStoreFSException {
        Boolean result = true;
        Path fileName = helper.getPath(entry.getId(), dbPath);
        try {
            Marshaller marshaller = createMarschaller(EntryWrapper.class);
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(helper.getEntryWrapper(entry), fileName.toFile());
        } catch (JAXBException e) {
            throw new DataStoreFSException(e);
        }
        return result;
    }

    @Override
    public Boolean delete(UUID id) throws DataStoreFSException {
        return delete(helper.getPath(id, dbPath));
    }

    @Override
    public Boolean delete(Path file) throws DataStoreFSException {
        Boolean result = false;
        try {
            result = helper.delete(file);
        } catch (Exception e) {
            throw new DataStoreFSException(e);
        }
        return result;
    }

    @Override
    public Entry read(UUID id) throws DataStoreFSException {
        return read(helper.getPath(id, dbPath));
    }

    @Override
    public Entry read(Path file) throws DataStoreFSException {
        Entry entry = null;
        if (helper.exists(file)) {
            if (logger.isDebugEnabled()) {
                logger.debug("Read " + file);
            }
            try {
                Unmarshaller unmarshaller = createUnmarschaller(EntryWrapper.class);
                EntryWrapper wrapper = (EntryWrapper) unmarshaller.unmarshal(file.toFile());
                entry = wrapper.createEntry();
            } catch (JAXBException e) {
                throw new DataStoreFSException(e);
            }
        }
        return entry;
    }

    @Override
    public void clear() throws DataStoreFSException {
        try {
            for (Path file : helper.getListFiles(dbPath)) {
                helper.delete(file);
            }
        } catch (IOException e) {
            throw new DataStoreFSException(e);
        }
    }

    @Override
    public List<Entry> getEntries() throws DataStoreFSException {
        return null;
    }

    @Override
    public List<Path> getListFiles() throws DataStoreFSException {
        return helper.getListFiles(dbPath);
    }

    public Marshaller createMarschaller(Class inputClass) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(inputClass);
        return context.createMarshaller();
    }

    public Unmarshaller createUnmarschaller(Class outputClass) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(outputClass);
        return context.createUnmarshaller();
    }

}
