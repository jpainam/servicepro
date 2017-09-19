package com.cfao.app.controllers;

import com.cfao.app.Main;
import com.cfao.app.StageManager;
import com.cfao.app.beans.*;
import com.cfao.app.model.*;
import com.cfao.app.reports.CiviliteExcel;
import com.cfao.app.reports.PrintCivilite;
import com.cfao.app.util.*;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import org.apache.log4j.Logger;
import org.controlsfx.control.CheckComboBox;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by JP on 6/9/2017.
 */
public class CiviliteController implements Initializable {
    static Logger logger = Logger.getLogger(CiviliteController.class);

    /**
     * TableView des Personne
     */
    public TableView<Personne> personneTable;
    public TableColumn<Personne, String> columnNom;
    public TableColumn<Personne, String> columnMatricule;

    public DatePicker datePicker;
    public TextField txtMatricule;
    public TextField txtNom;
    public TextField txtPrenom;
    public ComboBox<Societe> comboSociete;
    public ComboBox<Groupe> comboGroupe;

    public ComboBox<Pays> comboPays;
    public Button btnNext;

    public Button btnNouveau;
    public Button btnModifier;

    public Button btnSuppr;

    public int stateBtnNouveau = 0;
    public int stateBtnModifier = 0;
    public ComboBox<Section> comboSection;
    public Label labelAge;

    public CheckComboBox<Langue> comboLanguesParlees;
    public Button addPhoto;
    public Button delPhoto;
    public ImageView imageview;
    public final Image defaultImage = new Image(ResourceBundle.getBundle("Application").getString("default.image"));
    public Button btnPrevious;
    public Button btnPrint;
    public GridPane gridB;
    public Button btnAnnuler;
    public Tab tabDetails;
    public Tab tabCompetence;
    public Tab tabFormation;
    public Tab tabTest;
    public ComboBox<Ambition> comboAmbition;
    public ComboBox<Potentiel> comboPotentiel;
    public ComboBox<Contrat> comboContrat;
    public ComboBox<Langue> comboLangue;
    public VBox hboxSearch;
    public SearchBox searchBox = new SearchBox();


    public TitledPane posteAccordeon;
    public TitledPane profilAccordeon;
    public Accordion accordeon;

    public ObservableMap<String, ObservableList> map;
    public TextArea txtMemo;
    public TextField txtPassport;
    public DatePicker dateFincontrat;
    public DatePicker datePassport;
    public TableColumn<Personne, String> columnTelephone;
    public TableColumn<Personne, Societe> columnPersonneSociete;
    public TextField txtTelephone;
    public TextField txtEmail;
    public TextField txtFonction;

    public CiviliteCompetenceController competenceController;
    public CiviliteFormationController formationController;
    public CiviliteProfilController profilController;
    public CivilitePosteController posteController;
    public CiviliteQcmController qcmController;
    public CivilitePhoto civilitePhoto;
    public StackPane imageviewStackPane;
    public StackPane personneStackPane;

    public Label searchLabelStat = new Label();
    public Button btnPrintExcel;
    private ObservableList<Personne> masterData = FXCollections.observableArrayList();
    private FilteredList<Personne> filteredList = new FilteredList<Personne>(masterData, p -> true);
    private SortedList<Personne> sortedList = new SortedList<Personne>(filteredList);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        buildcontent();
        searchBox.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(searchBox, Priority.ALWAYS);
        //searchLabelStat.textProperty().bind(new SimpleIntegerProperty(filteredList.size()).asString());
        hboxSearch.getChildren().setAll(new HBox(new Label("Civilité "), searchLabelStat), searchBox);

