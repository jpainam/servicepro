package com.cfao.app.model;

import com.cfao.app.beans.Societe;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Transaction;

import javax.xml.crypto.dsig.TransformService;
import java.util.List;

/**
 * Created by JP on 6/11/2017.
 */
public class SocieteModel extends Model<Societe> {

    public SocieteModel(){
        super("Societe");
    }
}
