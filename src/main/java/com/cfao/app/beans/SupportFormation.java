package com.cfao.app.beans;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * SupportFormation generated by hbm2java
 */
@Entity
@Table(name="support_formation"
        ,catalog="servicepro"
)
public class SupportFormation  implements java.io.Serializable {


    private SupportFormationId id;
    private ObjectProperty<Formation> formation = new SimpleObjectProperty<>();
    private ObjectProperty<Support> support =  new SimpleObjectProperty<>();

    public SupportFormation() {
    }

    public SupportFormation(SupportFormationId id, Formation formation, Support support) {
        this.id = id;
        this.formation.set(formation);
        this.support.set(support);
    }

    @EmbeddedId


    @AttributeOverrides( {
            @AttributeOverride(name="support", column=@Column(name="SUPPORT", nullable=false) ),
            @AttributeOverride(name="formation", column=@Column(name="FORMATION", nullable=false) ) } )
    public SupportFormationId getId() {
        return this.id;
    }

    public void setId(SupportFormationId id) {
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
    @JoinColumn(name="SUPPORT", nullable=false, insertable=false, updatable=false)
    public Support getSupport() {
        return this.support.get();
    }

    public void setSupport(Support support) {
        this.support.set(support);
    }




}

