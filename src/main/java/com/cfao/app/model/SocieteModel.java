package com.cfao.app.model;

import com.cfao.app.beans.Societe;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Transaction;

import javax.xml.crypto.dsig.TransformService;
import java.util.List;

/**
 * Created by JP on 6/11/2017.
 */
public class SocieteModel extends Model {
    public SocieteModel(){
        super();

    }
    public List<Societe> select() {
        try {
            session.beginTransaction();
            Criteria criteria = session.createCriteria(Societe.class);
            return criteria.list();
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            session.close();
        }
        return null;
    }

    public boolean insert(Societe societe) {
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            session.save(societe);
            tx.commit();
            return true;
        }catch(HibernateException ex){
            if(tx != null) tx.rollback();
            ex.printStackTrace();
        }finally {
            session.close();
        }
        return false;
    }

    public boolean delete(Societe societe) {
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            session.delete(societe);
            tx.commit();
            return true;
        }catch (Exception ex){
            if(tx != null) tx.rollback();
            ex.printStackTrace();
        }finally {
            session.close();
        }
        return false;
    }

    public boolean update(Societe societe) {
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            session.update(societe);
            tx.commit();
            return true;
        }catch (Exception ex){
            if(tx != null) tx.rollback();
            ex.printStackTrace();
        }finally {
            session.close();
        }
        return false;
    }
}
