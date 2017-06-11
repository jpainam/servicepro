package com.cfao.app.beans;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by JP on 6/10/2017.
 */
public class Pays {
    private SimpleIntegerProperty idPays = new SimpleIntegerProperty();
    private SimpleStringProperty iso = new SimpleStringProperty();
    private SimpleStringProperty nameFr = new SimpleStringProperty();
    private SimpleStringProperty nameEn = new SimpleStringProperty();
    private SimpleStringProperty iso3 = new SimpleStringProperty();
    private SimpleIntegerProperty numCode = new SimpleIntegerProperty();
    private SimpleIntegerProperty phoneCode = new SimpleIntegerProperty();

    public Pays(int idPays, String iso, String nameFr, String nameEn, String iso3,
                int numCode, int phoneCode) {
        this.idPays.set(idPays);
        this.iso.set(iso);
        this.nameFr.set(nameFr);
        this.nameEn.set(nameEn);
        this.iso3.set(iso3);
        this.numCode.set(numCode);
        this.phoneCode.set(phoneCode);
    }
    public Pays(){

    }

    public int getIdpays() {
        return idPays.get();
    }

    public String getIso() {
        return iso.get();
    }


    public String getNameFr() {
        return nameFr.get();
    }


    public String getNameEn() {
        return nameEn.get();
    }


    public String getIso3() {
        return iso3.get();
    }


    public int getNumCode() {
        return numCode.get();
    }

    public int getPhoneCode() {
        return phoneCode.get();
    }

    public void setIdpays(int idPays) {
        this.idPays.set(idPays);
    }

    public void setIso(String iso) {
        this.iso.set(iso);
    }

    public void setNameFr(String nameFr) {
        this.nameFr.set(nameFr);
    }

    public void setNameEn(String nameEn) {
        this.nameEn.set(nameEn);
    }

    public void setIso3(String iso3) {
        this.iso3.set(iso3);
    }

    public void setNumCode(int numCode) {
        this.numCode.set(numCode);
    }

    public void setPhoneCode(int phoneCode) {
        this.phoneCode.set(phoneCode);
    }

    @Override
    public String toString() {
        return  getNameFr() ;
    }
}
