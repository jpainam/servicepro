package com.cfao.app.model;

import com.cfao.app.beans.User;
import org.hibernate.Query;

/**
 * Created by JP on 6/9/2017.
 */
public class UserModel extends Model{
    public UserModel(){
        this.table = "User";
        this.key = "IDUSER";
    }


    public boolean isAuthorized(String login, String pwd){
        session.beginTransaction();
        String q = "FROM User WHERE LOGIN = :login AND PASSWORD = :pwd";
        Query query = session.createQuery(q);
        query.setString("login", login);
        query.setString("pwd", pwd);
        User user = (User)query.uniqueResult();
        close();
        return user != null;
    }
}
