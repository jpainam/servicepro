package com.cfao.app;

import com.cfao.app.controllers.LoginController;
import com.cfao.app.model.Model;
import com.cfao.app.util.AlertUtil;
import com.cfao.app.util.FXMLView;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import org.apache.log4j.Logger;
import org.hibernate.Session;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;

/**
 * Created by JP on 6/9/2017.
 */
public class Main extends Application {
    static Logger logger = Logger.getLogger(Main.class);
    public static Stage stage;
    public static java.awt.TrayIcon trayIcon;
    public static java.awt.SystemTray tray;

    // Directory separator
    public static final String DS = File.separator;

    public void start(Stage primaryStage) throws Exception {
        primaryStage.initStyle(StageStyle.DECORATED);
        primaryStage.setMaximized(true);
        stage = primaryStage;
        // sets up the tray icon (using awt code run on the swing thread).
        javax.swing.SwingUtilities.invokeLater(this::addAppToTray);

        Platform.setImplicitExit(false);
        String iconName = ResourceBundle.getBundle("Application").getString("app.icon");

        Image icon = new Image(getClass().getResourceAsStream(iconName));
        primaryStage.getIcons().add(icon);

        Pane mainPane = FXMLLoader.load(getClass().getResource("/views/template/template.fxml"));
        primaryStage.setTitle(FXMLView.TEMPLATE.getTitle());

        Scene scene = new Scene(mainPane);
        scene.getStylesheets().clear();
        scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
        primaryStage.setOnCloseRequest((WindowEvent e) -> stage.setIconified(true));
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public double getWidth(double per) {
        double w = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().getWidth();
        return (per * w) / 100;
    }

    public double getHeight(double per) {
        double h = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().getHeight();
        return (per * h) / 100;
    }

    private void closeWindow() {
        System.exit(0);
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void stop() throws Exception {
        Session session = Model.getCurrentSession();
        if (session.isOpen()) {
            session.close();
        }
        super.stop();
    }


    /**
     * Sets up a system tray icon for the application.
     */
    private void addAppToTray() {
        // a timer allowing the tray icon to provide a periodic notification event.
        Timer notificationTimer = new Timer();

        // format used to display the current time in a tray icon notification.

        try {
            // ensure awt toolkit is initialized.
            java.awt.Toolkit.getDefaultToolkit();

            // app requires system tray support, just exit if there is no support.
            if (!java.awt.SystemTray.isSupported()) {
                System.out.println("No system tray support, application exiting.");
                AlertUtil.showSimpleAlert("Information", "No system tray support, application exiting.");
                //Platform.exit();
                return;
            }

            // set up a system tray icon.
            tray = java.awt.SystemTray.getSystemTray();
            URL imageLoc = getClass().getResource("/images/tray-icon.png").toURI().toURL();
            java.awt.Image image = ImageIO.read(imageLoc);
           trayIcon = new java.awt.TrayIcon(image);

            // if the user double-clicks on the tray icon, show the main app stage.
            trayIcon.addActionListener(event -> Platform.runLater(this::showStage));

            // if the user selects the default menu item (which includes the app name),
            // show the main app stage.
            java.awt.MenuItem openItem = new java.awt.MenuItem("Show App");
            openItem.addActionListener(event -> Platform.runLater(this::showStage));

            // the convention for tray icons seems to be to set the default icon for opening
            // the application stage in a bold font.
            java.awt.Font defaultFont = java.awt.Font.decode(null);
            java.awt.Font boldFont = defaultFont.deriveFont(java.awt.Font.BOLD);
            openItem.setFont(boldFont);

            // to really exit the application, the user must go to the system tray icon
            // and select the exit option, this will shutdown JavaFX and remove the
            // tray icon (removing the tray icon will also shut down AWT).
            java.awt.MenuItem exitItem = new java.awt.MenuItem("Exit");
            exitItem.addActionListener(event -> {
                LoginController.stopServiceNotification();
                LoginController.stopServicePlanification();
                notificationTimer.cancel();
                Platform.exit();
                tray.remove(trayIcon);
                System.exit(0);
            });

            // setup the popup menu for the application.
            final java.awt.PopupMenu popup = new java.awt.PopupMenu();
            popup.add(openItem);
            popup.addSeparator();
            popup.add(exitItem);
            trayIcon.setPopupMenu(popup);

            // create a timer which periodically displays a notification message.
            /*notificationTimer.schedule(
                    new TimerTask() {
                        @Override
                        public void run() {
                            javax.swing.SwingUtilities.invokeLater(() ->
                                    trayIcon.displayMessage(
                                            "hello",
                                            "The time is now " + timeFormat.format(new Date()),
                                            java.awt.TrayIcon.MessageType.INFO
                                    )
                            );
                        }
                    },
                    5000,
                    6000
            );*/

            // add the application tray icon to the system tray.
            tray.add(trayIcon);
        } catch (Exception e) {
            System.out.println("Unable to init system tray");
            e.printStackTrace();
        }
    }

    /**
     * Shows the application stage and ensures that it is brought ot the front of all stages.
     */
    private void showStage() {
        if (stage != null) {
            stage.show();
            stage.toFront();
        }
    }

}
