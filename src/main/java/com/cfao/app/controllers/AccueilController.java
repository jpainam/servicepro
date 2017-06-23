package com.cfao.app.controllers;

import com.cfao.app.beans.Formation;
import com.cfao.app.beans.Personne;
import com.cfao.app.model.FormationModel;
import com.cfao.app.model.Model;
import com.cfao.app.model.PersonneModel;
import com.cfao.app.util.SearchBox;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import org.controlsfx.control.CheckListView;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

/**
 * Created by JP on 6/21/2017.
 */
public class AccueilController implements Initializable {
    public CheckListView<String> formationListView = new CheckListView<>();
    public TableView participantTable = new TableView();
    public StackPane overalPerformance;
    public StackPane participantPerformance;
    public TableColumn participantNom = new TableColumn("Nom/Name");
    public TableColumn participantMatricule = new TableColumn("Matricule");
    public SearchBox searchBox1 = new SearchBox();
    public SearchBox searchBox2 = new SearchBox();
    public VBox formationBox;
    public VBox participantBox;

    PieChart.Data passedChart, failedChart, averageChart;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Region veil = new Region();
        veil.setStyle("-fx-background-color: rgba(0, 0, 0, 0.4)");
        ProgressIndicator p = new ProgressIndicator();
        p.setMaxSize(150, 150);

        overalPerformance.getChildren().add(createChartFormation());
        participantPerformance.getChildren().add(createChartPersonnel());
        createFormationList();
        createParticipantTable();

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

    protected void createFormationList() {
        Label label = new Label("Formations : ");
        HBox reserchePanel = new HBox();
        HBox.setHgrow(searchBox1, Priority.ALWAYS);
        reserchePanel.setSpacing(10);
        HBox.setMargin(reserchePanel, new Insets(10, 10, 10, 10));
        searchBox1.setMaxWidth(Double.MAX_VALUE);
        reserchePanel.setMaxWidth(Double.MAX_VALUE);
        reserchePanel.getChildren().addAll(label, searchBox1);

        FormationModel formationModel = new FormationModel(Model.getBeansClass("Formation"));
        formationListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

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
        String[] formations = {"CFAO Academy", "JCB Distance Learning", "JCB JDS","Introduction conduite engins",
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

        formationListView.getCheckModel().getCheckedItems().addListener(new ListChangeListener<String>() {
            public void onChanged(ListChangeListener.Change<? extends String> c) {
                System.out.println(formationListView.getCheckModel().getCheckedItems());
            }
        });

        formationBox.setPadding(new Insets(10, 10, 10, 10));
        formationBox.setSpacing(10);
        formationBox.getChildren().addAll(reserchePanel, formationListView);
    }


    private void createParticipantTable() {
        Label label = new Label("Participants : ");
        HBox reserchePanel = new HBox();
        HBox.setHgrow(searchBox2, Priority.ALWAYS);
        reserchePanel.setSpacing(10);
        HBox.setMargin(reserchePanel, new Insets(5, 5, 5, 5));
        searchBox2.setMaxWidth(Double.MAX_VALUE);
        reserchePanel.setMaxWidth(Double.MAX_VALUE);
        reserchePanel.getChildren().addAll(label, searchBox2);

        participantNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        participantMatricule.setCellValueFactory(new PropertyValueFactory<>("matricule"));
        participantTable.getColumns().addAll(participantNom, participantMatricule);
        participantTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        PersonneModel personneModel = new PersonneModel();

        Task<ObservableList<Personne>> task = new Task<ObservableList<Personne>>() {
            @Override
            protected ObservableList<Personne> call() throws Exception {
                return FXCollections.observableArrayList(personneModel.select());
            }
        };
        new Thread(task).start();
        task.setOnSucceeded(event -> {
            participantTable.setItems(task.getValue());
        });
        participantBox.setPadding(new Insets(5, 5, 5, 5));
        participantBox.setSpacing(10);
        participantBox.getChildren().addAll(reserchePanel, participantTable);
    }


    protected PieChart createChartPersonnel() {
        // String drilldownCss = DrilldownPieChartSample.class.getResource("DrilldownChart.css").toExternalForm();

        PieChart pie = new PieChart(
                FXCollections.observableArrayList(
                        passedChart = new PieChart.Data("Réussit/Passed", 20),
                        failedChart = new PieChart.Data("Echoué/Failed", 30),
                        averageChart = new PieChart.Data("Moyen/Average", 10)
                ));
        //((Parent) pie).getStylesheets().add(drilldownCss);
        pie.setTitle("Performance par participant / Participant performance");

        setDrilldownData(pie, passedChart, "a");
        setDrilldownData(pie, failedChart, "b");
        setDrilldownData(pie, averageChart, "c");
        return pie;
    }

    protected AreaChart<Number, Number> createChartFormation() {
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

    private void setDrilldownData(final PieChart pie, PieChart.Data data, final String labelPrefix) {
        pie.setTitle("Performance par formation");
        data.getNode().setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent t) {
                pie.setData(FXCollections.observableArrayList(
                        new PieChart.Data(labelPrefix + "-1", 7),
                        new PieChart.Data(labelPrefix + "-2", 2),
                        new PieChart.Data(labelPrefix + "-3", 5),
                        new PieChart.Data(labelPrefix + "-4", 3),
                        new PieChart.Data(labelPrefix + "-5", 2)));
            }
        });
    }
}
