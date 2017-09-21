package com.cfao.app.model;

import com.cfao.app.util.AlertUtil;
import com.cfao.app.util.HibernateUtil;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hsqldb.persist.HsqlProperties;
import org.hsqldb.server.Server;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by JP on 6/9/2017.
 */
public class Model<T> {
    static org.apache.log4j.Logger logger = Logger.getLogger(Model.class);
    protected String table;
    protected String key;
    protected String tmpClass;
    protected String className;
    protected static Transaction transaction;

    public Model() {
        //startServer();
    }

    public void startServer() {
        try {
            HsqlProperties p = new HsqlProperties();
            p.setProperty("server.database.0", "file:/data/servicepro");
            p.setProperty("server.dbname.0", "servicepro");
            // set up the rest of properties

            // alternative to the above is
            Server server = new Server();
            server.setProperties(p);
            server.setLogWriter(null); // can use custom writer
            server.setErrWriter(null); // can use custom writer
            server.start();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static Session getCurrentSession() {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            if (session == null) {
                session = HibernateUtil.getSessionFactory().openSession();
                //transaction = session.beginTransaction();
                return session;
            }
        } catch (Exception ex) {
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
            session.beginTransaction();
            session.clear();
            Criteria criteria = session.createCriteria(Class.forName(className));
            return criteria.list();
        } catch (Exception ex) {
            AlertUtil.showErrorMessage(ex);
        } finally {
            if (session.isOpen()) {
                session.close();
            }
        }
        return null;
    }

    public boolean saveOrUpdate(T type) {
        Session session = getCurrentSession();
        try {
            Transaction tx = session.beginTransaction();
            session.saveOrUpdate(type);
            tx.commit();
            return true;
        } catch (Exception ex) {
            AlertUtil.showErrorMessage(ex);
        } finally {
            if (session.isOpen()) {
                session.close();
            }
        }
        return false;
    }


    public boolean save(T type) {
        Session session = getCurrentSession();
        try {
            Transaction tx = session.beginTransaction();
            session.save(type);
            session.flush();
            tx.commit();
            return true;
        } catch (Exception ex) {
            AlertUtil.showErrorMessage(ex);
        } finally {
            if (session.isOpen()) {
                session.close();
            }
        }
        return false;
    }


    public boolean update(T type) {
        Session session = getCurrentSession();
        try {
            Transaction tx = session.beginTransaction();
            session.update(type);
            session.flush();
            tx.commit();
            return true;
        } catch (Exception ex) {
            AlertUtil.showErrorMessage(ex);
            return false;
        } finally {
            if (session.isOpen()) {
                session.close();
            }
        }
    }

    public boolean delete(T type) {
        Session session = getCurrentSession();
        try {
            Transaction tx = session.beginTransaction();
            session.delete(type);
            session.flush();
            tx.commit();
            return true;
        } catch (Exception ex) {
            AlertUtil.showErrorMessage(ex);
        } finally {
            if (session.isOpen()) {
                session.close();
            }
        }
        return false;
    }
    public boolean delete(List<T> objects) {
        Session session = getCurrentSession();
        try {
            Transaction tx = session.beginTransaction();
            for(T t : objects) {
                session.delete(t);
            }
            tx.commit();
            return true;
        } catch (Exception ex) {
            AlertUtil.showErrorMessage(ex);
        } finally {
            if (session.isOpen()) {
                session.close();
            }
        }
        return false;
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

    public long countTemplate(int cas) {
        String req = queryCountCase(cas);
        Session session = getCurrentSession();
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery(req);
            return ((BigInteger) query.list().get(0)).longValue();

        } catch (Exception ex) {
            AlertUtil.showErrorMessage(ex);
        } finally {
            if (session.isOpen()) {
                session.close();
            }
        }
        return 0;
    }

    public String queryCountCase(int cas) {
        return "";
    }

    public Integer truncate() {
        Session s = getCurrentSession();
        int rowsAffected = 0;
        try {
            s.beginTransaction();
            String hql = "delete from " + tmpClass;
            Query q = s.createQuery(hql);
            rowsAffected = q.executeUpdate();
        } catch (Exception e) {
            logger.error(e);
            AlertUtil.showErrorMessage(e);
        }finally {
            if(s.isOpen()){
                s.close();
            }
        }
        return rowsAffected;
    }


    public List<Object[]> query(String req) {
        Session session = getCurrentSession();
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery(req);
            return query.list();

        } catch (Exception ex) {
            AlertUtil.showErrorMessage(ex);
        } finally {
            if (session.isOpen()) {
                session.close();
            }
        }
        return null;
    }




}