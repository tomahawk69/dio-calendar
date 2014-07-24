package com.dio.calendar;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.List;
import java.util.UUID;

/**
 * Created by yur on 25.07.2014.
 */
@WebService
public interface CalendarRemoteServiceSoap {

    @WebMethod(operationName="getEntry")
    EntryRemoteWrapper getEntry(String id);

    @WebMethod(exclude=true)
    Entry getEntry(UUID id);

    @WebMethod(operationName="getEntries")
    List<EntryRemoteWrapper> getEntries();
}
