package com.cfao.app.beans;

import javafx.beans.property.SetProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleSetProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import javafx.scene.control.ProgressBar;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by JP on 6/11/2017.
 */
@Entity
@Table(name = "profils")
public class Profil {
    private SimpleIntegerProperty idprofil = new SimpleIntegerProperty();
    private SimpleStringProperty abbreviation = new SimpleStringProperty();
    private SimpleStringProperty libelle = new SimpleStringProperty();

    private SetProperty<Competence> competences = new SimpleSetProperty<>();

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

    @Id
    @GeneratedValue
    @Column(name = "IDPROFIL")
    public int getIdprofil() {
        return idprofil.get();
    }


    @Column(name = "ABBREVIATION")
    public String getAbbreviation() {
        return abbreviation.get();
    }


    @Column(name = "LIBELLE")
    public String getLibelle() {
        return libelle.get();
    }

    public SimpleIntegerProperty idprofilProperty() {
        return idprofil;
    }

    public SimpleStringProperty abbreviationProperty() {
        return abbreviation;
    }

    public SimpleStringProperty libelleProperty() {
        return libelle;
    }

    @Override
    public String toString() {
        return  getLibelle();
    }

    @ManyToMany()
    @JoinTable(name = "profil_competence", joinColumns = {@JoinColumn(name = "PROFIL")},
            inverseJoinColumns = {@JoinColumn(name = "COMPETENCE")})
    public Set<Competence> getCompetences() {
        return competences.get();
    }

    public SetProperty<Competence> competencesProperty() {
        return competences;
    }


    public void setCompetences(Set<Competence> competences) {
        this.competences.set(FXCollections.observableSet(competences));
    }
}
