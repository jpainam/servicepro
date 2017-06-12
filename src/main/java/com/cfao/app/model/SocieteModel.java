package com.cfao.app.model;

import com.cfao.app.beans.Societe;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Transaction;

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

    public void insert(Societe societe) {
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            session.save(societe);
            tx.commit();
        }catch(HibernateException ex){
            if(tx != null) tx.rollback();
            ex.printStackTrace();
        }finally {
            session.close();
        }
    }
}
