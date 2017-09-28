package com.cfao.app.beans;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;

import javax.persistence.*;
import java.util.List;

/**
 * Created by JP on 9/3/2017.
 */
@Entity
@Table(name="lieu_formation"
)
public class LieuFormation {
    private SimpleIntegerProperty idlieu = new SimpleIntegerProperty();
    private SimpleStringProperty description = new SimpleStringProperty();
    private ListProperty<Formation> formations = new SimpleListProperty<>();

    public LieuFormation(){

    }

    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)


    @Column(name="IDLIEU", unique=true, nullable=false)
    public Integer getIdlieu() {
        return idlieu.get();
    }

    public SimpleIntegerProperty idlieuProperty() {
        return idlieu;
    }

    public void setIdlieu(int idlieu) {
        this.idlieu.set(idlieu);
    }

    @Column(name="DESCRIPTION", nullable=false, length=30)
    public String getDescription() {
        return description.get();
    }

    public SimpleStringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    @OneToMany(fetch=FetchType.LAZY, mappedBy="lieuFormation")
    public List<Formation> getFormations() {
        return formations.get();
    }

    public ListProperty<Formation> formationsProperty() {
        return formations;
    }

    public void setFormations(List<Formation> formations) {
        this.formations.set(FXCollections.observableArrayList(formations));
    }

    @Override
    public String toString(){
        return getDescription();
    }

}
