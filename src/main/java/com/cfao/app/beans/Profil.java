package com.cfao.app.beans;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.ProgressBar;

/**
 * Created by JP on 6/11/2017.
 */
public class Profil {
    private SimpleIntegerProperty idprofil = new SimpleIntegerProperty();
    private SimpleStringProperty abbreviation = new SimpleStringProperty();
    private SimpleStringProperty libelle = new SimpleStringProperty();

    public Profil(int idprofil, String abbreviation, String libelle) {
        this.idprofil.set(idprofil);
        this.abbreviation.set(abbreviation);
        this.libelle.set(libelle);
    }
    public Profil(){}

    public void setIdprofil(int idprofil) {
        this.idprofil.set(idprofil);
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation.set(abbreviation);
    }

    public void setLibelle(String libelle) {
        this.libelle.set(libelle);
    }

    public int getIdprofil() {
        return idprofil.get();
    }


    public String getAbbreviation() {
        return abbreviation.get();
    }


    public String getLibelle() {
        return libelle.get();
    }

    @Override
    public String toString() {
        return  getLibelle();
    }
}
