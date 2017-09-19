package com.cfao.app.model;

import com.cfao.app.beans.Planification;
import com.cfao.app.util.AlertUtil;
import org.hibernate.Session;

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

}