package com.cfao.app.model;

import com.cfao.app.beans.CompetenceCertification;
import com.cfao.app.beans.Personne;
import com.cfao.app.beans.PersonneCompetence;
import com.cfao.app.util.AlertUtil;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
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
            AlertUtil.showErrorMessage(ex);
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
            AlertUtil.showErrorMessage(ex);
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
                return "select count(*) from personnes p where ! isnull(p.PASSPORT)";
            case 2:
                return "select count(*) from personnes p inner join personne_competence c on (p.IDPERSONNE = c.PERSONNE) where c.CERTIFICATION = 'EN'";
            default:
                return "select count(*) from personnes";
        }
    }

    public Integer countPersonneCompetenceEncours() {
        Session session = getCurrentSession();
        try {
            session.beginTransaction();
            Criteria criteria = session.createCriteria(PersonneCompetence.class).add(
                    Restrictions.eq("competenceCertification.certification", "EN")
            );
            criteria.setProjection(Projections.countDistinct("personne"));
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

    public Integer countPersonnePassportNull() {
        Session session = getCurrentSession();
        try {
            session.beginTransaction();
            Criteria criteria = session.createCriteria(Personne.class).add(
                    Restrictions.or(Restrictions.isNull("passport"), Restrictions.eq("passport", ""))
            );
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
