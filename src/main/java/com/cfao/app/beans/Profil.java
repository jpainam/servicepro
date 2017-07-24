package com.cfao.app.beans;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
    private ListProperty<ProfilPersonne> profilPersonnes = new SimpleListProperty<>();
    private ListProperty<ProfilCompetence> profilCompetences = new SimpleListProperty<>();

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
    @OneToMany(mappedBy = "pk.profil")
    @Cascade({CascadeType.PERSIST, CascadeType.DELETE})
    public List<ProfilPersonne> getProfilPersonnes() {
        return this.profilPersonnes;
    }

    public void setProfilPersonnes(List<ProfilPersonne> profilPersonnes) {
        this.profilPersonnes.set(FXCollections.observableList(profilPersonnes));
    }
    /*
    public void setCompetences(Set<Competence> competences) {
        this.competences.set(FXCollections.observableSet(competences));
    }
    */
    @OneToMany(mappedBy = "pk.profil")
    @Cascade({CascadeType.ALL})
    public List<ProfilCompetence> getProfilCompetences() {
        return profilCompetences.get();
    }

    public ListProperty<ProfilCompetence> profilCompetencesProperty() {
        return profilCompetences;
    }

    public void setProfilCompetences(List<ProfilCompetence> profilCompetences) {
        this.profilCompetences.set(FXCollections.observableList(profilCompetences));
    }

    @Transient
    public List<Competence> getCompetences(Niveau niveau){
        List<Competence> competenceList = new ArrayList<>();
        if(!this.getProfilCompetences().isEmpty()){
            for(ProfilCompetence pc : this.getProfilCompetences()){
                if(pc.getNiveau().equals(niveau)){
                    competenceList.add(pc.getCompetence());
                }
            }
        }
        return competenceList;
    }
}
