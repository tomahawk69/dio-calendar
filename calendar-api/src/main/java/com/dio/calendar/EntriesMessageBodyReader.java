package com.dio.calendar;

/**
 * Created by iovchynnikov on 7/9/14.
 */


/**
 * Created by yur on 08.07.2014.
 */

import javax.ws.rs.ext.MessageBodyReader;
import com.dio.calendar.Entry;
import com.dio.calendar.EntryWrapper;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import javax.ws.rs.Consumes;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Provider
@Consumes("application/json")
public class EntriesMessageBodyReader implements MessageBodyReader<List<Entry>> {

    private static Logger logger = Logger.getLogger(EntriesMessageBodyReader.class);

    @Override
    public boolean isReadable(Class<?> type, Type genericType,
                              Annotation[] annotations, MediaType mediaType) {
        logger.debug("is readable EntriesMessageBodyReader " + type);
        List<Entry> typeClass = new ArrayList<>();
        System.out.println(typeClass.getClass());
        return type == List.class && genericType == typeClass.getClass();
    }

    @Override
    public List<Entry> readFrom(Class<List<Entry>> type,
                                Type genericType,
                                Annotation[] annotations, MediaType mediaType,
                                MultivaluedMap<String, String> httpHeaders,
                                InputStream entityStream)
            throws IOException, WebApplicationException {

        logger.debug("readFrom EntriesMessageBodyReader");
        try {
            System.out.println("readFrom EntriesMessageBodyReader");
            ObjectMapper mapper = new ObjectMapper();
            List<Entry> entries = mapper.readValue(entityStream, new TypeReference<List<Entry>>() {});
            return entries;
        } catch (Exception e) {
            logger.error("Error de-serializing entries. " + e);
        }
        return null;
    }
}