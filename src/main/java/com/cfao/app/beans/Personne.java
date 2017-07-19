package com.cfao.app.beans;

import com.cfao.app.util.FormatDate;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

/**
 * Created by JP on 6/10/2017.
 */
@Entity
@Table(name = "personnes")
public class Personne {

    /**
     * FIELDS
     */
    private SimpleIntegerProperty idPersonne = new SimpleIntegerProperty();
    private SimpleStringProperty matricule = new SimpleStringProperty();
    private SimpleStringProperty nom = new SimpleStringProperty();
    private SimpleStringProperty prenom = new SimpleStringProperty();
    private ObjectProperty<LocalDate> naissance = new SimpleObjectProperty<LocalDate>();
    // Liste des langues parlees
    private ListProperty<Langue> langues = new SimpleListProperty<Langue>();
    private ObjectProperty<Pays> pays = new SimpleObjectProperty<Pays>();
    private ObjectProperty<Groupe> groupe = new SimpleObjectProperty<Groupe>();
    private ObjectProperty<Societe> societe = new SimpleObjectProperty<Societe>();
    private ObjectProperty<Section> section = new SimpleObjectProperty<Section>();

    private ObjectProperty<Contrat> contrat = new SimpleObjectProperty<Contrat>();
    private ObjectProperty<Ambition> ambition = new SimpleObjectProperty<>();
    // Langue principale
    private ObjectProperty<Langue> langue = new SimpleObjectProperty<>();

    private ListProperty<Profil> profils = new SimpleListProperty<>();
    private SetProperty<ProfilPersonne> profilPersonnes = new SimpleSetProperty<>();
    /**
     * CONSTRUCT
     */


    /**
     * GETTERS
     */
    @Id
    @GeneratedValue
    @Column(name = "IDPERSONNE")
    public int getIdPersonne() {
        return idPersonne.get();
    }

    @Column(name = "MATRICULE")
    public String getMatricule() {
        return matricule.get();
    }

    @Column(name = "NOM")
    public String getNom() {
        return nom.get();
    }

    @Column(name = "PRENOM")
    public String getPrenom() {
        return prenom.get();
    }

    @Column(name = "DATENAISS")
    public Date getNaissance() {
        return Date.valueOf(naissance.get());
    }

    @ManyToMany()
    @JoinTable(name = "langue_parlee", joinColumns = {@JoinColumn(name = "IDPERS")},
            inverseJoinColumns = {@JoinColumn(name = "IDLANGUE")})
    public java.util.List<Langue> getLangues() {
        return langues.get();
    }

    @ManyToOne()
    @JoinColumn(name = "PAYS")
    public Pays getPays() {
        return pays.get();
    }

    @ManyToOne()
    @JoinColumn(name = "GROUPE")
    public Groupe getGroupe() {
        return groupe.get();
    }

    @ManyToOne()
    @JoinColumn(name = "SOCIETE")
    public Societe getSociete() {
        return societe.get();
    }

    @ManyToOne()
    @JoinColumn(name = "SECTION")
    public Section getSection() {
        return section.get();
    }

    @ManyToOne()
    @JoinColumn(name = "CONTRAT")
    public Contrat getContrat() {
        return contrat.get();
    }

    @ManyToOne()
    @JoinColumn(name = "AMBITION")
    public Ambition getAmbition() {
        return ambition.get();
    }
    @ManyToOne()
    @JoinColumn(name="LANGUE")
    public Langue getLangue() {
        return langue.get();
    }
    public ObjectProperty<Contrat> contratProperty() {
        return contrat;
    }

    /*private ListProperty<ProfilPersonne> profilPersonne = new SimpleListProperty<ProfilPersonne>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.personne", cascade = CascadeType.ALL)
    public java.util.List<ProfilPersonne> getProfilPersonne() {
        return profilPersonne.get();
    }
    public void setProfilPersonne(java.util.List<ProfilPersonne> set){
        this.profilPersonne.set(FXCollections.observableList(set));
    }*/


    /**
     * SETTERS
     */
    public void setIdPersonne(int idPersonne) {
        this.idPersonne.set(idPersonne);
    }

    public void setMatricule(String matricule) {
        this.matricule.set(matricule);
    }

    public void setNom(String nom) {
        this.nom.set(nom);
    }

    public void setPrenom(String prenom) {
        this.prenom.set(prenom);
    }

    public void setNaissance(Date date) {
        this.naissance.set(date.toLocalDate());
    }

    public void setLangues(java.util.List<Langue> set) {
        this.langues.set(FXCollections.observableList(set));
    }

    public void setPays(Pays pays) {
        this.pays.set(pays);
    }

    public void setGroupe(Groupe groupe) {
        this.groupe.set(groupe);
    }

    public void setSociete(Societe societe) {
        this.societe.set(societe);
    }

    public void setSection(Section section) {
        this.section.set(section);
    }

    public void setContrat(Contrat contrat) {
        this.contrat.set(contrat);
    }

    public void setAmbition(Ambition ambition) {
        this.ambition.set(ambition);
    }

    /**
     * METHODS
     */
    @Override
    public String toString() {
        return getNom() + " " + getPrenom();
    }


    /**
     * GETTERS PROPERTY
     */
    public IntegerProperty idPersonneProperty() {
        return idPersonne;
    }

    public StringProperty matriculeProperty() {
        return matricule;
    }

    public StringProperty nomProperty() {
        return nom;
    }

    public StringProperty prenomProperty() {
        return prenom;
    }

    public ObjectProperty<LocalDate> naissance() {
        return naissance;
    }

    public ListProperty<Langue> langues() {
        return langues;
    }

    public ObjectProperty<Pays> pays() {
        return pays;
    }

    public ObjectProperty<Section> section() {
        return section;
    }

    public ObjectProperty<Societe> societe() {
        return societe;
    }

    public ObjectProperty<Groupe> groupe() {
        return groupe;
    }

    public ObjectProperty<Contrat> contrat() {
        return contrat;
    }

    public ObjectProperty<Ambition> ambitionProperty() {
        return ambition;
    }



    public ObjectProperty<Langue> langueProperty() {
        return langue;
    }

    public void setLangue(Langue langue) {
        this.langue.set(langue);
    }

    @ManyToMany()
    @JoinTable(name = "personne_profil", joinColumns = {@JoinColumn(name = "PERSONNE")},
            inverseJoinColumns = {@JoinColumn(name = "PROFIL")})
    public List<Profil> getProfils() {
        return profils.get();
    }

    public ListProperty<Profil> profilsProperty() {
        return profils;
    }

    public void setProfils(List<Profil> profils) {
        this.profils.set(FXCollections.observableArrayList(profils));
    }

    @OneToMany(mappedBy = "pk.personne", cascade=CascadeType.ALL)
    public Set<ProfilPersonne> getProfilPersonnes() {
        return profilPersonnes;
    }

    public SetProperty<ProfilPersonne> profilPersonnesProperty() {
        return profilPersonnes;
    }

    public void setProfilPersonnes(Set<ProfilPersonne> profilPersonnes) {
        this.profilPersonnes.set(FXCollections.observableSet(profilPersonnes));
    }
}