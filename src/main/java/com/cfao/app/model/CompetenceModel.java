package com.cfao.app.model;

import com.cfao.app.beans.Competence;
import org.hibernate.Criteria;

import java.util.List;

/**
 * Created by JP on 6/15/2017.
 */
public class CompetenceModel extends Model {
    public CompetenceModel(){
        super();
    }
    public List<Competence> select(){
        try {
            session.beginTransaction();
            Criteria criteria = session.createCriteria(Competence.class);
            return criteria.list();
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            session.close();
        }
        return null;
    }
}
