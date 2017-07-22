package com.cfao.app.beans;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

/**
 * Created by armel on 21/07/2017.
 */
@Embeddable
public class PosteId implements java.io.Serializable{
    private ObjectProperty<Personne> personne = new SimpleObjectProperty<Personne>();
    private ObjectProperty<Societe> societe = new SimpleObjectProperty<Societe>();
    private StringProperty titre = new SimpleStringProperty();

    @ManyToOne
    public Personne getPersonne() {
        return personne.get();
    }

    public void setPersonne(Personne personne) {
        this.personne.set(personne);
    }

    @ManyToOne
    public Societe getSociete() {
        return societe.get();
    }

    public void setSociete(Societe societe) {
        this.societe.set(societe);
    }

    @Column(name = "TITRE")
    public String getTitre() {
        return titre.get();
    }

    public void setTitre(String titre) {
        this.titre.set(titre);
    }
}
