package com.cfao.app.beans;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.persistence.*;

import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;

/**
 * Created by JP on 6/10/2017.
 */
@Entity
@Table(name="personnes"
        ,catalog="servicepro"
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


    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="IDPERSONNE", unique=true, nullable=false)
    public int getIdpersonne() {
        return this.idpersonne.get();
    }

    public void setIdpersonne(Integer idpersonne) {
        this.idpersonne.set(idpersonne);
    }

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="AMBITION")
    public Ambition getAmbition() {
        return this.ambition.get();
    }

    public void setAmbition(Ambition ambition) {
        this.ambition.set(ambition);
    }

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="CONTRAT")
    public Contrat getContrat() {
        return this.contrat.get();
    }

    public void setContrat(Contrat contrat) {
        this.contrat.set(contrat);
    }

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="GROUPE")
    public Groupe getGroupe() {
        return this.groupe.get();
    }

    public void setGroupe(Groupe groupe) {
        this.groupe.set(groupe);
    }

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="LANGUE")
    public Langue getLangue() {
        return this.langue.get();
    }

    public void setLangue(Langue langue) {
        this.langue.set(langue);
    }

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="PAYS")
    public Pays getPays() {
        return this.pays.get();
    }

    public void setPays(Pays pays) {
        this.pays.set(pays);
    }

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="POTENTIEL")
    public Potentiel getPotentiel() {
        return this.potentiel.get();
    }

    public void setPotentiel(Potentiel potentiel) {
        this.potentiel.set(potentiel);
    }

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="SECTION")
    public Section getSection() {
        return this.section.get();
    }

    public void setSection(Section section) {
        this.section.set(section);
    }

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="SOCIETE")
    public Societe getSociete() {
        return this.societe.get();
    }

    public void setSociete(Societe societe) {
        this.societe.set(societe);
    }


    @Column(name="MATRICULE", nullable=false, length=15)
    public String getMatricule() {
        return this.matricule.get();
    }

    public void setMatricule(String matricule) {
        this.matricule.set(matricule);
    }


    @Column(name="NOM", nullable=false, length=30)
    public String getNom() {
        return this.nom.get();
    }

    public void setNom(String nom) {
        this.nom.set(nom);
    }


    @Column(name="PRENOM", nullable=false, length=30)
    public String getPrenom() {
        return this.prenom.get();
    }

    public void setPrenom(String prenom) {
        this.prenom.set(prenom);
    }


    @Column(name="AUTRENOM", length=30)
    public String getAutrenom() {
        return this.autrenom.get();
    }

    public void setAutrenom(String autrenom) {
        this.autrenom.set(autrenom);
    }

    @Temporal(TemporalType.DATE)
    // @Temporal should only be set on a java.util.Date or java.util.Calendar property: com.cfao.app.beans.Personne.datenaiss
    @Column(name="DATENAISS", nullable=false, length=10)
    public Date getDatenaiss() {
        return datenaiss.get();
    }

    public void setDatenaiss(Date datenaiss) {
        this.datenaiss.set(datenaiss);
    }


    @Column(name="TELEPHONE", length=20)
    public String getTelephone() {
        return this.telephone.get();
    }

    public void setTelephone(String telephone) {
        this.telephone.set(telephone);
    }


    @Column(name="EMAIL", length=50)
    public String getEmail() {
        return this.email.get();
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    @Temporal(TemporalType.DATE)
    @Column(name="DATECONTRAT", length=10)
    public Date getDatecontrat() {
       return fincontrat.get();

    }

    public void setDatecontrat(Date datecontrat) {
            this.fincontrat.set(datecontrat);

    }


    @Column(name="MEMO")
    public String getMemo() {
        return this.memo.get();
    }

    public void setMemo(String memo) {
        this.memo.set(memo);
    }

    @OneToMany(fetch=FetchType.LAZY, mappedBy="personne")
    public List<Poste> getPostes() {
        return this.postes;
    }

    public void setPostes(List<Poste> postes) {
        this.postes.set(FXCollections.observableList(postes));
    }

    @OneToMany(fetch=FetchType.LAZY, mappedBy="personne")
    public List<ProfilPersonne> getProfilPersonnes() {
        return this.profilPersonnes.get();
    }

    public void setProfilPersonnes(List<ProfilPersonne> profilPersonnes) {
        this.profilPersonnes.set(FXCollections.observableList(profilPersonnes));
    }

    @ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(name="langue_parlee", catalog="servicepro", joinColumns = {
            @JoinColumn(name="IDPERS", nullable=false, updatable=false) }, inverseJoinColumns = {
            @JoinColumn(name="IDLANGUE", nullable=false, updatable=false) })
    public List<Langue> getLangues() {
        return this.langues.get();
    }

    public void setLangues(List<Langue> langues) {
        this.langues.set(FXCollections.observableList(langues));
    }

    @ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(name="formation_personne", catalog="servicepro", joinColumns = {
            @JoinColumn(name="PERSONNE", nullable=false, updatable=false) }, inverseJoinColumns = {
            @JoinColumn(name="FORMATION", nullable=false, updatable=false) })
    public List<Formation> getFormations() {
        return this.formations;
    }

    public void setFormations(List<Formation> formations) {
        this.formations.set(FXCollections.observableList(formations));
    }


    public SimpleStringProperty telephoneProperty() {
        return telephone;
    }

    public ObjectProperty<Societe> societe() {
        return societe;
    }


    public SimpleStringProperty matriculeProperty() {
        return matricule;
    }

    public SimpleStringProperty nomProperty() {
        return nom;
    }

    public SimpleStringProperty prenomProperty() {
        return prenom;
    }

    public ObjectProperty<Pays> pays() {
        return pays;
    }

    public ObjectProperty<Date> datenaiss() {
        return datenaiss;
    }

    public ObjectProperty<Section> section() {
        return section;
    }

    public ObjectProperty<Groupe> groupe() {
        return groupe;
    }

    public ListProperty<Langue> langues() {
        return langues;
    }

    public ObjectProperty<Date> datecontratProperty() {
        return fincontrat;
    }

    public SimpleStringProperty memoProperty() {
        return memo;
    }

    public SimpleStringProperty emailProperty() {
        return email;
    }

    public ObjectProperty<Potentiel> potentiel() {
        return potentiel;
    }

    public ObjectProperty<Date> naissance() {
        return datenaiss;
    }

}