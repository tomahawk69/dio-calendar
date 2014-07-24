package com.dio.calendar.client;

import com.dio.calendar.CalendarRemoteServiceSoap;
import com.dio.calendar.EntryRemoteWrapper;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.URL;
import java.util.List;

/**
 * Created by yur on 25.07.2014.
 */
public class ClientSoapMain {


    public static void main(String[] args) throws Exception {
        URL url = new URL("http://localhost:8080/soap?wsdl");
        QName qname = new QName("http://calendar.dio.com/", "CalendarRemoteServiceSoapImplService");

        Service service = Service.create(url, qname);

        CalendarRemoteServiceSoap remoteService = service.getPort(com.dio.calendar.CalendarRemoteServiceSoap.class);

        List<EntryRemoteWrapper> entries = remoteService.getEntries();
        System.out.println(entries.size());
        EntryRemoteWrapper entry = remoteService.getEntry(entries.get(0).getId().toString());
        System.out.println(entry);
    }
}
