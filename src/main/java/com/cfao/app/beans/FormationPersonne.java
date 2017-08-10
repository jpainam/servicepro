package com.cfao.app.beans;
// Generated Aug 9, 2017 5:49:15 PM by Hibernate Tools 4.3.1


import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import javax.persistence.*;

/**
 * LangueParlee generated by hbm2java
 */
@Entity
@Table(name="formation_personne"
        ,catalog="servicepro"
)
public class FormationPersonne  implements java.io.Serializable {


    private FormationPersonneId id;
    private ObjectProperty<Formation> formation = new SimpleObjectProperty<>();
    private ObjectProperty<Personne> personne = new SimpleObjectProperty<>();
    private SimpleStringProperty document = new SimpleStringProperty();

    public FormationPersonne() {
    }

    public FormationPersonne(FormationPersonneId id, Formation formation, Personne personne) {
        this.id = id;
        this.formation.set(formation);
        this.personne.set(personne);
    }

    @EmbeddedId


    @AttributeOverrides( {
            @AttributeOverride(name="personne", column=@Column(name="PERSONNE", nullable=false) ),
            @AttributeOverride(name="formation", column=@Column(name="FORMATION", nullable=false) ) } )
    public FormationPersonneId getId() {
        return this.id;
    }

    public void setId(FormationPersonneId id) {
        this.id = id;
    }

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="FORMATION", nullable=false, insertable=false, updatable=false)
    public Formation getFormation() {
        return this.formation.get();
    }

    public void setFormation(Formation formation) {
        this.formation.set(formation);
    }

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="PERSONNE", nullable=false, insertable=false, updatable=false)
    public Personne getPersonne() {
        return this.personne.get();
    }

    public void setPersonne(Personne personne) {
        this.personne.set(personne);
    }


    @Column(name="DOCUMENT", length=150)
    public String getDocument() {
        return document.get();
    }

    public SimpleStringProperty documentProperty() {
        return document;
    }

    public void setDocument(String document) {
        this.document.set(document);
    }
}


