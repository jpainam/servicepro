package com.cfao.app.util;

import com.cfao.app.Module;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import org.controlsfx.control.BreadCrumbBar;

import java.util.HashMap;

/**
 * Created by JP on 6/28/2017.
 */
public class BreadcrumbUtil extends HBox {

    public static BreadCrumbItem ACCUEIL;

    public static BreadCrumbBar<String> breadCrumb = new BreadCrumbBar<>();
    public BreadcrumbUtil(){
        super();
        this.setSpacing(10);
        buildBreadCrumb();
        breadCrumb.setAutoNavigationEnabled(true);
        HBox.setHgrow(breadCrumb, Priority.ALWAYS);
        this.getChildren().addAll(breadCrumb);
    }

    public void buildBreadCrumb(){
        //ACCUEIL = new BreadCrumbItem("Accueil", Module.accueil);
    }

}

class BreadCrumbItem{
    public  String breadCrumbLibelle;
    public Pane breadCrumbContent;

    public BreadCrumbItem(String libelle, Pane content){
        this.breadCrumbContent = content;
        this.breadCrumbLibelle = libelle;
    }

    @Override
    public String toString() {
        return breadCrumbLibelle;
    }
}
