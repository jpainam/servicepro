package com.cfao.app.beans;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;

import javax.persistence.*;
import java.util.List;

/**
 * Created by JP on 6/10/2017.
 */
@Entity
@Table(name="pays"
)
public class Pays {
    private SimpleIntegerProperty idpays = new SimpleIntegerProperty();
    private SimpleStringProperty iso = new SimpleStringProperty();
    private SimpleStringProperty namefr = new SimpleStringProperty();
    private SimpleStringProperty nameen = new SimpleStringProperty();
    private SimpleStringProperty iso3 = new SimpleStringProperty();
    private SimpleIntegerProperty numcode = new SimpleIntegerProperty();
    private SimpleIntegerProperty phonecode = new SimpleIntegerProperty();
    private ListProperty<Personne> personnes = new SimpleListProperty<>();


    public Pays(int idPays, String iso, String nameFr, String nameEn, String iso3,
                int numCode, int phoneCode) {
        this.idpays.set(idPays);
        this.iso.set(iso);
        this.namefr.set(nameFr);
        this.nameen.set(nameEn);
        this.iso3.set(iso3);
        this.numcode.set(numCode);
        this.phonecode.set(phoneCode);
    }
    public Pays(){

    }

    @Id @GeneratedValue(strategy= GenerationType.AUTO)


    @Column(name="IDPAYS", unique=true, nullable=false)
    public Integer getIdpays() {
        return this.idpays.get();
    }

    public void setIdpays(Integer idpays) {
        this.idpays.set(idpays);
    }


    @Column(name="ISO", nullable=false, length=2)
    public String getIso() {
        return this.iso.get();
    }

    public void setIso(String iso) {
        this.iso.set(iso);
    }


    @Column(name="NAMEFR", nullable=false, length=80)
    public String getNamefr() {
        return this.namefr.get();
    }

    public void setNamefr(String namefr) {
        this.namefr.set(namefr);
    }


    @Column(name="NAMEEN", nullable=false, length=80)
    public String getNameen() {
        return this.nameen.get();
    }

    public void setNameen(String nameen) {
        this.nameen.set(nameen);
    }


    @Column(name="ISO3", length=3)
    public String getIso3() {
        return this.iso3.get();
    }

    public void setIso3(String iso3) {
        this.iso3.set(iso3);
    }


    @Column(name="NUMCODE")
    public Integer getNumcode() {
        return this.numcode.get();
    }

    public void setNumcode(Integer numcode) {
        if(numcode != null) {
            this.numcode.set(numcode);
        }
    }


    @Column(name="PHONECODE", nullable=false)
    public Integer getPhonecode() {
        return this.phonecode.get();
    }

    public void setPhonecode(Integer phonecode) {
        this.phonecode.set(phonecode);
    }

    @OneToMany(fetch=FetchType.LAZY, mappedBy="pays")
    public List<Personne> getPersonnes() {
        return this.personnes;
    }

    public void setPersonnes(List<Personne> personnes) {
        this.personnes.set(FXCollections.observableList(personnes));
    }

    @Override
    public String toString() {
        return getNamefr();
    }
}


