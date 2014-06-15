package com.dio.calendar.webservice;

import org.apache.log4j.Logger;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by yur on 05.06.2014.
 */
public class CalendarWebServiceMemberFilter extends DelegatingFilterProxy {
    Logger logger = Logger.getLogger(CalendarWebServiceMemberFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        logger.info("Member area access");
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession();
        String login = (String) session.getAttribute("login");
        String password = (String) session.getAttribute("password");
        if (login == null) {
            httpResponse.sendRedirect("/login");
        }
        filterChain.doFilter(request, response);
    }
}
