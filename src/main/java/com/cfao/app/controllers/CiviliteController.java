package com.cfao.app.controllers;

import com.cfao.app.Main;
import com.cfao.app.StageManager;
import com.cfao.app.beans.*;
import com.cfao.app.model.*;
import com.cfao.app.util.*;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import org.controlsfx.control.CheckComboBox;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by JP on 6/9/2017.
 */
public class CiviliteController implements Initializable {

    /**
     * TableView des Personne
     */
    public Model<Personne> modelPersonne;
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
    public ComboBox searchCombo;
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
    public DatePicker dateFincontrat;
    public TableColumn<Personne, String> columnTelephone;
    public TableColumn<Personne, Societe> columnPersonneSociete;
    public TextField txtTelephone;
    public TextField txtEmail;
    public ObservableMap<String, String> mapFoto;
    public CiviliteFormationController formationController;
    public CiviliteProfilController profilController;
    public CivilitePosteController posteController;
    public CiviliteQcmController qcmController;
    public CivilitePhoto civilitePhoto;
    public StackPane imageviewStackPane;
    public StackPane personneStackPane;

    public Label searchLabelStat = new Label();
    private ObservableList<Personne> masterData = FXCollections.observableArrayList();
    private FilteredList<Personne> filteredList = new FilteredList<Personne>(masterData, p -> true);
    private SortedList<Personne> sortedList = new SortedList<Personne>(filteredList);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        buildcontent();
        searchBox.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(searchBox, Priority.ALWAYS);
        //searchLabelStat.textProperty().bind(new SimpleIntegerProperty(filteredList.size()).asString());
        hboxSearch.getChildren().setAll(new HBox(new Label("Civilité "), searchLabelStat),  searchBox);

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
    }

    private void buildcontent() {
        formationController = new CiviliteFormationController();
        tabFormation.setContent(formationController);
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
        gridB.add(comboLanguesParlees, 3, 1);
        datePicker.setValue(LocalDate.now());
        GlyphsDude.setIcon(addPhoto, FontAwesomeIcon.PICTURE_ALT);
        GlyphsDude.setIcon(delPhoto, FontAwesomeIcon.CLOSE);
        imageview.setImage(defaultImage);
        GlyphsDude.setIcon(tabDetails, FontAwesomeIcon.USER);
        GlyphsDude.setIcon(tabFormation, FontAwesomeIcon.TASKS);
        GlyphsDude.setIcon(tabTest, FontAwesomeIcon.SITEMAP);

        GlyphsDude.setIcon(btnNext, FontAwesomeIcon.ARROW_RIGHT);
        GlyphsDude.setIcon(btnPrevious, FontAwesomeIcon.ARROW_LEFT);
        GlyphsDude.setIcon(btnPrint, FontAwesomeIcon.PRINT);
        GlyphsDude.setIcon(btnSuppr, FontAwesomeIcon.TRASH);
        GlyphsDude.setIcon(btnModifier, FontAwesomeIcon.PENCIL);
        GlyphsDude.setIcon(btnNouveau, FontAwesomeIcon.FILE);
        GlyphsDude.setIcon(btnAnnuler, FontAwesomeIcon.SHARE_SQUARE);

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
        ServiceproUtil.setEditable(!bool, txtMatricule, txtNom, txtPrenom, txtMemo, txtTelephone, txtEmail);
        ServiceproUtil.setDisable(bool, comboGroupe, comboPays, comboSociete, comboSection, comboPotentiel, comboAmbition,
                comboLangue, comboContrat, datePicker, dateFincontrat, comboLanguesParlees);
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

        LocalDate date1 = new java.sql.Date(person.getDatenaiss().getTime()).toLocalDate();
        datePicker.getEditor().setText(date1.format(FormatDate.currentForme));
        datePicker.setValue(date1);
        labelAge.setText(DateUtil.age(date1));

        //LocalDate date2 = new java.sql.Date(person.getDatecontrat().getTime()).toLocalDate();
        //dateFincontrat.getEditor().setText(date2.format(FormatDate.currentForme));
        //dateFincontrat.setValue(date2);

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
                    btnNouveau.setText(ResourceBundle.getBundle("Bundle").getString("button.save"));
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
                    /*personneTable.getItems().add(personne);
                    ServiceproUtil.setDisable(false, btnModifier, btnSuppr);
                    disableAllComponents(true);
                    btnNouveau.setText(ResourceBundle.getBundle("Bundle").getString("button.new"));
                    this.stateBtnNouveau = 0;
                    profilController.setActive(false);
                    posteController.setActive(false);*/
                    break;
            }
    }

    /*
        bool == true for save, and false for update
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
                    ServiceproUtil.setEditable(true, txtEmail, txtMatricule, txtMemo, txtNom, txtPrenom, txtTelephone);
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
                    /*disableAllComponents(true);
                    btnModifier.setText(ResourceBundle.getBundle("Bundle").getString("button.edit"));
                    ServiceproUtil.setDisable(false, btnNouveau);
                    this.stateBtnModifier = 0;
                    profilController.setActive(false);
                    posteController.setActive(false);*/
                    break;
            }
    }

    private void updatePersonne(Personne personne) {
        personne.setProfilPersonnes(profilController.getItems());
        for(ProfilPersonne profilPersonne : personne.getProfilPersonnes()){
            profilPersonne.getId().setPersonne(personne.getIdpersonne());
        }
        personne.setPostes(posteController.getItems());
        for(Poste poste : personne.getPostes()){
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

    public void clicAnnuler(ActionEvent actionEvent) {
        StageManager.loadContent("/views/civilite/civilite.fxml");
    }

    public void clicPrev(ActionEvent actionEvent) {
        personneTable.getSelectionModel().selectPrevious();
    }

    public void clicNext(ActionEvent actionEvent) {
        personneTable.getSelectionModel().selectNext();
    }
}