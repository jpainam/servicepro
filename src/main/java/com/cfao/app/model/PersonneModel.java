package com.cfao.app.model;

import com.cfao.app.beans.Personne;
import com.cfao.app.util.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.Query;

import java.util.List;
import java.util.Vector;

/**
 * Created by JP on 6/10/2017.
 */
public class PersonneModel extends Model<Personne> {
    public PersonneModel(){
        super("Personne");
    }
    public PersonneModel(String className){
        super(className);
    }
}
