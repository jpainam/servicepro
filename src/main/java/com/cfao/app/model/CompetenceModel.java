package com.cfao.app.model;

import com.cfao.app.beans.*;
import com.cfao.app.util.AlertUtil;
import com.cfao.app.util.HibernateUtil;
import org.hibernate.*;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JP on 6/15/2017.
 */
public class CompetenceModel extends Model<Competence> {
    public CompetenceModel(){
        super("Competence");
    }

    public CompetenceModel(String className){
        super(className);
    }

    public List<Profilcompetence> getCompetenceParProfil(Profil profil){
        Session session = getCurrentSession();
        try {
            /*Criteria criteria = session.createCriteria(Profilcompetence.class);
            criteria.add(Restrictions.eq("profil", profil));
            return criteria.list();*/
            session.beginTransaction();
            Query query = session.createQuery(
                    "from Profilcompetence as pc " +
                            "inner join fetch pc.competence co " +
                            "left join fetch co.niveau " +
                            "where pc.profil = :profil");
            query.setParameter("profil", profil);
            return query.list();
        }catch (Exception ex){
            ex.printStackTrace();
            AlertUtil.showErrorMessage(ex);
        }finally {
            session.close();
        }
        return null;
    }

    public List<Competence> getNonCompetences(List<Competence> competences) {
        Session session = getCurrentSession();
        try{
            session.beginTransaction();
            List<Integer> competencesIds = new ArrayList<>();
            for(Competence c : competences){
                competencesIds.add(c.getIdcompetence());
            }
            Criteria criteria = session.createCriteria(Competence.class);
           criteria.add(Restrictions.not(Restrictions.in("idcompetence", competencesIds)));
            return criteria.list();
        }catch (Exception ex){
            ex.printStackTrace();
            AlertUtil.showErrorMessage(ex);
        }finally {
            session.close();
        }
        return null;
    }
    public List<Competence> getNonCompetences(Profil profil, Niveau niveau) {
        Session session = getCurrentSession();
        try{
            session.beginTransaction();
            /*Query query1 = session.createQuery("select pc.competence from ProfilcompetenceId pc where pc.profil = :profil and pc.niveau = :niveau");
            query1.setParameter("profil", profil);
            query1.setParameter("niveau", niveau);
            Query query2 = session.createQuery("from Competence where idcomptence not in (:competences)");
            query2.setParameterList("competences", query1.list());

            /*Query query = session.createQuery("from Competence where idcompetence not in :competence");
            query.setParameterList("competence",competences);*/
            //return query2.list();
            SQLQuery sqlQuery = session.createSQLQuery("Select * from competences where idcompetence not in (" +
                    "Select competence from profil_competence where niveau = :niveau and profil = :profil)");
            sqlQuery.setParameter("profil", profil.getIdprofil());
            sqlQuery.setParameter("niveau", niveau.getIdniveau());
            return sqlQuery.list();
        }catch (Exception ex){
            ex.printStackTrace();
            AlertUtil.showErrorMessage(ex);
        }finally {
            session.close();
        }
        return null;
    }

    /*
     Quand le mapping de Formationcompetence va marcher
    public List<Formationcompetence> getFormationByCompetence(Competence competence) {
        Session session = getCurrentSession();
        try{
            session.beginTransaction();
            Criteria criteria = session.createCriteria(Formationcompetence.class).add(
                    Restrictions.eq("competence", competence));
            return criteria.list();
        }catch (Exception ex){
            ex.printStackTrace();
            AlertUtil.showErrorMessage(ex);
        }finally {
            session.close();
        }
        return null;
    }
    */
    public List<Formation> getFormationByCompetence(Competence competence) {
        Session session = getCurrentSession();
        try{
            session.beginTransaction();
            SQLQuery query = session.createSQLQuery("select formation from formation_competence where competence = :competence");
            query.setParameter("competence", competence.getIdcompetence());
           Criteria criteria = session.createCriteria(Formation.class).add(
                   Restrictions.in("idformation", query.list()));
            return criteria.list();
        }catch (Exception ex){
            ex.printStackTrace();
            AlertUtil.showErrorMessage(ex);
        }finally {
            session.close();
        }
        return null;

    }

    public List<Profilcompetence> getProfilByCompetence(Competence competence) {
        Session session = getCurrentSession();
        try{
            session.beginTransaction();
            Criteria criteria = session.createCriteria(Profilcompetence.class).add(
                    Restrictions.eq("pk.competence", competence));
            return criteria.list();
        }catch (Exception ex){
            ex.printStackTrace();
            AlertUtil.showErrorMessage(ex);
        }finally {
            session.close();
        }
        return null;
    }
}
