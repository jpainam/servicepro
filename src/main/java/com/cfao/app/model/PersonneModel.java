package com.cfao.app.model;

import com.cfao.app.beans.Personne;
import org.hibernate.Criteria;
import org.hibernate.Query;

import java.util.List;
import java.util.Vector;

/**
 * Created by JP on 6/10/2017.
 */
public class PersonneModel extends Model {
    public PersonneModel(){
        super();
    }
    public List<Personne> select(){
        try {
            session.beginTransaction();
            Criteria criteria = session.createCriteria(Personne.class);
            return criteria.list();
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            close();
        }
        return null;
    }
}
