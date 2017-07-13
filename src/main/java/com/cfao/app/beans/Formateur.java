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

public class Formateur {
    private ObjectProperty<Personnel> personnel = new SimpleObjectProperty<>();
    private ObjectProperty<Formation> formation = new SimpleObjectProperty<>();

    public Formateur(ObjectProperty<Personnel> personnel, ObjectProperty<Formation> formation) {
        this.personnel = personnel;
        this.formation = formation;
    }
    public Formateur(){}

    public Personnel getPersonnel() {
        return personnel.get();
    }

    public ObjectProperty<Personnel> personnelProperty() {
        return personnel;
    }

    public void setPersonnel(Personnel personnel) {
        this.personnel.set(personnel);
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
        if(obj instanceof Formateur){
            Formateur formateur = (Formateur)obj;
            if(formateur.getFormation().getIdformation() == this.getFormation().getIdformation() && formateur.getPersonnel().getIdpersonnel() == this.getPersonnel().getIdpersonnel()){
                return true;
            }
        }
        return false;
    }
}
