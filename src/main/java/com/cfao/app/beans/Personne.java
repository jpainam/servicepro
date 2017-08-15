package com.cfao.app.beans;

import javafx.beans.property.*;
import javafx.collections.FXCollections;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by JP on 6/10/2017.
 */
@Entity
@Table(name="personnes"
)
public class Personne implements java.io.Serializable {

    /**
     * FIELDS
     */
    private SimpleIntegerProperty idpersonne = new SimpleIntegerProperty();
    private SimpleStringProperty matricule = new SimpleStringProperty();
    private SimpleStringProperty nom = new SimpleStringProperty();
    private SimpleStringProperty prenom = new SimpleStringProperty();
    private SimpleStringProperty autrenom = new SimpleStringProperty();
    private SimpleStringProperty telephone = new SimpleStringProperty();
    private SimpleStringProperty email = new SimpleStringProperty();
    private SimpleStringProperty memo = new SimpleStringProperty();
    private SimpleStringProperty photo = new SimpleStringProperty();
    private ObjectProperty<Date> datenaiss = new SimpleObjectProperty<Date>();
    private ObjectProperty<Date> fincontrat = new SimpleObjectProperty<Date>();
    private ListProperty<Langue> langues = new SimpleListProperty<Langue>();
    private ObjectProperty<Pays> pays = new SimpleObjectProperty<Pays>();
    private ObjectProperty<Groupe> groupe = new SimpleObjectProperty<Groupe>();
    private ObjectProperty<Societe> societe = new SimpleObjectProperty<Societe>();
    private ObjectProperty<Section> section = new SimpleObjectProperty<Section>();
    private ObjectProperty<Contrat> contrat = new SimpleObjectProperty<Contrat>();
    private ObjectProperty<Ambition> ambition = new SimpleObjectProperty<Ambition>();
    private ObjectProperty<Potentiel> potentiel = new SimpleObjectProperty<Potentiel>();
    private ObjectProperty<Langue> langue = new SimpleObjectProperty<>();
    private ListProperty<ProfilPersonne> profilPersonnes = new SimpleListProperty<ProfilPersonne>();
    private ListProperty<Poste> postes = new SimpleListProperty<Poste>();
    private ListProperty<Formation> formations = new SimpleListProperty<>();
    private ListProperty<PersonneCompetence> personneCompetences = new SimpleListProperty<>();
    private ListProperty<PersonneQcm> personneQcms = new SimpleListProperty<>();
    private ListProperty<FormationPersonne> formationPersonnes = new SimpleListProperty<>();
    private ObjectProperty<Date> expirationPassport = new SimpleObjectProperty<Date>();
    private SimpleStringProperty passport = new SimpleStringProperty();


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getIdpersonne() {
        return idpersonne.get();
    }

    public SimpleIntegerProperty idpersonneProperty() {
        return idpersonne;
    }

    public void setIdpersonne(int idpersonne) {
        this.idpersonne.set(idpersonne);
    }

    @Column(name = "MATRICULE")
    public String getMatricule() {
        return matricule.get();
    }

