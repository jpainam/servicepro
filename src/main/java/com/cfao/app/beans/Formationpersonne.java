package com.cfao.app.beans;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by JP on 7/6/2017.
 */

public class Formationpersonne {
    private ObjectProperty<Personne> personne = new SimpleObjectProperty<>();
    private ObjectProperty<Formation> formation = new SimpleObjectProperty<>();

    public Formationpersonne(ObjectProperty<Personne> personne, ObjectProperty<Formation> formation) {
        this.personne = personne;
        this.formation = formation;
    }
    public Formationpersonne(){}

    public Personne getPersonne() {
        return personne.get();
    }

    public ObjectProperty<Personne> personneProperty() {
        return personne;
    }

    public void setPersonnel(Personne personne) {
        this.personne.set(personne);
    }

    public Formation getFormation() {
        return formation.get();
    }

    public ObjectProperty<Formation> formationProperty() {
        return formation;
    }

    public void setFormation(Formation formation) {
        this.formation.set(formation);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Formationpersonne){
            Formationpersonne fp = (Formationpersonne)obj;
            if(fp.getFormation().getIdformation() == this.getFormation().getIdformation() &&
                    fp.getPersonne().getIdPersonne() == this.getPersonne().getIdPersonne()){
                return true;
            }
        }
        return false;
    }
}

