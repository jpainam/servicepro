package com.cfao.app.beans;

import javafx.beans.property.SimpleObjectProperty;

import java.io.Serializable;

/**
 * Created by JP on 6/29/2017.
 */
public class Profilcompetence implements Serializable {
    private SimpleObjectProperty<Competence> competence;
    private SimpleObjectProperty<Profil> profil;

    public Profilcompetence(SimpleObjectProperty<Competence> competence, SimpleObjectProperty<Profil> profil) {
        this.competence = competence;
        this.profil = profil;
    }

    public Competence getCompetence() {
        return competence.get();
    }

    public SimpleObjectProperty<Competence> competenceProperty() {
        return competence;
    }

    public void setCompetence(Competence competence) {
        this.competence.set(competence);
    }

    public Profil getProfil() {
        return profil.get();
    }

    public SimpleObjectProperty<Profil> profilProperty() {
        return profil;
    }

    public void setProfil(Profil profil) {
        this.profil.set(profil);
    }
}
