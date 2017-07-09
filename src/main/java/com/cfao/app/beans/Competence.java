package com.cfao.app.beans;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by JP on 6/14/2017.
 */
public class Competence {
    private SimpleIntegerProperty idcompetence = new SimpleIntegerProperty();
    private SimpleStringProperty description = new SimpleStringProperty();
    private Niveaucompetence niveau = new Niveaucompetence();
    private SimpleStringProperty type = new SimpleStringProperty();

    public Competence(int idcompetence, String description, Niveaucompetence niveau, String type) {
        this.idcompetence.set(idcompetence);
        this.description.set(description);
        this.niveau = niveau;
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

    public Niveaucompetence getNiveau() {
        return niveau;
    }

    public void setNiveau(Niveaucompetence niveau) {
        this.niveau = niveau;
    }

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
}
