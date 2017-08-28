package com.cfao.app.util;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.log4j.Logger;

import java.io.*;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;

public class Updater extends Application {

    Stage stage;

    static Logger logger = Logger.getLogger(Updater.class);

    // The App directory
    static final String AppDirName = "Servicepro";

    // The main jar name
    static final String JarName = "servicepro-1.0-SNAPSHOT.jar";

    // The path to the update jar at website
    static final String JarURL = "http://uacosendai-edu.net/servicepro/servicepro-1.0-SNAPSHOT.jar";

    // The name of jar that holds this class
    //static final String LoaderJarName = "Updater";

    // The main class
    static final String MainClass = "com.cfao.app.Login";

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.initStyle(StageStyle.UNDECORATED);
        stage = primaryStage;
        VBox loading = new VBox(20);
        loading.setAlignment(Pos.CENTER);
        loading.setMaxWidth(Region.USE_PREF_SIZE);
        loading.setMaxHeight(Region.USE_PREF_SIZE);
        loading.getChildren().add(new ProgressBar());
        loading.getChildren().add(new Label("Téléchargement de mise à jour"));

        BorderPane root = new BorderPane(loading);
        Scene scene = new Scene(root);

        primaryStage.setWidth(200);
        primaryStage.setHeight(100);
        primaryStage.setScene(scene);
        primaryStage.show();
        main1();
    }

    /**
     * Main method - reinvokes main1() on app event thread in exception handler.
     */
    public static void main(String args[]) {
            launch(args);
    }

    /**
     * Main method:
     * - Gets main Jar file from default, if missing
     * - Updates main Jar file from local update file, if previously loaded
     * - Load main Jar into URLClassLoader, load main class and invoke main method
     * - Check for update from remove site in background
     */
    public static void main1() throws Exception {
        // Make sure default jar is in place
        try {
            copyDefaultMainJar();
        } catch (Exception e) {
            //showMessage("Error Copying Main Jar", e.toString());
            AlertUtil.showErrorMessage(e);
            System.err.println("Error copying Main jar");
        }

        // If Updated Jar exists, copy it into place
        // Get the jar file in AppData
        File jar = getAppFile(JarName);

        // If jar doesn't exist complain bitterly
        if (!jar.exists() || !jar.canRead())
            throw new RuntimeException("Main Jar not found!");

        // Check for updates in background thread
        new Thread(() -> checkForUpdatesSilent()).start();

        // Create URLClassLoader for main jar file, get App class and invoke main
        URLClassLoader ucl = new URLClassLoader(new URL[]{jar.toURI().toURL()});
        Class cls = ucl.loadClass(MainClass); //ucl.close();
        Method meth = cls.getMethod("main", new Class[]{String[].class});
        String args[] = new String[]{};
        meth.invoke(null, new Object[]{args});
        if (cls == Object.class) ucl.close(); // Getting rid of warning message for ucl
    }

    /**
     * Copies the default main jar into place.
     */
    public static void copyDefaultMainJar() throws IOException, ParseException {
        //Get current working directory
        URL url = Updater.class.getProtectionDomain().getCodeSource().getLocation();
        String path0 = url.getPath();

        //AppData file servicepro
        File jar0 = getAppFile(JarName);
        //local file serviceprof
        Path path1 = Paths.get(".").toAbsolutePath();
        String chemin = path1.toString() + File.separator + JarName;
        File jar1 =  new File(chemin);

        if (jar0.exists() && jar0.lastModified() > jar1.lastModified())
            return;
        Files.copy(jar1.toPath(), jar0.toPath(), StandardCopyOption.COPY_ATTRIBUTES);
    }

    /**
     * Check for updates.
     */
    private static void checkForUpdatesSilent() {
        try {
            checkForUpdates();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Check for updates.
     */
    private static void checkForUpdates() throws IOException {
        // Get URL connection and lastModified time
        // Get local AppData File
        File jarFile = getAppFile(JarName);
        // Get distant File
        URL url = new URL(JarURL);
        URLConnection connection = url.openConnection();
        connection.setUseCaches(false);
        long mod0 = jarFile.lastModified(), mod1 = connection.getLastModified();
        if (mod0 >= mod1) {
            System.out.println("No update available at " + JarURL + '(' + mod0 + '>' + mod1 + ')');
            AlertUtil.showSimpleAlert("Update", "Aucune mise à jour disponible");
            return;
        }

        // Load Update bytes from URL connection
        System.out.println("Loading update from " + JarURL);
        byte bytes[] = getBytes(connection);
        System.out.println("Update loaded");
        // Creation d'un fichier update
        File updateFile = getAppFile(JarName + ".update");

        writeBytes(updateFile, bytes);
        System.out.println("Update saved: " + updateFile);

        // Set modified time so it matches server
        updateFile.setLastModified(mod1);

        // Let the user know
        //String msg = "A new update is available. Restart application to apply";
        //SwingUtilities.invokeLater(() -> showMessage("New Update Found", msg));
        AlertUtil.showSimpleAlert("Information", "Mise à jour disponible; redemarrer l'application");
    }

    /**
     * Returns the Main jar file.
     */
    private static File getAppFile(String aName) {
        return new File(getAppDir(), aName);
    }

    /**
     * Returns the Main jar file.
     */
    private static File getAppDir() {
        return getAppDataDir(AppDirName, true);
    }

    /**
     * Returns the AppData or Application Support directory file.
     */
    public static File getAppDataDir(String aName, boolean doCreate) {
        // Get user home + AppDataDir (platform specific) + name (if provided)
        String dir = System.getProperty("user.home");
        if (isWindows) dir += File.separator + "AppData" + File.separator + "Local";
        else if (isMac) dir += File.separator + "Library" + File.separator + "Application Support";
        if (aName != null) dir += File.separator + aName;

        // Create file, actual directory (if requested) and return
        File dfile = new File(dir);
        if (doCreate && aName != null) dfile.mkdirs();
        return dfile;
    }

    // Whether Windows/Mac
    static boolean isWindows = (System.getProperty("os.name").indexOf("Windows") >= 0);
    static boolean isMac = (System.getProperty("os.name").indexOf("Mac OS X") >= 0);

    /**
     * Writes the given bytes (within the specified range) to the given file.
     */
    public static void writeBytes(File aFile, byte theBytes[]) throws IOException {
        if (theBytes == null) {
            aFile.delete();
            return;
        }
        FileOutputStream fileStream = new FileOutputStream(aFile);
        fileStream.write(theBytes);
        fileStream.close();
    }

    /**
     * Returns bytes for connection.
     */
    public static byte[] getBytes(URLConnection aConnection) throws IOException {
        InputStream stream = aConnection.getInputStream(); // Get stream for connection
        byte bytes[] = getBytes(stream); // Get bytes for stream
        stream.close();  // Close stream
        return bytes;  // Return bytes
    }

    /**
     * Returns bytes for an input stream.
     */
    public static byte[] getBytes(InputStream aStream) throws IOException {
        ByteArrayOutputStream bs = new ByteArrayOutputStream();
        byte chunk[] = new byte[8192];
        for (int len = aStream.read(chunk, 0, 8192); len > 0; len = aStream.read(chunk, 0, 8192))
            bs.write(chunk, 0, len);
        return bs.toByteArray();
    }



}