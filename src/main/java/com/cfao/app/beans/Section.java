package com.cfao.app.beans;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import org.hibernate.Session;

/**
 * Created by JP on 6/11/2017.
 */
public class Section {
    private SimpleIntegerProperty idsection = new SimpleIntegerProperty();
    private SimpleStringProperty libelle = new SimpleStringProperty();

    public Section(int idsection, String libelle) {
        this.idsection.set(idsection);
        this.libelle.set(libelle);
    }

    public Section(){}

    public int getIdsection() {
        return idsection.get();
    }

    public void setIdsection(int idsection) {
        this.idsection.set(idsection);
    }

    public String getLibelle() {
        return libelle.get();
    }

    public void setLibelle(String libelle) {
        this.libelle.set(libelle);
    }

    @Override
    public String toString() {
        return getLibelle();
    }
}
