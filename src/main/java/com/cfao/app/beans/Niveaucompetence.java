package com.cfao.app.beans;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by JP on 6/14/2017.
 */
public class Niveaucompetence {
    private SimpleIntegerProperty idniveaucompetence = new SimpleIntegerProperty();
    private SimpleStringProperty libelle = new SimpleStringProperty();

    public Niveaucompetence(int idniveaucompetence, String libelle) {
        this.idniveaucompetence.set(idniveaucompetence);
        this.libelle.set(libelle);
    }
    public Niveaucompetence(){}

    @Override
    public String toString() {
        return getLibelle();
    }

    public int getIdniveaucompetence() {
        return idniveaucompetence.get();
    }


    public void setIdniveaucompetence(int idniveaucompetence) {
        this.idniveaucompetence.set(idniveaucompetence);
    }

    public String getLibelle() {
        return libelle.get();
    }


    public void setLibelle(String libelle) {
        this.libelle.set(libelle);
    }

    public SimpleIntegerProperty idniveaucompetenceProperty() {
        return idniveaucompetence;
    }

    public SimpleStringProperty libelleProperty() {
        return libelle;
    }
}
