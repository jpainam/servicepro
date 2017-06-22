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
    protected Session session;
    protected String table;
    protected  String key;
    protected String tmpClass;

    public Model(){
        session = HibernateUtil.getSessionFactory().openSession();
    }

    public Model(String tmpClass){
        this();
        this.tmpClass = tmpClass;
    }
    public void close(){
        session.flush();
        session.close();
    }
    public List<T> getList(){
        try {
            session.beginTransaction();
            Criteria criteria;
            criteria = session.createCriteria(Class.forName(tmpClass));
            return criteria.list();
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            session.close();
        }
        return null;
    }
    public static String getBeansClass(String s){return "com.cfao.app.beans."+s;}
    public List<T> select(){
        return this.getList();
    }
}