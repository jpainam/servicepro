package com.cfao.app.model;

import com.cfao.app.beans.Formation;
import com.cfao.app.beans.Formationpersonne;
import com.cfao.app.beans.Personne;
import com.cfao.app.beans.Personnel;
import com.cfao.app.util.AlertUtil;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;

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
            Query query = session.createQuery("from Personne where idpersonne not in :formation");
            query.setParameterList("formation", formation.getParticipants());
            return query.list();
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
