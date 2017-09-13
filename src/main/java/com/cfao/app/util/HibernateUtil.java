package com.cfao.app.util;

import javafx.application.Platform;
import org.controlsfx.control.Notifications;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.util.logging.Level;

/**
 * Created by JP on 6/9/2017.
 */
public class HibernateUtil {
    private static SessionFactory sessionFactory = null;
    private static ServiceRegistry serviceRegistry;
    private static final String HIBERNATE_CONFIG = "/hibernate.cfg.xml";

    private synchronized static SessionFactory buildSessionFactory() {
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);
        try {
            Configuration configuration = new Configuration();
            configuration.configure();

            /*configuration.setProperty("hibernate.connection.driver_class", "org.hsqldb.jdbcDriver");

            configuration.setProperty("hibernate.connection.url", "jdbc:hsqldb:file:data/servicepro");

            configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.HSQLDialect");
            configuration.setProperty("hibernate.connection.username", "sa");*/

            //configuration.configure(HIBERNATE_CONFIG);

            serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
            return configuration.buildSessionFactory(serviceRegistry);
        } catch (Exception ex) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    Notifications.create().title("Connexion à la Base de données")
                            .text("Impossible d'ouvrir une connexion à la Base de données ").showError();
                    AlertUtil.showErrorMessage(ex);
                }
            });
            ex.printStackTrace();
            throw new ExceptionInInitializerError(ex);
        }
    }

    public synchronized static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            sessionFactory = buildSessionFactory();
        }
        return sessionFactory;

    }

    public synchronized static void shutdown() {
        getSessionFactory().close();
    }

    /*public static void main(String args[]){
        try{
            Session session = getSessionFactory().openSession();
            System.out.println("Session oppened");
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }*/
}
