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
public class Model<T> {
    protected String table;
    protected String key;
    protected String tmpClass;
    protected String className;
    protected static Transaction transaction;

    public Model() {
    }

    public static Session getCurrentSession() {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
           if(session == null){
                session = HibernateUtil.getSessionFactory().openSession();
                //transaction = session.beginTransaction();
                return session;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            AlertUtil.showErrorMessage(ex);
        }
        return session;
    }

    public Model(String tmpClass) {
        this();
        this.tmpClass = tmpClass;
        className = getBeanPath(tmpClass);
    }

    public List<T> getList() {
        Session session = getCurrentSession();
        try {
            Criteria criteria;
            session.beginTransaction();
            criteria = session.createCriteria(Class.forName(className));
            return criteria.list();
        } catch (Exception ex) {
            ex.printStackTrace();
            AlertUtil.showErrorMessage(ex);
        } finally {
            if(session.isOpen()) {
                session.close();
            }
        }
        return null;
    }

    public boolean saveOrUpdate(T type) {
        Session session = getCurrentSession();
        try {
            session.beginTransaction();
            session.saveOrUpdate(type);
            session.getTransaction().commit();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            AlertUtil.showErrorMessage(ex);
        } finally {
            if(session.isOpen()) {
                session.close();
            }
        }
        return false;
    }


    public boolean save(T type) {
        Session session = getCurrentSession();
        try {
            session.beginTransaction();
            session.save(type);
            session.getTransaction().commit();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            AlertUtil.showErrorMessage(ex);
            return false;
        } finally {
            if(session.isOpen()) {
                session.close();
            }
        }
    }


    public boolean update(T type) {
        Session session = getCurrentSession();
        try {
            session.beginTransaction();
            session.update(type);
            session.getTransaction().commit();

            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            AlertUtil.showErrorMessage(ex);
            return false;
        } finally {
            if(session.isOpen()) {
                session.close();
            }
        }
    }

    public boolean delete(T type) {
        Session session = getCurrentSession();
        try {
            session.beginTransaction();
            session.delete(type);
            session.getTransaction().commit();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            AlertUtil.showErrorMessage(ex);
            return false;
        } finally {
            if(session.isOpen()) {
                session.close();
            }
        }
    }


    public static String getBeanPath(String s) {
        return "com.cfao.app.beans." + s;
    }

    public static String getBeansClass(String s) {
        return getBeanPath(s);
    }

    public List<T> select() {
        return this.getList();
    }

    public void close() {
        getCurrentSession().flush();
        getCurrentSession().close();
    }


}