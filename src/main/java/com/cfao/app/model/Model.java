package com.cfao.app.model;

import com.cfao.app.util.HibernateUtil;
import org.controlsfx.control.Notifications;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by JP on 6/9/2017.
 */
public class Model {
    protected Session session;
    protected String table;
    protected  String key;
    public Model(){
        session = HibernateUtil.getSessionFactory().openSession();
    }
    public void close(){
        session.flush();
        session.close();
    }

    public List select() {
        return null;
    }
}
