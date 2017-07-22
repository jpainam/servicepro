package com.cfao.app.beans;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;

/**
 * Created by Communication on 12/07/2017.
 */
@Entity
@Table(name = "postes")
@AssociationOverrides({@AssociationOverride(name = "pk.personne", joinColumns = @JoinColumn(name = "PERSONNE")),
        @AssociationOverride(name = "pk.societe", joinColumns = @JoinColumn(name = "SOCIETE"))})
public class Poste {
    private PosteId pk = new PosteId();
    private ObjectProperty<LocalDate> dateDebut = new SimpleObjectProperty<LocalDate>();
    private ObjectProperty<LocalDate> dateFin = new SimpleObjectProperty<LocalDate>();


    @EmbeddedId
    public PosteId getPk() {
        return pk;
    }

    public void setPk(PosteId pk) {
        this.pk = pk;
    }

    @Column(name = "DATEDEBUT")
    public Date getDateDebut() {
        return Date.valueOf(dateDebut.get());
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut.set(dateDebut.toLocalDate());
    }

    @Column(name = "DATEFIN")
    public Date getDatefin() {
        return Date.valueOf(dateFin.get());
    }

    public void setDatefin(Date datefin) {
        this.dateFin.set(datefin.toLocalDate());
    }

    @Transient
    public Personne getPersonne() {
        return getPk().getPersonne();
    }


    public void setPersonne(Personne personne) {
        getPk().setPersonne(personne);
    }

    @Transient
    public Societe getSociete() {
        return getPk().getSociete();
    }


    public void setSociete(Societe societe) {
        getPk().setSociete(societe);
    }

    @Transient
    public String getTitre() {
        return getPk().getTitre();
    }

    public void setTitre(String titre) {
        getPk().setTitre(titre);
    }
}
