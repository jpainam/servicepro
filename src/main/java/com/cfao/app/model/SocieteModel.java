package com.cfao.app.model;

import com.cfao.app.beans.Societe;
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
        session.beginTransaction();
        String q = "FROM Societe";
        Query query = session.createQuery(q);
        return query.list();

    }

    public void insert(Societe societe) {
        try{
            Transaction t = session.beginTransaction();
            session.save(societe);
            t.commit();
            session.close();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
