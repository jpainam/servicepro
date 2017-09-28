package com.cfao.app.beans;

import javafx.beans.property.SetProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleSetProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;

import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "competences"
        , catalog = "servicepro"
)
public class Competence implements java.io.Serializable {


    private SimpleIntegerProperty idcompetence;
    private SimpleStringProperty description;
    private SimpleStringProperty type;
    private SetProperty<ProfilCompetence> profilCompetence = new SimpleSetProperty<>();
    private SetProperty<Formation> formations = new SimpleSetProperty<>();

    public Competence() {
    }

    public Competence(String description, String type, Set<ProfilCompetence> profilCompetence, Set<Formation> formations) {
        this.description.set(description);
        this.type.set(type);
        this.profilCompetence.set(FXCollections.observableSet(profilCompetence));
        this.formations.set(FXCollections.observableSet(formations));
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)


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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "competences")
    public Set<ProfilCompetence> getProfilCompetence() {
        return this.profilCompetence;
    }

    public void setProfilCompetence(Set<ProfilCompetence> profilCompetence) {
        this.profilCompetence.set(FXCollections.observableSet(profilCompetence));
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "formation_competence", catalog = "servicepro", joinColumns = {
            @JoinColumn(name = "COMPETENCE", nullable = false, updatable = false)}, inverseJoinColumns = {
            @JoinColumn(name = "FORMATION", nullable = false, updatable = false)})
    public Set<Formation> getFormations() {
        return this.formations.get();
    }

    public void setFormations(Set<Formation> formations) {
        this.formations.set(FXCollections.observableSet(formations));
    }

    public SimpleStringProperty descriptionProperty() {
        return description;
    }
}


