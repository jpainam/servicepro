package com.cfao.app.model;

import com.cfao.app.beans.Competence;
import com.cfao.app.beans.Profil;
import com.cfao.app.beans.Profilcompetence;
import com.cfao.app.util.HibernateUtil;
import javafx.collections.ObservableSet;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JP on 6/15/2017.
 */
public class ProfilModel extends  Model {
    public static final int INITIAL = 1;
    public static final int FONDAMENTAL = 2;
    public static final int AVANCE = 3;
    public static final int EXPERT = 4;
    public ProfilModel(){
        super();
    }

    public ProfilModel(String className){
        super(className);
    }
    public List<Profil> select(){
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            Transaction tx = session.beginTransaction();
            Criteria criteria = session.createCriteria(Profil.class);
            List list = criteria.list();
            tx.commit();

            return list;
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            //close();
        }
        return null;
    }

   /* public List getCompetenceParProfil(Profil profil){
        List list = new ArrayList();
        try{
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            Transaction tx = session.beginTransaction();
            /*Criteria criteria = session.createCriteria(Profilcompetence.class, "pc");
            criteria.add(Restrictions.eq("PROFIL", profil.getIdprofil()))
                    .createCriteria("competence", JoinType.INNER_JOIN)
                    .createCriteria("niveau", JoinType.INNER_JOIN)
                    .addOrder(Order.asc("co.DESCRIPTION"));
            list = criteria.list();*/
        /*    String sql = "SELECT pc.*, co.*, niv.* " +
                    "FROM profil_competence pc " +
                    "INNER JOIN competences co ON co.IDCOMPETENCE = pc.COMPETENCE " +
                    "INNER JOIN niveau_competence niv ON niv.IDNIVEAUCOMPETENCE = co.NIVEAU " +
                    "WHERE pc.PROFIL = :profil";
            SQLQuery query = session.createSQLQuery(sql);
            query.setParameter("profil", profil.getIdprofil());
            query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
            list = query.list();
            tx.commit();
            return list;
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return list;
    }
   */
    public List getCompetenceParProfil2(Profil profil){
        List list = new ArrayList();
        try{
            String sql = "SELECT co.*, IF(co.TYPE='CN' OR co.TYPE='CNP',TRUE,FALSE) AS connaissance, " +
                    "IF(co.TYPE='CP' OR co.TYPE='CNP',TRUE,FALSE) AS competence, " +
                    "IF(nc.IDNIVEAUCOMPETENCE = :initial, TRUE, FALSE) AS initial, " +
                    "IF(nc.IDNIVEAUCOMPETENCE = :fondamental, TRUE, FALSE) AS fondamendal, " +
                    "IF(nc.IDNIVEAUCOMPETENCE = :avance, TRUE, FALSE) AS avance, " +
                    "IF(nc.IDNIVEAUCOMPETENCE = :expert, TRUE, FALSE) AS expert " +
                    "FROM competences co " +
                    "INNER JOIN profil_competence pc " +
                    "LEFT JOIN niveau_competence nc ON nc.IDNIVEAUCOMPETENCE = co.NIVEAU " +
                    "WHERE pc.COMPETENCE = co.IDCOMPETENCE AND pc.PROFIL = :profil " +
                    "ORDER BY co.DESCRIPTION ASC ";
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            Transaction tx = session.beginTransaction();
            SQLQuery query = session.createSQLQuery(sql);
            query.setParameter("initial", INITIAL);
            query.setParameter("fondamental", FONDAMENTAL);
            query.setParameter("avance", AVANCE);
            query.setParameter("expert", EXPERT);
            query.setParameter("profil", profil.getIdprofil());
            //query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
            list = query.list();
            tx.commit();
        }catch(Exception ex){
            ex.printStackTrace();
        }finally {
           //close();
        }
        return list;
    }

    public boolean insert(Profil profil) {
        try{
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            Transaction tx = session.beginTransaction();
            session.save(profil);
            tx.commit();
            return true;
        }catch(Exception ex){
            ex.printStackTrace();
        }finally {
            close();
        }
        return false;
    }

    public boolean insert(ObservableSet<Competence> selectedItems, Profil profil) {
        try{
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            Transaction tx = session.beginTransaction();
            for(Competence c : selectedItems){
                Profilcompetence profilcompetence = new Profilcompetence();
                profilcompetence.setCompetence(c);
                profilcompetence.setProfil(profil);
                session.save(profilcompetence);
            }
            tx.commit();
            return true;
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return false;
    }
}





