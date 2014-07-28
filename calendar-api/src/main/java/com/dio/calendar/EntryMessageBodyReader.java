package com.dio.calendar;

/**
 * Created by yur on 08.07.2014.
 */

import com.dio.calendar.Entry;
import com.dio.calendar.EntryWrapper;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

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

//@Provider
//@Consumes("application/json")
public class EntryMessageBodyReader implements MessageBodyReader<Entry> {

    private static Logger logger = Logger.getLogger(EntryMessageBodyReader.class);

    @Override
    public boolean isReadable(Class<?> type, Type genericType,
                              Annotation[] annotations, MediaType mediaType) {
//        logger.info("is readable EntryMessageBodyReader " + type);
        return type == Entry.class;
    }

    @Override
    public Entry readFrom(Class<Entry> type,
                          Type genericType,
                          Annotation[] annotations, MediaType mediaType,
                          MultivaluedMap<String, String> httpHeaders,
                          InputStream entityStream)
            throws IOException, WebApplicationException {

//        logger.info("readFrom EntryMessageBodyReader");
        try {
            ObjectMapper mapper = new ObjectMapper();
            EntryWrapper entryWrapper = mapper.readValue(entityStream, EntryWrapper.class);
            return entryWrapper.createEntry();
        } catch (Exception e) {
            logger.error("Error de-serializing an entry. " + e);
        }
        return null;
    }
}