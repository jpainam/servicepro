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

    public int getIdPays() {
        return idPays.get();
    }

    public SimpleIntegerProperty idPaysProperty() {
        return idPays;
    }

    public void setIdPays(int idPays) {
        this.idPays.set(idPays);
    }

    public SimpleStringProperty isoProperty() {
        return iso;
    }

    public SimpleStringProperty nameFrProperty() {
        return nameFr;
    }

    public SimpleStringProperty nameEnProperty() {
        return nameEn;
    }

    public SimpleStringProperty iso3Property() {
        return iso3;
    }

    public SimpleIntegerProperty numCodeProperty() {
        return numCode;
    }

    public SimpleIntegerProperty phoneCodeProperty() {
        return phoneCode;
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

    public void setNumCode(Integer numCode)
    {
        if(numCode != null)
                       this.numCode.set(numCode);
             else
                    this.numCode.set(0);

    }

    public void setPhoneCode(Integer phoneCode) {
        this.phoneCode.set(phoneCode);
    }

    @Override
    public String toString() {
        return  getNameFr() ;
    }
}
