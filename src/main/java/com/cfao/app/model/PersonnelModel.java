package com.cfao.app.model;

import com.cfao.app.beans.Personne;
import com.cfao.app.beans.Personnel;
import com.cfao.app.beans.Societe;
import com.cfao.app.util.AlertUtil;
import org.hibernate.Criteria;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * Created by JP on 7/2/2017.
 */
public class PersonnelModel extends  Model<Personnel> {
    public PersonnelModel(){
        super(Model.getBeanPath("Personne"));
    }
    public PersonnelModel(String className){
        super(className);
    }

    public List<Personne> getPersonneBySociete(Societe societe){
        try{
            Transaction tx = session.beginTransaction();
            Criteria criteria = session.createCriteria(Personne.class);
            criteria.add(Restrictions.eq("societe", societe));
            List<Personne> list = criteria.list();
            tx.commit();
            return list;
        }catch (Exception ex){
            ex.printStackTrace();
            AlertUtil.showErrorMessage(ex);
        }
        return null;
    }
}
