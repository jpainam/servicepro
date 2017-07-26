package com.cfao.app.beans;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by JP on 6/22/2017.
 */
public class Modele {
    private SimpleIntegerProperty idmodele = new SimpleIntegerProperty();
    private SimpleStringProperty libelle = new SimpleStringProperty();

    public Modele(SimpleIntegerProperty idmodele, SimpleStringProperty libelle) {
        this.idmodele = idmodele;
        this.libelle = libelle;
    }

    public Modele(int idmodele, String libelle) {
        this.idmodele .set(idmodele);
        this.libelle.set(libelle);
    }
    public Modele(){}


    public int getIdmodele() {
        return idmodele.get();
    }

    public SimpleIntegerProperty idmodeleProperty() {
        return idmodele;
    }

    public void setIdmodele(int idmodele) {
        this.idmodele.set(idmodele);
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Modele modele = (Modele) o;

        if (idmodele != null ? !idmodele.equals(modele.idmodele) : modele.idmodele != null) return false;
        return libelle != null ? libelle.equals(modele.libelle) : modele.libelle == null;
    }

    @Override
    public int hashCode() {
        int result = idmodele != null ? idmodele.hashCode() : 0;
        result = 31 * result + (libelle != null ? libelle.hashCode() : 0);
        return result;
    }
}
