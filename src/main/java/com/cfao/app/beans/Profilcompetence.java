package com.cfao.app.beans;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableSet;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by JP on 6/29/2017.
 */

@Entity
@Table(name = "profil_competence")
@AssociationOverrides({
        @AssociationOverride(name = "pk.profil", joinColumns = @JoinColumn(name = "PROFIL")),
        @AssociationOverride(name = "pk.competence", joinColumns = @JoinColumn(name = "COMPETENCE")),
        @AssociationOverride(name = "pk.niveau", joinColumns = @JoinColumn(name = "NIVEAU"))  })
public class Profilcompetence implements Serializable {

    private ProfilcompetenceId pk = new ProfilcompetenceId();

    public Profilcompetence() {
    }
   public Profilcompetence(Profil profil, Competence competence, Niveau niveau){
        this.pk.setProfil(profil);
        this.pk.setCompetence(competence);
        this.pk.setNiveau(niveau);
    }


    @EmbeddedId
    public ProfilcompetenceId getPk() {
        return pk;
    }

    public void setPk(ProfilcompetenceId pk) {
        this.pk = pk;
    }

    @Transient
    public Competence getCompetence() {
        return getPk().getCompetence();
    }

    public void setCompetence(Competence competence) {
        getPk().setCompetence(competence);
    }

    @Transient
    public Niveau getNiveau() {
        return getPk().getNiveau();
    }

    public void setNiveau(Niveau niveau) {
        getPk().setNiveau(niveau);
    }

    @Transient
    public Profil getProfil() {
        return getPk().getProfil();
    }

    public void setProfil(Profil profil) {
        getPk().setProfil(profil);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Profilcompetence that = (Profilcompetence) o;

        if (getPk() != null ? !getPk().equals(that.getPk())
                : that.getPk() != null)
            return false;
        return true;
    }


    @Override
    public String toString() {
        return "Profilcompetence{" +
                "pk=" + pk +
                '}';
    }

    @Override
    public int hashCode() {
        return pk != null ? pk.hashCode() : 0;
    }

/*
    @ManyToOne
    @MapsId("profilId") // maps profil Id attribute of the embedded id
    public Profil getProfil() {
        return profil;
    }

    public void setProfil(Profil profil) {
        this.profil = profil;
    }

    @ManyToOne
    @MapsId("niveauId") // maps niveau Id attribute of the embedded id
    public Niveau getNiveau() {
        return niveau;
    }

    public void setNiveau(Niveau niveau) {
        this.niveau = niveau;
    }
    @ManyToOne
    @MapsId("competenceId") // maps competence Id attribute of the embedded id
    public Competence getCompetence() {
        return competence;
    }

    public void setCompetence(Competence competence) {
        this.competence = competence;
    }
    */
}
