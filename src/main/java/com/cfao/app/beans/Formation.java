package com.cfao.app.beans;

import javafx.beans.property.*;
import javafx.collections.FXCollections;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * Created by JP on 6/21/2017.
 */
@Entity
@Table(name = "formations")
public class Formation {
    private SimpleIntegerProperty idformation = new SimpleIntegerProperty();
    private SimpleStringProperty codeformation = new SimpleStringProperty();
    private ObjectProperty<Modele> modele = new SimpleObjectProperty<>();
    private SimpleStringProperty titre = new SimpleStringProperty();
    private SimpleStringProperty description = new SimpleStringProperty();
    private ObjectProperty<EtatFormation> etatFormation = new SimpleObjectProperty<>();
    private ObjectProperty<Typeformation> typeFormation = new SimpleObjectProperty<>();
    private SimpleObjectProperty<Date> datedebut = new SimpleObjectProperty<>();
    private SimpleObjectProperty<Date> datefin = new SimpleObjectProperty<>();

    private ListProperty<Personnel> personnels = new SimpleListProperty<>();
    private ListProperty<SupportFormation> supportFormations = new SimpleListProperty<>();
    private ListProperty<Competence> competences = new SimpleListProperty<>();
    //private SetProperty<Competence> prerequis = new SimpleSetProperty<>();

    //Les deux font la meme chose
    private ListProperty<Personne> personnes = new SimpleListProperty<>();
    private ListProperty<FormationPersonne> formationPersonnes = new SimpleListProperty<>();

    public Formation(){}
    public Formation(int idformation, String codeformation, Modele modele, String titre,
                     String description, Date datedebut,
                     Date datefin) {
        this.idformation.set(idformation);
        this.codeformation.set(codeformation);
        this.modele.set(modele);
        this.titre.set(titre);
        this.description.set(description);
        this.datedebut.set(datedebut);
        this.datefin.set(datefin);
    }

    @Id @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="IDFORMATION", unique=true, nullable=false)
    public Integer getIdformation() {
        return this.idformation.get();
    }

    public void setIdformation(Integer idformation) {
        this.idformation.set(idformation);
    }

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="ETATFORMATION")
    public EtatFormation getEtatFormation() {
        return this.etatFormation.get();
    }

    public void setEtatFormation(EtatFormation etatFormation) {
        this.etatFormation.set(etatFormation);
    }

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="MODELE")
    public Modele getModele() {
        return this.modele.get();
    }

    public void setModele(Modele modele) {
        this.modele.set(modele);
    }

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="TYPEFORMATION")
    public Typeformation getTypeFormation() {
        return this.typeFormation.get();
    }

    public void setTypeFormation(Typeformation typeFormation) {
        this.typeFormation.set(typeFormation);
    }


    @Column(name="CODEFORMATION", nullable=false, length=15)
    public String getCodeformation() {
        return this.codeformation.get();
    }

    public void setCodeformation(String codeformation) {
        this.codeformation.set(codeformation);
    }


    @Column(name="TITRE", nullable=false, length=150)
    public String getTitre() {
        return this.titre.get();
    }

    public void setTitre(String titre) {
        this.titre.set(titre);
    }


    @Column(name="DESCRIPTION", length=65535)
    public String getDescription() {
        return this.description.get();
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    @Temporal(TemporalType.DATE)
    @Column(name="DATEDEBUT", length=10)
    public Date getDatedebut() {
        return this.datedebut.get();
    }

    public void setDatedebut(Date datedebut) {
        this.datedebut.set(datedebut);
    }

    @Temporal(TemporalType.DATE)
    @Column(name="DATEFIN", length=10)
    public Date getDatefin() {
        return this.datefin.get();
    }

    public void setDatefin(Date datefin) {
        this.datefin.set(datefin);
    }

    @ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(name="formateurs",  joinColumns = {
            @JoinColumn(name="FORMATION", nullable=false, updatable=false) }, inverseJoinColumns = {
            @JoinColumn(name="PERSONNEL", nullable=false, updatable=false) })
    public List<Personnel> getPersonnels() {
        return this.personnels;
    }

    public void setPersonnels(List<Personnel> personnels) {
        if(personnels != null) {
            this.personnels.set(FXCollections.observableArrayList(personnels));
        }
    }

    @OneToMany(fetch=FetchType.LAZY, mappedBy="formation")
    public List<SupportFormation> getSupportFormations() {
        return this.supportFormations.get();
    }

    public void setSupportFormations(List<SupportFormation> supportFormations) {
        if(supportFormations != null) {
            this.supportFormations.set(FXCollections.observableArrayList(supportFormations));
        }
    }

    /*@ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(name="prerequis", catalog="servicepro", joinColumns = {
            @JoinColumn(name="FORMATION", nullable=false, updatable=false) }, inverseJoinColumns = {
            @JoinColumn(name="COMPETENCE", nullable=false, updatable=false) })
    public Set<Competence> getCompetenceses() {
        return this.competenceses;
    }

    public void setCompetence(Set<Competences> competenceses) {
        this.competenceses = competenceses;
    }
    */

    @ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(name="formation_competence", joinColumns = {
            @JoinColumn(name="FORMATION", nullable=false, updatable=false) }, inverseJoinColumns = {
            @JoinColumn(name="COMPETENCE", nullable=false, updatable=false) })
    public List<Competence> getCompetences() {
        return this.competences.get();
    }

    public void setCompetences(List<Competence> competences) {
        if(competences != null) {
            this.competences.set(FXCollections.observableList(competences));
        }
    }

    @ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(name="formation_personne", joinColumns = {
            @JoinColumn(name="FORMATION", nullable=false, updatable=false) }, inverseJoinColumns = {
            @JoinColumn(name="PERSONNE", nullable=false, updatable=false) })
    public List<Personne> getPersonnes() {
        return this.personnes;
    }

    public void setPersonnes(List<Personne> personnes) {
        this.personnes.set(FXCollections.observableArrayList(personnes));
    }


    public SimpleStringProperty titreProperty() {
        return titre;
    }

    public SimpleStringProperty codeformationProperty(){
        return codeformation;
    }
    public ObjectProperty<LocalDate> datedebutProperty() {
        return new SimpleObjectProperty<>(new java.sql.Date(datedebut.get().getTime()).toLocalDate());

    }

    public ObjectProperty<LocalDate> datefinProperty() {
        return new SimpleObjectProperty<>(new java.sql.Date(datefin.get().getTime()).toLocalDate());
    }


    @Override
    public String toString() {
        return getTitre();
    }

    @OneToMany(fetch=FetchType.LAZY, mappedBy="formation")
    public List<FormationPersonne> getFormationPersonnes() {
        return formationPersonnes.get();
    }

    public ListProperty<FormationPersonne> formationPersonnesProperty() {
        return formationPersonnes;
    }

    public void setFormationPersonnes(List<FormationPersonne> formationPersonnes) {
        this.formationPersonnes.set(FXCollections.observableArrayList(formationPersonnes));
    }
}


