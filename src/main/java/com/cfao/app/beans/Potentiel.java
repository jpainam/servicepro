package com.cfao.app.beans;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import javax.persistence.*;


/**
 * Created by JP on 6/11/2017.
 */
@Entity
@Table(name = "potentiels")
public class Potentiel {

    private SimpleIntegerProperty idpotentiel = new SimpleIntegerProperty();
    private SimpleStringProperty libelle = new SimpleStringProperty();

    public Potentiel(int idpotentiel, String libelle) {
        this.idpotentiel.set(idpotentiel);
        this.libelle.set(libelle);
    }

    public Potentiel(SimpleIntegerProperty idpotentiel, SimpleStringProperty libelle) {
        this.idpotentiel = idpotentiel;
        this.libelle = libelle;
    }

    public Potentiel(){}

    @Id
    @GeneratedValue
    @Column(name = "IDPOTENTIEL")
    public int getIdpotentiel() {
        return idpotentiel.get();
    }

    @Column(name = "LIBELLE")
    public String getLibelle() {
        return libelle.get();
    }

    public void setIdpotentiel(int idpotentiel) {
        this.idpotentiel.set(idpotentiel);
    }

    public void setLibelle(String libelle) {
        this.libelle.set(libelle);
    }

    @Override
    public String toString() {
        return getLibelle();
    }

    public SimpleIntegerProperty idpotentielProperty() {
        return idpotentiel;
    }

    public SimpleStringProperty libelleProperty() {
        return libelle;
    }
}