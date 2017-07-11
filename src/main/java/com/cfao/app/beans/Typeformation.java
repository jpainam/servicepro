package com.cfao.app.beans;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import javax.persistence.*;

/**
 * Created by JP on 7/10/2017.
 */
@Entity
@Table(name = "type_formation")
public class Typeformation {
    private SimpleIntegerProperty idtypeformation = new SimpleIntegerProperty();
    private SimpleStringProperty libelle = new SimpleStringProperty();

    public Typeformation(SimpleIntegerProperty idetatformation, SimpleStringProperty libelle) {
        this.idtypeformation = idetatformation;
        this.libelle = libelle;
    }

    @Override
    public String toString() {
        return getLibelle();
    }

    @Id
    @GeneratedValue
    @Column(name = "IDTYPEFORMATION")
    public int getIdetatformation() {
        return idtypeformation.get();
    }

    public SimpleIntegerProperty idetatformationProperty() {
        return idtypeformation;
    }

    public void setIdetatformation(int idetatformation) {
        this.idtypeformation.set(idetatformation);
    }

    @Column(name = "LIBELLE")
    public String getLibelle() {
        return libelle.get();
    }

    public SimpleStringProperty libelleProperty() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle.set(libelle);
    }

    public Typeformation(){
    }


}
