package com.cfao.app.controllers;

import com.cfao.app.Main;
import com.cfao.app.StageManager;
import com.cfao.app.beans.*;
import com.cfao.app.model.*;
import com.cfao.app.util.FormatDate;
import com.cfao.app.util.SearchBox;
import com.cfao.app.util.ServiceproUtil;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import org.controlsfx.control.CheckComboBox;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
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

    /**
     * TableView des Personne
     */
    public Model<Personne> modelPersonne;
    public TableView<Personne> personneTable;
    public TableColumn columnNom;
    public TableColumn columnMatricule;

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
    public AnchorPane paneImage;
    public File photo = null;
    public final String defaultImage = "/images/civilite/avatar_defaut.png";
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
    public HBox hboxSearch;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        buildcontent();
        searchBox.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(searchBox, Priority.ALWAYS);
        hboxSearch.getChildren().setAll(new Label("Civilités :"), searchBox);
        ServiceproUtil.setAccordionExpanded(accordeon, profilAccordeon);
    }

    private void buildcontent() {
        formationController = new CiviliteFormationController();
        tabFormation.setContent(formationController);
        profilController = new CiviliteProfilController();
        profilAccordeon.setContent(profilController);
        posteController = new CivilitePosteController();
        posteAccordeon.setContent(posteController);
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

        Rectangle clip = new Rectangle(
                paneImage.getPrefWidth(), paneImage.getPrefHeight()
        );
        clip.setArcWidth(20);
        clip.setArcHeight(20);
        paneImage.setClip(clip);
        Image img = new Image(defaultImage);
        imageview.setImage(img);

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
                if(row.isHover() && personne != null){
                    tooltip.setText(personne.getNom() + " " +personne.getPrenom() + " => " + personne.getSociete());
                    row.setTooltip(tooltip);
                }
            });
            return row;
        });

        /*DESACTIVATION DES BUTTON MODIF ET SUPPR*/
        ServiceproUtil.setDisable(true, btnModifier, btnSuppr);
    }

    private void buildCombo() {
        System.out.println("je suis dans le build Combo");
        Task<ObservableMap<String, ObservableList>> task = new Task<ObservableMap<String, ObservableList>>() {
            @Override
            protected ObservableMap<String, ObservableList> call() throws Exception {

                /* MAP PHOTOS */
                mapFoto = FXCollections.observableHashMap();
                Path path = Paths.get(URI.create(getClass().getResource("/photos").toExternalForm()));
                if (path == null) {
                    System.exit(0);
                }
                if (path == null) {
                    Files.createDirectories(path);
                }
                File f = new File(URI.create(getClass().getResource("/photos").toExternalForm()));

                if (f.exists() && f.isDirectory())
                    if (f.listFiles().length > 0)
                        for (File fils : f.listFiles()) {
                            if (fils.isFile()) {
                                String[] tab = getSplitNameImage(fils.getName());
                                mapFoto.put(tab[0], tab[1]);
                            }
                        }
                /* END MAP PHOTOS */
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
                map.put("personne", FXCollections.observableList((new PersonneModel()).getList()));
                return map;
            }
        };
        new Thread(task).run();
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
    }

    private void disableAllComponents(boolean bool) {
        ServiceproUtil.setEditable(bool, txtMatricule, txtNom, txtPrenom, txtMemo);
        ServiceproUtil.setDisable(bool, comboGroupe, comboPays, comboSociete, comboSection, comboPotentiel, comboAmbition,
                comboLangue, comboContrat, datePicker, dateFincontrat, comboLanguesParlees);
    }

    private void buildtablePersonne() {

        columnMatricule.setCellValueFactory(new PropertyValueFactory<Personne, String>("matricule"));
        columnNom.setCellValueFactory(new PropertyValueFactory<Personne, String>("nom"));
        columnPersonneSociete.setCellValueFactory(param -> param.getValue().societe());
        columnTelephone.setCellValueFactory(param -> param.getValue().telephoneProperty());

        personneTable.setItems(map.get("personne"));
        personneTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Personne>() {
            @Override
            public void changed(ObservableValue<? extends Personne> observable, Personne oldValue, Personne newValue) {
                if (stateBtnNouveau == 1) {
                    ServiceproUtil.setDisable(false, btnModifier, btnSuppr);
                    disableAllComponents(true);
                    btnNouveau.setText("Nouveau / New");
                    stateBtnNouveau = 0;
                }

                if (stateBtnModifier != 1 && newValue != null)
                    renduFormulaire(newValue);

                if (newValue == null) {
                    btnModifier.setDisable(true);
                    btnSuppr.setDisable(true);
                }
            }
        });
    }

    private void renduFormulaire(Personne person) {
        ServiceproUtil.setDisable(false, btnModifier, btnSuppr);
        /* Chargement Photo */
        chargementPhoto(person.getIdpersonne());

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
        labelAge.setText(age(date1));

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

    }

    private void chargementPhoto(int idpersonne) {
        if (mapFoto.containsKey(String.valueOf(idpersonne))) {
            Task<Void> task = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    try {
                        String namefile = idpersonne + "." + mapFoto.get(String.valueOf(idpersonne));
                        Path path = Paths.get(URI.create(getClass().getResource("/photos/" + namefile).toExternalForm()));
                        if (path == null) {
                            Files.createDirectories(path);
                        }
                        imageview.setImage(new Image(new FileInputStream(path.toFile())));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return null;
                }
            };
            new Thread(task).run();
        } else
            try {
                imageview.setImage(new Image(getClass().getResource(defaultImage).openStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    private void updateLangue(List<Langue> listLangue) {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                comboLanguesParlees.getCheckModel().clearChecks();
                int index = 0;
                for (Langue l : comboLanguesParlees.getItems()) {
                    Iterator<Langue> iterator = listLangue.iterator();
                    while (iterator.hasNext()) {
                        if (l.equals(iterator.next()))
                            comboLanguesParlees.getCheckModel().check(index);
                    }
                    index++;
                }
                return null;
            }
        };
        task.run();
    }

    public String age(LocalDate date) {
        int a = LocalDate.now().getYear() - date.getYear();
        return a + (a > 1 ? " ans" : " an");
    }
    private void setPersonneParameters(Personne personne){
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
        personne.setLangues(FXCollections.observableArrayList(comboLanguesParlees.getCheckModel().getCheckedItems()));
        personne.setGroupe(comboGroupe.getValue());
        personne.setSection(comboSection.getValue());
        personne.setSociete(comboSociete.getValue());
        personne.setContrat(comboContrat.getValue());
        personne.setLangue(comboLangue.getValue());
        personne.setAmbition(comboAmbition.getValue());
        personne.setPotentiel(comboPotentiel.getValue());
    }

    public void clickNouveau(ActionEvent actionEvent) {
        if (!btnNouveau.isDisable())
            switch (stateBtnNouveau) {
                case 0:
                    ServiceproUtil.setDisable(true, btnModifier, btnSuppr);
                    disableAllComponents(false);
                    btnNouveau.setText("Enregistrer / Save");
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
                    personneTable.getItems().add(personne);
                    ServiceproUtil.setDisable(false, btnModifier, btnSuppr);
                    disableAllComponents(true);
                    btnNouveau.setText(ResourceBundle.getBundle("Bundle").getString("buttion.add"));
                    this.stateBtnNouveau = 0;
                    profilController.setActive(false);
                    posteController.setActive(false);
                    break;
            }
    }

    /*
        bool == true for save, and false for update
     */
    private void savePersonne(Personne personne) {

        Task<Personne> task = new Task<Personne>() {
            @Override
            protected Personne call() throws Exception {
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

                new Model<Personne>().save(personne);

                if (photo != null) {
                    savePhoto(personne.getIdpersonne());
                }

                return personne;
            }
        };
        new Thread(task).run();
        task.setOnSucceeded(event -> {
            Platform.runLater(() -> {
                personneTable.getSelectionModel().select(task.getValue());
                System.out.println("Good");
            });
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
                    disableAllComponents(true);
                    btnModifier.setText(ResourceBundle.getBundle("Bundle").getString("button.edit"));
                    ServiceproUtil.setDisable(false, btnNouveau);
                    this.stateBtnModifier = 0;
                    profilController.setActive(false);
                    posteController.setActive(false);
                    break;
            }
    }

    private void updatePersonne(Personne personne) {
        Task<Boolean> task = new Task<Boolean>() {
            @Override
            protected Boolean call() throws Exception {
                if (photo != null) {
                    savePhoto(personne.getIdpersonne());
                }
                personne.setProfilPersonnes(profilController.getItems());
                personne.setPostes(posteController.getItems());
                PersonneModel personneModel = new PersonneModel();
                if (personneModel.update(personne)) {
                    return true;
                }
                return false;
            }
        };
        new Thread(task).run();
        task.setOnSucceeded(event -> {
            if (task.getValue()) {
                ServiceproUtil.notify("Modification OK");
                StageManager.loadContent("/views/civilite/civilite.fxml");
            } else {
                ServiceproUtil.notify("Erreur de modification");
            }
        });
    }

    private void savePhoto(int idpersonne) throws IOException {
        String namefile = idpersonne + "." + getExtentionImg(photo.getName());
        String fileName = photo.getName();
        String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1, photo.getName().length());
        Path src = photo.toPath();
        String chemin = getClass().getResource("/photos/").toExternalForm() + namefile;
        Path cible = Paths.get(URI.create(chemin));
        Files.copy(src, cible, StandardCopyOption.REPLACE_EXISTING);
        mapFoto.put(String.valueOf(idpersonne), getExtentionImg(photo.getName()));
        photo = null;
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
                    personneTable.getSelectionModel().selectPrevious();
                    personneTable.getItems().remove(p);
                } else {
                    ServiceproUtil.notify("Erreur de suppression");
                }
            });
        }
    }

    public void datePickerClic(ActionEvent actionEvent) {
        if (datePicker.getValue() != null)
            labelAge.setText(age(datePicker.getValue()));
    }

    public void delPhotoClic(ActionEvent actionEvent) {
        Personne pers = personneTable.getSelectionModel().getSelectedItem();
        if (stateBtnNouveau == 1 || stateBtnModifier == 1) {
            imageview.setImage(new Image(defaultImage));
            if (mapFoto.containsKey(String.valueOf(pers.getIdpersonne()))) {
                Task<Void> task = new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {

                        String name = pers.getIdpersonne() + "." + mapFoto.get(String.valueOf(pers.getIdpersonne()));
                        mapFoto.remove(String.valueOf(pers.getIdpersonne()));
                        /*
                        try {
                            String chemin = getClass().getResource("/documents/photos/").toExternalForm() + name;
                            Files.deleteIfExists(Paths.get(URI.create(chemin)));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }*/
                        return null;
                    }
                };
                new Thread(task).run();
            }
        }
    }

    public void addPhotoClic(ActionEvent actionEvent) {
        if (stateBtnNouveau == 1 || stateBtnModifier == 1) {
            FileChooser choosePic = new FileChooser();
            choosePic.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("Fichiers Image", "*.png", "*.jpg", "*.gif", "*.jpeg")
            );
            photo = choosePic.showOpenDialog(Main.stage);
            try {
                if (photo != null) {
                    FileInputStream image = new FileInputStream(photo);
                    imageview.setImage(new Image(image));
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String[] getSplitNameImage(String name) {
        String[] tab = name.split("\\.");
        return tab;
    }

    public String getExtentionImg(String name) {
        String[] tab = name.split("\\.");
        return tab[tab.length - 1];
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