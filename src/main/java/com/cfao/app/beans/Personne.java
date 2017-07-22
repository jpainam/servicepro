package com.cfao.app.beans;

import com.cfao.app.util.FormatDate;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.geometry.Pos;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by JP on 6/10/2017.
 */
@Entity
@Table(name = "personnes")
public class Personne {

    /**
     * FIELDS
     */
    private SimpleIntegerProperty idPersonne = new SimpleIntegerProperty();
    private SimpleStringProperty matricule = new SimpleStringProperty();
    private SimpleStringProperty nom = new SimpleStringProperty();
    private SimpleStringProperty prenom = new SimpleStringProperty();
    private SimpleStringProperty telephone = new SimpleStringProperty();
    private SimpleStringProperty email = new SimpleStringProperty();
    private StringProperty memo = new SimpleStringProperty();
    private ObjectProperty<LocalDate> naissance = new SimpleObjectProperty<LocalDate>();
    private ObjectProperty<LocalDate> fincontrat = new SimpleObjectProperty<LocalDate>();
    private ListProperty<Langue> langues = new SimpleListProperty<Langue>();
    private ObjectProperty<Pays> pays = new SimpleObjectProperty<Pays>();
    private ObjectProperty<Groupe> groupe = new SimpleObjectProperty<Groupe>();
    private ObjectProperty<Societe> societe = new SimpleObjectProperty<Societe>();
    private ObjectProperty<Section> section = new SimpleObjectProperty<Section>();
    private ObjectProperty<Contrat> contrat = new SimpleObjectProperty<Contrat>();
    private ObjectProperty<Ambition> ambition = new SimpleObjectProperty<Ambition>();
    private ObjectProperty<Potentiel> potentiel = new SimpleObjectProperty<Potentiel>();
    private ObjectProperty<Langue> langue = new SimpleObjectProperty<>();

    //private ListProperty<Profil> profils = new SimpleListProperty<>();
    private ListProperty<ProfilPersonne> profilPersonne = new SimpleListProperty<ProfilPersonne>();
    private ListProperty<Poste> postePersonne = new SimpleListProperty<Poste>();
    /**
     * CONSTRUCT
     */


    /**
     * GETTERS - SETTERS - GET PROPERTY
     */

    @Id
    @GeneratedValue
    @Column(name = "IDPERSONNE")
    public int getIdPersonne() {
        return idPersonne.get();
    }
    public void setIdPersonne(int idPersonne) {
        this.idPersonne.set(idPersonne);
    }
    public IntegerProperty idPersonneProperty() {
        return idPersonne;
    }

    @Column(name = "MATRICULE")
    public String getMatricule() {
        return matricule.get();
    }
    public void setMatricule(String matricule) {
        this.matricule.set(matricule);
    }
    public StringProperty matriculeProperty() {
        return matricule;
    }

    @Column(name = "NOM")
    public String getNom() {
        return nom.get();
    }
    public void setNom(String nom) {
        this.nom.set(nom);
    }
    public StringProperty nomProperty() {
        return nom;
    }

    @Column(name = "PRENOM")
    public String getPrenom() {
        return prenom.get();
    }
    public void setPrenom(String prenom) {
        this.prenom.set(prenom);
    }
    public StringProperty prenomProperty() {
        return prenom;
    }

    @Column(name = "DATENAISS")
    public Date getNaissance() {
        return Date.valueOf(naissance.get());
    }
    public void setNaissance(Date date) {
        this.naissance.set(date.toLocalDate());
    }
    public ObjectProperty<LocalDate> naissance() {
        return naissance;
    }

    @Column(name = "MEMO")
    public String getMemo() {return memo.get();}
    public StringProperty memoProperty() {return memo;}
    public void setMemo(String memo) {this.memo.set(memo);}

    @Column(name = "DATECONTRAT")
    public Date getFincontrat() {
        if(fincontrat.get() != null)
            return Date.valueOf(fincontrat.get());
        return Date.valueOf(LocalDate.now());
    }
    public void setFincontrat(Date date) {
        if(date != null)
            this.fincontrat.set(date.toLocalDate());
    }
    public ObjectProperty<LocalDate> fincontratProperty() {
        return fincontrat;
    }

