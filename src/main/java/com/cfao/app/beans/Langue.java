package com.cfao.app.beans;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import javax.persistence.*;

/**
 * Created by armel on 01/07/2017.
 */

@Entity
@Table(name = "langue")
public class Langue {

    /**
     * FIELDS
     */
    private SimpleIntegerProperty idLangue = new SimpleIntegerProperty();
    private SimpleStringProperty libelle = new SimpleStringProperty();

    /**
     * CONSTRUCTS
     */

    /**
     * GETTERS
     */

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "IDLANGUE")
    public int getIdLangue(){return idLangue.get();}

    @Column(name = "LIBELLE")
    public String getLibelle(){return libelle.get();}

    /**
     * SETTERS
     */
    public void setIdLangue(Integer id){idLangue.set(id);}
    public void setLibelle(String lib){libelle.set(lib);}

    /**
     * METHODS
     */
    @Override
    public String toString(){
        return libelle.get();
    }

    public Boolean equals(Langue l){
        return (this.getLibelle().equals(l.getLibelle()));
    }
}