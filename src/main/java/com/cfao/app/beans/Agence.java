package com.cfao.app.beans;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;

import javax.persistence.*;
import java.util.List;

/**
 * Created by JP on 6/11/2017.
 */
@Entity
@Table(name="agences"

)
public class Agence  implements java.io.Serializable {


    private SimpleIntegerProperty idagence = new SimpleIntegerProperty();
    private SimpleStringProperty libelle = new SimpleStringProperty();
    private ListProperty<Personne> personnes = new SimpleListProperty<>();

    public Agence() {
    }


    public Agence(String libelle) {
        this.libelle.set(libelle);
    }
    public Agence(String libelle, List<Personne> personnes) {
        this.libelle.set(libelle);
        this.personnes.set(FXCollections.observableArrayList(personnes));
    }

    @Id @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="IDAGENCE", unique=true, nullable=false)
    public Integer getIdagence() {
        return this.idagence.get();
    }

    public void setIdagence(Integer idagence) {
        this.idagence.set(idagence);
    }


    @Column(name="LIBELLE", nullable=false, length=50)
    public String getLibelle() {
        return this.libelle.get();
    }

    public void setLibelle(String libelle) {
        this.libelle.set(libelle);
    }

    @OneToMany(fetch=FetchType.LAZY, mappedBy="agence")
    public List<Personne> getPersonnes() {
        return this.personnes.get();
    }

    public void setPersonnes(List<Personne> personnes) {
        this.personnes.set(FXCollections.observableArrayList(personnes));
    }

    @Override
    public String toString() {
        return getLibelle();
    }

    public SimpleStringProperty libelleProperty() {
        return libelle;
    }


    public ListProperty<Personne> personnesProperty() {
        return personnes;
    }

    @Override
    public boolean equals(Object object){
        if(object instanceof Agence){
            Agence a = (Agence)object;
            return this.getIdagence().equals(a.getIdagence());
        }
        return false;
    }
}


