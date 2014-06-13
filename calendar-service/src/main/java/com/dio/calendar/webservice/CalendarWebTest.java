package com.dio.calendar.webservice;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.HttpRequestHandler;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by yur on 04.06.2014.
 */
public class CalendarWebTest implements HttpRequestHandler {
    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        try (PrintWriter out = response.getWriter()) {
            if (out != null) {
                out.println("<h1>Test</h1>");
                out.println("<p>" + request + "</p>");
            }
        }
    }
}

