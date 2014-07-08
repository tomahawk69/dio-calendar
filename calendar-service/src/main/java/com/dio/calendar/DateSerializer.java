package com.dio.calendar;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by yur on 09.07.2014.
 */
public class DateSerializer extends JsonSerializer<Date>
{
    @Override
    public void serialize(Date date, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException
    {
        jgen.writeString(new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(date));
    }
}