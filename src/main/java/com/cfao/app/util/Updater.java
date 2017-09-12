package com.cfao.app.util;

import javafx.scene.layout.AnchorPane;

import java.io.File;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * This class checks for an updated main jar and, if found, downloads it. After
 * update check, forwards on to main jar main method.
 * https://reportmill.wordpress.com/2014/12/04/automatically-update-your-javapackager-applications/
 */
public class Updater extends AnchorPane {

    // The main jar name
    static final String JarName = "servicepro-1.0-SNAPSHOT.jar";

    // The path to the update jar at website
    static final String remoteFile = "http://uacosendai-edu.net/jpainam/servicepro/servicepro-1.0-SNAPSHOT.jar";


    public boolean checkForUpdates() throws Exception {
        URL url = new URL(remoteFile);
        URLConnection connection = url.openConnection();
        long remoteTime = connection.getLastModified();
        Path path = Paths.get(".").toAbsolutePath();
        File jarFile = new File(path.toString(), JarName);
        if (jarFile.exists()) {
            long localTime = jarFile.lastModified();
            System.err.println(localTime + " et " + remoteTime);
            if (localTime >= remoteTime) {
                System.err.println("false");
                return false;
            } else {
                System.err.println("true");
                return true;
            }
        }
        System.err.println("Local jarfile not exist");
        return false;
    }
}