package com.cfao.app.beans;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by JP on 6/11/2017.
 */
public class Niveauetude {
    private SimpleIntegerProperty idniveauetude = new SimpleIntegerProperty();
    private SimpleStringProperty libelle = new SimpleStringProperty();

    public Niveauetude(int idniveauetude, String libelle) {
        this.idniveauetude.set(idniveauetude);
        this.libelle.set(libelle);
    }
    public Niveauetude(){}

    public int getIdniveauetude() {
        return idniveauetude.get();
    }


    public void setIdniveauetude(int idniveauetude) {
        this.idniveauetude.set(idniveauetude);
    }

    public String getLibelle() {
        return libelle.get();
    }

    public void setLibelle(String libelle) {
        this.libelle.set(libelle);
    }

    public SimpleIntegerProperty idniveauetudeProperty() {
        return idniveauetude;
    }

    public SimpleStringProperty libelleProperty() {
        return libelle;
    }
}
