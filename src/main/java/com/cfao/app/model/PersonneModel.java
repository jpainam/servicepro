package com.cfao.app.model;

import com.cfao.app.beans.Personne;

/**
 * Created by JP on 6/10/2017.
 */
public class PersonneModel extends Model<Personne> {
    public PersonneModel(){
        super("Personne");
    }
    public PersonneModel(String className){
        super(className);
    }

}
