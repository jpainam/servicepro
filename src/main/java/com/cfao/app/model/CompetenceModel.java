package com.cfao.app.model;

import com.cfao.app.beans.*;
import com.cfao.app.util.AlertUtil;
import javafx.application.Platform;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JP on 6/15/2017.
 */
public class CompetenceModel extends Model<Competence> {
    public CompetenceModel() {
        super("Competence");
    }

    public CompetenceModel(String className) {
        super(className);
    }

    /*public List<ProfilCompetence> getCompetenceParProfil(Profil profil){
        Session session = getCurrentSession();
        try {
            /*Criteria criteria = session.createCriteria(ProfilCompetence.class);
            criteria.add(Restrictions.eq("profil", profil));
            return criteria.list();*/
           /* session.beginTransaction();
            Query query = session.createQuery(
                    "from ProfilCompetence as pc " +
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
    }*/

    public List<Competence> getNonCompetences(List<Competence> competences) {
        Session session = getCurrentSession();
        try {
            session.beginTransaction();
            List<Integer> competencesIds = new ArrayList<>();
            for (Competence c : competences) {
                competencesIds.add(c.getIdcompetence());
            }
            Criteria criteria = session.createCriteria(Competence.class);
            if (!competencesIds.isEmpty()) {
                criteria.add(Restrictions.not(Restrictions.in("idcompetence", competencesIds)));
            }
            return criteria.list();
        } catch (Exception ex) {
            ex.printStackTrace();
            AlertUtil.showErrorMessage(ex);
        } finally {
            session.close();
        }
        return null;
    }

    public List<Competence> getNonCompetences(Profil profil, Niveau niveau) {
        Session session = getCurrentSession();
        try {
            session.beginTransaction();
            /*Query query1 = session.createQuery("select pc.competence from ProfilCompetenceId pc where pc.profil = :profil and pc.niveau = :niveau");
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
        } catch (Exception ex) {
            ex.printStackTrace();
            AlertUtil.showErrorMessage(ex);
        } finally {
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

    public List<Competence> findByNiveau(Niveau niveau) {
        Session session = getCurrentSession();
        try {
            session.beginTransaction();
            Criteria criteria = session.createCriteria(Competence.class).add(
                    Restrictions.eq("niveau", niveau)
            );
            return criteria.list();
        } catch (Exception ex) {
            AlertUtil.showErrorMessage(ex);
        } finally {
            if (session.isOpen()) {
                session.close();
            }
        }
        return null;
    }

    public List<Competence> getCompetencesByCertification(Personne personne, CompetenceCertification certification) {
        Session session = getCurrentSession();
        try{
            session.beginTransaction();
            Criteria criteria = session.createCriteria(PersonneCompetence.class).add(
                            Restrictions.eq("competenceCertification", certification)).add(
                                    Restrictions.eq("personne", personne)
            );
            criteria.setProjection(Projections.property("competence"));
            return criteria.list();
        }catch (Exception ex){
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    AlertUtil.showErrorMessage(ex);
                }
            });
        }finally {
            if(session.isOpen()){
                session.close();
            }
        }
        return null;
    }

    public List<PersonneCompetence> getCompetencePersonneByProfil(ProfilPersonne profilPersonne) {
        Session session = getCurrentSession();
        try{
            session.beginTransaction();
            Criteria criteria = session.createCriteria(PersonneCompetence.class).add(
                    Restrictions.in("competence", profilPersonne.getProfil().getCompetences())
            ).add(Restrictions.eq("personne", profilPersonne.getPersonne()));
            return  criteria.list();
        }catch (Exception ex){
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    AlertUtil.showErrorMessage(ex);
                }
            });
        }finally {
            if(session.isOpen()){
                session.close();
            }
        }
        return null;
    }

    /*public List<ProfilCompetence> getProfilByCompetence(Competence competence) {
        Session session = getCurrentSession();
        try{
            session.beginTransaction();
            Criteria criteria = session.createCriteria(ProfilCompetence.class).add(
                    Restrictions.eq("pk.competence", competence));
            return criteria.list();
        }catch (Exception ex){
            ex.printStackTrace();
            AlertUtil.showErrorMessage(ex);
        }finally {
            session.close();
        }
        return null;
    }*/
}
