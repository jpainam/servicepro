package com.cfao.app.beans;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by JP on 6/10/2017.
 */
public class Personne {

    private SimpleIntegerProperty idPersonne = new SimpleIntegerProperty();
    private SimpleStringProperty matricule = new SimpleStringProperty();
    private SimpleStringProperty nom = new SimpleStringProperty();
    private SimpleStringProperty prenom = new SimpleStringProperty();
    private Pays pays;
    private Groupe groupe;
    private Societe societe;
    private Section section;

    public int getIdpersonne() {
        return idPersonne.get();
    }

    public String getMatricule() {
        return matricule.get();
    }


    public String getNom() {
        return nom.get();
    }


    public String getPrenom() {
        return prenom.get();
    }

    public void setIdpersonne(int idPersonne) {
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

    @Override
    public String toString() {
        return "Personne{" +
                "idPersonne=" + idPersonne +
                ", matricule=" + matricule +
                ", nom=" + nom +
                ", prenom=" + prenom +
                '}';
    }

    public Pays getPays() {
        return pays;
    }

    public void setPays(Pays pays) {
        this.pays = pays;
    }

    public Groupe getGroupe() {
        return groupe;
    }

    public void setGroupe(Groupe groupe) {
        this.groupe = groupe;
    }

    public Societe getSociete() {
        return societe;
    }

    public void setSociete(Societe societe) {
        this.societe = societe;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public int getIdPersonne() {
        return idPersonne.get();
    }

    public SimpleIntegerProperty idPersonneProperty() {
        return idPersonne;
    }

    public void setIdPersonne(int idPersonne) {
        this.idPersonne.set(idPersonne);
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
}
