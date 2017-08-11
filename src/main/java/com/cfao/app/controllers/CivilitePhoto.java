package com.cfao.app.controllers;

import com.cfao.app.beans.Personne;
import com.cfao.app.util.AlertUtil;
import javafx.scene.image.Image;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ResourceBundle;
import java.util.UUID;

/**
 * Created by JP on 8/5/2017.
 */
public class CivilitePhoto {
    public final Image defaultImage = new Image(ResourceBundle.getBundle("Application").getString("default.image"));
    private String currentPhoto = "";

    public CivilitePhoto() {

    }

    public Image getImage(Personne personne) {
        return getImage(personne.getPhoto());
    }

    public Image getPhoto(Personne personne) {
        return getImage(personne);
    }

    public String getCurrentPhoto() {
        return currentPhoto;
    }

    public Image getImage(String filename) {
        FileInputStream file = null;
        try {
            Path path = Paths.get(ResourceBundle.getBundle("Bundle").getString("photo.dir")).toAbsolutePath();
            String chemin = path.toString() + File.separator + filename;
           File f = new File(chemin);
            if (f.exists() && !f.isDirectory()) {
                file = new FileInputStream(f);
                return new Image(file, defaultImage.getWidth(), defaultImage.getHeight(), false, false);
            }
        } catch (Exception ex) {
            AlertUtil.showErrorMessage(ex);
        } finally {
            // Leve une erreur lors de la suppression si le fichier n'est pas fermer
            //Error: The process cannot access the file because it is being used...
            try {
                if (file != null)
                    file.close();
            } catch (Exception ex) {
                AlertUtil.showErrorMessage(ex);
            }
        }
        return new Image(ResourceBundle.getBundle("Application").getString("default.image"));
    }

    public boolean deletePhoto(Personne personne) {
        try {
            Path path = Paths.get(ResourceBundle.getBundle("Bundle").getString("photo.dir")).toAbsolutePath();
            if (!path.toFile().exists()) {
                path.toFile().mkdir();
            }
            String chemin = path.toString() + File.separator + personne.getPhoto();
            File file = new File(chemin);

            if (!file.exists() || file.isDirectory()) {
                return true;
            }
            currentPhoto = "";
            return file.delete();
        } catch (Exception ex) {
            AlertUtil.showErrorMessage(ex);
        }
        return false;
    }

    public String savePhoto(File photo) throws IOException {
        String uniqueID = UUID.randomUUID().toString();
        String fileName = photo.getName();
        String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1, photo.getName().length());
        Path src = photo.toPath();
        String newFileName = currentPhoto = uniqueID + "." + fileExtension;

        Path path = Paths.get(ResourceBundle.getBundle("Bundle").getString("photo.dir")).toAbsolutePath();
        if (path == null) {
            Files.createDirectories(path);
        }
        String chemin = path.toString() + File.separator + newFileName;

        //Path cible = Paths.get(URI.create(chemin));
        Files.copy(src, Paths.get(chemin), StandardCopyOption.REPLACE_EXISTING);
        return newFileName;
    }
}
