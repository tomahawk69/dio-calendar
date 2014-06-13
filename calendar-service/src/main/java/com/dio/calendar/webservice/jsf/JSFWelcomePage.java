package com.dio.calendar.webservice.jsf;

import org.apache.log4j.Logger;

import javax.faces.bean.ManagedBean;

/**
 * Created by yur on 10.06.2014.
 */
@ManagedBean(name = "welcome", eager = true)
public class JSFWelcomePage {
    Logger logger = Logger.getLogger(JSFWelcomePage.class);

    public JSFWelcomePage() {
        logger.info("Welcome page init");
    }

    public String getMessage() {
        logger.info("getMessage call");
        return "Welcome message from class";
    }
}