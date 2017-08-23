package com.cfao.app.beans;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Created by JP on 8/14/2017.
 */
@Embeddable
public class FormationCompetenceId implements Serializable {

    private Integer formation;
    private Integer competence;

    @Column(name="COMPETENCE", nullable=false)
    public Integer getCompetence() {
        return competence;
    }

    public void setCompetence(Integer competence) {
        this.competence = competence;
    }

    @Column(name="FORMATION", nullable=false)
    public Integer getFormation() {
        return formation;
    }

    public void setFormation(Integer formation) {
        this.formation = formation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FormationCompetenceId that = (FormationCompetenceId) o;
        if(that.getCompetence().equals(this.getCompetence()) && that.getFormation().equals(this.getFormation())){
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = getFormation() != null ? getFormation().hashCode() : 0;
        result = 31 * result + (competence != null ? competence.hashCode() : 0);
        return result;
    }
}
