package com.dio.calendar.client;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;


/**
 * Created by iovchynnikov on 7/8/2014.
 */
public class ClientRestMain {

    public static void main(String[] args) {
        ClientConfig config = new DefaultClientConfig();
        Client client = Client.create(config);
        WebResource service = client.resource(getBaseURI());

        // Fluent interfaces
        System.out.println(service.path("rest").path("test").accept(MediaType.TEXT_PLAIN).get(ClientResponse.class).toString());
        // Get plain text
        System.out.println(service.path("rest").path("test").accept(MediaType.TEXT_PLAIN).get(String.class));
        // Get XML
        System.out.println(service.path("rest").path("test").accept(MediaType.TEXT_XML).get(String.class));
        // The HTML
        System.out.println(service.path("rest").path("test").accept(MediaType.TEXT_HTML).get(String.class));
    }


    private static URI getBaseURI() {
        return UriBuilder.fromUri("http://localhost:8080/").build();
    }

}
