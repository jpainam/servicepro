package com.cfao.app.beans;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * Created by JP on 6/21/2017.
 */
@Entity
@Table(name = "formations")
public class Formation {
    private SimpleIntegerProperty idformation = new SimpleIntegerProperty();
    private SimpleStringProperty codeformation = new SimpleStringProperty();
    private ObjectProperty<Modele> modele = new SimpleObjectProperty<>();
    private SimpleStringProperty titre = new SimpleStringProperty();
    private SimpleStringProperty description = new SimpleStringProperty();
    private ObjectProperty<Etatformation> etatformation = new SimpleObjectProperty<>();
    private ObjectProperty<Typeformation> typeformation = new SimpleObjectProperty<>();
    private SimpleObjectProperty<LocalDate> datedebut = new SimpleObjectProperty<>();
    private SimpleObjectProperty<LocalDate> datefin = new SimpleObjectProperty<>();

    private ListProperty<Personnel> formateurs = new SimpleListProperty<>();
    private ListProperty<Support> supports = new SimpleListProperty<>();

    public Formation(int idformation, String codeformation, Modele modele, String titre,
                     String description, LocalDate datedebut,
                     LocalDate datefin) {
        this.idformation.set(idformation);
        this.codeformation.set(codeformation);
        this.modele.set(modele);
        this.titre.set(titre);
        this.description.set(description);
        this.datedebut.set(datedebut);
        this.datefin.set(datefin);
    }

    @Id
    @GeneratedValue
    @Column(name = "IDFORMATION")
    public int getIdformation() {
        return idformation.get();
    }

    public SimpleIntegerProperty idformationProperty() {
        return idformation;
    }

    public void setIdformation(int idformation) {
        this.idformation.set(idformation);
    }

    @Column(name = "CODEFORMATION")
    public String getCodeformation() {
        return codeformation.get();
    }

    public SimpleStringProperty codeformationProperty() {
        return codeformation;
    }

    public void setCodeformation(String codeformation) {
        this.codeformation.set(codeformation);
    }

    @ManyToOne()
    @JoinColumn(name = "MODELE")
    public Modele getModele() {
        return modele.get();
    }

    public void setModele(Modele modele) {
        this.modele.set(modele);
    }

    @Column(name = "TITRE")
    public String getTitre() {
        return titre.get();
    }

    public SimpleStringProperty titreProperty() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre.set(titre);
    }

    @Column(name = "DESCRIPTION")
    public String getDescription() {
        return description.get();
    }

    public SimpleStringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    @Column(name = "DATEDEBUT")
    public Date getDatedebut() {
        return Date.valueOf(datedebut.get());
    }

    public SimpleObjectProperty<LocalDate> datedebutProperty() {
        return datedebut;
    }

    public void setDatedebut(Date datedebut) {
        this.datedebut.set(datedebut.toLocalDate());
    }

    @Column(name = "DATEFIN")
    public Date getDatefin() {
        return Date.valueOf(datefin.get());
    }

    public SimpleObjectProperty<LocalDate> datefinProperty() {
        return datefin;
    }

    public void setDatefin(Date datefin) {
        this.datefin.set(datefin.toLocalDate());
    }

    public Formation(){}

    @Override
    public String toString() {
        return getTitre();
    }


    @ManyToMany()
    @JoinTable(name = "formateurs", joinColumns = {@JoinColumn(name = "FORMATION")},
            inverseJoinColumns = {@JoinColumn(name = "PERSONNEL")})
    public java.util.List<Personnel> getFormateurs() {
        return formateurs.get();
    }

    public ListProperty<Personnel> formateursProperty() {
        return formateurs;
    }

    public void setFormateurs(List<Personnel> formateurs) {
        this.formateurs.set(FXCollections.observableList(formateurs));
    }

    @ManyToOne()
    @JoinColumn(name = "ETATFORMATION")
    public Etatformation getEtatformation() {
        return etatformation.get();
    }

    public ObjectProperty<Etatformation> etatformationProperty() {
        return etatformation;
    }

    public void setEtatformation(Etatformation etatformation) {
        this.etatformation.set(etatformation);
    }

    @ManyToOne()
    @JoinColumn(name = "TYPEFORMATION")
    public Typeformation getTypeformation() {
        return typeformation.get();
    }

    public ObjectProperty<Typeformation> typeformationProperty() {
        return typeformation;
    }

    public void setTypeformation(Typeformation typeformation) {
        this.typeformation.set(typeformation);
    }

    @ManyToMany()
    @JoinTable(name = "support_formation", joinColumns = {@JoinColumn(name="FORMATION")},
    inverseJoinColumns = {@JoinColumn(name="SUPPORT")})
    public List<Support> getSupports() {
        return supports.get();
    }

    public ListProperty<Support> supportsProperty() {
        return supports;
    }

    public void setSupports(List<Support> supports) {
        this.supports.set(FXCollections.observableList(supports));
    }
}
