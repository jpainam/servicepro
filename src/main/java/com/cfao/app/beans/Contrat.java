package com.cfao.app.beans;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import javax.persistence.*;



/**
 * Created by JP on 6/11/2017.
 */
@Entity
@Table(name = "contrats")
public class Contrat {

    private SimpleIntegerProperty idcontrat = new SimpleIntegerProperty();
    private SimpleStringProperty libelle = new SimpleStringProperty();

    public Contrat(int idcontrat, String libelle) {
        this.idcontrat.set(idcontrat);
        this.libelle.set(libelle);
    }

    public Contrat(SimpleIntegerProperty idcontrat, SimpleStringProperty libelle) {
        this.idcontrat = idcontrat;
        this.libelle = libelle;
    }

    public Contrat(){}

    @Id
    @GeneratedValue
    @Column(name = "IDCONTRAT")
    public int getIdcontrat() {
        return idcontrat.get();
    }

    @Column(name = "LIBELLE")
    public String getLibelle() {
        return libelle.get();
    }

    public void setIdcontrat(int idcontrat) {
        this.idcontrat.set(idcontrat);
    }

    public void setLibelle(String libelle) {
        this.libelle.set(libelle);
    }

    @Override
    public String toString() {
        return getLibelle();
    }

    public SimpleIntegerProperty idcontratProperty() {
        return idcontrat;
    }

    public SimpleStringProperty libelleProperty() {
        return libelle;
    }
}