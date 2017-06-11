package com.cfao.app.util;

import java.util.ResourceBundle;

/**
 * Created by JP on 6/9/2017.
 */
public enum FXMLView {
    MAIN{
        public String getTitle() {

            return getStringFromResourceBundle("app.title");
        }
        public String getFXMLFile() {
            return "src/main/java/com/cfao/app/views/accueil/accueil.fxml";
        }
    }, LOGIN{
        public String getTitle() {
            return getStringFromResourceBundle("login.title");
        }

        public String getFXMLFile() {
            return  "src/main/java/com/cfao/app/views/login/login.fxml";
        }

    }, CIVILITE{
        public String getTitle() {
            return getStringFromResourceBundle("civilite.title");
        }

        public String getFXMLFile() {
            return  "src/main/java/com/cfao/app/views/civilite/civilite.fxml";
        }
    }, PERSONNE{
        public String getTitle() {
            return getStringFromResourceBundle("login.title");
        }

        public String getFXMLFile() {
            return  "src/main/java/com/cfao/app/views/civilite/personne.fxml";
        }
    };
    public abstract String getTitle();
    public abstract String getFXMLFile();
    String getStringFromResourceBundle(String key){
        return ResourceBundle.getBundle("Bundle").getString(key);
    }
}
