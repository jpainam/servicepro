package com.cfao.app.beans;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.text.Normalizer;

/**
 * Created by JP on 7/21/2017.
 */
@Embeddable
public class FormationcompetenceId implements Serializable {
    private Competence competence;
    private Formation formation;

    public FormationcompetenceId(){

    }

    @ManyToOne
    public Competence getCompetence() {
        return competence;
    }

    public void setCompetence(Competence competence) {
        this.competence = competence;
    }

    @ManyToOne
    public Formation getFormation() {
        return formation;
    }

    public void setFormation(Formation formation) {
        this.formation = formation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FormationcompetenceId that = (FormationcompetenceId) o;

        if (competence != null ? !competence.equals(that.competence) : that.competence != null) return false;
        return formation != null ? formation.equals(that.formation) : that.formation == null;
    }

    @Override
    public int hashCode() {
        int result = competence != null ? competence.hashCode() : 0;
        result = 31 * result + (formation != null ? formation.hashCode() : 0);
        return result;
    }
}
