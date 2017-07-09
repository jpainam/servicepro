package com.cfao.app.model;

import com.cfao.app.util.AlertUtil;
import com.cfao.app.util.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.lang.Class;
import java.util.List;

/**
 * Created by JP on 6/9/2017.
 */
public class Model <T>{
    protected Session session;
    protected String table;
    protected String key;
    protected String tmpClass;
    protected Transaction tx;

    public Model(){
        session = HibernateUtil.getSessionFactory().openSession();
    }

    public Model(String tmpClass){
        this();
        this.tmpClass = tmpClass;
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
            //session.close();
        }
        return null;
    }

    public void saveOrUpdate(T type){
        try {
            if(!session.isOpen()){
                session = HibernateUtil.getSessionFactory().openSession();
            }
            tx = session.beginTransaction();
            session.saveOrUpdate(type);
            tx.commit();

        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            //session.close();
        }
    }


    public boolean save(T type){
        try {
            if(!session.isOpen()){
                session = HibernateUtil.getSessionFactory().openSession();
            }
            tx = session.beginTransaction();
            session.save(type);
            tx.commit();
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
            AlertUtil.showErrorMessage(ex);
            return false;
        }finally {
            //session.close();
        }
    }


    public boolean update(T type){
        try {
            if(!session.isOpen()){
                session = HibernateUtil.getSessionFactory().openSession();
            }
            tx = session.beginTransaction();
            session.update(type);
            tx.commit();
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
            AlertUtil.showErrorMessage(ex, "", "");
            return false;
        }finally {
            //session.close();
        }
    }
    public boolean delete(T type){
        try {
            tx = session.beginTransaction();
            session.delete(type);
            tx.commit();
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
            AlertUtil.showErrorMessage(ex);
            return false;
        }finally {
            //session.close();
        }
    }


    public static String getBeanPath(String s){return "com.cfao.app.beans."+s;}
    public static String getBeansClass(String s){return getBeanPath(s);}

    public List<T> select(){
        return this.getList();
    }

    public void close(){
        session.flush();
        session.close();
    }


}