        ServiceproUtil.setAccordionExpanded(accordeon, profilAccordeon);
        /*Platform.runLater(() -> {
            personneTable.requestFocus();
            if(personneTable.getItems().size() > 0) {
                personneTable.getSelectionModel().select(0);
                personneTable.getFocusModel().focus(0);
            }
        });*/
        disableAllComponents(true);
        personneTable.setTableMenuButtonVisible(true);
        /**
         * GENERATION DU MATRICULE
         */
        comboPays.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (stateBtnNouveau == 1) {
                if (newValue != null) {
                    Task<Personne> task = new Task<Personne>() {
                        @Override
                        protected Personne call() throws Exception {
                            return new PersonneModel().getLastPersonneByPays(newValue);
                        }
                    };
                    new Thread(task).start();
                    task.setOnSucceeded(event -> {
                        if (task.getValue() != null) {
                            Personne p = task.getValue();
                            String matric = String.valueOf(Integer.parseInt(p.getMatricule().substring(3, p.getMatricule().length())) + 1);
                            matric = new String(new char[5 - matric.length()]).replace("\0", "0") + matric;
                            txtMatricule.setText(newValue.getIso() + "" + matric);
                        } else {
                            // Premier Civilite de ce pays
                            String matric = String.valueOf((int) (Math.random() * 99999));
                            matric = new String(new char[5 - matric.length()]).replace("\0", "0") + matric;
                            txtMatricule.setText(newValue.getIso() + matric);
                        }
                    });
                }
            }
        });
    }

    private void buildcontent() {
        formationController = new CiviliteFormationController();
        tabFormation.setContent(formationController);
        competenceController = new CiviliteCompetenceController();
        tabCompetence.setContent(competenceController);
        profilController = new CiviliteProfilController();
        profilAccordeon.setContent(profilController);
        posteController = new CivilitePosteController();
        posteAccordeon.setContent(posteController);
        qcmController = new CiviliteQcmController();
        civilitePhoto = new CivilitePhoto();
        tabTest.setContent(qcmController);
        initComponents();
        buildCombo();
        buildtablePersonne();
    }

    private void initComponents() {

        comboLanguesParlees = new CheckComboBox(FXCollections.observableArrayList());
        comboLanguesParlees.setMaxWidth(Double.MAX_VALUE);
        //comboLanguesParlees.setDisable(true);
        gridB.add(comboLanguesParlees, 3, 3);
        datePicker.setValue(LocalDate.now());
        GlyphsDude.setIcon(addPhoto, FontAwesomeIcon.PICTURE_ALT);
        GlyphsDude.setIcon(delPhoto, FontAwesomeIcon.CLOSE);
        imageview.setImage(defaultImage);
        GlyphsDude.setIcon(tabDetails, FontAwesomeIcon.USER);
        GlyphsDude.setIcon(tabFormation, FontAwesomeIcon.TASKS);
        GlyphsDude.setIcon(tabCompetence, FontAwesomeIcon.SLACK);
        GlyphsDude.setIcon(tabTest, FontAwesomeIcon.SITEMAP);
        ButtonUtil.excel(btnPrintExcel);

        GlyphsDude.setIcon(btnNext, FontAwesomeIcon.ARROW_RIGHT);
        GlyphsDude.setIcon(btnPrevious, FontAwesomeIcon.ARROW_LEFT);
        ButtonUtil.print(btnPrint);
        ButtonUtil.delete(btnSuppr);
        ButtonUtil.edit(btnModifier);
        ButtonUtil.add(btnNouveau);
        ButtonUtil.cancel(btnAnnuler);

        personneTable.setRowFactory(param -> {
            final TableRow<Personne> row = new TableRow<>();
            final Tooltip tooltip = new Tooltip();
            row.hoverProperty().addListener(observable -> {
                final Personne personne = row.getItem();
                if (row.isHover() && personne != null) {
                    tooltip.setText(personne.getNom() + " " + personne.getPrenom() + " => " + personne.getSociete());
                    row.setTooltip(tooltip);
                }
            });
            final ContextMenu rowMenu = new ContextMenu();
            MenuItem viewPassportItem = new MenuItem("Afficher passport");
            GlyphsDude.setIcon(viewPassportItem, FontAwesomeIcon.FILE_ALT);
            viewPassportItem.setOnAction(event -> afficherPassport(row.getItem()));
            MenuItem editItem = new MenuItem("Modifier/Edit");
            GlyphsDude.setIcon(editItem, FontAwesomeIcon.PENCIL);
            editItem.setOnAction(event -> clicModifier(event));
            MenuItem removeItem = new MenuItem("Supprimer/Delete");
            GlyphsDude.setIcon(removeItem, FontAwesomeIcon.TRASH);
            removeItem.setOnAction(event -> clicSuppr(event));
            rowMenu.getItems().addAll(viewPassportItem, new SeparatorMenuItem(), editItem, removeItem);

            // only display context menu for non-null items:
            row.contextMenuProperty().bind(
                    Bindings.when(Bindings.isNotNull(row.itemProperty()))
                            .then(rowMenu)
                            .otherwise((ContextMenu) null));
            return row;
        });
        ServiceproUtil.setDisable(true, btnModifier, btnSuppr);
    }


    private void buildCombo() {
        Task<ObservableMap<String, ObservableList>> task = new Task<ObservableMap<String, ObservableList>>() {
            @Override
            protected ObservableMap<String, ObservableList> call() throws Exception {
                map = FXCollections.observableHashMap();
                map.put("societe", FXCollections.observableList((new SocieteModel()).getList()));
                map.put("section", FXCollections.observableList((new SectionModel()).getList()));
                map.put("groupe", FXCollections.observableList((new GroupeModel()).getList()));
                map.put("pays", FXCollections.observableList((new Model<Pays>("Pays")).getList()));
                map.put("langue", FXCollections.observableList(new Model<Langue>("Langue").getList()));
                map.put("potentiel", FXCollections.observableList(new Model<Potentiel>("Potentiel").getList()));
                map.put("contrat", FXCollections.observableList(new Model<Contrat>("Contrat").getList()));
                map.put("ambition", FXCollections.observableList(new Model<Ambition>("Ambition").getList()));
                map.put("langue", FXCollections.observableList(new Model<Langue>("Langue").getList()));
                return map;
            }
        };
        new Thread(task).start();
        task.setOnSucceeded(event -> {
            map = task.getValue();
            comboPays.setItems(map.get("pays"));
            comboGroupe.setItems(map.get("groupe"));
            comboSociete.setItems(map.get("societe"));
            comboSection.setItems(map.get("section"));
            comboLanguesParlees.getItems().setAll(map.get("langue"));
            comboPotentiel.setItems(map.get("potentiel"));
            comboContrat.setItems(map.get("contrat"));
            comboAmbition.setItems(map.get("ambition"));
            comboLangue.setItems(map.get("langue"));
        });
        task.setOnFailed(event -> {
            System.err.println(task.getException());
            AlertUtil.showErrorMessage(new Exception(task.getException()));
        });
    }

    private void disableAllComponents(boolean bool) {
        ServiceproUtil.setEditable(!bool, txtMatricule, txtNom, txtPrenom, txtMemo, txtTelephone, txtEmail, txtFonction);
        ServiceproUtil.setDisable(bool, comboGroupe, comboPays, comboSociete, comboSection, comboPotentiel, comboAmbition,
                comboLangue, comboContrat, datePicker, dateFincontrat, comboLanguesParlees, datePassport);
    }

    private void buildtablePersonne() {
        Task<ObservableList<Personne>> task = new Task<ObservableList<Personne>>() {
            @Override
            protected ObservableList<Personne> call() throws Exception {
                return FXCollections.observableArrayList(new PersonneModel().getList());
            }
        };
        ProgressIndicatorUtil.show(personneStackPane, task);
        new Thread(task).start();
        task.setOnSucceeded(event -> {
            masterData.addAll(task.getValue());
            sortedList.comparatorProperty().bind(personneTable.comparatorProperty());
            searchLabelStat.setText("(" + filteredList.size() + ")");
            personneTable.setItems(sortedList);
        });
        searchBox.textProperty().addListener((observable, oldValue, newValue) -> filteredList.setPredicate(p -> {
            if (newValue == null || newValue.isEmpty()) {
                return true;
            }
            String val = newValue.toLowerCase();
            if (p.getNom().toLowerCase().contains(val) || p.getPrenom().toLowerCase().contains(val) || p.getMatricule().toLowerCase().contains(newValue)) {
                return true;
            }
            if (p.getSociete() != null && p.getSociete().toString().toLowerCase().contains(val)) {
                return true;
            }
            return false;
        }));
        columnMatricule.setCellValueFactory(param -> param.getValue().matriculeProperty());
        columnNom.setCellValueFactory(param -> param.getValue().nomProperty());
        columnPersonneSociete.setCellValueFactory(param -> param.getValue().societe());
        columnTelephone.setCellValueFactory(param -> param.getValue().telephoneProperty());

        personneTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (stateBtnNouveau == 1) {
                ServiceproUtil.setDisable(false, btnModifier, btnSuppr);
                disableAllComponents(true);
                btnNouveau.setText(ResourceBundle.getBundle("Bundle").getString("button.new"));
                stateBtnNouveau = 0;
            }

            if (stateBtnModifier != 1 && newValue != null)
                renduFormulaire(newValue);

            if (newValue == null) {
                btnModifier.setDisable(true);
                btnSuppr.setDisable(true);
            }
        });
    }

    private void renduFormulaire(Personne person) {
        ServiceproUtil.setDisable(false, btnModifier, btnSuppr);
        /* Mise à jour du formalaire fonction des choix sur la table*/
        txtMatricule.setText(person.getMatricule());
        txtNom.setText(person.getNom());
        txtPrenom.setText(person.getPrenom());
        txtMemo.setText(person.getMemo());
        txtTelephone.setText(person.getTelephone());
        txtEmail.setText(person.getEmail());
        txtPassport.setText(person.getPassport());
        txtFonction.setText(person.getFonction());

        if (person.getDatenaiss() != null) {
            LocalDate date = new java.sql.Date(person.getDatenaiss().getTime()).toLocalDate();
            datePicker.getEditor().setText(date.format(DateUtil.currentForme));
            datePicker.setValue(date);
            labelAge.setText(DateUtil.age(date));
        }

        if (person.getDatecontrat() != null) {
            LocalDate date = new java.sql.Date(person.getDatecontrat().getTime()).toLocalDate();
            dateFincontrat.getEditor().setText(date.format(DateUtil.currentForme));
            dateFincontrat.setValue(date);
        }
        if (person.getExpirationPassport() != null) {
            LocalDate date = new java.sql.Date(person.getExpirationPassport().getTime()).toLocalDate();
            datePassport.getEditor().setText(date.format(DateUtil.currentForme));
            datePassport.setValue(date);
        }

        comboSection.setValue(person.getSection());
        comboSociete.setValue(person.getSociete());
        comboPays.setValue(person.getPays());
        comboGroupe.setValue(person.getGroupe());
        comboContrat.setValue(person.getContrat());
        comboAmbition.setValue(person.getAmbition());
        comboLangue.setValue(person.getLangue());
        comboPotentiel.setValue(person.getPotentiel());

        /*Necessaire à l'update du component checkcombobox des Langues parlées par une personne*/
        updateLangue(person.getLangues());

        formationController.setPersonne(person);
        formationController.buildFormation();
        competenceController.setPersonne(person);
        competenceController.buildCompetence();
        profilController.setPersonne(person);
        profilController.buildProfil();
        posteController.setPersonne(person);
        posteController.buildPoste();
        qcmController.setPersonne(person);
        qcmController.buildTable();

         /* Chargement Photo */
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                imageview.setImage(civilitePhoto.getImage(person));
                return null;
            }
        };
        /* Activer le progress bar pdt le chargement de la photo et des tableview*/
        ProgressIndicatorUtil.show(imageviewStackPane, task);
        new Thread(task).start();
        task.setOnFailed(event -> {
            task.getException().printStackTrace();
            System.err.println(task.getException().getCause());
        });
    }

    private void updateLangue(List<Langue> listLangue) {
        comboLanguesParlees.getCheckModel().clearChecks();
        Task<int[]> task = new Task<int[]>() {
            @Override
            protected int[] call() throws Exception {
                int[] tab = new int[comboLanguesParlees.getItems().size()];
                int index = 0, i = 0;
                for (Langue l : comboLanguesParlees.getItems()) {
                    Iterator<Langue> iterator = listLangue.iterator();
                    while (iterator.hasNext()) {
                        if (l.equals(iterator.next())) {
                            tab[i] = index;
                            i++;
                        }
                    }
                    index++;
                }
                return tab;
            }
        };
        new Thread(task).start();
        task.setOnSucceeded(event -> {
            Platform.runLater(() -> {
                comboLanguesParlees.getCheckModel().checkIndices(task.getValue());
            });
        });
    }

    private void setPersonneParameters(Personne personne) {
        personne.setMatricule(txtMatricule.getText());
        personne.setNom(txtNom.getText());
        personne.setPrenom(txtPrenom.getText());
        personne.setMemo(txtMemo.getText());
        personne.setTelephone(txtTelephone.getText());
        personne.setEmail(txtEmail.getText());
        personne.setPassport(txtPassport.getText());
        personne.setFonction(txtFonction.getText());
        if (datePassport.getValue() != null) {
            personne.setExpirationPassport(java.sql.Date.valueOf(datePassport.getValue()));
        }
        if (datePicker.getValue() != null) {
            personne.setDatenaiss(java.sql.Date.valueOf(datePicker.getValue()));
        }
        if (dateFincontrat.getValue() != null) {
            personne.setDatecontrat(java.sql.Date.valueOf(dateFincontrat.getValue()));
        }
        personne.setPays(comboPays.getValue());
        /*
        * comboLanguesParlees.getCheckModel().getCheckedItems() renvoi une ReadOnlyObserverList
        * */
        personne.setLangues(comboLanguesParlees.getCheckModel().getCheckedItems());
        personne.setGroupe(comboGroupe.getValue());
        personne.setSection(comboSection.getValue());
        personne.setSociete(comboSociete.getValue());
        personne.setContrat(comboContrat.getValue());
        personne.setLangue(comboLangue.getValue());
        personne.setAmbition(comboAmbition.getValue());
        personne.setPotentiel(comboPotentiel.getValue());
        personne.setPhoto(civilitePhoto.getCurrentPhoto());
        System.err.println(civilitePhoto.getCurrentPhoto());
    }

    public void clickNouveau(ActionEvent actionEvent) {
        if (!btnNouveau.isDisable())
            switch (stateBtnNouveau) {
                case 0:
                    ServiceproUtil.setDisable(true, btnModifier, btnSuppr);
                    disableAllComponents(false);
                    ButtonUtil.save(btnNouveau);
                    this.stateBtnNouveau = 1;
                    profilController.setActive(true);
                    posteController.setActive(true);
                    break;
                case 1:
                    /**
                     * Creation d'une nouvel personne
                     */
                    Personne personne = new Personne();
                    setPersonneParameters(personne);
                    savePersonne(personne);
                    StageManager.loadContent("/views/civilite/civilite.fxml");
                    break;
            }
    }

    /*
        bool == true for add, and false for update
     */
    private void savePersonne(Personne personne) {

        Task<Boolean> task = new Task<Boolean>() {
            @Override
            protected Boolean call() throws Exception {
                if (profilController.getItems().size() > 0) {
                    for (ProfilPersonne p : profilController.getItems())
                        p.setPersonne(personne);
                }
                personne.setProfilPersonnes(profilController.getItems());

                if (posteController.getItems().size() > 0) {
                    for (Poste p : posteController.getItems())
                        p.setPersonne(personne);
                }
                personne.setPostes(posteController.getItems());
                boolean bool;
                PersonneModel model = new PersonneModel();
                bool = model.save(personne);
                return bool;
            }
        };
        new Thread(task).start();
        task.setOnSucceeded(event -> {
            if (task.getValue()) {
                //personneTable.getSelectionModel().select(personne);
                ServiceproUtil.notify("Ajout OK");
                StageManager.loadContent("/views/civilite/civilite.fxml");
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

    public void clicModifier(ActionEvent actionEvent) {
        Personne personne = personneTable.getSelectionModel().getSelectedItem();
        if (!btnModifier.isDisable() && personne != null)
            switch (stateBtnModifier) {
                case 0:
                    ServiceproUtil.setEditable(true, txtEmail, txtMatricule, txtMemo, txtNom, txtPrenom, txtTelephone, txtFonction);
                    ServiceproUtil.setDisable(true, btnNouveau, btnSuppr);
                    disableAllComponents(false);
                    btnModifier.setText(ResourceBundle.getBundle("Bundle").getString("button.save"));
                    this.stateBtnModifier = 1;
                    profilController.setActive(true);
                    posteController.setActive(true);
                    break;
                case 1:
                    setPersonneParameters(personne);
                    updatePersonne(personne);
                    StageManager.loadContent("/views/civilite/civilite.fxml");
                    break;
            }
    }

    private void updatePersonne(Personne personne) {
        personne.setProfilPersonnes(profilController.getItems());
        for (ProfilPersonne profilPersonne : personne.getProfilPersonnes()) {
            profilPersonne.getId().setPersonne(personne.getIdpersonne());
        }
        personne.setPostes(posteController.getItems());
        for (Poste poste : personne.getPostes()) {
            poste.setPersonne(personne);
        }
        Task<Boolean> task = new Task<Boolean>() {
            @Override
            protected Boolean call() throws Exception {
                PersonneModel personneModel = new PersonneModel();
                if (personneModel.update(personne)) {
                    return true;
                }
                return false;
            }
        };
        new Thread(task).start();
        task.setOnSucceeded(event -> {
            if (task.getValue()) {
                ServiceproUtil.notify("Modification OK");

                StageManager.loadContent("/views/civilite/civilite.fxml");
            } else {
                ServiceproUtil.notify("Erreur de modification");
            }
        });

        task.setOnFailed(event -> {
            ServiceproUtil.notify("Modification Thread failed");
            //AlertUtil.showErrorMessage(new Exception(task.getException()));
            task.getException().printStackTrace();
            System.err.println(task.getException());

        });
    }

    public void clicSuppr(ActionEvent actionEvent) {

        if (!btnSuppr.isDisable() && personneTable.getSelectionModel().getSelectedItem() != null) {
            Personne p = personneTable.getSelectionModel().getSelectedItem();
            boolean confirm = AlertUtil.showConfirmationMessage("Suppression d'une civilité", "Etes vous sûr de vouloir supprimer " + p);
            if (!confirm) {
                return;
            }
            Task<Boolean> task = new Task<Boolean>() {
                @Override
                protected Boolean call() throws Exception {
                    return new Model<Personne>().delete(p);
                }
            };
            new Thread(task).start();
            task.setOnSucceeded(event -> {
                if (task.getValue()) {
                    ServiceproUtil.notify("Suppression OK");
                    /*personneTable.getSelectionModel().selectPrevious();
                    personneTable.getItems().remove(p);*/
                    StageManager.loadContent("/views/civilite/civilite.fxml");
                } else {
                    ServiceproUtil.notify("Erreur de suppression");
                }
            });
        }
    }

    public void datePickerClic(ActionEvent actionEvent) {
        if (datePicker.getValue() != null)
            labelAge.setText(DateUtil.age(datePicker.getValue()));
    }

    public void delPhotoClic(ActionEvent actionEvent) {
        Personne personne = personneTable.getSelectionModel().getSelectedItem();
        if (stateBtnNouveau == 1 || stateBtnModifier == 1) {
            Task<Boolean> task = new Task<Boolean>() {
                @Override
                protected Boolean call() throws Exception {
                    return civilitePhoto.deletePhoto(personne);
                }
            };
            new Thread(task).start();
            task.setOnSucceeded(event -> {
                if (task.getValue()) {
                    imageview.setImage(defaultImage);
                } else {
                    ServiceproUtil.notify("Erreur de suppression de la photo");
                }
            });
            task.setOnFailed(event -> AlertUtil.showErrorMessage(new Exception(task.getException())));
        }
    }

    public void addPhotoClic(ActionEvent actionEvent) {
        if (stateBtnNouveau == 1 || stateBtnModifier == 1) {
            FileChooser choosePic = new FileChooser();
            choosePic.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("Fichiers Image", "*.png", "*.jpg", "*.gif", "*.jpeg", "*.pmb")
            );
            File file;
            try {
                if ((file = choosePic.showOpenDialog(Main.stage)) != null) {
                    String dstFileName = civilitePhoto.savePhoto(file);
                    imageview.setImage(civilitePhoto.getImage(dstFileName));
                }
            } catch (Exception ex) {
                AlertUtil.showErrorMessage(ex);
            }
        }
    }

    public void addPassportFile(MouseEvent event) {
        if (stateBtnNouveau == 1 || stateBtnModifier == 1) {
            FileChooser fileChooser = new FileChooser();
            File file;
            try {
                if ((file = fileChooser.showOpenDialog(Main.stage)) != null) {
                    Path path = Paths.get(ResourceBundle.getBundle("Bundle").getString("passport.dir")).toAbsolutePath();
                    if (path == null) {
                        Files.createDirectories(path);
                    }
                    String chemin = path.toString() + File.separator + file.getName();
                    Files.copy(file.toPath(), Paths.get(chemin), StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.COPY_ATTRIBUTES);
                    txtPassport.setText(file.getName());
                }
            } catch (Exception ex) {
                AlertUtil.showErrorMessage(ex);
            }
        }
    }

    public void clicAnnuler(ActionEvent actionEvent) {
        StageManager.loadContent("/views/civilite/civilite.fxml");
    }

    public void clicPrev(ActionEvent actionEvent) {
        personneTable.getSelectionModel().selectPrevious();
    }

    public void clicNext(ActionEvent actionEvent) {
        personneTable.getSelectionModel().selectNext();
    }

    private void afficherPassport(Personne personne) {
        Task<Boolean> task = new Task<Boolean>() {
            @Override
            protected Boolean call() throws Exception {
                Path path = Paths.get(ResourceBundle.getBundle("Bundle").getString("passport.dir")).toAbsolutePath();
                String chemin = path.toString() + File.separator + personne.getPassport();
                File file = new File(chemin);
                if (file.exists() && !file.isDirectory()) {
                    ServiceproUtil.openDocument(file);
                    return true;
                } else {
                    return false;
                }
            }
        };
        new Thread(task).start();
        task.setOnSucceeded(event -> {
            if (!task.getValue()) {
                AlertUtil.showSimpleAlert("Information", "Fichier passport introuvable");
            }
        });
        task.setOnFailed(event -> {
            ServiceproUtil.notify("Erreur dans le thread du print passport");
            task.getException().printStackTrace();
        });
    }

    public void printCiviliteAction(ActionEvent event) {
        final Personne personne = personneTable.getSelectionModel().getSelectedItem();
        if (null == personne) {
            AlertUtil.showWarningMessage("Attention", "Veuillez choisir la personne à imprimer");
            return;
        }
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                PrintCivilite print = new PrintCivilite();
                print.printDetails(personne);

                return null;
            }
        };
        StageManager.getProgressBar().progressProperty().bind(task.progressProperty());
        new Thread(task).start();
        task.setOnSucceeded(event1 -> {
            StageManager.getProgressBar().progressProperty().unbind();
            StageManager.getProgressBar().setProgress(0);
            ServiceproUtil.notify("Impression réussie");
        });
        task.setOnFailed(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                task.getException().printStackTrace();
                logger.error(task.getException());
            }
        });
    }

    public void printExcelCivilite(ActionEvent event) {
        Personne personne = personneTable.getSelectionModel().getSelectedItem();
        if (personne == null) {
            AlertUtil.showWarningMessage("Information", "Veuillez d'abord choisir une civilité");
            return;
        }
        try {
            Task<Void> task = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    CiviliteExcel civiliteExcel = new CiviliteExcel();
                    civiliteExcel.printDetails(personne);
                    return null;
                }
            };

            StageManager.getProgressBar().progressProperty().bind(task.progressProperty());
            new Thread(task).start();
            task.setOnSucceeded(event1 -> {
                StageManager.getProgressBar().progressProperty().unbind();
                StageManager.getProgressBar().setProgress(0);
                ServiceproUtil.notify("Impression réussie");
            });
            task.setOnFailed(event12 -> {
                logger.error(task.getException());
                task.getException().printStackTrace();
            });
        } catch (Exception ex) {
            AlertUtil.showErrorMessage(ex);
            logger.error(ex);
        }
    }
}