package com.cfao.app.controllers;

import com.cfao.app.beans.Personne;
import com.cfao.app.beans.PersonneQcm;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

/**
 * Created by JP on 8/3/2017.
 */
public class CiviliteQcmDiagram {
    private Personne personne;

    public CiviliteQcmDiagram(Personne personne) {
        this.personne = personne;
    }

    public CiviliteQcmDiagram() {

    }

    public void setPersonne(Personne personne) {
        this.personne = personne;
    }


    public BarChart<String, Number> createChart() {
        if (personne == null) {
            return null;
        }
        final String[] years = {"2007", "2008", "2009"};
        final CategoryAxis xAxis = new CategoryAxis();
        //final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        //yAxis.setTickLabelFormatter(new NumberAxis.DefaultFormatter(yAxis, "$", null));
        final BarChart<String, Number> bc = new BarChart<String, Number>(xAxis, yAxis);
        // setup chart
        bc.setTitle("Note obtenue par tests");
        //xAxis.setLabel("Tests");
        //xAxis.setCategories(FXCollections.<String>observableArrayList(Arrays.asList(years)));
        yAxis.setLabel("Note obtenue");
        for(PersonneQcm personneQcm : personne.getPersonneQcms()){
            XYChart.Series<String, Number> series = new XYChart.Series<String, Number>();
            series.setName(personneQcm.getQcm().getTitre());
            series.getData().add(new XYChart.Data<String, Number>("", personneQcm.getNote()));
            bc.getData().add(series);
        }
        // add starting data
        /*XYChart.Series<String, Number> series1 = new XYChart.Series<String, Number>();
        series1.setName("Data Series 1");
        XYChart.Series<String, Number> series2 = new XYChart.Series<String, Number>();
        series2.setName("Data Series 2");
        XYChart.Series<String, Number> series3 = new XYChart.Series<String, Number>();
        series3.setName("Data Series 3");
        // create sample data
        series1.getData().add(new XYChart.Data<String, Number>(years[0], 567));
        series1.getData().add(new XYChart.Data<String, Number>(years[1], 1292));
        series1.getData().add(new XYChart.Data<String, Number>(years[2], 2180));
        series2.getData().add(new XYChart.Data<String, Number>(years[0], 956));
        series2.getData().add(new XYChart.Data<String, Number>(years[1], 1665));
        series2.getData().add(new XYChart.Data<String, Number>(years[2], 2450));
        series3.getData().add(new XYChart.Data<String, Number>(years[0], 800));
        series3.getData().add(new XYChart.Data<String, Number>(years[1], 1000));
        series3.getData().add(new XYChart.Data<String, Number>(years[2], 2800));
        bc.getData().add(series1);
        bc.getData().add(series2);
        bc.getData().add(series3);*/
        return bc;
    }

}
