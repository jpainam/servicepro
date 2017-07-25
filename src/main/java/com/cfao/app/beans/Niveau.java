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
    private IntegerProperty idniveau = new SimpleIntegerProperty();
    private StringProperty libelle = new SimpleStringProperty();
   // private ListProperty<ProfilPersonne> profilPersonnes = new SimpleListProperty<ProfilPersonne>();


    public IntegerProperty idniveauProperty() {
        return idniveau;
    }


    public StringProperty libelleProperty() {
        return libelle;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "IDNIVEAU")
    public int getIdniveau (){
        return this.idniveau.get();
    }
    public void setIdniveau(int niveau){
        this.idniveau.set(niveau);
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
        int result = idniveau != null ? idniveau.hashCode() : 0;
        result = 31 * result + (libelle != null ? libelle.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return getLibelle();
    }
}
