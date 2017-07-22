package com.cfao.app.beans;

/**
 * Created by JP on 7/21/2017.
 */

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by JP on 7/6/2017.
 */

@Entity
@Table(name = "formation_competence")
@AssociationOverrides({
        @AssociationOverride(name = "pk.formation", joinColumns = @JoinColumn(name = "FORMATION")),
        @AssociationOverride(name = "pk.competence", joinColumns = @JoinColumn(name = "COMPETENCE"))})
public class Formationcompetence implements Serializable{
   private FormationcompetenceId pk = new FormationcompetenceId();

    @EmbeddedId
   public FormationcompetenceId getPk(){
       return pk;
   }
    public Formationcompetence(){}

    @Transient
    public Competence getCompetence() {
        return pk.getCompetence();
    }

    public void setCompetence(Competence competence) {
        this.pk.setCompetence(competence);
    }


    @Transient
    public Formation getFormation() {
        return pk.getFormation();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Formationcompetence that = (Formationcompetence) o;

        return pk != null ? pk.equals(that.pk) : that.pk == null;
    }

    @Override
    public int hashCode() {
        return pk != null ? pk.hashCode() : 0;
    }

    public void setFormation(Formation formation) {
        this.pk.setFormation(formation);
    }
}
