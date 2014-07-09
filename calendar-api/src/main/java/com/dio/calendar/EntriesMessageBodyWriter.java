package com.dio.calendar;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.lang.reflect.Type;

/**
 * Created by iovchynnikov on 7/9/14.
 */
@Provider
@Produces("application/json")
public class EntriesMessageBodyWriter implements MessageBodyWriter<List<Entry>> {

    private static Logger logger = Logger.getLogger(EntryMessageBodyWriter.class);

    @Override
    public boolean isWriteable(Class<?> type, Type genericType,
                              Annotation[] annotations, MediaType mediaType) {
        logger.info(String.format("is readable EntriesMessageBodyWriter type %s, genericType %s", type, genericType));
//        System.out.println(new TypeReference<List<Entry>>() {});
//        System.out.println(genericType);
//        if (genericType == Entry.class) {
//            System.out.println(1);
//        }
//        if (genericType.equals(Entry.class)) {
//            System.out.println(2);
//        }
//        if (type.isAssignableFrom(List.class)) {
//            System.out.println(3);
//
//        }
//        return type == ArrayList.class && genericType == Entry.class;
        return type == ArrayList.class;
    }

    @Override
    public long getSize(List<Entry> entries, Class<?> type, Type genericType,
                        Annotation[] annotations, MediaType mediaType) {
        return -1;
    }

    @Override
    public void writeTo(List<Entry> entries,
                        Class<?> type,
                        Type genericType,
                        Annotation[] annotations,
                        MediaType mediaType,
                        MultivaluedMap<String, Object> httpHeaders,
                        OutputStream entityStream)
            throws IOException, WebApplicationException {
        logger.debug("write EntriesMessageBodyWriter " + entries.size());
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(entityStream, entries);
        } catch (Exception e) {
            logger.error("Error serializing entries to the output stream. " + e);
        }
    }
}
