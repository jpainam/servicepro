package com.cfao.app.model;

import com.cfao.app.beans.CompetenceCertification;
import com.cfao.app.beans.Personne;
import com.cfao.app.beans.PersonneCompetence;
import com.cfao.app.util.AlertUtil;
import javafx.application.Platform;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JP on 6/10/2017.
 */
public class PersonneModel extends Model<Personne> {
    public PersonneModel() {
        super("Personne");
    }

    public PersonneModel(String className) {
        super(className);
    }

    public Boolean saveCompetence(List<PersonneCompetence> competenceList) {

        Session session = getCurrentSession();
        try {

            if (competenceList.size() > 0) {
                Transaction tx = session.beginTransaction();

                SQLQuery query = session.createSQLQuery("delete from personne_competence where personne = :personne");
                query.setParameter("personne", competenceList.get(0).getPersonne());
                query.executeUpdate();

                query = session.
                        createSQLQuery("insert into personne_competence(personne, competence, certification) " +
                                "values (:personne, :competence, :certif)");
                for (PersonneCompetence pc : competenceList) {
                    query.setParameter("personne", pc.getPersonne());
                    query.setParameter("competence", pc.getCompetence());
                    query.setParameter("certif", pc.getCompetenceCertification());
                    query.executeUpdate();
                }
                tx.commit();
            }
            return true;
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
        return false;
    }

    public List<PersonneCompetence> getCompetencesByCertification(Personne personne, CompetenceCertification certification) {
        Session session = getCurrentSession();
        try {
            session.beginTransaction();
            Criteria criteria = session.createCriteria(PersonneCompetence.class).add(
                    Restrictions.eq("competenceCertification", certification)
            ).add(Restrictions.eq("personne", personne));

            return criteria.list();
        } catch (Exception ex) {
            Platform.runLater(() -> AlertUtil.showErrorMessage(ex));
            ex.printStackTrace();
        } finally {
            if (session.isOpen()) {
                session.close();
            }
        }
        return new ArrayList<>();
    }
}
