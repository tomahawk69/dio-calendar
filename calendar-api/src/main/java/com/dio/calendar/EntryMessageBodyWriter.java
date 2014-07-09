package com.dio.calendar;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

/**
 * Created by yur on 08.07.2014.
 */

@Provider
@Produces(MediaType.APPLICATION_JSON)
public class EntryMessageBodyWriter implements MessageBodyWriter<Entry> {

    private static Logger logger = Logger.getLogger(EntryMessageBodyWriter.class);

    @Override
    public boolean isWriteable(Class<?> type, Type genericType,
                               Annotation[] annotations, MediaType mediaType) {
        logger.debug("is writable EntryMessageBodyWriter" + type);
        return type == Entry.class;
    }

    @Override
    public long getSize(Entry Entry, Class<?> type, Type genericType,
                        Annotation[] annotations, MediaType mediaType) {
        return -1;
    }

    @Override
    public void writeTo(Entry entry,
                        Class<?> type,
                        Type genericType,
                        Annotation[] annotations,
                        MediaType mediaType,
                        MultivaluedMap<String, Object> httpHeaders,
                        OutputStream entityStream)
            throws IOException, WebApplicationException {
        logger.debug("write EntryMessageBodyWriter " + entry);
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(entityStream, new EntryWrapper(entry));
        } catch (Exception e) {
            logger.error("Error serializing an entry to the output stream. " + e);
        }
    }
}
