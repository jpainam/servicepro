package com.cfao.app.model;

import com.cfao.app.util.HibernateUtil;
import org.hibernate.Session;

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
}
