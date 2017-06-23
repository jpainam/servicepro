package com.cfao.app.util;

import java.util.ResourceBundle;

/**
 * Created by JP on 6/9/2017.
 */
public enum FXMLView {
    TEMPLATE {
        public String getFXMLFile() {
            return "src/main/java/com/cfao/app/views/template.fxml";
        }
    }, LOGIN {
        public String getTitle() {
            return getStringFromResourceBundle("login.title");
        }

        public String getFXMLFile() {
            return "src/main/java/com/cfao/app/views/login/login.fxml";
        }

    }, CIVILITE {
        public String getTitle() {
            return getStringFromResourceBundle("civilite.title");
        }

        public String getFXMLFile() {
            return "src/main/java/com/cfao/app/views/civilite/civilite.fxml";
        }
    }, PERSONNE {
        public String getTitle() {
            return getStringFromResourceBundle("login.title");
        }

        public String getFXMLFile() {
            return "src/main/java/com/cfao/app/views/civilite/personne.fxml";
        }
    }, PARAMETRE {
        public String getTitle() {
            return getStringFromResourceBundle("parametre.title");
        }

        public String getFXMLFile() {
            return "src/main/java/com/cfao/app/views/parametre/parametre.fxml";
        }
    }, COMPETENCE {
        public String getTitle() {
            return getStringFromResourceBundle("competence.title");
        }

        public String getFXMLFile() {
            return "src/main/java/com/cfao/app/views/competence/competence.fxml";
        }

    }, ADDCOMPETENCE {
        public String getTitle() {
            return getStringFromResourceBundle("competence.title");
        }

        public String getFXMLFile() {
            return "src/main/java/com/cfao/app/views/competence/add.fxml";
        }

    }, PROFIL {
        public String getTitle() {
            return getStringFromResourceBundle("profil.title");
        }

        public String getFXMLFile() {
            return "src/main/java/com/cfao/app/views/profil/profil.fxml";
        }
    }, FORMATION {
        public String getTitle() {
            return getStringFromResourceBundle("formation.title");
        }
        public String getFXMLFile() {
            return "src/main/java/com/cfao/app/views/formation/formation.fxml";
        }
    }, ACCUEIL {
        public String getFXMLFile() {
            return "src/main/java/com/cfao/app/views/accueil/accueil.fxml";
        }
    }, LEFTMENU {
        public String getFXMLFile() {
            return "src/main/java/com/cfao/app/views/menu/leftmenu.fxml";
        }
    }, SOCIETE {
        public String getFXMLFile() {
            return "src/main/java/com/cfao/app/views/societe/societe.fxml";
        }
    }, USER {
        public String getFXMLFile() {
            return "src/main/java/com/cfao/app/views/user/user.fxml";
        }
    }, GROUPE {
        public String getFXMLFile() {
            return "src/main/java/com/cfao/app/views/groupe/groupe.fxml";
        }
    }, SECTION {
        public String getFXMLFile() {
            return "src/main/java/com/cfao/app/views/section/section.fxml";
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
