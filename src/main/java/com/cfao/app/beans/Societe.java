package com.cfao.app.beans;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by JP on 6/11/2017.
 */
public class Societe {
    private SimpleIntegerProperty idsociete = new SimpleIntegerProperty();
    private SimpleStringProperty nom = new SimpleStringProperty();
    private SimpleStringProperty code = new SimpleStringProperty();
    private SimpleStringProperty contact = new SimpleStringProperty();
    private SimpleStringProperty telephone = new SimpleStringProperty();
    private SimpleStringProperty email = new SimpleStringProperty();
    private SimpleStringProperty fax = new SimpleStringProperty();

    private SimpleStringProperty adresse = new SimpleStringProperty();

    public Societe(int idsociete, String nom, String code, String contact,
                   String telephone, String email, String fax,  String adresse) {
        this.idsociete.set(idsociete);
        this.nom.set(nom);
        this.code.set(code);
        this.contact.set(contact);
        this.telephone.set(telephone);
        this.email.set(email);
        this.fax.set(fax);
        this.adresse.set(adresse);
    }
    public Societe(){}

    public SimpleIntegerProperty idsocieteProperty() {
        return idsociete;
    }

    public SimpleStringProperty nomProperty() {
        return nom;
    }

    public SimpleStringProperty codeProperty() {
        return code;
    }

    public SimpleStringProperty contactProperty() {
        return contact;
    }

    public SimpleStringProperty telephoneProperty() {
        return telephone;
    }

    public SimpleStringProperty emailProperty() {
        return email;
    }

    public SimpleStringProperty faxProperty() {
        return fax;
    }

    public SimpleStringProperty adresseProperty() {
        return adresse;
    }

    public int getIdsociete() {
        return idsociete.get();
    }

    public String getNom() {
        return nom.get();
    }


    public String getAdresse() {
        return adresse.get();
    }

    public void setIdsociete(int idsociete) {
        this.idsociete.set(idsociete);
    }

    public void setNom(String nom) {
        this.nom.set(nom);
    }

    public void setAdresse(String adresse) {
        this.adresse.set(adresse);
    }

    @Override
    public String toString() {
        return  getNom();
    }

    public String getCode() {
        return code.get();
    }


    public void setCode(String code) {
        this.code.set(code);
    }

    public String getContact() {
        return contact.get();
    }


    public void setContact(String contact) {
        this.contact.set(contact);
    }

    public String getTelephone() {
        return telephone.get();
    }


    public void setTelephone(String telephone) {
        this.telephone.set(telephone);
    }

    public String getEmail() {
        return email.get();
    }


    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getFax() {
        return fax.get();
    }


    public void setFax(String fax) {
        this.fax.set(fax);
    }
}