    public SimpleStringProperty matriculeProperty() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule.set(matricule);
    }

    @Column(name = "NOM")
    public String getNom() {
        return nom.get();
    }

    public SimpleStringProperty nomProperty() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom.set(nom);
    }

    @Column(name = "PRENOM")
    public String getPrenom() {
        return prenom.get();
    }

    public SimpleStringProperty prenomProperty() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom.set(prenom);
    }

    @Column(name = "TELEPHONE")
    public String getTelephone() {
        return telephone.get();
    }

    public SimpleStringProperty telephoneProperty() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone.set(telephone);
    }

    @Column(name = "EMAIL")
    public String getEmail() {
        return email.get();
    }

    public SimpleStringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    @Column(name = "MEMO")
    public String getMemo() {
        return memo.get();
    }

    public SimpleStringProperty memoProperty() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo.set(memo);
    }

    @Column(name = "PHOTO")
    public String getPhoto() {
        return photo.get();
    }

    public SimpleStringProperty photoProperty() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo.set(photo);
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "DATENAISS")
    public Date getDatenaiss() {
        return datenaiss.get();
    }

    public ObjectProperty<LocalDate> datenaissProperty() {
        return new SimpleObjectProperty<>(new java.sql.Date(datenaiss.get().getTime()).toLocalDate());
    }

    public void setDatenaiss(Date datenaiss) {
        this.datenaiss.set(datenaiss);
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "DATECONTRAT")
    public Date getDatecontrat() {
        return fincontrat.get();
    }

    public ObjectProperty<Date> fincontratProperty() {
        return fincontrat;
    }

    public void setDatecontrat(Date fincontrat) {
        this.fincontrat.set(fincontrat);
    }

    @ManyToMany
    @JoinTable(name = "langue_parlee", joinColumns = @JoinColumn(name = "PERSONNE"), inverseJoinColumns = @JoinColumn(name = "LANGUE"))
    public List<Langue> getLangues() {
        return langues.get();
    }

    public ListProperty<Langue> languesProperty() {
        return langues;
    }

    public void setLangues(List<Langue> langues) {
        this.langues.set(FXCollections.observableList(langues));
    }

    @ManyToOne
    @JoinColumn(name = "PAYS")
    public Pays getPays() {
        return pays.get();
    }

    public ObjectProperty<Pays> paysProperty() {
        return pays;
    }

    public void setPays(Pays pays) {
        this.pays.set(pays);
    }

    @ManyToOne
    @JoinColumn(name = "GROUPE")
    public Groupe getGroupe() {
        return groupe.get();
    }

    public ObjectProperty<Groupe> groupeProperty() {
        return groupe;
    }

    public void setGroupe(Groupe groupe) {
        this.groupe.set(groupe);
    }

    @ManyToOne
    @JoinColumn(name = "SOCIETE")
    public Societe getSociete() {
        return societe.get();
    }

    public ObjectProperty<Societe> societeProperty() {
        return societe;
    }

    public void setSociete(Societe societe) {
        this.societe.set(societe);
    }

    @ManyToOne
    @JoinColumn(name = "SECTION")
    public Section getSection() {
        return section.get();
    }

    public ObjectProperty<Section> sectionProperty() {
        return section;
    }

    public void setSection(Section section) {
        this.section.set(section);
    }

    @ManyToOne
    @JoinColumn(name = "CONTRAT")
    public Contrat getContrat() {
        return contrat.get();
    }

    public ObjectProperty<Contrat> contratProperty() {
        return contrat;
    }

    public void setContrat(Contrat contrat) {
        this.contrat.set(contrat);
    }

    @ManyToOne
    @JoinColumn(name = "AMBITION")
    public Ambition getAmbition() {
        return ambition.get();
    }

    public ObjectProperty<Ambition> ambitionProperty() {
        return ambition;
    }

    public void setAmbition(Ambition ambition) {
        this.ambition.set(ambition);
    }

    @ManyToOne
    @JoinColumn(name = "POTENTIEL")
    public Potentiel getPotentiel() {
        return potentiel.get();
    }

    public ObjectProperty<Potentiel> potentielProperty() {
        return potentiel;
    }

    public void setPotentiel(Potentiel potentiel) {
        this.potentiel.set(potentiel);
    }

    @ManyToOne
    @JoinColumn(name = "LANGUE")
    public Langue getLangue() {
        return langue.get();
    }

    public ObjectProperty<Langue> langueProperty() {
        return langue;
    }

    public void setLangue(Langue langue) {
        this.langue.set(langue);
    }

    @OneToMany(mappedBy = "personne", cascade = CascadeType.ALL)
    public List<ProfilPersonne> getProfilPersonnes() {
        return profilPersonnes.get();
    }

    public ListProperty<ProfilPersonne> profilPersonnesProperty() {
        return profilPersonnes;
    }

    public void setProfilPersonnes(List<ProfilPersonne> profilPersonnes) {
        this.profilPersonnes.set(FXCollections.observableList(profilPersonnes));
    }

    @OneToMany(mappedBy = "personne", cascade = CascadeType.ALL)
    public List<Poste> getPostes() {
        return postes.get();
    }

    public ListProperty<Poste> postesProperty() {
        return postes;
    }

    public void setPostes(List<Poste> postes) {
        this.postes.set(FXCollections.observableList(postes));
    }

    @ManyToMany
    @JoinTable(name="formation_personne", joinColumns =
    @JoinColumn(name="PERSONNE", nullable=false, updatable=false) , inverseJoinColumns =
    @JoinColumn(name="FORMATION", nullable=false, updatable=false))
    public List<Formation> getFormations() {
        return formations.get();
    }

    public ListProperty<Formation> formationsProperty() {
        return formations;
    }

    public void setFormations(List<Formation> formations) {
        if(formations != null)
            this.formations.set(FXCollections.observableList(formations));
    }

    @OneToMany(mappedBy="personne", cascade = CascadeType.ALL)
    public List<PersonneCompetence> getPersonneCompetences() {
        return personneCompetences.get();
    }

    public ListProperty<PersonneCompetence> personneCompetencesProperty() {
        return personneCompetences;
    }

    public void setPersonneCompetences(List<PersonneCompetence> personneCompetences) {
        if(personneCompetences != null)
            this.personneCompetences.set(FXCollections.observableList(personneCompetences));
    }

    @Transient
    public List<Competence> getCompetences(){
        List<Competence> competenceList = new ArrayList<>();
        for(PersonneCompetence co : personneCompetences){
            competenceList.add(co.getCompetence());
        }
        return competenceList;
    }

    @OneToMany(mappedBy = "personne", cascade = CascadeType.ALL)
    public List<PersonneQcm> getPersonneQcms() {
        return personneQcms.get();
    }

    public ListProperty<PersonneQcm> personneQcmsProperty() {
        return personneQcms;
    }

    public void setPersonneQcms(List<PersonneQcm> personneQcms) {
        if(personneQcms!= null)
            this.personneQcms.set(FXCollections.observableList(personneQcms));
    }

    @OneToMany(fetch=FetchType.LAZY, mappedBy="personne")
    public List<FormationPersonne> getFormationPersonnes() {
        return formationPersonnes.get();
    }

    public ListProperty<FormationPersonne> formationPersonnesProperty() {
        return formationPersonnes;
    }

    public void setFormationPersonnes(List<FormationPersonne> formationPersonnes) {
        if(formationPersonnes != null) {
            this.formationPersonnes.set(FXCollections.observableArrayList(formationPersonnes));
        }
    }

    public ObjectProperty<Societe> societe() {
        return societe;
    }

    @Column(name = "AUTRENOM")
    public String getAutrenom() {
        return autrenom.get();
    }

    public SimpleStringProperty autrenomProperty() {
        return autrenom;
    }

    public void setAutrenom(String autrenom) {
        this.autrenom.set(autrenom);
    }

    @Override
    public String toString(){
        return this.getNom() + " " + this.getPrenom();
    }

    public ObjectProperty<LocalDate> datecontratProperty() {
        if(fincontrat != null) {
            return new SimpleObjectProperty<>(new java.sql.Date(fincontrat.get().getTime()).toLocalDate());
        }
        return new SimpleObjectProperty<>(new java.sql.Date(new Date().getTime()).toLocalDate());
    }
    @Column(name = "PASSPORT")
    public String getPassport() {
        return passport.get();
    }

    public SimpleStringProperty passportProperty() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport.set(passport);
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "EXPIRE")
    public Date getExpirationPassport() {
        return expirationPassport.get();
    }

    public ObjectProperty<Date> expirationPassportProperty() {
        return expirationPassport;
    }

    public void setExpirationPassport(Date expire) {
        this.expirationPassport.set(expire);
    }
}