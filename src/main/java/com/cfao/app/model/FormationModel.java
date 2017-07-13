package com.cfao.app.model;

import com.cfao.app.beans.Formation;
import com.cfao.app.beans.Personne;
import com.cfao.app.beans.Personnel;
import com.cfao.app.util.AlertUtil;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * Created by JP on 6/21/2017.
 */
public class FormationModel extends Model<Formation> {
    protected String className  = "Formation";

    public FormationModel(String classname){
        super(classname);
    }
    public FormationModel(){
        super("Formation");
    }

    public List<Personne> getNonParticipants(Formation formation) {
        Session session = getCurrentSession();
        try{
            session.beginTransaction();
            Criteria criteria = session.createCriteria(Personne.class);
            criteria.add(Restrictions.not(
                    Restrictions.in("idPersonne", formation.getParticipants().)
            ));
           /* Query query = session.createQuery("from Per
           sonne where idpersonne not in (select personne from Formationpersonne where formation = :formation)");
            query.setParameter("formation", formation);*/
            return criteria.list();
        }catch (Exception ex){
            ex.printStackTrace();
            AlertUtil.showErrorMessage(ex);
        }finally {
            if(session.isOpen()){
                session.close();
            }
        }
        return null;
    }
}
