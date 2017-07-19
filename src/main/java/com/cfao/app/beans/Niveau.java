package com.cfao.app.beans;

import javafx.beans.property.*;
import javafx.collections.FXCollections;

import javax.persistence.*;

/**
 * Created by armel on 17/07/2017.
 */
@Entity
@Table(name = "niveau")
public class Niveau {
    private IntegerProperty id = new SimpleIntegerProperty();
    private StringProperty libelle = new SimpleStringProperty();
   // private ListProperty<ProfilPersonne> profilPersonnes = new SimpleListProperty<ProfilPersonne>();

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

    /*@OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.niveau")
    public java.util.List<StockCategory> getProfilpersonne() {
        return stock.get();
    }
    public void setProfilpersonne(java.util.List<StockCategory> set){
        this.stock.set(FXCollections.observableList(set));
    }
    */

    @Override
    public String toString() {
        return getLibelle();
    }
}
