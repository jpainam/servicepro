package com.cfao.app.model;

import com.cfao.app.beans.User;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Restrictions;

/**
 * Created by JP on 6/9/2017.
 */
public class UserModel extends Model<User>{
    protected String className = "User";

    public UserModel(){
        super();
        this.table = "User";
        this.key = "IDUSER";
    }

    public UserModel(String className){
        super(className);
    }


    public boolean isAuthorized(String login, String pwd){
        Session session = getCurrentSession();
        try {
            Criteria criteria = session.createCriteria(User.class);
            Criterion loginCriterion = Restrictions.eq("login", login);
            Criterion pwdCriterion = Restrictions.eq("password", pwd);
            LogicalExpression andExp = Restrictions.and(loginCriterion, pwdCriterion);
            criteria.add(andExp);
            return criteria.list().size() != 0;
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            session.close();
        }
        return false;
    }
}
