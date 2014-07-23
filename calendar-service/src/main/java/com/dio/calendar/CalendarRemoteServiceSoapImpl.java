package com.dio.calendar;

import com.dio.calendar.datastore.CalendarDataStoreImpl;
import org.apache.log4j.Logger;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/**
 * Created by iovchynnikov on 7/23/2014.
 */
// http://java.globinch.com/enterprise-java/web-services/soap-binding-document-rpc-style-web-services-difference/

@WebService
//@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use= SOAPBinding.Use.LITERAL)
@SOAPBinding(style = SOAPBinding.Style.RPC)
public class CalendarRemoteServiceSoapImpl {

    private final CalendarDataStoreImpl data;

    private static Logger logger = Logger.getLogger(CalendarRemoteServiceSoapImpl.class);

    public CalendarRemoteServiceSoapImpl(CalendarDataStoreImpl data) {
        this.data = data;
    }

    public EntryRestWrapper getEntry(String id)  {
        logger.debug("get uuid " + id);
        Entry entry = data.getEntry(UUID.fromString(id));
        return new EntryRestWrapper(entry);
    }

    public Entry getEntry(UUID id)  {
        logger.debug("get entry by id " + id);
        return data.getEntry(id);
    }


    @WebMethod
    public List<EntryRestWrapper> getEntries() {
        logger.debug("Get entries execute");
        List<Entry> entries = data.getEntries();

        List<EntryRestWrapper> entriesWrapper = new LinkedList<>();
        if (entries != null) {
            for (Entry entry : entries) {
                entriesWrapper.add(new EntryRestWrapper(entry));
            }
        }
        return entriesWrapper;
    }

}
