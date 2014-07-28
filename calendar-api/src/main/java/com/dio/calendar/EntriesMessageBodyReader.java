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
import java.util.Arrays;
import java.util.List;

//@Provider
//@Consumes("application/json")
public class EntriesMessageBodyReader implements MessageBodyReader<List<Entry>> {

    private static Logger logger = Logger.getLogger(EntriesMessageBodyReader.class);

    @Override
    public boolean isReadable(Class<?> type, Type genericType,
                              Annotation[] annotations, MediaType mediaType) {
//        logger.info(String.format("is readable EntriesMessageBodyReader type %s genericType %s", type, Arrays.toString(genericType.getClass().getGenericInterfaces())));
        List<Entry> typeClass = new ArrayList<>();
//        if(genericType.getClass().getClasses()getName().equals(Entry.class.getName())){
//
//        }
//        return type == List.class && genericType == typeClass.getClass();
        return type == List.class;
    }

    @Override
    public List<Entry> readFrom(Class<List<Entry>> type,
                                Type genericType,
                                Annotation[] annotations, MediaType mediaType,
                                MultivaluedMap<String, String> httpHeaders,
                                InputStream entityStream)
            throws IOException, WebApplicationException {

//        logger.info("readFrom EntriesMessageBodyReader");
        try {
            ObjectMapper mapper = new ObjectMapper();
            List<Entry> entries = mapper.readValue(entityStream, new TypeReference<List<Entry>>() {});
            return entries;
        } catch (Exception e) {
            logger.error("Error de-serializing entries. " + e);
        }
        return null;
    }
}