package com.dio.calendar.client;

/**
 * Created by yur on 08.07.2014.
 */

import com.dio.calendar.Entry;
import com.dio.calendar.EntryWrapper;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

@Provider
@Consumes("application/json")
public class MyBeanMessageBodyReader implements MessageBodyReader<Entry> {

    @Override
    public boolean isReadable(Class<?> type, Type genericType,
                              Annotation[] annotations, MediaType mediaType) {
        System.out.println("is readable");
        return type == Entry.class;
    }

    @Override
    public Entry readFrom(Class<Entry> type,
                           Type genericType,
                           Annotation[] annotations, MediaType mediaType,
                           MultivaluedMap<String, String> httpHeaders,
                           InputStream entityStream)
            throws IOException, WebApplicationException {

        try {
            System.out.println("read");
            JAXBContext jaxbContext = JAXBContext.newInstance(Entry.class);
            EntryWrapper entryWrapper = (EntryWrapper) jaxbContext.createUnmarshaller()
                    .unmarshal(entityStream);
            return entryWrapper.createEntry();
        } catch (JAXBException jaxbException) {
            throw new RuntimeException("Error deserializing an Entry. ", jaxbException);
        }
    }
}