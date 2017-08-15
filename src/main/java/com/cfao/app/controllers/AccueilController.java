package com.cfao.app.controllers;

import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by JP on 6/21/2017.
 */
public class AccueilController implements Initializable {


    public AnchorPane personneStatContent;
    public AccueilPersonneController personneController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initComponents();
        personneController = new AccueilPersonneController();
        personneStatContent.getChildren().setAll(personneController);
        // Use binding to be notified whenever the data source chagnes
        /*Task<ObservableList<DailySales>> task = new GetDailySalesTask();
        p.progressProperty().bind(task.progressProperty());
        veil.visibleProperty().bind(task.runningProperty());
        p.visibleProperty().bind(task.runningProperty());
        tableView.itemsProperty().bind(task.valueProperty());

        root.getChildren().add(stack);
        new Thread(task).start();
        */
        // create the data to show in the CheckListView

    }

    /*protected void createFormationList() {
        Label label = new Label("Formations : ");
        HBox reserchePanel = new HBox();
        HBox.setHgrow(searchBox1, Priority.ALWAYS);
        reserchePanel.setSpacing(10);
        HBox.setMargin(reserchePanel, new Insets(10, 10, 10, 10));
        searchBox1.setMaxWidth(Double.MAX_VALUE);
        reserchePanel.setMaxWidth(Double.MAX_VALUE);
        reserchePanel.getChildren().addAll(label, searchBox1);

        //FormationModel formationModel = new FormationModel(Model.getBeansClass("Formation"));
        formationListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        VBox.setVgrow(formationListView, Priority.ALWAYS);

        /* Task<ObservableList<Formation>> task = new Task<ObservableList<Formation>>() {
            @Override
            protected ObservableList<Formation> call() throws Exception {

                return FXCollections.observableArrayList(formationModel.getList());
            }
        };

        new Thread(task).start();
        task.setOnSucceeded(event -> {
            formationListView.setItems(task.getValue());
        });
        */
        /*String[] formations = {"CFAO Academy", "JCB Distance Learning", "JCB JDS","Introduction conduite engins",
                "Mécanique de base CFAO Academy", "Niveau Initial CFAO Academy (4 modules)"};
        final ObservableList<String> strings = FXCollections.observableArrayList();
        for (int i = 0; i < formations.length; i++) {
            strings.add(formations[i]);
        }
        formationListView.setItems(strings);
        /*formationListView.getCheckModel().getCheckedItems().addListener(new ListChangeListener<Formation>() {
            @Override
            public void onChanged(Change<? extends Formation> c) {
                System.out.println(formationListView.getCheckModel().getCheckedItems());
            }
        });*/

        /*formationListView.getCheckModel().getCheckedItems().addListener(new ListChangeListener<String>() {
            public void onChanged(ListChangeListener.Change<? extends String> c) {
                System.out.println(formationListView.getCheckModel().getCheckedItems());
            }
        });

        formationBox.setPadding(new Insets(10, 10, 10, 10));
        formationBox.setSpacing(10);
        formationBox.getChildren().addAll(reserchePanel, formationListView);
    }
    */

    private void initComponents() {

    }




    /*protected AreaChart<Number, Number> createChartFormation() {
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        AreaChart<Number, Number> ac = new AreaChart<Number, Number>(xAxis, yAxis);
        // setup chart
        ac.setTitle("Performance générale des formations / Overall Training Performance");
        xAxis.setLabel("Nombre de participants");
        yAxis.setLabel("Moyenne obtenue");
        // add starting data
        String[] checked = {"CFAO Academy", "JCB JDS", "Introduction conduite d'engins"};
        for (int s = 0; s < 3; s++) {
            XYChart.Series<Number, Number> series = new XYChart.Series<Number, Number>();
            series.setName(checked[s]);
            double x = 0;
            while (x < 95) {
                series.getData().add(new XYChart.Data<Number, Number>(x, Math.random() * 99));
                x += 5 + (15 * Math.random());
            }
            series.getData().add(new XYChart.Data<Number, Number>(99d, Math.random() * 99));
            ac.getData().add(series);
        }
        return ac;
    }
    */



}
