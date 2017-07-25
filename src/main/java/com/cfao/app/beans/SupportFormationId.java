package com.cfao.app.beans;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * SupportFormationId generated by hbm2java
 */
@Embeddable
public class SupportFormationId  implements java.io.Serializable {


    private int support;
    private int formation;

    public SupportFormationId() {
    }

    public SupportFormationId(int support, int formation) {
        this.support = support;
        this.formation = formation;
    }



    @Column(name="SUPPORT", nullable=false)
    public int getSupport() {
        return this.support;
    }

    public void setSupport(int support) {
        this.support = support;
    }


    @Column(name="FORMATION", nullable=false)
    public int getFormation() {
        return this.formation;
    }

    public void setFormation(int formation) {
        this.formation = formation;
    }


    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( (other == null ) ) return false;
        if ( !(other instanceof SupportFormationId) ) return false;
        SupportFormationId castOther = ( SupportFormationId ) other;

        return (this.getSupport()==castOther.getSupport())
                && (this.getFormation()==castOther.getFormation());
    }

    public int hashCode() {
        int result = 17;

        result = 37 * result + this.getSupport();
        result = 37 * result + this.getFormation();
        return result;
    }


}


