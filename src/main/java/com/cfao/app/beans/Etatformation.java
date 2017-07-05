package com.cfao.app.beans;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by JP on 7/2/2017.
 */
public class Etatformation {
    private SimpleIntegerProperty idetatformation = new SimpleIntegerProperty();
    private SimpleStringProperty libelle = new SimpleStringProperty();

    public Etatformation(SimpleIntegerProperty idetatformation, SimpleStringProperty libelle) {
        this.idetatformation = idetatformation;
        this.libelle = libelle;
    }

    @Override
    public String toString() {
        return getLibelle();
    }

    public int getIdetatformation() {
        return idetatformation.get();
    }

    public SimpleIntegerProperty idetatformationProperty() {
        return idetatformation;
    }

    public void setIdetatformation(int idetatformation) {
        this.idetatformation.set(idetatformation);
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

    public Etatformation(){

    }
}
