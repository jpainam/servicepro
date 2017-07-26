package com.cfao.app.util;

import java.util.ResourceBundle;

/**
 * Created by JP on 6/9/2017.
 */
public enum FXMLView {
    TEMPLATE {
        public String getFXMLFile() {
            return "/views/template/template.fxml";
        }
    }, LOGIN {
        public String getTitle() {
            return getStringFromResourceBundle("login.title");
        }

        public String getFXMLFile() {
            return "/views/login/login.fxml";
        }

    }, CIVILITE {
        public String getTitle() {
            return getStringFromResourceBundle("civilite.title");
        }

        public String getFXMLFile() {
            return "/views/civilite/civilite.fxml";
        }
    }, PERSONNE {
        public String getTitle() {
            return getStringFromResourceBundle("login.title");
        }

        public String getFXMLFile() {
            return "/views/civilite/personne.fxml";
        }
    }, PARAMETRE {
        public String getTitle() {
            return getStringFromResourceBundle("parametre.title");
        }

        public String getFXMLFile() {
            return "/views/parametre/parametre.fxml";
        }
    }, COMPETENCE {
        public String getTitle() {
            return getStringFromResourceBundle("competence.title");
        }

        public String getFXMLFile() {
            return "/views/competence/competence.fxml";
        }
    }, PROFIL {
        public String getTitle() {
            return getStringFromResourceBundle("profil.title");
        }

        public String getFXMLFile() {
            return "/views/profil/profil.fxml";
        }
    }, FORMATION {
        public String getTitle() {
            return getStringFromResourceBundle("formation.title");
        }
        public String getFXMLFile() {
            return "/views/formation/formation.fxml";
        }
    }, ACCUEIL {
        public String getFXMLFile() {
            return "/views/accueil/accueil.fxml";
        }
    }, LEFTMENU {
        public String getFXMLFile() {
            return "/views/menu/leftmenu.fxml";
        }
    }, SOCIETE {
        public String getFXMLFile() {
            return "/views/societe/societe.fxml";
        }
    }, USER {
        public String getFXMLFile() {
            return "/views/user/user.fxml";
        }
    }, GROUPE {
        public String getFXMLFile() {
            return "/views/groupe/groupe.fxml";
        }
    }, SECTION {
        public String getFXMLFile() {
            return "/views/section/section.fxml";
        }
    };

    public String getTitle() {
        return getStringFromResourceBundle("app.title");
    }

    public abstract String getFXMLFile();

    String getStringFromResourceBundle(String key) {
        return ResourceBundle.getBundle("Bundle").getString(key);
    }
}
