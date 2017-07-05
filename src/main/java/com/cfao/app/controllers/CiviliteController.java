package com.cfao.app.controllers;

import com.cfao.app.Controller;
import com.cfao.app.Main;
import com.cfao.app.beans.*;
import com.cfao.app.model.Model;
import com.cfao.app.util.FXMLView;
import com.cfao.app.util.FormatDate;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.controlsfx.control.CheckComboBox;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by JP on 6/9/2017.
 */
public class CiviliteController implements Initializable{


    public TableView <Personne> personneTable;
    public TableColumn columnNom;
    public TableColumn columnMatricule;
    public Accordion accordion;
    public TitledPane informationPanel;
    public DatePicker datePicker;
    public TextField txtMatricule;
    public TextField txtNom;
    public TextField txtPrenom;
    public ComboBox<Societe> comboSociete;
    public ComboBox<Groupe> comboGroupe;

    public ComboBox<Pays> comboPays;
    public JFXButton btnNext;
    public TitledPane profilPanel;
    public TitledPane postePanel;
    public JFXButton btnNouveau;
    public JFXButton btnModifier;
    public ComboBox searchCombo;
    public JFXButton btnSuppr;

    public int stateBtnNouveau = 0;
    public int stateBtnModifier = 0;
    public ComboBox<Section> comboSection;
    public Label labelAge;
    public GridPane gridB;
    public CheckComboBox<Langue> comboLangue;
    public Button addPhoto;
    public Button delPhoto;
    public ImageView imageview;
    public AnchorPane paneImage;
    public File photo;
    public final String defaultImage = "/images/civilite/avatar_defaut.png";

