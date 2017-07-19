package com.cfao.app.beans;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import javafx.scene.control.ProgressBar;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * Created by JP on 6/11/2017.
 */
@Entity
@Table(name = "profils")
public class Profil{
    private SimpleIntegerProperty idprofil = new SimpleIntegerProperty();
    private SimpleStringProperty abbreviation = new SimpleStringProperty();
    private SimpleStringProperty libelle = new SimpleStringProperty();
    //private ListProperty<ProfilPersonne> profilpersonne = new SimpleListProperty<ProfilPersonne>();
    private SetProperty<ProfilPersonne> profilPersonnes = new SimpleSetProperty<>();

    private SetProperty<Competence> competences = new SimpleSetProperty<>();

    public Profil(SimpleIntegerProperty idprofil, SimpleStringProperty abbreviation, SimpleStringProperty libelle, SetProperty<ProfilPersonne> profilPersonnes, SetProperty<Competence> competences) {
        this.idprofil = idprofil;
        this.abbreviation = abbreviation;
        this.libelle = libelle;
        this.profilPersonnes = profilPersonnes;
        this.competences = competences;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @OneToMany(mappedBy = "pk.profil")
    public Set<ProfilPersonne> getProfilPersonnes() {
        return this.profilPersonnes;
    }

    public void setProfilPersonnes(Set<ProfilPersonne> profilPersonnes) {
        this.profilPersonnes.set(FXCollections.observableSet(profilPersonnes));
    }

    public void setCompetences(Set<Competence> competences) {
        this.competences.set(FXCollections.observableSet(competences));
    }
}
