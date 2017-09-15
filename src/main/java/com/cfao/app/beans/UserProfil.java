package com.cfao.app.beans;

// Generated Aug 29, 2017 4:18:12 PM by Hibernate Tools 4.3.1


import javafx.beans.property.*;
import javafx.collections.FXCollections;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * UserProfil generated by hbm2java
 */
@Entity
@Table(name = "user_profil"
)
public class UserProfil implements java.io.Serializable {


    private IntegerProperty iduserprofil = new SimpleIntegerProperty();
    private StringProperty libelle = new SimpleStringProperty();
    private ListProperty<PlanificationModele> planificationModelesResponsable = new SimpleListProperty<>();
    private ListProperty<PlanificationModele> planificationModelesValidation = new SimpleListProperty<>();

    private ListProperty<Planification> planificationsesValidation = new SimpleListProperty<>();
    private ListProperty<Planification> planificationsesResponsable = new SimpleListProperty<>();

    private ListProperty<User> users = new SimpleListProperty<>();

    public UserProfil() {
    }


    public UserProfil(Integer iduserprofil, String libelle) {
        this.setIduserprofil(iduserprofil);
        this.libelle.set(libelle);
    }

    public UserProfil(String libelle, List<PlanificationModele> planificationModelesResponsable, List<PlanificationModele> planificationModelesValidation, List<User> users) {
        this.libelle.set(libelle);
        this.planificationModelesResponsable.set(FXCollections.observableArrayList(planificationModelesResponsable));
        this.planificationModelesValidation.set(FXCollections.observableArrayList(planificationModelesValidation));
        this.users.set(FXCollections.observableArrayList(users));
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)


    @Column(name = "IDUSERPROFIL", unique = true, nullable = false)
    public Integer getIduserprofil() {
        return this.iduserprofil.get();
    }

    public void setIduserprofil(Integer iduserprofil) {
        this.iduserprofil.set(iduserprofil);
    }


    @Column(name = "LIBELLE", nullable = false, length = 50)
    public String getLibelle() {
        return this.libelle.get();
    }

    public void setLibelle(String libelle) {
        this.libelle.set(libelle);
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "responsable")
    public List<PlanificationModele> getPlanificationModelesResponsable() {
        return this.planificationModelesResponsable.get();
    }

    public void setPlanificationModelesResponsable(List<PlanificationModele> planificationModelesResponsable) {
        if(planificationModelesResponsable != null) {
            this.planificationModelesResponsable.set(FXCollections.observableArrayList(planificationModelesResponsable));
        }
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "validation")
    public List<PlanificationModele> getPlanificationModelesValidation() {
        return this.planificationModelesValidation.get();
    }

    public void setPlanificationModelesValidation(List<PlanificationModele> planificationModelesValidation) {
        if(planificationModelesValidation != null) {
            this.planificationModelesValidation.set(FXCollections.observableArrayList(planificationModelesValidation));
        }
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userProfil")
    public List<User> getUsers() {
        return this.users.get();
    }

    public void setUsers(List<User> users) {
        if(users != null) {
            this.users.set(FXCollections.observableArrayList(users));
        }
    }


    @OneToMany(fetch=FetchType.LAZY, mappedBy="responsable")
    public List<Planification> getPlanificationsesResponsable() {
        return this.planificationsesResponsable.get();
    }

    public void setPlanificationsesResponsable(List<Planification> planificationsesResponsable) {
        if(planificationsesResponsable != null) {
            this.planificationsesResponsable.set(FXCollections.observableArrayList(planificationsesResponsable));
        }
    }

    @OneToMany(fetch=FetchType.LAZY, mappedBy="validation")
    public List<Planification> getPlanificationsesValidation() {
        return this.planificationsesValidation.get();
    }

    public void setPlanificationsesValidation(List<Planification> planificationsesValidation) {
        if(planificationsesValidation != null) {
            this.planificationsesValidation.set(FXCollections.observableArrayList(planificationsesValidation));
        }
    }

    @Override
    public String toString() {
        return getLibelle();
    }
}


