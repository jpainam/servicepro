package com.cfao.app.beans;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;

/**
 * Created by JP on 7/19/2017.
 */
@Embeddable
public class ProfilCompetenceId implements Serializable {
    private Competence competence;
    private Profil profil; // type corresponds to Profil's id
    private Niveau niveau;


    @ManyToOne
    public Competence getCompetence() {
        return competence;
    }

    public void setCompetence(Competence competence) {
        this.competence = competence;
    }

    @ManyToOne
    public Profil getProfil() {
        return profil;
    }

    public void setProfil(Profil profil) {
        this.profil = profil;
    }

    @ManyToOne
    public Niveau getNiveau() {
        return niveau;
    }

    public void setNiveau(Niveau niveau) {
        this.niveau = niveau;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProfilCompetenceId that = (ProfilCompetenceId) o;

        if (competence != null ? !competence.equals(that.competence) : that.competence != null) return false;
        if (profil != null ? !profil.equals(that.profil) : that.profil != null) return false;
        return niveau != null ? niveau.equals(that.niveau) : that.niveau == null;
    }

    @Override
    public int hashCode() {
        int result = competence != null ? competence.hashCode() : 0;
        result = 31 * result + (profil != null ? profil.hashCode() : 0);
        result = 31 * result + (niveau != null ? niveau.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ProfilCompetenceId{" +
                "competence=" + competence +
                ", profil=" + profil +
                ", niveau=" + niveau +
                '}';
    }

}
