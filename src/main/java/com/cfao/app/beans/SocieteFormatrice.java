package com.cfao.app.beans;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;

import javax.persistence.*;
import java.util.List;

/**
 * Created by JP on 9/3/2017.
 */
@Entity
@Table(name="societe_formatrice"
)
public class SocieteFormatrice {
    private SimpleIntegerProperty idformateur = new SimpleIntegerProperty();
    private SimpleStringProperty libelle = new SimpleStringProperty();
    private ListProperty<Formation> formations = new SimpleListProperty<>();

    public SocieteFormatrice(){

    }

    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)


    @Column(name="IDFORMATEUR", unique=true, nullable=false)
    public Integer getIdformateur() {
        return idformateur.get();
    }

    public SimpleIntegerProperty idformateurProperty() {
        return idformateur;
    }

    public void setIdformateur(int idformateur) {
        this.idformateur.set(idformateur);
    }

    @Column(name="LIBELLE", nullable=false, length=30)
    public String getLibelle() {
        return libelle.get();
    }

    public SimpleStringProperty libelleProperty() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle.set(libelle);
    }

    @OneToMany(fetch=FetchType.LAZY, mappedBy="societeFormatrice")
    public List<Formation> getFormations() {
        return formations.get();
    }

    public ListProperty<Formation> formationsProperty() {
        return formations;
    }

    public void setFormations(List<Formation> formations) {
        this.formations.set(FXCollections.observableArrayList(formations));
    }

    @Override
    public String toString(){
        return getLibelle();
    }
}
