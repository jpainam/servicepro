package com.cfao.app.model;

import com.cfao.app.beans.Personne;
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
        session.beginTransaction();
        String q = "FROM Personne";
        Query query = session.createQuery(q);
        return query.list();
    }
}
