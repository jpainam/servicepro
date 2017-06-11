package com.cfao.app.beans;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by JP on 6/11/2017.
 */
public class Societe {
    private SimpleIntegerProperty idsociete = new SimpleIntegerProperty();
    private SimpleStringProperty nom = new SimpleStringProperty();
    private SimpleStringProperty adresse = new SimpleStringProperty();

    public Societe(int idsociete, String nom, String adresse) {
        this.idsociete.set(idsociete);
        this.nom.set(nom);
        this.adresse.set(adresse);
    }
    public Societe(){}

    public int getIdsociete() {
        return idsociete.get();
    }

    public String getNom() {
        return nom.get();
    }


    public String getAdresse() {
        return adresse.get();
    }

    public void setIdsociete(int idsociete) {
        this.idsociete.set(idsociete);
    }

    public void setNom(String nom) {
        this.nom.set(nom);
    }

    public void setAdresse(String adresse) {
        this.adresse.set(adresse);
    }

    @Override
    public String toString() {
        return  getNom();
    }
}
