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
            Criteria criteria = session.createCriteria(FormationPersonnel.class);
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

    public List<String> getPrestataires(){
        Session session = getCurrentSession();
        try{
            session.beginTransaction();
            Criteria criteria = session.createCriteria(Personnel.class);
            criteria.add(Restrictions.isNotNull("prestataire"));
            criteria.setProjection(Projections.distinct(Projections.property("prestataire")));
            /*List<Personnel> personnels = criteria.list();
            System.err.println(personnels);
            //Platform.exit();
           if(personnels == null){
               return null;
           }
            List<String> prestataires = new ArrayList<>();
            for(Personnel p : personnels.cl){
                if(p != null && p.getPrestataire() != null) {
                    prestataires.add(p.getPrestataire());
                }
            }
            return prestataires;*/
            return  criteria.list();
        }catch (Exception ex){
            AlertUtil.showErrorMessage(ex);
        }finally {
            if(session.isOpen()){
                session.close();
            }
        }
        return null;
    }

}
