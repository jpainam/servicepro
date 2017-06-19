package com.cfao.app.beans;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by JP on 6/14/2017.
 */
public class Competence {
    private SimpleIntegerProperty idcompetence = new SimpleIntegerProperty();
    private SimpleStringProperty description = new SimpleStringProperty();
    private Niveaucompetence niveaucompetence;
    private SimpleStringProperty type = new SimpleStringProperty();

    public Competence(int idcompetence, String description, Niveaucompetence niveaucompetence, String type) {
        this.idcompetence.set(idcompetence);
        this.description.set(description);
        this.niveaucompetence = niveaucompetence;
        this.type.set(type);
    }
    public Competence(){}

    public int getIdcompetence() {
        return idcompetence.get();
    }

    public void setIdcompetence(int idcompetence) {
        this.idcompetence.set(idcompetence);
    }

    public String getDescription() {
        return description.get();
    }


    public void setDescription(String description) {
        this.description.set(description);
    }

    public Niveaucompetence getNiveaucompetence() {
        return niveaucompetence;
    }

    public void setNiveaucompetence(Niveaucompetence niveaucompetence) {
        this.niveaucompetence = niveaucompetence;
    }

    public String getType() {
        return type.get();
    }

    public void setType(String type) {
        this.type.set(type);
    }
}
