package com.cfao.app.model;

import com.cfao.app.beans.*;
import com.cfao.app.util.AlertUtil;
import com.cfao.app.util.Constante;
import javafx.application.Platform;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by JP on 6/21/2017.
 */
public class FormationModel extends Model<Formation> {
    protected String className = "Formation";

    public FormationModel(String classname) {
        super(classname);
    }

    public FormationModel() {
        super("Formation");
    }

    public List<Personne> getNonParticipants(Formation formation) {
        Session session = getCurrentSession();
        try {
            List<Integer> personneIds = new ArrayList<>();
            for (FormationPersonne fp : formation.getFormationPersonnes()) {
                personneIds.add(fp.getPersonne().getIdpersonne());
            }
            session.beginTransaction();
            Criteria criteria = session.createCriteria(Personne.class);
            criteria.add(Restrictions.not(Restrictions.in("idpersonne", personneIds)));
            return criteria.list();
            /*Query query = session.createQuery("from Personne where idpersonne not in :formation");
            query.setParameterList("formation", formation.getParticipants());*/
            //return query.list();
        } catch (Exception ex) {
            ex.printStackTrace();
            AlertUtil.showErrorMessage(ex);
        } finally {
            if (session.isOpen()) {
                session.close();
            }
        }
        return null;
    }

    public List<Formation> getFormationsByPersonne(Personne personne) {
        Session session = getCurrentSession();
        try {
            session.beginTransaction();
            Criteria criteria = session.createCriteria(FormationPersonne.class).add(
                    Restrictions.eq("personne", personne)
            );
            criteria.setProjection(Projections.property("formation"));
            return criteria.list();
        } catch (Exception ex) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    AlertUtil.showErrorMessage(ex);
                }
            });
        } finally {
            if (session.isOpen()) {
                session.close();
            }
        }
        return null;
    }

    public List<Formation> getFormationsSouhaitees(Personne personne) {
        Session session = getCurrentSession();
        try {
            session.beginTransaction();
            /** Competences de la personne a certifier (non certifier) */
            //CompetenceCertification certif = new CompetenceCertification("AC", "A Certifier");
            Criteria competence = session.createCriteria(PersonneCompetence.class).add(
                    Restrictions.eq("competenceCertification.certification", Constante.COMPETENCE_ACERTIFIER)
            ).add(Restrictions.eq("personne", personne));
            competence.setProjection(Projections.property("competence"));

            /** Formations qui couvre ces competence et qui n'ont pas n'ont pas deja debute */
            if (competence.list().size() > 0) {
                Criteria formations = session.createCriteria(FormationCompetence.class, "fc").add(
                        Restrictions.in("fc.competence", competence.list())).setProjection(
                        Projections.distinct(Projections.property("formation"))
                ).
                        createCriteria("fc.formation", "f").add(
                        Restrictions.ge("f.datedebut", new Date())
                );
                return formations.list();
            }
        } catch (Exception ex) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    AlertUtil.showErrorMessage(ex);
                }
            });
            ex.printStackTrace();
        } finally {
            if (session.isOpen()) {
                session.close();
            }
        }
        return new ArrayList<>();
    }

    @Override
    public String queryCountCase(int cas) {
        switch (cas) {

            case 1:
                return "select count(*) from formations f inner join etat_formation e on(e.IDETATFORMATION = f.ETATFORMATION) where f.DATEFIN < current_date() and f.ETATFORMATION = 1";
            case 2:
                return "select count(*) from formations f inner join etat_formation e on(e.IDETATFORMATION = f.ETATFORMATION) where f.ETATFORMATION = 4";
            case 3:
                return "select count(*) from formations f inner join etat_formation e on(e.IDETATFORMATION = f.ETATFORMATION) where f.ETATFORMATION = 3";

            default:
                return "select count(*) formations";
        }
    }


    public Integer countFormation(Integer etatFormation) {
        Session session = getCurrentSession();
        try {
            session.beginTransaction();
            Criteria criteria = session.createCriteria(Formation.class, "f").createCriteria(
                    "f.etatFormation", "e"
            ).add(Restrictions.eq("e.idetatformation", etatFormation));
            criteria.setProjection(Projections.rowCount());
            Long count = (Long) criteria.uniqueResult();
            return count.intValue();
        } catch (Exception ex) {
            AlertUtil.showErrorMessage(ex);
        } finally {
            if (session.isOpen()) {
                session.close();
            }
        }
        return 0;
    }

    public Integer countFormationTerminees() {
        Session session = getCurrentSession();
        try {
            session.beginTransaction();
            Criteria criteria = session.createCriteria(Formation.class, "f")
                    .add(Restrictions.le("f.datefin", new Date())
            ).createCriteria("f.etatFormation", "e")
                    .add(Restrictions.eq("e.idetatformation", Constante.FORMATION_TERMINEE));
            criteria.setProjection(Projections.rowCount());
            Long count = (Long) criteria.uniqueResult();
            return count.intValue();

        } catch (Exception ex) {
            AlertUtil.showErrorMessage(ex);
        } finally {
            if (session.isOpen()) {
                session.close();
            }
        }
        return 0;
    }

}
