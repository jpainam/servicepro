package com.cfao.app.model;

import com.cfao.app.beans.Formation;
import com.cfao.app.beans.Personnel;
import org.hibernate.Criteria;
import org.hibernate.Query;

/**
 * Created by JP on 6/21/2017.
 */
public class FormationModel extends Model<Formation> {
    public FormationModel(String classname){
        super(classname);
    }
    public FormationModel(){}

    public boolean deleteFormateurs(Personnel personnel, Formation formation){
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("DELETE formateurs WHERE FORMATION = :formation AND PERSONNEL = :personnel");
            query.setParameter("formation", formation.getIdformation());
            query.setParameter("personnel", personnel.getIdpersonnel());
            query.executeUpdate();
            tx.commit();
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            //session.close();
        }
        return false;
    }

}
