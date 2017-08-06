package com.cfao.app.controllers;

import com.cfao.app.beans.Personne;
import com.cfao.app.util.AlertUtil;
import javafx.scene.image.Image;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
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
    public String getCurrentPhoto(){
        return currentPhoto;
    }

    public Image getImage(String filename) {
        FileInputStream file = null;
        try {
            String chemin = getClass().getResource(ResourceBundle.getBundle("Bundle").getString("photo.dir")).toExternalForm() + filename;
            Path cible = Paths.get(URI.create(chemin));
            if(cible.toFile().exists()) {
                file = new FileInputStream(cible.toString());
                return new Image(file, defaultImage.getWidth(), defaultImage.getHeight(), false, false);
            }
        } catch (Exception ex) {
            AlertUtil.showErrorMessage(ex);
        }finally {
            // Leve une erreur lors de la suppression si le fichier n'est pas fermer
            //Error: The process cannot access the file because it is being used...
            try {
                if(file != null)
                    file.close();
            }catch (Exception ex){
                AlertUtil.showErrorMessage(ex);
            }
        }
        return new Image(ResourceBundle.getBundle("Application").getString("default.image"));
    }

    public boolean deletePhoto(Personne personne) {
        try {
            String chemin = getClass().getResource(ResourceBundle.getBundle("Bundle").getString("photo.dir")).toExternalForm() + personne.getPhoto();
            Path path = Paths.get(URI.create(chemin));
            if (!path.toFile().exists()) {
                return true;
            }

            Files.delete(path);
            currentPhoto = "";
            return true;
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
        String chemin = getClass().getResource(ResourceBundle.getBundle("Bundle").getString("photo.dir")).toExternalForm() + newFileName;
        Path cible = Paths.get(URI.create(chemin));
        Files.copy(src, cible, StandardCopyOption.REPLACE_EXISTING);
        return newFileName;
    }
}
