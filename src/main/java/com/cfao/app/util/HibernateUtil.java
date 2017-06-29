package com.cfao.app.util;

import org.controlsfx.control.Notifications;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/**
 * Created by JP on 6/9/2017.
 */
public class HibernateUtil {
    private static SessionFactory sessionFactory = null;
    private static ServiceRegistry serviceRegistry;
    private static final String HIBERNATE_CONFIG = "/hibernate.cfg.xml";
    private static SessionFactory buildSessionFactory(){
        try {
            Configuration configuration = new Configuration();
            configuration.configure(HIBERNATE_CONFIG);
            serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
            return configuration.buildSessionFactory(serviceRegistry);
        }catch (Throwable ex){
            Notifications.create().title("Connexion à la Base de données")
                    .text("Impossible d'ouvrir une connexion à la Base de données ").showError();
            throw new ExceptionInInitializerError(ex);
        }
    }
    public static SessionFactory getSessionFactory(){
        if(sessionFactory == null){
            sessionFactory = buildSessionFactory();
        }
        return sessionFactory;

    }
    public static void shutdown(){
        getSessionFactory().close();
    }
}
