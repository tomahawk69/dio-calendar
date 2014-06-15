package com.dio.calendar.client;

import com.dio.calendar.*;
import com.dio.calendar.datastore.DataStoreFSException;
import org.apache.log4j.Logger;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by yur on 15.06.2014.
 */
public class Wrapper {
    private final String serviceUrl;
    private Boolean isConnected = false;
    private CalendarService service;
    private final Logger logger = Logger.getLogger(Wrapper.class);

    public Wrapper(String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }

    public Boolean init() {
        try {
            service = (CalendarService) Naming.lookup(serviceUrl);
            isConnected = true;
            logger.debug("calendarServiceWrapper initialized with URL " + serviceUrl);
            logger.info("calendarServiceWrapper initialised successfully");
        } catch (Exception e) {
            logger.error("calendarServiceWrapper initializing error: " + e);
            return false;
        }
        return true;
    }

    public Entry newEntry(String subject, String description, Date dateFrom, Date dateTo,
                          List<String> attenders, List<Notification> notifications) {
        logger.debug("New entry");
        Entry result = null;
        if (!isConnected) {
            init();
        }
        if (isConnected) {
            try {
                result = service.newEntry(subject, description, dateFrom, dateTo, attenders, notifications);
            } catch (RemoteException e) {
                logger.error(e);
            }
        }
        return result;
    }

    public Entry addEntry(Entry entry) {
        logger.debug("Add entry " + entry);
        Entry result = null;
        if (!isConnected) {
            init();
        }
        if (isConnected) {
            try {
                result = service.addEntry(entry);
            } catch (Exception e) {
                logger.error(e);
            }
        }
        return result;
    }

    public Entry getEntry(UUID id) {
        logger.debug("Get entry " + id);
        Entry result = null;
        if (!isConnected) {
            init();
        }
        if (isConnected) {
            try {
                result = service.getEntry(id);
            } catch (RemoteException e) {
                logger.error(e);
            }
        }
        return result;
    }

    public Entry saveEntry(Entry entry) {
        logger.debug("Save entry " + entry);
        Entry result = null;
        if (!isConnected) {
            init();
        }
        if (isConnected) {
            try {
                result = service.updateEntry(entry);
            } catch (Exception e) {
                logger.error(e);
            }
        }
        return result;
    }

    public Boolean removeEntry(Entry entry) {
        logger.debug("Remove entry " + entry);
        if (!isConnected) {
            init();
        }
        if (isConnected) {
            try {
                service.removeEntry(entry);
                return true;
            } catch (Exception e) {
                logger.error(e);
            }
        }
        return false;
    }
}
