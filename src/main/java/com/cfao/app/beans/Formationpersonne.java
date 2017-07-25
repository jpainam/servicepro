package com.cfao.app.beans;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import javax.persistence.*;

/**
 * Created by JP on 7/6/2017.
 */

@Entity
@Table(name = "formation_personne")
@AssociationOverrides({
        @AssociationOverride(name = "pk.personne",
                joinColumns = @JoinColumn(name = "personne")),
        @AssociationOverride(name = "pk.formation",
                joinColumns = @JoinColumn(name = "formation")) })
public class Formationpersonne {
    private ObjectProperty<Personne> personne = new SimpleObjectProperty<>();
    private ObjectProperty<Formation> formation = new SimpleObjectProperty<>();

    public Formationpersonne(ObjectProperty<Personne> personne, ObjectProperty<Formation> formation) {
        this.personne = personne;
        this.formation = formation;
    }
    public Formationpersonne(){}

    @ManyToOne
    public Personne getPersonne() {
        return personne.get();
    }

    public ObjectProperty<Personne> personneProperty() {
        return personne;
    }

    public void setPersonnel(Personne personne) {
        this.personne.set(personne);
    }

    @ManyToOne
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
                    fp.getPersonne().getIdpersonne() == this.getPersonne().getIdpersonne()){
                return true;
            }
        }
        return false;
    }
}

