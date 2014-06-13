package com.dio.calendar.webservice;

import org.apache.log4j.Logger;
import org.springframework.web.HttpRequestHandler;

import javax.mail.Session;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yur on 05.06.2014.
 */
public class CalendarWebServiceLogin implements HttpRequestHandler {
    private String login;
    private String password;
    private final Logger logger = Logger.getLogger(CalendarWebServiceLogin.class);
    private final Map<String, String> users;

    public CalendarWebServiceLogin() {
        users = new HashMap<>();
        users.put("test", "test");
        users.put("admin", "admin");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        printForm(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!parseParams(request)) {
            logger.warn("Bad login request");
            doGet(request, response);
        } else {
            HttpSession session = request.getSession();
            session.setAttribute("login", login);
            session.setAttribute("password", password);
            response.sendRedirect("/member/");
        }
    }

    private Boolean parseParams(HttpServletRequest request) {
        login = request.getParameter("login");
        if (login == null)
            login = "";
        password = request.getParameter("password");
        if (password == null)
            login = "";
        Boolean result = (login != null && password != null && password.equals(users.get(login)));
        return result;
    }

    private void printForm(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try (PrintWriter out = response.getWriter()) {
            if (out != null) {
                out.println("<h1>Login to member area</h1>");
                out.println("<blockquote>");
                out.println("<p style=\"color: red;\">All fields are required</p>");
                out.println("<form method=\"post\" action=\"login\">");
                out.println("<input type=\"text\" name=\"login\" value=\"" + login + "\"><br/>");
                out.println("<input type=\"password\" name=\"password\"><br/>");
                out.println("<input type=\"submit\">");
                out.println("</form>");
                out.println("</blockquote>");
                out.println("<p>You can try users " + users.keySet() + "</p>");
            }
        }
    }

    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        login = "";
        password = "";
        switch (request.getMethod()) {
            case "GET":
                doGet(request, response);
                break;
            case "POST":
                doPost(request, response);
                break;
            default:
                doBarRequest(request, response);
        }
    }

    private void doBarRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        try (PrintWriter out = response.getWriter()) {
            if (out != null) {
                out.println("<h1>Bad request</h1>");
            }
        }

    }
}
