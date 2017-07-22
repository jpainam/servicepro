package com.cfao.app.beans;

import javafx.beans.property.*;
import javafx.collections.FXCollections;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by armel on 17/07/2017.
 */
@Entity
@Table(name = "niveau")
public class Niveau implements Serializable{
    private static final long serialVersionUID = 1L;
    private IntegerProperty id = new SimpleIntegerProperty();
    private StringProperty libelle = new SimpleStringProperty();
   // private ListProperty<ProfilPersonne> profilPersonnes = new SimpleListProperty<ProfilPersonne>();


    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public StringProperty libelleProperty() {
        return libelle;
    }

    @Id
    @GeneratedValue
    @Column(name = "IDNIVEAU")
    public int getIdniveau (){
        return this.id.get();
    }
    public void setIdniveau(int niveau){
        this.id.set(niveau);
    }

    @Column(name = "LIBELLE")
    public String getLibelle(){
        return this.libelle.get();
    }
    public void setLibelle(String libelle){
        this.libelle.set(libelle);
    }

    /*@OneToMany(fetch = FetchType.LAZY, mappedBy = "niveau")
    public java.util.List<StockCategory> getProfilpersonne() {
        return stock.get();
    }
    public void setProfilpersonne(java.util.List<StockCategory> set){
        this.stock.set(FXCollections.observableList(set));
    }
    */

    @Override
    public boolean equals(Object o) {
        if(o instanceof Niveau){
            Niveau niveau = (Niveau)o;
            if(niveau.getIdniveau() == this.getIdniveau()){
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (libelle != null ? libelle.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return getLibelle();
    }
}
