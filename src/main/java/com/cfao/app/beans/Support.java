package com.cfao.app.beans;

import com.sun.javafx.beans.IDProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import javax.persistence.*;

/**
 * Created by JP on 7/11/2017.
 */
@Entity
@Table(name = "supports")
public class Support {
    private SimpleIntegerProperty idsupport = new SimpleIntegerProperty();
    private SimpleStringProperty code = new SimpleStringProperty();
    private SimpleStringProperty titre = new SimpleStringProperty();
    private SimpleStringProperty lien = new SimpleStringProperty();

    @Id
    @GeneratedValue
    @Column(name = "IDSUPPORT")
    public int getIdsupport() {
        return idsupport.get();
    }

    public SimpleIntegerProperty idsupportProperty() {
        return idsupport;
    }

    public void setIdsupport(int idsupport) {
        this.idsupport.set(idsupport);
    }

    @Column(name = "CODE")
    public String getCode() {
        return code.get();
    }

    public SimpleStringProperty codeProperty() {
        return code;
    }

    public void setCode(String code) {
        this.code.set(code);
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

    @Column(name = "LIEN")
    public String getLien() {
        return lien.get();
    }

    public SimpleStringProperty lienProperty() {
        return lien;
    }

    public void setLien(String lien) {
        this.lien.set(lien);
    }

    @Override
    public boolean equals(Object o) {
       if(o instanceof  Support){
           Support support = (Support) o;
           if(support.getIdsupport() == this.getIdsupport()){
               return true;
           }
       }
       return false;
    }

    @Override
    public String toString() {
        return getCode() + " " + getTitre();
    }
}
