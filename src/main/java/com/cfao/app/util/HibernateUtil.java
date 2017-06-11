package com.cfao.app.util;

import org.controlsfx.control.Notifications;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Created by JP on 6/9/2017.
 */
public class HibernateUtil {
    private static final SessionFactory sessionFactory = buildSessionFactory();
    private static SessionFactory buildSessionFactory(){
        try {
            // Create the sessionFactory from hibernate.cfg.xml
            return new Configuration().configure().buildSessionFactory();
        }catch (Throwable ex){
            /*System.err.println("SessionFactory creation failed." + ex); */
            Notifications.create().title("Connexion à la Base de données")
                    .text("Impossible de se connecter à la Base de données ").showError();
            throw new ExceptionInInitializerError(ex);
        }
    }
    public static SessionFactory getSessionFactory(){
         return sessionFactory;

    }
    public static void shutdown(){
        getSessionFactory().close();
    }
}
