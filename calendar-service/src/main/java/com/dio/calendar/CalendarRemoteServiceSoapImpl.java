package com.dio.calendar;

import com.dio.calendar.datastore.CalendarDataStoreImpl;
import org.apache.log4j.Logger;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/**
 * Created by iovchynnikov on 7/23/2014.
 */
// http://java.globinch.com/enterprise-java/web-services/soap-binding-document-rpc-style-web-services-difference/

@WebService(endpointInterface = "com.dio.calendar.CalendarRemoteServiceSoap")
public class CalendarRemoteServiceSoapImpl implements CalendarRemoteServiceSoap {

    private final CalendarDataStoreImpl data;

    private static Logger logger = Logger.getLogger(CalendarRemoteServiceSoapImpl.class);

    public CalendarRemoteServiceSoapImpl(CalendarDataStoreImpl data) {
        this.data = data;
    }

    @Override
    @WebMethod(operationName="getEntry")
    public EntryRemoteWrapper getEntry(String id)  {
        logger.debug("get entry by string id " + id);
        Entry entry = data.getEntry(UUID.fromString(id));
        return new EntryRemoteWrapper(entry);
    }

    @Override
    @WebMethod(exclude=true)
    public Entry getEntry(UUID id)  {
        logger.debug("get entry by id " + id);
        return data.getEntry(id);
    }

    @Override
    @WebMethod(operationName="getEntries")
    public List<EntryRemoteWrapper> getEntries() {
        logger.debug("Get entries execute");
        List<Entry> entries = data.getEntries();

        List<EntryRemoteWrapper> entriesWrapper = new LinkedList<>();
        if (entries != null) {
            for (Entry entry : entries) {
                entriesWrapper.add(new EntryRemoteWrapper(entry));
            }
        }
        return entriesWrapper;
    }

}
