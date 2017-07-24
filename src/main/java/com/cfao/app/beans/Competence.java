package com.cfao.app.beans;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;

import javax.persistence.*;
import javax.persistence.Transient;
import java.beans.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * Created by JP on 6/14/2017.
 */
@Entity
@Table(name = "competences")
public class Competence implements Serializable{
    private static final long serialVersionUID = 1L;
    private SimpleIntegerProperty idcompetence = new SimpleIntegerProperty();
    private SimpleStringProperty description = new SimpleStringProperty();
    private SimpleStringProperty type = new SimpleStringProperty();
    private ListProperty<ProfilCompetence> profilcompetences = new SimpleListProperty<>();

    public Competence(){}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDCOMPETENCE")
    public int getIdcompetence() {
        return idcompetence.get();
    }

    public void setIdcompetence(int idcompetence) {
        this.idcompetence.set(idcompetence);
    }

    @Column(name = "DESCRIPTION")
    public String getDescription() {
        return description.get();
    }


    public void setDescription(String description) {
        this.description.set(description);
    }

    @Column(name = "TYPE")
    public String getType() {
        return type.get();
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public SimpleIntegerProperty idcompetenceProperty() {
        return idcompetence;
    }

    public SimpleStringProperty descriptionProperty() {
        return description;
    }

    public SimpleStringProperty typeProperty() {
        return type;
    }

    @Override
    public String toString() {
        return getDescription();
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Competence){
            Competence competence = (Competence)o;
            if(competence.getIdcompetence() == this.getIdcompetence())
                return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return idcompetence != null ? idcompetence.hashCode() : 0;
    }

    @OneToMany(mappedBy = "pk.competence")
    public List<ProfilCompetence> getProfilcompetences() {
        return profilcompetences.get();
    }

    public ListProperty<ProfilCompetence> profilcompetencesProperty() {
        return profilcompetences;
    }

    public void setProfilcompetences(List<ProfilCompetence> profilcompetences) {
        this.profilcompetences.set(FXCollections.observableArrayList(profilcompetences));
    }
}
