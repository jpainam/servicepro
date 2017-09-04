package com.cfao.app.model;

import com.cfao.app.beans.Sujet;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 * Created by Communication on 04/09/2017.
 */
public class SujetModel extends Model<Sujet> {

    public SujetModel(){
        super("Sujet");
    }

    public boolean exist(String str){
        Session session = getCurrentSession();
        session.beginTransaction();
        try {
            Criteria criteria = session.createCriteria(Class.forName(className));
            criteria.add(Restrictions.eq("libelle", str));
            return criteria.list().size() == 0;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }
}