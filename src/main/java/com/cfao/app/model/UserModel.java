package com.cfao.app.model;

import com.cfao.app.beans.Users;
import org.hibernate.Query;

/**
 * Created by JP on 6/9/2017.
 */
public class UserModel extends Model{
    public UserModel(){
        this.table = "Users";
        this.key = "IDUSER";
    }

    public Users get(String login){
        /*session.beginTransaction();
        String q = "FROM Users WHERE LOGIN = :login AND PASSWORD = :pwd";
        Query query = session.createQuery(q);
        query.setString("login", login);
        query.setString("pwd", pwd);
        Users user = (Users)query.uniqueResult();*/
        return null;
    }
}
