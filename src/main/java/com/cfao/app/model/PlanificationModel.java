package com.cfao.app.model;

import com.cfao.app.beans.Planification;
import com.cfao.app.util.AlertUtil;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * Created by armel on 17/09/2017.
 */
public class PlanificationModel extends Model<Planification> {

    public PlanificationModel() {
        super("Planification");
    }

    public Planification getById(Integer i) {
        Session session = getCurrentSession();
        try {
            session.beginTransaction();
            Planification p = (Planification) session.load(Planification.class, i);
            return p;
        } catch (Exception ex) {
            AlertUtil.showErrorMessage(ex);
        } finally {
            if (session.isOpen()) {
                session.close();
            }
        }
        return null;
    }

    public List<Planification> getPlanificationToAlert() {
        Session session = getCurrentSession();
        try {
            Criterion critere = Restrictions.and(Restrictions.eq("fait", false),
                    Restrictions.eq("alert", true));
            session.beginTransaction();
            session.clear();
            Criteria criteria = session.createCriteria(Class.forName(className));
            criteria.add(critere);
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
}