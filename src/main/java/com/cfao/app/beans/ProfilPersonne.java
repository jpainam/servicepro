package com.cfao.app.beans;

import java.util.Date;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name = "profil_personne")
@AssociationOverrides({
        @AssociationOverride(name = "pk.personne", joinColumns = @JoinColumn(name = "PERSONNE")),
        @AssociationOverride(name = "pk.profil", joinColumns = @JoinColumn(name = "PROFIL")),
        @AssociationOverride(name = "pk.niveau", joinColumns = @JoinColumn(name = "NIVEAU"))  })
public class ProfilPersonne implements java.io.Serializable {

    private ProfilPersonneId pk = new ProfilPersonneId();

    public ProfilPersonne() {
    }

    @EmbeddedId
    public ProfilPersonneId getPk() {
        return pk;
    }

    public void setPk(ProfilPersonneId pk) {
        this.pk = pk;
    }

    @Transient
    public Personne getPersonne() {
        return getPk().getPersonne();
    }

    public void setPersonne(Personne personne) {
        getPk().setPersonne(personne);
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

    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        ProfilPersonne that = (ProfilPersonne) o;

        if (getPk() != null ? !getPk().equals(that.getPk())
                : that.getPk() != null)
            return false;

        return true;
    }

    @Override
    public String toString() {
        return "ProfilPersonne{" +
                "pk=" + pk +
                '}';
    }

    public int hashCode() {
        return (getPk() != null ? getPk().hashCode() : 0);
    }
}