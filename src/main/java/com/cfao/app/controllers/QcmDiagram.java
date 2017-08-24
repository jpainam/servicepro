package com.cfao.app.controllers;

import com.cfao.app.beans.PersonneQcm;
import com.cfao.app.beans.Qcm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JP on 8/23/2017.
 */
public class QcmDiagram {
    private Qcm qcm;

    public void setQcm(Qcm qcm) {
        this.qcm = qcm;
    }

    public PieChart createPieChart() {
        qcm.getPersonneQcms();
        ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
        int n = qcm.getBase() / 4;
        List<Integer> qcms = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            qcms.add(0);
        }
        for (PersonneQcm personneQcm : qcm.getPersonneQcms()) {
            if (personneQcm.getNote() <= n) {
                qcms.set(0, qcms.get(0) + 1);
            } else if (personneQcm.getNote() <= n * 2) {
                qcms.set(1, qcms.get(1) + 1);
            } else if (personneQcm.getNote() <= n * 3) {
                qcms.set(2, qcms.get(2) + 1);
            } else {
                qcms.set(3, qcms.get(3) + 1);
            }
        }
        data.add(new PieChart.Data("Moy < " + n, qcms.get(0)));
        data.add(new PieChart.Data("Moy < " + n * 2, qcms.get(1)));
        data.add(new PieChart.Data("Moy < " + n * 3, qcms.get(2)));
        data.add(new PieChart.Data("Moy < " + qcm.getBase(), qcms.get(3)));

        final PieChart pc = new PieChart(data);
        pc.setTitle("Analyse des résultats du Test");
        pc.setLegendVisible(false);
        //pc.setClockwise(true);
        pc.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        /*final Label caption = new Label("JESUISR");
        caption.setTextFill(Color.DARKORANGE);
        for (final PieChart.Data pieData : pc.getData()) {
            pieData.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED,
                    e -> {
                System.err.println(pieData.getPieValue());
                        ServiceproUtil.notify(pieData.getPieValue() + "%");
                    });
        }
        for (PieChart.Data pieData : data) {
            Label valueLabel = new Label(String.valueOf(pieData.getPieValue()));
            valueLabel.setTranslateX(pieData.getNode().getLayoutX());
            valueLabel.setTranslateY(pieData.getNode().getLayoutY());
            valueLabel.setVisible(true);
            valueLabel.toFront();
        }*/
        return pc;
    }

}
