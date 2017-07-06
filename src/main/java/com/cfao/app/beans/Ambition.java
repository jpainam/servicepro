package com.cfao.app.beans;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import javax.persistence.*;


/**
 * Created by JP on 6/11/2017.
 */
@Entity
@Table(name = "ambitions")
public class Ambition {

    private SimpleIntegerProperty idambition = new SimpleIntegerProperty();
    private SimpleStringProperty libelle = new SimpleStringProperty();

    public Ambition(int idambition, String libelle) {
        this.idambition.set(idambition);
        this.libelle.set(libelle);
    }

    public Ambition(SimpleIntegerProperty idambition, SimpleStringProperty libelle) {
        this.idambition = idambition;
        this.libelle = libelle;
    }

    public Ambition(){}

    @Id
    @GeneratedValue
    @Column(name = "IDAMBITION")
    public int getIdambition() {
        return idambition.get();
    }

    @Column(name = "LIBELLE")
    public String getLibelle() {
        return libelle.get();
    }

    public void setIdambition(int idambition) {
        this.idambition.set(idambition);
    }

    public void setLibelle(String libelle) {
        this.libelle.set(libelle);
    }

    @Override
    public String toString() {
        return getLibelle();
    }

    public SimpleIntegerProperty idambitionProperty() {
        return idambition;
    }

    public SimpleStringProperty libelleProperty() {
        return libelle;
    }
}