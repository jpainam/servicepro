package com.cfao.app.beans;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import javafx.scene.control.ProgressBar;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by JP on 6/11/2017.
 */
@Entity
@Table(name = "profils")
public class Profil implements  Serializable{
    private static final long serialVersionUID = 1L;
    private SimpleIntegerProperty idprofil = new SimpleIntegerProperty();
    private SimpleStringProperty abbreviation = new SimpleStringProperty();
    private SimpleStringProperty libelle = new SimpleStringProperty();
    //private ListProperty<ProfilPersonne> profilpersonne = new SimpleListProperty<ProfilPersonne>();
    private SetProperty<ProfilPersonne> profilPersonnes = new SimpleSetProperty<>();
    private ListProperty<Profilcompetence> profilcompetences = new SimpleListProperty<>();

   // private SetProperty<Competence> competences = new SimpleSetProperty<>();

   /* public Profil(SimpleIntegerProperty idprofil, SimpleStringProperty abbreviation, SimpleStringProperty libelle, SetProperty<ProfilPersonne> profilPersonnes, SetProperty<Competence> competences) {
        this.idprofil = idprofil;
        this.abbreviation = abbreviation;
        this.libelle = libelle;
        this.profilPersonnes = profilPersonnes;
        this.competences = competences;
    }*/

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
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    /*@ManyToMany()
    @JoinTable(name = "profil_competence", joinColumns = {@JoinColumn(name = "PROFIL")},
            inverseJoinColumns = {@JoinColumn(name = "COMPETENCE")})
    public Set<Competence> getCompetences() {
        return competences.get();
    }

    public SetProperty<Competence> competencesProperty() {
        return competences;
    }
    */
    @OneToMany(mappedBy = "pk.profil",  cascade=CascadeType.ALL)
    public Set<ProfilPersonne> getProfilPersonnes() {
        return this.profilPersonnes;
    }

    public void setProfilPersonnes(Set<ProfilPersonne> profilPersonnes) {
        this.profilPersonnes.set(FXCollections.observableSet(profilPersonnes));
    }
    /*
    public void setCompetences(Set<Competence> competences) {
        this.competences.set(FXCollections.observableSet(competences));
    }
    */
    @OneToMany(mappedBy = "pk.profil", cascade=CascadeType.ALL)
    public List<Profilcompetence> getProfilcompetences() {
        return profilcompetences.get();
    }

    public ListProperty<Profilcompetence> profilcompetencesProperty() {
        return profilcompetences;
    }

    public void setProfilcompetences(List<Profilcompetence> profilcompetences) {
        this.profilcompetences.set(FXCollections.observableList(profilcompetences));
    }

    @Transient
    public List<Competence> getCompetences(Niveau niveau){
        List<Competence> competenceList = new ArrayList<>();
        if(!this.getProfilcompetences().isEmpty()){
            for(Profilcompetence pc : this.getProfilcompetences()){
                if(pc.getNiveau().equals(niveau)){
                    competenceList.add(pc.getCompetence());
                }
            }
        }
        return competenceList;
    }
}
