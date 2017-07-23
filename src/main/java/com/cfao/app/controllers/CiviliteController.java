package com.cfao.app.controllers;

import com.cfao.app.Main;
import com.cfao.app.beans.*;
import com.cfao.app.model.*;
import com.cfao.app.util.AlertUtil;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import javafx.util.Pair;
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
import java.sql.Date;
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

    /**
     * TableView des Profils d'une personne
     */
    public TableView<ProfilPersonne> tableProfil;
    public TableColumn columnProfil;
    public TableColumn columnNiveau;

    /**
     * TableView des Postes d'une personne
     */
    public TableView<Poste> tablePoste;
    public TableColumn columnPoste;
    public TableColumn columnSociete;
    public TableColumn columnDebut;
    public TableColumn columnFin;

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

    public Personne pers;

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
    public Button btnAjouterProfil;
    public Button btnDeleteProfil;
    public Button btnAjouterPoste;
    public Button btnDeletePoste;
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


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        buildcontent();
        searchBox.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(searchBox, Priority.ALWAYS);
        hboxSearch.getChildren().setAll(new Label("Civilités :" ), searchBox);
        ServiceproUtil.setAccordionExpanded(accordeon, profilAccordeon);
    }

    private void buildcontent() {
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
        GlyphsDude.setIcon(btnAjouterPoste, FontAwesomeIcon.PLUS_SQUARE);
        GlyphsDude.setIcon(btnAjouterProfil, FontAwesomeIcon.PLUS_SQUARE);
        GlyphsDude.setIcon(btnDeletePoste, FontAwesomeIcon.MINUS_SQUARE);
        GlyphsDude.setIcon(btnDeleteProfil, FontAwesomeIcon.MINUS_SQUARE);

        /*DESACTIVATION DES BUTTON MODIF ET SUPPR*/
        btnModifier.setDisable(true);
        btnSuppr.setDisable(true);
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

    private void buildtablePersonne() {

        columnMatricule.setCellValueFactory(new PropertyValueFactory<Personne, String>("matricule"));
        columnNom.setCellValueFactory(new PropertyValueFactory<Personne, String>("nom"));
        columnPersonneSociete.setCellValueFactory(param -> param.getValue().societe());
        columnTelephone.setCellValueFactory(param -> param.getValue().telephoneProperty());

        // COLONNE DU TABLEVIEW PROFIL D'UNE PERSONNE

        columnProfil.setCellValueFactory(new PropertyValueFactory<ProfilPersonne, String>("profil"));
        columnNiveau.setCellValueFactory(new PropertyValueFactory<ProfilPersonne, String>("niveau"));

        columnPoste.setCellValueFactory(new PropertyValueFactory<Poste, String>("titre"));
        columnSociete.setCellValueFactory(new PropertyValueFactory<Poste, String>("societe"));
        columnDebut.setCellValueFactory(new PropertyValueFactory<Poste, Date>("dateDebut"));
        columnFin.setCellValueFactory(new PropertyValueFactory<Poste, Date>("datefin"));

        /*
        Task<ObservableList<Personne>> task = new Task<ObservableList<Personne>>() {
            @Override
            protected ObservableList<Personne> call() throws Exception {
                return FXCollections.observableList(modelPersonne.getList());
            }
        };
        task.run();
        task.setOnSucceeded((WorkerStateEvent event) -> {
            FilteredList<Personne> filteredList = new FilteredList<Personne>(task.getValue(), p -> true);
            searchBox.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                filteredList.setPredicate((Personne personne) -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    // Comparer les champs dans la classe Personne
                    String valueCompare = newValue.toLowerCase();
                    if (personne.getNom().toLowerCase().contains(valueCompare)) {
                        return true;
                    }
                    if (personne.getPrenom() != null && personne.getPrenom().toLowerCase().contains(valueCompare)) {
                        return true;
                    }
                    if(personne.getPays() != null && personne.getPays().getNameFr().toLowerCase().contains(valueCompare)){
                        return true;
                    }
                    return false;
                });
            });
            SortedList<Personne> sortedList = new SortedList<Personne>(filteredList);
            sortedList.comparatorProperty().bind(personneTable.comparatorProperty());

            personneTable.setItems(task.getValue());
        });*/
        personneTable.setItems(map.get("personne"));
        personneTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Personne>() {
            @Override
            public void changed(ObservableValue<? extends Personne> observable, Personne oldValue, Personne newValue) {
                if (stateBtnNouveau == 1) {
                    disableComponent(btnModifier, false);
                    disableComponent(btnSuppr, false);
                    disableAllComponents(true);
                    btnNouveau.setText("Nouveau / New");
                    stateBtnNouveau = 0;
                }

                if (stateBtnModifier != 1 && newValue != null)
                    renduFormulaire(newValue);

                if(newValue == null) {
                    btnModifier.setDisable(true);
                    btnSuppr.setDisable(true);
                    pers = null;
                }
            }
        });
    }

    private void renduFormulaire(Personne person) {
        /*
        Affectation de la personne utilisee pour la modification et supression */
        pers = person;
        /*
        Activation des buttons modifier et supprimer*/
        btnModifier.setDisable(false);
        btnSuppr.setDisable(false);

        /*
        Mise à jour du formalaire fonction des choix sur la table*/
        txtMatricule.setText(person.getMatricule());
        txtNom.setText(person.getNom());
        txtPrenom.setText(person.getPrenom());
        txtMemo.setText(person.getMemo());
        txtTelephone.setText(person.getTelephone());
        txtEmail.setText(person.getEmail());

        LocalDate date1 = person.getNaissance().toLocalDate();
        datePicker.getEditor().setText(date1.format(FormatDate.currentForme));
        datePicker.setValue(date1);
        labelAge.setText(age(date1));


        LocalDate date2 = person.getFincontrat().toLocalDate();
        dateFincontrat.getEditor().setText(date2.format(FormatDate.currentForme));
        dateFincontrat.setValue(date2);

        comboSection.setValue(person.getSection());
        comboSociete.setValue(person.getSociete());
        comboPays.setValue(person.getPays());
        comboGroupe.setValue(person.getGroupe());
        comboContrat.setValue(person.getContrat());
        comboAmbition.setValue(person.getAmbition());
        comboLangue.setValue(person.getLangue());
        comboPotentiel.setValue(person.getPotentiel());


        /**
         * MISE A JOUR DE LA TABLE DE PROFIL D'UNE PERSONNE
         */



        /*
        Necessaire à l'update du component checkcombobox
        des Langues parlées par une personne*/
        updateLangue(person.getLangues());

        tableProfil.setItems(FXCollections.observableArrayList(person.getProfilPersonne()));
        tablePoste.setItems(FXCollections.observableArrayList(person.getPostePersonne()));

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

    public void disableComponent(Node component, boolean b) {
        if (component instanceof Button) {
            ((Button) component).setDisable(b);
        } else if (component instanceof TextField) {
            ((TextField) component).setEditable(!b);
        } else if (component instanceof ComboBox) {
            ((ComboBox) component).setDisable(b);
        }else if (component instanceof CheckComboBox) {
            ((CheckComboBox) component).setDisable(b);
        } else if (component instanceof DatePicker) {
            ((DatePicker) component).setEditable(!b);
        } else if (component instanceof TextArea) {
            ((TextArea) component).setEditable(!b);
        }

        if (this.stateBtnNouveau == 0 && !btnNouveau.isDisable()) {
            if (component instanceof TextField) {
                ((TextField) component).setText("");
            } else if (component instanceof ComboBox) {
                ((ComboBox) component).setValue(null);
            } else if (component instanceof DatePicker) {
                ((DatePicker) component).getEditor().setText("");
                ((DatePicker) component).setValue(null);
            } else if (component instanceof CheckComboBox) {
                ((CheckComboBox) component).getCheckModel().clearChecks();
            } else if (component instanceof TableView) {
                ((TableView) component).setItems(FXCollections.observableArrayList());
            } else if (component instanceof TextArea) {
                ((TextArea) component).setText("");
            }
        }
    }



    public void disableAllComponents(boolean bool) {
        disableComponent(txtMatricule, bool);
        disableComponent(txtNom, bool);
        disableComponent(txtPrenom, bool);
        disableComponent(txtMemo, bool);
        disableComponent(comboGroupe, bool);
        disableComponent(comboPays, bool);
        disableComponent(comboSociete, bool);
        disableComponent(comboSection, bool);
        disableComponent(comboPotentiel, bool);
        disableComponent(comboAmbition, bool);
        disableComponent(comboLangue, bool);
        disableComponent(comboContrat, bool);
        disableComponent(datePicker, bool);
        disableComponent(dateFincontrat, bool);
        disableComponent(comboLanguesParlees, bool);
        disableComponent(tablePoste, bool);
        disableComponent(tableProfil, bool );
    }

    public String age(LocalDate date) {
        int a = LocalDate.now().getYear() - date.getYear();
        return a + (a > 1 ? " ans" : " an");
    }

    public void clickNouveau(ActionEvent actionEvent) {
        if (!btnNouveau.isDisable())
            switch (stateBtnNouveau) {
                case 0:
                    disableComponent(btnModifier, true);
                    disableComponent(btnSuppr, true);
                    disableAllComponents(false);
                    btnNouveau.setText("Enregistrer / Save");
                    this.stateBtnNouveau = 1;
                    break;
                case 1:
                    /**
                     * Creation d'une nouvel personne
                     */
                    Personne personne = new Personne();
                    personne.setMatricule(txtMatricule.getText());
                    personne.setNom(txtNom.getText());
                    personne.setPrenom(txtPrenom.getText());
                    personne.setMemo(txtMemo.getText());
                    personne.setTelephone(txtTelephone.getText());
                    personne.setEmail(txtEmail.getText());
                    if(datePicker.getValue() != null) {
                        personne.setNaissance(Date.valueOf(datePicker.getValue()));
                    }
                    if(dateFincontrat.getValue() != null) {
                        personne.setFincontrat(Date.valueOf(dateFincontrat.getValue()));
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
                    savePersonne(personne);
                    personneTable.getItems().add(personne);

                    disableComponent(btnModifier, false);
                    disableComponent(btnSuppr, false);
                    disableAllComponents(true);
                    btnNouveau.setText("Nouveau / New");
                    this.stateBtnNouveau = 0;
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
                if(tableProfil.getItems().size() > 0){
                    for (ProfilPersonne p : tableProfil.getItems())
                        p.setPersonne(personne);
                }
                personne.setProfilPersonne(tableProfil.getItems());

                if(tablePoste.getItems().size() > 0){
                    for (Poste p : tablePoste.getItems())
                        p.setPersonne(personne);
                }
                personne.setPostePersonne(tablePoste.getItems());

                new Model<Personne>().save(personne);

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

        if (!btnModifier.isDisable() && pers != null)
            switch (stateBtnModifier) {
                case 0:
                    System.out.println("Avant bind - " + pers.toString());
                    bindPersonne(pers, true);
                    disableComponent(btnNouveau, true);
                    disableComponent(btnSuppr, true);
                    disableAllComponents(false);
                    btnModifier.setText("Enregistrer / Save Modify");
                    this.stateBtnModifier = 1;
                    break;

                case 1:

                    bindPersonne(pers, false);
                    updatePersonne(pers);
                    disableAllComponents(true);
                    btnModifier.setText("Modifier / New");
                    disableComponent(btnNouveau, false);
                    disableComponent(btnSuppr, false);
                    this.stateBtnModifier = 0;
                    break;
            }
    }

    private void updatePersonne(Personne personne) {
        Task<Personne> task = new Task<Personne>() {
            @Override
            protected Personne call() throws Exception {
                personne.setProfilPersonne(FXCollections.observableList(tableProfil.getItems()));
                personne.setPostePersonne(FXCollections.observableList(tablePoste.getItems()));
                new Model<Personne>().update(personne);
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

    private void bindPersonne(Personne personne, boolean bind) {
        if (bind) {
            personne.matriculeProperty().bind(txtMatricule.textProperty());
            personne.nomProperty().bind(txtNom.textProperty());
            personne.prenomProperty().bind(txtPrenom.textProperty());
            personne.naissance().bind(datePicker.valueProperty());
            personne.pays().bind(comboPays.valueProperty());
            personne.societe().bind(comboSociete.valueProperty());
            personne.section().bind(comboSection.valueProperty());
            personne.groupe().bind(comboGroupe.valueProperty());
            personne.langues().bindContent(comboLanguesParlees.getCheckModel().getCheckedItems());
            personne.fincontratProperty().bind(dateFincontrat.valueProperty());
            personne.memoProperty().bind(txtMemo.textProperty());
            personne.telephoneProperty().bind(txtTelephone.textProperty());
            personne.emailProperty().bind(txtEmail.textProperty());
            personne.potentielProperty().bind(comboPotentiel.valueProperty());
        } else {
            personne.matriculeProperty().unbind();
            personne.nomProperty().unbind();
            personne.prenomProperty().unbind();
            personne.naissance().unbind();
            personne.pays().unbind();
            personne.societe().unbind();
            personne.section().unbind();
            personne.groupe().unbind();
            personne.langues().unbindContent(comboLanguesParlees.getCheckModel().getCheckedItems());
            personne.fincontratProperty().unbind();
            personne.memoProperty().unbind();
            personne.telephoneProperty().unbind();
            personne.emailProperty().unbind();
            personne.potentielProperty().unbind();
        }
    }

    public void clicSuppr(ActionEvent actionEvent) {

        if(!btnSuppr.isDisable() && personneTable.getSelectionModel().getSelectedItem()!= null){
            Personne p = personneTable.getSelectionModel().getSelectedItem();
            personneTable.getItems().removeAll(p);
            deletePersonne(p);
        }

    }

    private void deletePersonne(Personne personne) {
        personneTable.getItems().removeAll(personne);
        Task<Boolean> task = new Task<Boolean>() {
            @Override
            protected Boolean call() throws Exception {
                if (personneTable.getItems().size() > 0) {
                    System.out.println("dedans");
                    new Model<Personne>().delete(personne);
                }
                return true;
            }
        };
        task.run();
        task.setOnSucceeded(event -> {

            Platform.runLater(() -> {
                if (personneTable.getItems().size() == 0)
                    disableAllComponents(true);

                ServiceproUtil.notify("Suppression OK");
            });

        });
    }


    public void datePickerClic(ActionEvent actionEvent) {
        if(datePicker.getValue()!= null)
            labelAge.setText(age(datePicker.getValue()));
    }

    public void delPhotoClic(ActionEvent actionEvent) {
        if(stateBtnNouveau == 1 || stateBtnModifier == 1) {
            imageview.setImage(new Image(defaultImage));
        }
    }

    public void addPhotoClic(ActionEvent actionEvent) {
        if(true){
            //if(stateBtnNouveau == 1 || stateBtnModifier == 1) {
            FileChooser choosePic = new FileChooser();
            choosePic.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("Fichiers Image", "*.png", "*.jpg", "*.gif", "*.jpeg")
            );
            photo = choosePic.showOpenDialog(Main.stage);
            try {
                FileInputStream image = new FileInputStream(photo);
                imageview.setImage(new Image(image));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            Path source = photo.toPath();

            String ds = File.separator;
            try {
                URI uri = new URI(getClass().getResource(ResourceBundle.getBundle("Bundle").getString("photo.dir") + ds + photo.getName()).toExternalForm());
                Path to = Paths.get(uri);
                Files.copy(source, to, StandardCopyOption.COPY_ATTRIBUTES, StandardCopyOption.REPLACE_EXISTING);
            }catch (Exception ex){
                ex.printStackTrace();
                AlertUtil.showErrorMessage(ex);
            }
        }

    }

    public void clicAnnuler(ActionEvent actionEvent) {
        btnNouveau.setText("Nouveau / New");
        btnModifier.setText("Modifier / Edit");
    }

    public void addPoste(ActionEvent actionEvent) {
        if(stateBtnNouveau == 1 || stateBtnModifier == 1) {
            Pair<Node, Initializable> pair = templateDialog("DialogPoste.fxml");
            Node root = pair.getKey();
            DialogPosteController controller = (DialogPosteController) pair.getValue();

            Dialog<Poste> dialogPoste = new Dialog<Poste>();
            dialogPoste.setHeaderText("Ajouter un Poste :");
            dialogPoste.getDialogPane().setContent(root);

            ButtonType btnOkType = new ButtonType("Ajouter / Add", ButtonBar.ButtonData.OK_DONE);
            dialogPoste.getDialogPane().getButtonTypes().addAll(btnOkType, ButtonType.CANCEL);

            Button btnOk = (Button) dialogPoste.getDialogPane().lookupButton(btnOkType);

            btnOk.addEventFilter(ActionEvent.ACTION, event -> {
                event.consume();
                String valTxtPoste = controller.txtPoste.getText();
                Societe valComboSociete = controller.comboSociete.getSelectionModel().getSelectedItem();
                LocalDate valDateFrom = controller.dateFrom.getValue();
                LocalDate valDateTo = controller.dateTo.getValue();

                if (!valTxtPoste.isEmpty() && valComboSociete != null) {
                    Poste poste = new Poste();
                    poste.setTitre(valTxtPoste);
                    poste.setSociete(valComboSociete);
                    poste.setDateDebut(Date.valueOf(valDateFrom));
                    poste.setDatefin(Date.valueOf(valDateTo));

                    if(stateBtnNouveau == 0) {
                        poste.setPersonne(pers);
                        new Model<Poste>().save(poste);
                    }
                    tablePoste.getItems().add(poste);
                    dialogPoste.close();
                }
            });
            dialogPoste.showAndWait();
        }
    }

    public void deletePoste(ActionEvent actionEvent) {
        if(stateBtnNouveau == 1 || stateBtnModifier == 1) {
            if(tablePoste.getItems().size() > 0 && tablePoste.getSelectionModel().getSelectedIndex()!= -1) {
                Poste p = tablePoste.getSelectionModel().getSelectedItem();
                deletePosteBd(p);
                tablePoste.getItems().remove(p);
            }
        }
    }

    private void deletePosteBd(Poste p) {
        Task<Boolean> task = new Task<Boolean>() {
            @Override
            protected Boolean call() throws Exception {
                Model<Poste> model = new Model<Poste>("Poste");
                model.delete(p);
                return true;
            }
        };
        new Thread(task).run();
    }



    public void addProfil(ActionEvent actionEvent) {

        if(stateBtnNouveau == 1 || stateBtnModifier == 1) {
            Pair<Node, Initializable> pair = templateDialog("DialogProfil.fxml");
            Node root = pair.getKey();
            DialogProfilController controller = (DialogProfilController) pair.getValue();

            Dialog<ProfilPersonne> dialogProfil = new Dialog<ProfilPersonne>();
            dialogProfil.setHeaderText("Ajouter un Profil");
            dialogProfil.getDialogPane().setContent(root);

            ButtonType btnOkType = new ButtonType("Ajouter / Add", ButtonBar.ButtonData.OK_DONE);
            dialogProfil.getDialogPane().getButtonTypes().addAll(btnOkType, ButtonType.CANCEL);

            Button btnOk = (Button) dialogProfil.getDialogPane().lookupButton(btnOkType);

            btnOk.addEventFilter(ActionEvent.ACTION, event -> {
                event.consume();
                Profil valueComboProfil = controller.comboProfil.getSelectionModel().getSelectedItem();
                Niveau valueComboLevel = controller.comboLevel.getSelectionModel().getSelectedItem();

                if (valueComboProfil != null && valueComboLevel != null) {

                    ProfilPersonne profilPersonne = new ProfilPersonne();
                    profilPersonne.setNiveau(valueComboLevel);
                    profilPersonne.setProfil(valueComboProfil);

                    if (stateBtnNouveau == 0) {
                        profilPersonne.setPersonne(pers);
                        Model<ProfilPersonne> model = new Model<>("ProfilPersonne");
                        model.save(profilPersonne);
                    }

                    tableProfil.getItems().add(profilPersonne);
                    dialogProfil.close();
                }
            });
            dialogProfil.showAndWait();
        }

    }

    public void deleteProfil(ActionEvent actionEvent) {
        if(stateBtnNouveau == 1 || stateBtnModifier == 1) {
            if(tableProfil.getItems().size() > 0 && tableProfil.getSelectionModel().getSelectedIndex()!= -1) {
                ProfilPersonne p = tableProfil.getSelectionModel().getSelectedItem();
                deleteProfilBd(p);
                tableProfil.getItems().remove(p);
            }
        }
    }

    private void deleteProfilBd(ProfilPersonne p) {
        Task<Boolean> task = new Task<Boolean>() {
            @Override
            protected Boolean call() throws Exception {
                Model<ProfilPersonne> model = new Model<ProfilPersonne>("ProfilPersonne");
                model.delete(p);
                return true;
            }
        };
        new Thread(task).run();
    }

    public Pair<Node, Initializable> templateDialog(String fxml){

        Node root = null;
        Initializable controller = null;
        String url = "/views/civilite/Dialog/" + fxml;

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(url));
            root = loader.load();
            controller = loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new Pair(root, controller);
    }

    public void clicPrev(ActionEvent actionEvent) {
        if(personneTable.getSelectionModel().getSelectedIndex() > 0)
            personneTable.getSelectionModel().selectPrevious();
    }

    public void clicNext(ActionEvent actionEvent) {
        if(personneTable.getSelectionModel().getSelectedIndex() < personneTable.getItems().size())
            personneTable.getSelectionModel().selectNext();
    }
}