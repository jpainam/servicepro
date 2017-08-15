package com.cfao.app.beans;

import javafx.beans.property.SimpleObjectProperty;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by JP on 8/14/2017.
 */
@Entity
@Table(name = "formation_competence")
public class FormationCompetence implements Serializable {
    private FormationCompetenceId id;
    private SimpleObjectProperty<Formation> formation = new SimpleObjectProperty<>();
    private SimpleObjectProperty<Competence> competence = new SimpleObjectProperty<>();

    @EmbeddedId


    @AttributeOverrides( {
            @AttributeOverride(name="formation", column=@Column(name="FORMATION", nullable=false) ),
            @AttributeOverride(name="competence", column=@Column(name="COMPETENCE", nullable=false) ) } )
    public FormationCompetenceId getId() {
        return id;
    }

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="FORMATION", nullable=false, insertable=false, updatable=false)
    public Formation getFormation() {
        return formation.get();
    }

    public SimpleObjectProperty<Formation> formationProperty() {
        return formation;
    }

    public void setFormation(Formation formation) {
        this.formation.set(formation);
    }

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="COMPETENCE", nullable=false, insertable=false, updatable=false)
    public Competence getCompetence() {
        return competence.get();
    }

    public SimpleObjectProperty<Competence> competenceProperty() {
        return competence;
    }

    public void setCompetence(Competence competence) {
        this.competence.set(competence);
    }


    public void setId(FormationCompetenceId id) {
        this.id = id;
    }
}
