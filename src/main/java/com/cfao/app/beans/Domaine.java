package com.cfao.app.beans;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import javax.persistence.*;

/**
 * Created by JP on 8/22/2017.
 */
@Entity
@Table(name = "domaines")
public class Domaine {
    private SimpleIntegerProperty iddomaine = new SimpleIntegerProperty();
    private SimpleStringProperty libelle = new SimpleStringProperty();

    public Domaine(SimpleIntegerProperty iddomaine, SimpleStringProperty libelle) {
        this.iddomaine = iddomaine;
        this.libelle = libelle;
    }

    public Domaine(SimpleStringProperty libelle) {
        this.libelle = libelle;
    }

    public Domaine(){

    }
    @Id
    @GeneratedValue
    @Column(name = "IDDOMAINE", unique=true, nullable=false)
    public int getIddomaine() {
        return iddomaine.get();
    }

    public SimpleIntegerProperty iddomaineProperty() {
        return iddomaine;
    }

    public void setIddomaine(int iddomaine) {
        this.iddomaine.set(iddomaine);
    }

    @Column(name = "LIBELLE", nullable=false, length=150)
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
