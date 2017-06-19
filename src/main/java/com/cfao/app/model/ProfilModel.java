package com.cfao.app.model;

import com.cfao.app.beans.Competence;
import com.cfao.app.beans.Profil;
import org.hibernate.Criteria;

import java.util.List;

/**
 * Created by JP on 6/15/2017.
 */
public class ProfilModel extends  Model {
    public ProfilModel(){}

    public List<Profil> select(){
        try {
            session.beginTransaction();
            Criteria criteria = session.createCriteria(Profil.class);
            return criteria.list();
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            session.close();
        }
        return null;
    }
}
