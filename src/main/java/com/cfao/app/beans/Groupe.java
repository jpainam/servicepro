package com.cfao.app.beans;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by JP on 6/11/2017.
 */
public class Groupe {
    private SimpleIntegerProperty idgroupe = new SimpleIntegerProperty();
    private SimpleStringProperty libelle = new SimpleStringProperty();

    public Groupe(int idgroupe, String libelle) {
        this.idgroupe.set(idgroupe);
        this.libelle.set(libelle);
    }

    public Groupe(){}
    public int getIdgroupe() {
        return idgroupe.get();
    }

    public String getLibelle() {
        return libelle.get();
    }

    public void setIdgroupe(int idgroupe) {
        this.idgroupe.set(idgroupe);
    }

    public void setLibelle(String libelle) {
        this.libelle.set(libelle);
    }

    @Override
    public String toString() {
        return getLibelle();
    }

    public SimpleIntegerProperty idgroupeProperty() {
        return idgroupe;
    }

    public SimpleStringProperty libelleProperty() {
        return libelle;
    }
}
