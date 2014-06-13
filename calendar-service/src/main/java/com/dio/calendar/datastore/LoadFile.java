package com.dio.calendar.datastore;

import java.util.concurrent.Callable;

/**
 * Created by yur on 01.06.2014.
 */
public interface LoadFile extends Callable<Boolean> {


    Boolean call();

}
