package com.cfao.app.util;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by JP on 7/13/2017.
 */
public class Report {
    protected JasperDesign jasperDesign;
    protected JasperReport jasperReport;
    static{
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }

    public Report(){ }

    public void showReport(String jrxmlFile, HashMap<String, Object> parameters){
        try {
            jasperDesign = JRXmlLoader.load(getClass().getClassLoader().getResourceAsStream(jrxmlFile));
            jasperReport = JasperCompileManager.compileReport(jasperDesign);

            ArrayList<HashMap<String, Object>> list = new ArrayList<>();
            list.add(parameters);
            JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(list);
            JasperPrint print = JasperFillManager.fillReport(jasperReport, null, beanColDataSource);
            JasperViewer.viewReport(print, false);
        }catch (Exception ex){
            ex.printStackTrace();
            AlertUtil.showErrorMessage(ex);
        }
    }

}
