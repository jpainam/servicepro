package com.cfao.app.reports;

import com.cfao.app.beans.Formation;
import com.cfao.app.model.FormationModel;
import com.cfao.app.util.AlertUtil;
import com.cfao.app.util.Report;
import javafx.application.Platform;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by JP on 7/13/2017.
 */
public class PrintFormation extends Report {
    FormationModel formationModel;
    HashMap<String, Object> parameters;
    public PrintFormation(){
        super();
        formationModel = new FormationModel();
        parameters = new HashMap<>();
        parameters.put("logo", logo);
    }
    public void showReport() {
        try {
            List<Formation> formations = formationModel.getList();
            JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(formations);
            parameters.put("DS1", formations);
            jasperDesign = JRXmlLoader.load(getClass().getClassLoader().getResourceAsStream("views/formation/formation.jrxml"));
            jasperReport = JasperCompileManager.compileReport(jasperDesign);
            JasperPrint print = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
            JasperViewer.viewReport(print, false);
        }catch (Exception ex){
            Platform.runLater(() -> AlertUtil.showErrorMessage(ex));
        }
    }

    public void showReport(Formation formation){
        try {
            parameters.put("formation", formation);
            parameters.put("DSFormateurs", formation.getPersonnels());
            parameters.put("DSCompetences", formation.getCompetences());
            parameters.put("DSParticipants", formation.getFormationPersonnes());
            parameters.put("today", new Date());
            jasperDesign = JRXmlLoader.load(getClass().getClassLoader().getResourceAsStream("views/formation/details.jrxml"));
            jasperReport = JasperCompileManager.compileReport(jasperDesign);
            JasperPrint print = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
            JasperViewer.viewReport(print, false);
        }catch (Exception ex){
            Platform.runLater(() -> AlertUtil.showErrorMessage(ex));
        }
    }
}
