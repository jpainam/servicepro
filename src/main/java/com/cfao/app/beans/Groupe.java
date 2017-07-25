package com.cfao.app.beans;

import javafx.beans.property.SetProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleSetProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import org.hibernate.annotations.MetaValue;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by JP on 6/11/2017.
 */
@Entity
@Table(name="groupes"
        ,catalog="servicepro"
)
public class Groupe  implements java.io.Serializable {


    private SimpleIntegerProperty idgroupe = new SimpleIntegerProperty();
    private SimpleStringProperty libelle = new SimpleStringProperty();
    private SetProperty<Personne> personnes = new SimpleSetProperty<>();

    public Groupe() {
    }


    public Groupe(String libelle) {
        this.libelle.set(libelle);
    }
    public Groupe(String libelle, Set<Personne> personnes) {
        this.libelle.set(libelle);
        this.personnes.set(FXCollections.observableSet(personnes));
    }

    @Id @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="IDGROUPE", unique=true, nullable=false)
    public Integer getIdgroupe() {
        return this.idgroupe.get();
    }

    public void setIdgroupe(Integer idgroupe) {
        this.idgroupe.set(idgroupe);
    }


    @Column(name="LIBELLE", nullable=false, length=50)
    public String getLibelle() {
        return this.libelle.get();
    }

    public void setLibelle(String libelle) {
        this.libelle.set(libelle);
    }

    @OneToMany(fetch=FetchType.LAZY, mappedBy="groupe")
    public Set<Personne> getPersonnes() {
        return this.personnes;
    }

    public void setPersonnes(Set<Personne> personnes) {
        this.personnes.set(FXCollections.observableSet(personnes));
    }

    @Override
    public String toString() {
        return getLibelle();
    }
}


