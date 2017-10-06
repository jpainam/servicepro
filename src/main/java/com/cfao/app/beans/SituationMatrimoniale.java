package com.cfao.app.beans;

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
@Table(name="situation_matrimoniale"

)
public class SituationMatrimoniale  implements java.io.Serializable {


    private SimpleIntegerProperty idsituationmatrimoniale = new SimpleIntegerProperty();
    private SimpleStringProperty libelle = new SimpleStringProperty();
    private SimpleListProperty<Personne> personnes = new SimpleListProperty<>();

    public SituationMatrimoniale() {
    }


    public SituationMatrimoniale(String libelle) {
        this.libelle.set(libelle);
    }
    public SituationMatrimoniale(String libelle, List<Personne> personnes) {
        this.libelle.set(libelle);
        this.personnes.set(FXCollections.observableArrayList(personnes));
    }

    @Id @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="IDSITUATIONMATRIMONIALE", unique=true, nullable=false)
    public Integer getIdsituationmatrimoniale() {
        return idsituationmatrimoniale.get();
    }

    public SimpleIntegerProperty idsituationmatrimonialeProperty() {
        return idsituationmatrimoniale;
    }

    public void setIdsituationmatrimoniale(Integer idsituationmatrimoniale) {
        this.idsituationmatrimoniale.set(idsituationmatrimoniale);
    }

    @Column(name="LIBELLE", nullable=false, length=50)
    public String getLibelle() {
        return this.libelle.get();
    }

    public void setLibelle(String libelle) {
        this.libelle.set(libelle);
    }

    @OneToMany(fetch=FetchType.LAZY, mappedBy="situationmatrimoniale")
    public List<Personne> getPersonnes() {
        return this.personnes;
    }
    public SimpleListProperty<Personne> personnesProperty(){
        return personnes;
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