    @ManyToMany()
    @JoinTable(name = "langue_parlee", joinColumns = {@JoinColumn(name = "IDPERS")},
            inverseJoinColumns = {@JoinColumn(name = "IDLANGUE")})
    public java.util.List<Langue> getLangues() {
        return langues.get();
    }
    public void setLangues(java.util.List<Langue> set) {
        this.langues.set(FXCollections.observableList(set));
    }
    public ListProperty<Langue> langues() {
        return langues;
    }

    @ManyToOne()
    @JoinColumn(name = "PAYS")
    public Pays getPays() {
        return pays.get();
    }
    public void setPays(Pays pays) {
        this.pays.set(pays);
    }
    public ObjectProperty<Pays> pays() {
        return pays;
    }

    @ManyToOne()
    @JoinColumn(name = "GROUPE")
    public Groupe getGroupe() {
        return groupe.get();
    }
    public void setGroupe(Groupe groupe) {
        this.groupe.set(groupe);
    }
    public ObjectProperty<Groupe> groupe() {
        return groupe;
    }

    @ManyToOne()
    @JoinColumn(name = "SOCIETE")
    public Societe getSociete() {
        return societe.get();
    }
    public void setSociete(Societe societe) {
        this.societe.set(societe);
    }
    public ObjectProperty<Societe> societe() {
        return societe;
    }

    @ManyToOne()
    @JoinColumn(name = "SECTION")
    public Section getSection() {
        return section.get();
    }
    public void setSection(Section section) {
        this.section.set(section);
    }
    public ObjectProperty<Section> section() {
        return section;
    }

    @ManyToOne()
    @JoinColumn(name = "CONTRAT")
    public Contrat getContrat() {
        return contrat.get();
    }
    public void setContrat(Contrat contrat) {
        this.contrat.set(contrat);
    }
    public ObjectProperty<Contrat> contratProperty() {
        return contrat;
    }

    @ManyToOne()
    @JoinColumn(name = "AMBITION")
    public Ambition getAmbition() {
        return ambition.get();
    }
    public void setAmbition(Ambition ambition) {
        this.ambition.set(ambition);
    }
    public ObjectProperty<Ambition> ambitionProperty() {
        return ambition;
    }


    @ManyToOne()
    @JoinColumn(name="LANGUE")
    public Langue getLangue() {
        return langue.get();
    }
    public void setLangue(Langue langue) {
        this.langue.set(langue);
    }
    public ObjectProperty<Langue> langueProperty() {
        return langue;
    }

    @ManyToOne()
    @JoinColumn(name = "POTENTIEL")
    public Potentiel getPotentiel() {
        return potentiel.get();
    }
    public void setPotentiel(Potentiel potentiel) {
        this.potentiel.set(potentiel);
    }
    public ObjectProperty<Potentiel> potentielProperty() {
        return potentiel;
    }


    @OneToMany(mappedBy = "pk.personne", cascade = CascadeType.ALL)
    public java.util.List<ProfilPersonne> getProfilPersonne() {
        return profilPersonne.get();
    }
    public void setProfilPersonne(java.util.List<ProfilPersonne> set){
        this.profilPersonne.set(FXCollections.observableList(set));
    }

    @OneToMany(mappedBy = "pk.personne", cascade = CascadeType.ALL)
    public java.util.List<Poste> getPostePersonne() {
        return postePersonne.get();
    }
    public void setPostePersonne(java.util.List<Poste> set){
        this.postePersonne.set(FXCollections.observableList(set));
    }


    /**
     * METHODS
     */
    @Override
    public String toString() {
        return getNom() + " " + getPrenom();
    }



    @Transient
    public List<Profil> getProfils(){
        List<Profil> profils = new ArrayList<>();
        for(ProfilPersonne pp : profilPersonne){
            profils.add(pp.getProfil());
        }
        return profils;
    }

    public String getTelephone() {
        return telephone.get();
    }

    public SimpleStringProperty telephoneProperty() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone.set(telephone);
    }

    public String getEmail() {
        return email.get();
    }

    public SimpleStringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    /*
    @ManyToMany()
    @JoinTable(name = "personne_profil", joinColumns = {@JoinColumn(name = "PERSONNE")},
            inverseJoinColumns = {@JoinColumn(name = "PROFIL")})
    public List<Profil> getProfils() {
        return profils.get();
    }

    public ListProperty<Profil> profilsProperty() {
        return profils;
    }

    public void setProfils(List<Profil> profils) {
        this.profils.set(FXCollections.observableArrayList(profils));
    }
        */
}