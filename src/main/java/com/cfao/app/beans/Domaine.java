package com.cfao.app.beans;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;

import javax.persistence.*;
import java.util.List;

/**
 * Created by JP on 8/22/2017.
 */
@Entity
@Table(name = "domaines")
public class Domaine {
    private SimpleIntegerProperty iddomaine = new SimpleIntegerProperty();
    private SimpleStringProperty libelle = new SimpleStringProperty();
    private ListProperty<Personnel> personnels = new SimpleListProperty<>();

    public Domaine(SimpleIntegerProperty iddomaine, SimpleStringProperty libelle) {
        this.iddomaine = iddomaine;
        this.libelle = libelle;
    }

    public Domaine(SimpleStringProperty libelle) {
        this.libelle = libelle;
    }

    public Domaine(){

    }
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "IDDOMAINE", unique=true, nullable=false)
    public Integer getIddomaine() {
        return iddomaine.get();
    }

    public SimpleIntegerProperty iddomaineProperty() {
        return iddomaine;
    }

    public void setIddomaine(Integer iddomaine) {
        this.iddomaine.set(iddomaine);
    }

    @Column(name = "LIBELLE", nullable=false, length=150)
    public String getLibelle() {
        return libelle.get();
    }

    public SimpleStringProperty libelleProperty() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle.set(libelle);
    }

    @Override
    public String toString() {
        return getLibelle();
    }

    @ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(name="personnel_domaine", joinColumns = {
            @JoinColumn(name="DOMAINE", nullable=false, updatable=false) }, inverseJoinColumns = {
            @JoinColumn(name="PERSONNEL", nullable=false, updatable=false) })
    public List<Personnel> getPersonnels() {
        return personnels.get();
    }

    public ListProperty<Personnel> personnelsProperty() {
        return personnels;
    }

    public void setPersonnels(List<Personnel> personnels) {
        this.personnels.set(FXCollections.observableArrayList(personnels));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Domaine domaine = (Domaine) o;

        if (iddomaine != null ? !iddomaine.equals(domaine.iddomaine) : domaine.iddomaine != null) return false;
        return libelle != null ? libelle.equals(domaine.libelle) : domaine.libelle == null;
    }

    @Override
    public int hashCode() {
        int result = iddomaine != null ? iddomaine.hashCode() : 0;
        result = 31 * result + (libelle != null ? libelle.hashCode() : 0);
        return result;
    }
}
