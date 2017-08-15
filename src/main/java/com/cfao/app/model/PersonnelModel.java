package com.cfao.app.model;

import com.cfao.app.beans.*;
import com.cfao.app.util.AlertUtil;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * Created by JP on 7/2/2017.
 */
public class PersonnelModel extends  Model<Personnel> {
    protected  String className = "Personnel";

    public PersonnelModel(){
        super("Personnel");
    }
    public PersonnelModel(String className){
        super(className);
    }

    public List<Personne> getPersonneBySociete(Societe societe){
        Session session = getCurrentSession();
        try{
            session.beginTransaction();
            Criteria criteria = session.createCriteria(Personne.class);
            criteria.add(Restrictions.eq("societe", societe));
            List<Personne> list = criteria.list();
            session.getTransaction().commit();
            return list;
        }catch (Exception ex){
            ex.printStackTrace();
            AlertUtil.showErrorMessage(ex);
        }finally {
            if(session.isOpen()) {
                session.close();
            }
        }
        return null;
    }
    public void insertProfilNiveau(Personne personne, Profil profil, Niveau niveau){


    }

    public Integer countFormateurs(){
        Session session = getCurrentSession();
        try{
            session.beginTransaction();
            Criteria criteria = session.createCriteria(Formateur.class);
            criteria.setProjection(Projections.countDistinct("personnel"));
            Long count = (Long)criteria.uniqueResult();
            return count.intValue();
        }catch (Exception ex){
            AlertUtil.showErrorMessage(ex);
        }finally {
            if(session.isOpen()){
                session.close();
            }
        }
        return 0;
    }

}
