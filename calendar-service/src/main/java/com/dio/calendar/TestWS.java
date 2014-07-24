package com.dio.calendar;

import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 * Created by yur on 25.07.2014.
 */
@WebService
public class TestWS {

    @WebMethod
    public String getTestMessage() {
        return "Goooo";
    }
}
