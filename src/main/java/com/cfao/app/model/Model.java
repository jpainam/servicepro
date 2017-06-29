package com.cfao.app.model;

import com.cfao.app.util.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.Session;
import java.lang.Class;
import java.util.List;

/**
 * Created by JP on 6/9/2017.
 */
public class Model <T>  {
    protected static Session session = HibernateUtil.getSessionFactory().openSession();
    protected String table;
    protected  String key;
    protected String tmpClass;

    public Model(){
        if(!session.isOpen()){
            session = HibernateUtil.getSessionFactory().openSession();
        }
    }

    public Model(String tmpClass){
        this();
        this.tmpClass = tmpClass;
    }
    public void close(){
        if(session.isOpen()) {
            session.flush();
            session.close();
        }
    }
    public List<T> getList(){
        try {
            if(!session.isOpen()){
                session = HibernateUtil.getSessionFactory().openSession();
            }
            session.beginTransaction();
            Criteria criteria;
            criteria = session.createCriteria(Class.forName(tmpClass));
            return criteria.list();
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            close();
        }
        return null;
    }
    public static String getBeansClass(String s){return "com.cfao.app.beans."+s;}
    public List<T> select(){
        return this.getList();
    }
}