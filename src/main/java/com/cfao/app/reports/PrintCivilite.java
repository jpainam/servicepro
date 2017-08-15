package com.cfao.app.reports;

import com.cfao.app.beans.Personne;
import com.cfao.app.controllers.CivilitePhoto;
import com.cfao.app.model.PersonneModel;
import com.cfao.app.util.AlertUtil;
import com.cfao.app.util.Report;
import javafx.application.Platform;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by JP on 8/14/2017.
 */
public class PrintCivilite extends Report {
    HashMap<String, Object> parameters;
    PersonneModel personneModel;

    BufferedImage photo;
    CivilitePhoto civilitePhoto = new CivilitePhoto();

    public PrintCivilite(){
        super();
        parameters = new HashMap<>();
        parameters.put("logo", logo);
    }
    public void showDetails(Personne personne) {
        try {
            parameters.put("personne", personne);
            /*parameters.put("DSFormateurs", formation.getPersonnels());
            parameters.put("DSCompetences", formation.getCompetences());
            parameters.put("DSParticipants", formation.getFormationPersonnes());*/
            parameters.put("DSLangues", personne.getLangues());
            parameters.put("DSCompetences", personne.getPersonneCompetences());
            parameters.put("DSFormations", personne.getFormations());
            parameters.put("DSTests", personne.getPersonneQcms());
            parameters.put("today", new Date());
            photo = ImageIO.read(civilitePhoto.getImagePath(personne));
            parameters.put("photo", photo);
            jasperDesign = JRXmlLoader.load(getClass().getClassLoader().getResourceAsStream("views/civilite/details.jrxml"));
            jasperReport = JasperCompileManager.compileReport(jasperDesign);
            JasperPrint print = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
            JasperViewer.viewReport(print, false);
        } catch (Exception ex) {
            Platform.runLater(() -> AlertUtil.showErrorMessage(ex));
        }
    }
}
