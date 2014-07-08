package com.dio.calendar.webservice;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by iovchynnikov on 7/8/2014.
 */
//@Path("/test")
public class CalendarRestService {
    // This method is called if TEXT_PLAIN is request
    //@GET
    //@Produces(MediaType.TEXT_PLAIN)
    public String testJerseyText() {
        return "Test Jersey";
    }

    // This method is called if XML is request
    //@GET
    //@Produces(MediaType.TEXT_XML)
    public String testJerseyXml() {
        return "<?xml version=\"1.0\"?>" + "<hello> Hello Jersey" + "</hello>";
    }

    // This method is called if HTML is request
    //@GET
    //@Produces(MediaType.TEXT_HTML)
    public String testJerseyHtml() {
        return "<html> " + "<title>" + "Hello Jersey" + "</title>"
                + "<body><h1>" + "Hello Jersey" + "</body></h1>" + "</html> ";
    }
}
