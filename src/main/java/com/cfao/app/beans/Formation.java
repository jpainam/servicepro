package com.cfao.app.beans;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by JP on 6/21/2017.
 */
public class Formation {
    private SimpleIntegerProperty idformation = new SimpleIntegerProperty();
    private SimpleStringProperty libelle = new SimpleStringProperty();

    public Formation(SimpleIntegerProperty idformation, SimpleStringProperty libelle) {
        this.idformation = idformation;
        this.libelle = libelle;
    }
    public Formation(int idformation, String libelle) {
        this.idformation.set(idformation);
        this.libelle.set(libelle);
    }

    public Formation(){}

    public int getIdformation() {
        return idformation.get();
    }

    public SimpleIntegerProperty idformationProperty() {
        return idformation;
    }

    public void setIdformation(int idformation) {
        this.idformation.set(idformation);
    }

    public String getLibelle() {
        return libelle.get();
    }

    public SimpleStringProperty libelleProperty() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle.set(libelle);
    }

    @Override
    public String toString() {
        return getLibelle();
    }
}
