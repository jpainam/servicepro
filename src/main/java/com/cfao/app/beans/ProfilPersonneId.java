package com.cfao.app.beans;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class ProfilPersonneId implements java.io.Serializable {

    private Personne personne;
    private Profil profil;
    private Niveau niveau;

    @ManyToOne
    public Personne getPersonne() {
        return personne;
    }

    public void setPersonne(Personne personne) {
        this.personne = personne;
    }

    @ManyToOne
    public Niveau getNiveau() {
        return niveau;
    }

    public void setNiveau(Niveau niveau) {
        this.niveau = niveau;
    }

    @ManyToOne
    public Profil getProfil() {
        return profil;
    }

    public void setProfil(Profil profil) {
        this.profil = profil;
    }


    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProfilPersonneId that = (ProfilPersonneId) o;

        if (personne != null ? !personne.equals(that.personne) : that.personne != null) return false;
        if (profil != null ? !profil.equals(that.profil) : that.profil != null)
            return false;

        return true;
    }

    @Override
    public String toString() {
        return "ProfilPersonneId{" +
                "personne=" + personne +
                ", profil=" + profil +
                ", niveau=" + niveau +
                '}';
    }

    public int hashCode() {
        int result;
        result = (personne != null ? personne.hashCode() : 0);
        result = 31 * result + (profil != null ? profil.hashCode() : 0);
        return result;
    }

}