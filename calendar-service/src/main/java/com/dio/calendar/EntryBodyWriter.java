package com.dio.calendar;

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
@Produces(MediaType.APPLICATION_XML)
public class EntryBodyWriter implements MessageBodyWriter<Entry> {

        @Override
        public boolean isWriteable(Class<?> type, Type genericType,
                                   Annotation[] annotations, MediaType mediaType) {
            System.out.println("is writable " + type);
            return type == Entry.class;
        }

        @Override
        public long getSize(Entry Entry, Class<?> type, Type genericType,
                            Annotation[] annotations, MediaType mediaType) {
            return 0;
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
            try {

                System.out.println("write " + entry);

                JAXBContext jaxbContext = JAXBContext.newInstance(EntryWrapper.class);
                jaxbContext.createMarshaller().marshal(new EntryWrapper(entry), entityStream);
            } catch (JAXBException jaxbException) {
                throw new RuntimeException(
                        "Error serializing a Entry to the output stream", jaxbException);
            }
        }
}
