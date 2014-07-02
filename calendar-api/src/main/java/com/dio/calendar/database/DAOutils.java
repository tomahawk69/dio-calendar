package com.dio.calendar.database;

import org.hibernate.sql.ordering.antlr.Factory;

/**
 * Created by iovchynnikov on 7/2/2014.
 */
public class DAOUtils {

    private static EntryDAO entryDAO = null;
    private static DAOUtils instance = null;

    public static synchronized DAOUtils getInstance(){
        if (instance == null){
            instance = new DAOUtils();
        }
        return instance;
    }

    public EntryDAO getEntryDAO(){
        if (entryDAO == null){
            entryDAO = new EntryDAOImpl();
        }
        return entryDAO;
    }
}