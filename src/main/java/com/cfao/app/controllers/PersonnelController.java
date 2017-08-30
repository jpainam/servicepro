package com.cfao.app.controllers;

import com.cfao.app.beans.Domaine;
import com.cfao.app.beans.Personnel;
import com.cfao.app.model.Model;
import com.cfao.app.util.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.controlsfx.control.CheckComboBox;

import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by JP on 8/22/2017.
 */
public class PersonnelController implements Initializable{
    public Button btnPrintPersonnel;
    public Button btnNouveauPersonnel;
    public Button btnModifierPersonnel;
    public Button btnAnnulerPersonnel;
    public VBox vboxsearchBox;
    public VBox vBoxDomaine;
    public Button btnSupprimerPersonnel;
    public CheckComboBox<Domaine> comboDomaines;
    public ObservableMap<String, ObservableList> map;
    public TextField txtNom;
    public TextField txtPrenom;
    public TextField txtAdresse;
    public TextField txtTelephone;
    public StackPane personnelStackPane;
    public TableView<Personnel> personnelTable;
    public TableColumn<Personnel, String> nomColumn;
    public TableColumn<Personnel, String> telephoneColumn;
    public int stateBtnNouveau = 0;
    public int stateBtnModifier = 0;
    public Personnel personnel = null;


    private SearchBox searchBox = new SearchBox();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        HBox.setHgrow(searchBox, Priority.ALWAYS);
        searchBox.setMaxWidth(Double.MAX_VALUE);
        vboxsearchBox.getChildren().setAll(new Label("Personnels : "), searchBox);
        comboDomaines = new CheckComboBox<Domaine>(FXCollections.observableArrayList());
        comboDomaines.setMaxWidth(Double.MAX_VALUE);
        vBoxDomaine.getChildren().add(comboDomaines);

        ButtonUtil.print(btnPrintPersonnel);
        ButtonUtil.add(btnNouveauPersonnel);
        ButtonUtil.cancel(btnAnnulerPersonnel);
        ButtonUtil.edit(btnModifierPersonnel);
        ButtonUtil.delete(btnSupprimerPersonnel);

        ServiceproUtil.setDisable(true, btnModifierPersonnel, btnSupprimerPersonnel);
        disableAllComponents(false);
        nomColumn.setCellValueFactory(param -> param.getValue().infos());
        telephoneColumn.setCellValueFactory(param -> param.getValue().telephone());

        buildData();

        personnelTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

            if (stateBtnNouveau == 1) {
                ServiceproUtil.setDisable(true, btnModifierPersonnel, btnSupprimerPersonnel, comboDomaines);
                ServiceproUtil.setEditable(false, txtNom, txtPrenom, txtTelephone, txtAdresse);
                btnNouveauPersonnel.setText(ResourceBundle.getBundle("Bundle").getString("button.new"));
                stateBtnNouveau = 0;
            }

            if (stateBtnModifier != 1 && newValue != null)
                renduFormulaire(newValue);

