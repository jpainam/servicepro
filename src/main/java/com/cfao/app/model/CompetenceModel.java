package com.cfao.app.model;

import com.cfao.app.beans.Competence;
import com.cfao.app.beans.Profil;
import com.cfao.app.beans.Profilcompetence;
import com.cfao.app.util.AlertUtil;
import com.cfao.app.util.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

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
}
