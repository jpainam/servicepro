package com.cfao.app.reports;

import com.cfao.app.util.Report;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.swing.JRViewer;
import net.sf.jasperreports.view.JasperViewer;

import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class PrintReport extends Report{
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public void showReport() throws Exception {

        //JasperDesign jasperDesign = JRXmlLoader.load(getClass().getClassLoader().getResourceAsStream("reports/qcm.jrxml"));
        //JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        // Fields for report
        HashMap<String, Object> parameters = new HashMap<String, Object>();

        parameters.put("company", "MAROTHIA TECHS");
        parameters.put("receipt_no", "RE101".toString());
        parameters.put("name", "Khushboo");
        parameters.put("amount", "10000");
        parameters.put("receipt_for", "EMI Payment");
        parameters.put("date", "20-12-2016");
        parameters.put("contact", "98763178".toString());
        showReport("views/formation/formation.jrxml", parameters);

        /*ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
        list.add(parameters);

        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(list);
        JasperPrint print = JasperFillManager.fillReport(jasperReport, null, beanColDataSource);
        //jasperPrint = JasperFillManager.fillReport(jasperReport, params, new JREmptyDataSource());

        /*FileOutputStream output = new FileOutputStream(new File("D:\\qcm.pdf"));
        JasperExportManager.exportReportToPdfStream(print, output);
        */
        //System.out.println("PDF created");
        //JasperViewer.viewReport(print);
        /*JRViewer viewer = new JRViewer(print);
        viewer.setOpaque(true);
        viewer.setVisible(true);
        this.add(viewer);
        this.setSize(700, 500);
        this.setVisible(true);*/
        //System.out.print("Done!");
    }
   /* public static  void main(String args[]){
        try{
            new PrintReport().showReport();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }*/
}