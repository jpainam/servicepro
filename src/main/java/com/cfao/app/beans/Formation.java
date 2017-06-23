package com.cfao.app.beans;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.sql.Date;
import java.time.LocalDateTime;

/**
 * Created by JP on 6/21/2017.
 */
public class Formation {
    private SimpleIntegerProperty idformation = new SimpleIntegerProperty();
   private SimpleStringProperty codeformation = new SimpleStringProperty();
    private Modele modele = new Modele();
    private SimpleStringProperty titre = new SimpleStringProperty();
    private SimpleStringProperty description = new SimpleStringProperty();
    private SimpleObjectProperty<LocalDateTime> datedebut = new SimpleObjectProperty<>();
    private SimpleObjectProperty<LocalDateTime> datefin = new SimpleObjectProperty<>();

    public Formation(int idformation, String codeformation, Modele modele, String titre,
                     String description, LocalDateTime datedebut,
                     LocalDateTime datefin) {
        this.idformation.set(idformation);
        this.codeformation.set(codeformation);
        this.modele = modele;
        this.titre.set(titre);
        this.description.set(description);
        this.datedebut.set(datedebut);
        this.datefin.set(datefin);
    }

    public int getIdformation() {
        return idformation.get();
    }

    public SimpleIntegerProperty idformationProperty() {
        return idformation;
    }

    public void setIdformation(int idformation) {
        this.idformation.set(idformation);
    }

    public String getCodeformation() {
        return codeformation.get();
    }

    public SimpleStringProperty codeformationProperty() {
        return codeformation;
    }

    public void setCodeformation(String codeformation) {
        this.codeformation.set(codeformation);
    }

    public Modele getModele() {
        return modele;
    }

    public void setModele(Modele modele) {
        this.modele = modele;
    }

    public String getTitre() {
        return titre.get();
    }

    public SimpleStringProperty titreProperty() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre.set(titre);
    }

    public String getDescription() {
        return description.get();
    }

    public SimpleStringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public LocalDateTime getDatedebut() {
        return datedebut.get();
    }

    public SimpleObjectProperty<LocalDateTime> datedebutProperty() {
        return datedebut;
    }

    public void setDatedebut(LocalDateTime datedebut) {
        this.datedebut.set(datedebut);
    }

    public LocalDateTime getDatefin() {
        return datefin.get();
    }

    public SimpleObjectProperty<LocalDateTime> datefinProperty() {
        return datefin;
    }

    public void setDatefin(LocalDateTime datefin) {
        this.datefin.set(datefin);
    }

    public Formation(){}

    @Override
    public String toString() {
        return getTitre();
    }


}
