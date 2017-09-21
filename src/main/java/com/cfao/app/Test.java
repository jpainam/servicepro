package com.cfao.app;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.effect.Lighting;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

public class Test extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            VBox vbox = new VBox(10);

            Slider slider = new Slider();
            slider.setMin(0);
            slider.setMax(100);
            slider.setValue(40);
            slider.setShowTickLabels(true);
            slider.setShowTickMarks(true);
            slider.setMajorTickUnit(50);
            slider.setMinorTickCount(5);
            slider.setBlockIncrement(1);
            slider.setEffect(new Lighting());

            Button notif = new Button("show notif");
            notif.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent t) {
                    final Notifications notificationBuilder = Notifications.create()
                            .title("")
                            .text("test")
                            .graphic(null)
                            .hideAfter(Duration.seconds(3))
                            .position(Pos.BOTTOM_RIGHT);
                    notificationBuilder.owner(primaryStage); //this.getView().getScene().getWindow()
                    notificationBuilder.showInformation();
                }
            });
            vbox.getChildren().addAll(slider, notif);
            Scene scene = new Scene(vbox);

            primaryStage.setMinHeight(500);
            primaryStage.setMinWidth(500);

            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}