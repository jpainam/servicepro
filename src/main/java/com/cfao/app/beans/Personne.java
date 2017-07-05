package com.cfao.app.beans;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import org.hibernate.mapping.Set;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDateTime;

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
    private ObjectProperty<Date> naissance = new SimpleObjectProperty<Date>();
    private ObjectProperty<java.util.List<Langue>> langues = new SimpleObjectProperty<java.util.List<Langue>>();
    private Pays pays;
    private Groupe groupe;
    private Societe societe;
    private Section section;

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
    public Date getNaissance(){return naissance.get();}

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "langue_parlee", joinColumns = {@JoinColumn(name = "IDPERS")}, inverseJoinColumns = {@JoinColumn(name = "IDLANGUE")})
    public java.util.List<Langue> getLangues(){
        return langues.get();
    }

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public Pays getPays() {
        return pays;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    public Groupe getGroupe() {
        return groupe;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    public Societe getSociete() {
        return societe;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    public Section getSection() {
        return section;
    }


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

    public void setNaissance(Date date){this.naissance.set(date);}

    public void setLangues(java.util.List<Langue> set){
        this.langues.set(set);
    }

    public void setPays(Pays pays) {
        this.pays = pays;
    }

    public void setGroupe(Groupe groupe) {
        this.groupe = groupe;
    }

    public void setSociete(Societe societe) {
        this.societe = societe;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public SimpleIntegerProperty idPersonneProperty() {
        return idPersonne;
    }

    public SimpleStringProperty matriculeProperty() {
        return matricule;
    }

    public SimpleStringProperty nomProperty() {
        return nom;
    }

    public SimpleStringProperty prenomProperty() {
        return prenom;
    }

    /**
     * METHODS
     */
    @Override
    public String toString() {
        return "Personne{" +
                "idPersonne=" + idPersonne +
                ", matricule=" + matricule +
                ", nom=" + nom +
                ", prenom=" + prenom +
                '}';
    }



}