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
}
