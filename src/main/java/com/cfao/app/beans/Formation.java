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
    private SimpleStringProperty remarque = new SimpleStringProperty();
    private SimpleIntegerProperty score = new SimpleIntegerProperty();
    private SimpleStringProperty tp = new SimpleStringProperty();

    // Les formateurs en tant que personnel
    private ListProperty<Personnel> personnels = new SimpleListProperty<>();
    // Societe formatrice
    private SimpleObjectProperty<SocieteFormatrice> societeFormatrice = new SimpleObjectProperty<>();
    private SimpleObjectProperty<LieuFormation> lieuFormation = new SimpleObjectProperty<>();
    private ListProperty<SupportFormation> supportFormations = new SimpleListProperty<>();
    private ListProperty<Competence> competences = new SimpleListProperty<>();
    //private SetProperty<Competence> prerequis = new SimpleSetProperty<>();

    //Les deux font la meme chose
    private ListProperty<Personne> personnes = new SimpleListProperty<>();
    private ListProperty<FormationPersonne> formationPersonnes = new SimpleListProperty<>();
    private ListProperty<Planification> planifications = new SimpleListProperty<>();

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

    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
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
    @JoinTable(name="formation_personnel",  joinColumns = {
            @JoinColumn(name="FORMATION", nullable=false, updatable=false) }, inverseJoinColumns = {
            @JoinColumn(name="PERSONNEL", nullable=false, updatable=false) })
    public List<Personnel> getPersonnels() {
        return this.personnels.get();
    }

    public ListProperty<Personnel> personnelsProperty(){
        return personnels;
    }
    public void setPersonnels(List<Personnel> personnels) {
        if(personnels != null) {
            this.personnels.set(FXCollections.observableArrayList(personnels));
        }
    }

    @OneToMany(fetch=FetchType.LAZY, mappedBy="formation", cascade = CascadeType.ALL)
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
    public ListProperty<Competence> competencesProperty(){
        return competences;
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
        if(formationPersonnes != null) {
            this.formationPersonnes.set(FXCollections.observableArrayList(formationPersonnes));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Formation that = (Formation) o;
        if(this.idformation.equals(that.idformation)){
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = idformation != null ? idformation.hashCode() : 0;
        result = 31 * result + (titre != null ? titre.hashCode() : 0);
        return result;
    }

    @OneToMany(fetch=FetchType.LAZY, mappedBy="formation", cascade = CascadeType.ALL)
    @OrderBy("timing ASC")
    public List<Planification> getPlanifications() {
        return planifications.get();
    }

    public ListProperty<Planification> planificationsProperty() {
        return planifications;
    }

    public void setPlanifications(List<Planification> planifications) {
        if(planifications != null) {
            this.planifications.set(FXCollections.observableArrayList(planifications));
        }
    }

    @Column(name = "REMARQUE")
    public String getRemarque() {
        return remarque.get();
    }

    public SimpleStringProperty remarqueProperty() {
        return remarque;
    }

    public void setRemarque(String remarque) {
        this.remarque.set(remarque);
    }

    @Column(name = "SCORE")
    public Integer getScore() {
        return score.get();
    }

    public SimpleIntegerProperty scoreProperty() {
        return score;
    }

    public void setScore(Integer score) {
        if(score != null) {
            this.score.set(score);
        }
    }

    @Column(name = "TP", length = 10)
    public String getTp() {
        return tp.get();
    }

    public SimpleStringProperty tpProperty() {
        return tp;
    }

    public void setTp(String tp) {
        if(tp != null) {
            this.tp.set(tp);
        }
    }

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="SOCIETEFORMATRICE")
    public SocieteFormatrice getSocieteFormatrice() {
        return societeFormatrice.get();
    }

    public SimpleObjectProperty<SocieteFormatrice> societeFormatriceProperty() {
        return societeFormatrice;
    }

    public void setSocieteFormatrice(SocieteFormatrice societeFormatrice) {
        this.societeFormatrice.set(societeFormatrice);
    }

    public ListProperty<SupportFormation> supportFormationsProperty() {
        return supportFormations;
    }

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="LIEUFORMATION")
    public LieuFormation getLieuFormation() {
        return lieuFormation.get();
    }

    public SimpleObjectProperty<LieuFormation> lieuFormationProperty() {
        return lieuFormation;
    }

    public void setLieuFormation(LieuFormation lieuFormation) {
        if(lieuFormation != null) {
            this.lieuFormation.set(lieuFormation);
        }
    }

    public SimpleStringProperty descriptionProperty() {
        return description;
    }
}


