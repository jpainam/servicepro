package com.cfao.app.beans;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.io.Serializable;

/**
 * Created by JP on 7/6/2017.
 */

public class FormationPersonnel implements Serializable{
    private ObjectProperty<Personnel> personnel = new SimpleObjectProperty<>();
    private ObjectProperty<Formation> formation = new SimpleObjectProperty<>();

    public FormationPersonnel(ObjectProperty<Personnel> personnel, ObjectProperty<Formation> formation) {
        this.personnel = personnel;
        this.formation = formation;
    }
    public FormationPersonnel(){}

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
        if(obj instanceof FormationPersonnel){
            FormationPersonnel formationPersonnel = (FormationPersonnel)obj;
            if(formationPersonnel.getFormation().getIdformation() == this.getFormation().getIdformation() && formationPersonnel.getPersonnel().getIdpersonnel() == this.getPersonnel().getIdpersonnel()){
                return true;
            }
        }
        return false;
    }
}