            if (newValue == null)
                ServiceproUtil.setDisable(true, btnModifierPersonnel, btnSupprimerPersonnel);
        });
    }

    private void renduFormulaire(Personnel newValue) {
        ServiceproUtil.setDisable(false, btnModifierPersonnel, btnSupprimerPersonnel);
        personnel = newValue;
        txtNom.setText(personnel.getNom());
        txtPrenom.setText(personnel.getPrenom());
        txtAdresse.setText(personnel.getAdresse());
        txtTelephone.setText(personnel.getTelephone());
        updateDomaine(personnel.getDomaines());
    }

    private void updateDomaine(List<Domaine> domaines) {
        comboDomaines.getCheckModel().clearChecks();
        if( domaines.size() > 0) {
            Task<int[]> task = new Task<int[]>() {
                @Override
                protected int[] call() throws Exception {
                    int[] tab = new int[domaines.size()];
                    int i = 0;
                    for (int j = 0; j < comboDomaines.getItems().size(); j++) {
                        Iterator<Domaine> iterator = domaines.iterator();
                        while (iterator.hasNext()) {
                            if (comboDomaines.getItems().get(j).getIddomaine() == iterator.next().getIddomaine()) {
                                tab[i] = j;
                                i++;
                            }
                        }
                    }
                    return tab;
                }
            };
            new Thread(task).start();
            task.setOnSucceeded(event -> {
                Platform.runLater(() -> {
                    comboDomaines.getCheckModel().checkIndices(task.getValue());
                });
            });
        }
    }

    private void buildData() {
        Task<ObservableMap<String, ObservableList>> task = new Task<ObservableMap<String, ObservableList>>() {
            @Override
            protected ObservableMap<String, ObservableList> call() throws Exception {
                map = FXCollections.observableHashMap();
                map.put("personnel", FXCollections.observableList(new Model<Personnel>("Personnel").getList()));
                map.put("domaine", FXCollections.observableList(new Model<Domaine>("Domaine").getList()));
                return map;
            }
        };
        ProgressIndicatorUtil.show(personnelStackPane, task);
        new Thread(task).start();
        task.setOnSucceeded(event -> {
            map = task.getValue();
            personnelTable.getItems().setAll(map.get("personnel"));
            comboDomaines.getItems().setAll(map.get("domaine"));
        });
        task.setOnFailed(event -> {
            System.err.println(task.getException());
            AlertUtil.showErrorMessage(new Exception(task.getException()));
        });
    }

    public void printPersonnelAction(ActionEvent event) {
    }

    public void nouveauPersonnelAction(ActionEvent event) {
        if (!btnNouveauPersonnel.isDisable())
            switch (stateBtnNouveau) {
                case 0:
                    ServiceproUtil.setDisable(true, btnModifierPersonnel, btnSupprimerPersonnel);
                    disableAllComponents(true);
                    resetAllComponents();
                    btnNouveauPersonnel.setText(ResourceBundle.getBundle("Bundle").getString("button.save"));
                    this.stateBtnNouveau = 1;
                    break;
                case 1:
                    /**
                     * Creation d'une nouvel personne
                     */
                    Personnel p = new Personnel();
                    setPersonnelParameters(p);
                    savePersonnel(p);
                    btnNouveauPersonnel.setText(ResourceBundle.getBundle("Bundle").getString("button.new"));
                    disableAllComponents(false);
                    this.stateBtnNouveau = 0;
                    ServiceproUtil.setDisable(false, btnModifierPersonnel, btnSupprimerPersonnel);


                    break;
            }
    }

    private void resetAllComponents() {
        txtAdresse.setText("");
        txtNom.setText("");
        txtPrenom.setText("");
        txtTelephone.setText("");
        comboDomaines.getCheckModel().clearChecks();
    }

    public void setPersonnelParameters(Personnel p){
        p.setNom(txtNom.getText());
        p.setPrenom(txtPrenom.getText());
        p.setAdresse(txtAdresse.getText());
        p.setTelephone(txtTelephone.getText());
        p.setDomaines(comboDomaines.getCheckModel().getCheckedItems());
    }

    private void disableAllComponents(boolean b) {
        ServiceproUtil.setDisable(!b, comboDomaines);
        ServiceproUtil.setEditable(b, txtNom, txtPrenom, txtTelephone, txtAdresse);
    }

    private void savePersonnel(Personnel p) {
        Task<Boolean> task = new Task<Boolean>() {
            @Override
            protected Boolean call() throws Exception {
                boolean bool;
                bool = new Model<Personnel>().save(p);
                personnelTable.getItems().add(p);
                return bool;
            }
        };
        new Thread(task).start();
        task.setOnSucceeded(event -> {
            if (task.getValue()) {
                ServiceproUtil.notify("Ajout OK");
                System.out.println("Good");
            } else {
                ServiceproUtil.notify("Erreur d'ajout");
            }
        });
        task.setOnFailed(event -> {
            task.getException().printStackTrace();
            System.err.println(task.getException());
        });

    }

    public void modifierPersonnelAction(ActionEvent event) {
        if (!btnModifierPersonnel.isDisable() && personnelTable.getSelectionModel().getSelectedItem() != null)
            switch (stateBtnModifier) {
                case 0:
                    ServiceproUtil.setDisable(true, btnNouveauPersonnel, btnSupprimerPersonnel);
                    disableAllComponents(true);
                    btnModifierPersonnel.setText(ResourceBundle.getBundle("Bundle").getString("button.save"));
                    this.stateBtnModifier = 1;
                    break;
                case 1:
                    setPersonnelParameters(personnel);
                    updatePersonnel(personnel);
                    btnModifierPersonnel.setText(ResourceBundle.getBundle("Bundle").getString("button.edit"));
                    this.stateBtnModifier = 0;
                    disableAllComponents(false);
                    ServiceproUtil.setDisable(false, btnNouveauPersonnel, btnSupprimerPersonnel);
                    break;
            }
    }

    private void updatePersonnel(Personnel personnel) {
        Task<Boolean> task = new Task<Boolean>() {
            @Override
            protected Boolean call() throws Exception {
                return new Model<Personnel>("Personnel").update(personnel);
            }
        };
        new Thread(task).start();
        task.setOnSucceeded(event -> {
            if (task.getValue()) {
                personnelTable.refresh();
                ServiceproUtil.notify("Modification OK");
            } else {
                ServiceproUtil.notify("Erreur de modification");
            }
        });

        task.setOnFailed(event -> {
            ServiceproUtil.notify("Modification Thread failed");
            AlertUtil.showErrorMessage(new Exception(task.getException()));
            task.getException().printStackTrace();
            System.err.println(task.getException());

        });
    }



    public void annulerPersonnelAction(ActionEvent event) {
        if(!(btnModifierPersonnel.isDisable() && !btnNouveauPersonnel.isDisable() && stateBtnNouveau != 1)) {
            disableAllComponents(false);
            personnelTable.refresh();
            if (btnNouveauPersonnel.isDisable()) {
                btnModifierPersonnel.setText(ResourceBundle.getBundle("Bundle").getString("button.edit"));
                ServiceproUtil.setDisable(false, btnNouveauPersonnel, btnSupprimerPersonnel);
                stateBtnModifier = 0;
            } else if (btnModifierPersonnel.isDisable()) {
                btnNouveauPersonnel.setText(ResourceBundle.getBundle("Bundle").getString("button.new"));
                ServiceproUtil.setDisable(false, btnModifierPersonnel, btnSupprimerPersonnel);
                stateBtnNouveau = 0;
            }
        }
    }

    public void supprimerPersonnelAction(ActionEvent event) {

        if (!btnSupprimerPersonnel.isDisable() && personnel != null) {
            boolean confirm = AlertUtil.showConfirmationMessage("Suppression du Personnel", "Etes vous sûr de vouloir supprimer " + personnel);
            if (!confirm) {
                return;
            }
            Task<Boolean> task = new Task<Boolean>() {
                @Override
                protected Boolean call() throws Exception {
                    return new Model<Personnel>().delete(personnel);
                }
            };
            new Thread(task).start();
            task.setOnSucceeded(event1 -> {
                if (task.getValue()) {
                    personnelTable.getItems().remove(personnel);
                    ServiceproUtil.notify("Suppression OK");
                } else {
                    ServiceproUtil.notify("Erreur de suppression");
                }
            });
        }
    }
}