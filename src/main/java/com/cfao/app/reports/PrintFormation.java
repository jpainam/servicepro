package com.cfao.app.reports;

import com.cfao.app.beans.Formation;
import com.cfao.app.model.FormationModel;
import com.cfao.app.util.Report;
import javafx.scene.image.Image;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.*;

/**
 * Created by JP on 7/13/2017.
 */
public class PrintFormation extends Report {
    public void showReport() throws Exception {
        FormationModel formationModel = new FormationModel();
        List<Formation> formations = formationModel.getList();
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(formations);
        HashMap<String, Object> parameters = new HashMap<>();
        BufferedImage logo = ImageIO.read(getClass().getResource(ResourceBundle.getBundle("Application").getString("app.logo")));
        parameters.put("logo", logo);
        parameters.put("DS1", formations);

        jasperDesign = JRXmlLoader.load(getClass().getClassLoader().getResourceAsStream("views/formation/formation.jrxml"));
        jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint print = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
        JasperViewer.viewReport(print, false);

    }
}
