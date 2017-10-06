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
@Table(name="review_positionnement"

)
public class ReviewPositionnement  implements java.io.Serializable {


    private SimpleIntegerProperty idreviewpositionnement = new SimpleIntegerProperty();
    private SimpleStringProperty libelle = new SimpleStringProperty();
    private ListProperty<Personne> personnes = new SimpleListProperty<>();

    public ReviewPositionnement() {
    }


    public ReviewPositionnement(String libelle) {
        this.libelle.set(libelle);
    }
    public ReviewPositionnement(String libelle, List<Personne> personnes) {
        this.libelle.set(libelle);
        this.personnes.set(FXCollections.observableArrayList(personnes));
    }

    @Id @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="IDREVIEWPOSITIONNEMENT", unique=true, nullable=false)
    public Integer getIdreviewpositionnement() {
        return idreviewpositionnement.get();
    }

    public SimpleIntegerProperty idreviewpositionnementProperty() {
        return idreviewpositionnement;
    }

    public void setIdreviewpositionnement(Integer idreviewpositionnement) {
        this.idreviewpositionnement.set(idreviewpositionnement);
    }



    @Column(name="LIBELLE", nullable=false, length=50)
    public String getLibelle() {
        return this.libelle.get();
    }

    public void setLibelle(String libelle) {
        this.libelle.set(libelle);
    }

    @OneToMany(fetch=FetchType.LAZY, mappedBy="reviewpositionnement")
    public List<Personne> getPersonnes() {
        return this.personnes;
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


}


