package com.cfao.app.model;

import com.cfao.app.beans.Groupe;

/**
 * Created by JP on 6/22/2017.
 */
public class GroupeModel extends Model<Groupe> {
    public GroupeModel(String className){
        super(className);
    }
    public GroupeModel(){
        super("Groupe");
    }
}
