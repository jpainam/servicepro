package com.cfao.app.beans;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import javax.persistence.*;

/**
 * Created by armel on 01/07/2017.
 */

@Entity
@Table(name="langue"
        ,catalog="servicepro"
)
public class Langue {

    /**
     * FIELDS
     */
    private SimpleIntegerProperty idlangue = new SimpleIntegerProperty();
    private SimpleStringProperty libelle = new SimpleStringProperty();

    /*private ListProperty<Personne> personnesParlees = new SimpleListProperty<>();*/

    /** Liste des personnes qui ont cette langue comme premiere langue */
   /* private ListProperty<Personne> personnes = new SimpleListProperty<>();*/

    /**
     * CONSTRUCTS
     */

    /**
     * GETTERS
     */

    @Id @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="IDLANGUE")
    public Integer getIdLangue(){return idlangue.get();}

    @Column(name="LIBELLE", nullable=false, length=100)
    public String getLibelle(){return libelle.get();}

    /**
     * SETTERS
     */
    public void setIdLangue(Integer id){idlangue.set(id);}
    public void setLibelle(String lib){libelle.set(lib);}

    /**
     * METHODS
     */
    @Override
    public String toString(){
        return libelle.get();
    }

    public Boolean equals(Langue l){
        return (this.getIdLangue().equals(l.getIdLangue()));
    }

    /*
    @ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(name="langue_parlee", catalog="servicepro", joinColumns = {
            @JoinColumn(name="IDLANGUE", nullable=false, updatable=false) }, inverseJoinColumns = {
            @JoinColumn(name="IDPERS", nullable=false, updatable=false) })
    public List<Personne> getPersonnesParlees() {
        return personnesParlees.get();
    }

    public ListProperty<Personne> personnesParleesProperty() {
        return personnesParlees;
    }

    public void setPersonnesParlees(List<Personne> personnesParlees) {
        if(personnesParlees != null) {
            this.personnesParlees.set(FXCollections.observableArrayList(personnesParlees));
        }
    }

    @OneToMany(fetch=FetchType.LAZY, mappedBy="langue")
    public List<Personne> getPersonne() {
        return personnes.get();
    }

    public ListProperty<Personne> personnesProperty() {
        return personnes;
    }

    public void setPersonne(List<Personne> personnes) {
        this.personnes.set(FXCollections.observableArrayList(personnes));
    }
    */
}