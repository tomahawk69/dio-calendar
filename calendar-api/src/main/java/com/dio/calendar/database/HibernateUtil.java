package com.dio.calendar.database;

/**
 * Created by iovchynnikov on 7/2/2014.
 */
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.SessionFactory;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {

    private static final SessionFactory sessionFactory;
    static {
        Configuration configuration = new Configuration();
        configuration.configure();
        System.out.println("here");
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(
                configuration.getProperties()).build();
        System.out.println("there");
        System.out.println(configuration);
        configuration.setProperty("hibernate.temp.use_jdbc_metadata_defaults", "false");
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        System.out.println("11");
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}