    public TableView.TableViewSelectionModel<Personne> personneTableModel;
    public Model<Personne> modelPersonne;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        buildcontent();
    }

    private void buildcontent() {

        accordion.setExpandedPane(informationPanel);
        initComponents();
        buildtablePersonne();
        buildCombo();
    }

    private void initComponents() {

        comboLangue = new CheckComboBox(FXCollections.observableArrayList());
        comboLangue.setMaxWidth(Double.MAX_VALUE);
        comboLangue.setDisable(true);
        gridB.add(comboLangue, 2, 4 );
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

    }

    private void buildCombo() {

        Task<ObservableMap<String,ObservableList>> task = new Task<ObservableMap<String,ObservableList>>() {
            @Override
            protected ObservableMap<String,ObservableList> call() throws Exception {
                modelPersonne = new Model<>();
                ObservableMap<String,ObservableList> map =FXCollections.observableHashMap();
                map.put("societe", FXCollections.observableList(new Model<Societe>(Model.getBeanPath("Societe")).getList()));
                map.put("section", FXCollections.observableList(new Model<Section>(Model.getBeanPath("Section")).getList()));
                map.put("groupe", FXCollections.observableList(new Model<Groupe>(Model.getBeanPath("Groupe")).getList()));
                map.put("pays", FXCollections.observableList(new Model<Pays>(Model.getBeanPath("Pays")).getList()));
                map.put("langue", FXCollections.observableList(new Model<Langue>(Model.getBeanPath("Langue")).getList()));
                return map;
            }
        };
        task.run();
        task.setOnSucceeded(event -> {
            ObservableMap<String,ObservableList> map = task.getValue();
            comboPays.setItems(map.get("pays"));
            comboGroupe.setItems(map.get("groupe"));
            comboSociete.setItems(map.get("societe"));
            comboSection.setItems(map.get("section"));
            comboLangue.getItems().setAll(map.get("langue"));
        } );
    }

    private void buildtablePersonne() {

        columnMatricule.setCellValueFactory(new PropertyValueFactory<Personne, String>("matricule"));
        columnNom.setCellValueFactory(new PropertyValueFactory<Personne, String>("nom"));
        personneTableModel = personneTable.getSelectionModel();
        Task<ObservableList<Personne>> task = new Task<ObservableList<Personne>>() {
            @Override
            protected ObservableList<Personne> call() throws Exception {
                return FXCollections.observableList(new Model<Personne>(Model.getBeanPath("Personne")).getList());
            }
        };
        task.run();
        task.setOnSucceeded(event -> {
            personneTable.setItems(task.getValue());
        });
        personneTableModel.selectedItemProperty().addListener(new ChangeListener<Personne>() {
            @Override
            public void changed(ObservableValue<? extends Personne> observable, Personne oldValue, Personne newValue) {
                if(stateBtnNouveau == 1){
                    disableComponent(btnModifier, false);
                    disableComponent(btnSuppr, false);
                    disableAllComponents(true);
                    btnNouveau.setText("Nouveau / New");
                    stateBtnNouveau = 0;
                }
                renduFormulaire(newValue);
            }
        });
    }

    private void renduFormulaire(Personne person) {

        txtMatricule.setText(person.getMatricule());

        txtNom.setText(person.getNom());

        txtPrenom.setText(person.getPrenom());

        LocalDate date = person.getNaissance().toLocalDate();
        datePicker.getEditor().setText(date.format(FormatDate.dateFormat));

        labelAge.setText(age(date));

        comboSection.setValue(person.getSection());

        comboSociete.setValue(person.getSociete());

        comboGroupe.setValue(person.getGroupe());

        comboPays.setValue(person.getPays());

        updateLangue(person.getLangues());






    }

    private void updateLangue(List<Langue> listLangue) {

        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {

                comboLangue.getCheckModel().clearChecks();
                int index = 0;
                for(Langue l  : comboLangue.getItems()){

                    Iterator<Langue> iterator = listLangue.iterator();

                    while (iterator.hasNext()){
                        if(l.equals(iterator.next()))
                            comboLangue.getCheckModel().check(index);
                    }

                    index ++;
                }
                return null;
            }
        };
        task.run();
    }

    public void disableComponent (Node component, boolean b){
        component.setDisable(b);
        if(this.stateBtnNouveau == 0 && !btnNouveau.isDisable())
            if(component instanceof TextField)
                ((TextField) component).setText("");
            else if (component instanceof ComboBox)
                ((ComboBox) component).setValue(null);
            else if (component instanceof DatePicker)
                ((DatePicker) component).getEditor().setText("");
            else if (component instanceof CheckComboBox)
                ((CheckComboBox) component).getCheckModel().clearChecks();
    }

    public void disableAllComponents(boolean bool){
        disableComponent(txtMatricule, bool);
        disableComponent(txtNom, bool);
        disableComponent(txtPrenom, bool);
        disableComponent(comboGroupe, bool);
        disableComponent(comboPays, bool);
        disableComponent(comboSociete, bool);
        disableComponent(comboSection, bool);
        disableComponent(datePicker, bool);
        disableComponent(comboLangue, bool);
    }

    public String age(LocalDate date){
        int a = LocalDate.now().getYear() - date.getYear();
        return a + (a > 1 ? " ans" : " an");
    }
    /**
     * Action Click
     * @param actionEvent
     */

    public void nextAction(ActionEvent actionEvent) {
    }

    public void clickNouveau(ActionEvent actionEvent) {
        if(!btnNouveau.isDisable())
            switch (stateBtnNouveau){

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
                    personne.setNaissance(Date.valueOf(datePicker.getValue()));
                    personne.setPays(comboPays.getValue());

                    /*
                    * comboLangue.getCheckModel().getCheckedItems() renvoi une ReadOnlyObserverList
                    * */
                    personne.setLangues(FXCollections.observableArrayList(comboLangue.getCheckModel().getCheckedItems()));

                    personne.setGroupe(comboGroupe.getValue());
                    personne.setSection(comboSection.getValue());
                    personne.setSociete(comboSociete.getValue());

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

    private void savePersonne(Personne personne) {

        Task<Personne> task = new Task<Personne>() {
            @Override
            protected Personne call() throws Exception {
                modelPersonne.saveOrUpdate(personne);
                modelPersonne.close();
                return personne;
            }
        };
        task.run();
        task.setOnSucceeded(event -> {

            Platform.runLater(()->{
                personneTable.getSelectionModel().select(task.getValue());
                System.out.println("Good");
            });
        });
    }

    public void clicModifier(ActionEvent actionEvent) {

        if(!btnModifier.isDisable())
            switch (stateBtnModifier){
                case 0:

                    disableComponent(btnNouveau, true);
                    disableComponent(btnSuppr, true);
                    disableAllComponents(false);
                    btnModifier.setText("Enregistrer / Save Modify");
                    this.stateBtnModifier = 1;
                    break;

                case 1:

                    Personne personne = personneTable.getSelectionModel().getSelectedItem();
                    savePersonne(personne);

                    disableAllComponents(true);
                    btnModifier.setText("Modifier / New");
                    disableComponent(btnNouveau, false);
                    disableComponent(btnSuppr, false);
                    this.stateBtnModifier = 0;
                    break;
            }
    }

    public void clicSuppr(ActionEvent actionEvent) {
        Personne personne = personneTable.getSelectionModel().getSelectedItem();
        deletePersonne(personne);

    }

    private void deletePersonne(Personne personne) {

        personneTable.getItems().remove(personne);

        Task<Boolean> task = new Task<Boolean>() {
            @Override
            protected Boolean call() throws Exception {
                if(personneTable.getItems().size() > 0){
                    modelPersonne.delete(personne);
                    modelPersonne.close();
                }
                return true;
            }
        };
        task.run();
        task.setOnSucceeded(event -> {

            Platform.runLater(()->{
                if(personneTable.getItems().size() == 0)
                    disableAllComponents(true);

                System.out.println("delete Succeed");
            });

        });
    }

    public void datePickerClic(ActionEvent actionEvent) {
        labelAge.setText(age(datePicker.getValue()));
    }

    public void delPhotoClic(ActionEvent actionEvent) {
        imageview.setImage(new Image(defaultImage));
    }

    public void addPhotoClic(ActionEvent actionEvent) {
        FileChooser choosePic = new FileChooser();
        choosePic.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Fichiers Image", "*.png","*.jpg","*.gif", "*.jpeg")
        );
        photo = choosePic.showOpenDialog(Main.stage);
        try {
            FileInputStream image = new FileInputStream(photo);
            imageview.setImage(new Image(image));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}