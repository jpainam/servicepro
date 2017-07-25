package com.cfao.app.beans;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;

import java.util.List;
import java.util.Set;
import javax.persistence.*;

@Entity
@Table(name = "competences"
        , catalog = "servicepro"
)
public class Competence implements java.io.Serializable {


    private SimpleIntegerProperty idcompetence = new SimpleIntegerProperty();
    private SimpleStringProperty description = new SimpleStringProperty();
    private SimpleStringProperty type = new SimpleStringProperty();
    private ObjectProperty<Niveau> niveau = new SimpleObjectProperty<>();
    private ListProperty<Profil> profils = new SimpleListProperty<>();
    private ListProperty<Formation> formations = new SimpleListProperty<>();

    public Competence() {
    }

    public Competence(String description, String type, Niveau niveau, List<Profil> profils, List<Formation> formations) {
        this.description.set(description);
        this.type.set(type);
        this.profils.set(FXCollections.observableArrayList(profils));
        this.formations.set(FXCollections.observableArrayList(formations));
        this.niveau.set(niveau);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "IDCOMPETENCE", unique = true, nullable = false)
    public Integer getIdcompetence() {
        return this.idcompetence.get();
    }

    public void setIdcompetence(Integer idcompetence) {
        this.idcompetence.set(idcompetence);
    }


    @Column(name = "DESCRIPTION", length = 65535)
    public String getDescription() {
        return this.description.get();
    }

    public void setDescription(String description) {
        this.description.set(description);
    }


    @Column(name = "TYPE", length = 3)
    public String getType() {
        return this.type.get();
    }

    public void setType(String type) {
        this.type.set(type);
    }


    @ManyToMany(fetch=FetchType.LAZY, cascade=CascadeType.PERSIST)
    @JoinTable(name="profil_competence", catalog="servicepro", joinColumns = {
            @JoinColumn(name="COMPETENCE", nullable=false, updatable=false) }, inverseJoinColumns = {
            @JoinColumn(name="PROFIL", nullable=false, updatable=false) })
    public List<Profil> getProfils() {
        return this.profils;
    }

    public void setProfils(List<Profil> profils) {
        this.profils.set(FXCollections.observableList(profils));
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "formation_competence", catalog = "servicepro", joinColumns = {
            @JoinColumn(name = "COMPETENCE", nullable = false, updatable = false)}, inverseJoinColumns = {
            @JoinColumn(name = "FORMATION", nullable = false, updatable = false)})
    public List<Formation> getFormations() {
        return this.formations.get();
    }

    public void setFormations(List<Formation> formations) {
        this.formations.set(FXCollections.observableArrayList(formations));
    }


    public SimpleStringProperty descriptionProperty() {
        return description;
    }

    @Override
    public String toString() {
        return getDescription();
    }

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="NIVEAU")
    public Niveau getNiveau() {
        return niveau.get();
    }

    public ObjectProperty<Niveau> niveauProperty() {
        return niveau;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Competence that = (Competence) o;

        if (idcompetence != null) {
            return (idcompetence.get() == that.idcompetence.get());
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = idcompetence != null ? idcompetence.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (niveau != null ? niveau.hashCode() : 0);
        result = 31 * result + (profils != null ? profils.hashCode() : 0);
        result = 31 * result + (formations != null ? formations.hashCode() : 0);
        return result;
    }

    public void setNiveau(Niveau niveau) {
        this.niveau.set(niveau);
    }
}


