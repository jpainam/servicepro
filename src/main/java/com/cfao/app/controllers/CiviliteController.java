package com.cfao.app.controllers;

import com.cfao.app.Main;
import com.cfao.app.beans.*;
import com.cfao.app.model.Model;
import com.cfao.app.util.FormatDate;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.controlsfx.control.CheckComboBox;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * Created by JP on 6/9/2017.
 */
public class CiviliteController implements Initializable{


    public TableView personneTable;
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
    public CheckComboBox comboLangue;
    public Button addPhoto;
    public Button delPhoto;

    private TableView.TableViewSelectionModel<Personne> personneTableModel;


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
        comboLangue = new CheckComboBox();
        comboLangue.setMaxWidth(Double.MAX_VALUE);
        gridB.add(comboLangue, 2, 4 );

        GlyphsDude.setIcon(addPhoto, FontAwesomeIcon.PICTURE_ALT);
        GlyphsDude.setIcon(delPhoto, FontAwesomeIcon.CLOSE);


    }

    private void buildCombo() {

        Task<ObservableMap<String,ObservableList>> task = new Task<ObservableMap<String,ObservableList>>() {
            @Override
            protected ObservableMap<String,ObservableList> call() throws Exception {
                ObservableMap<String,ObservableList> map =FXCollections.observableHashMap();
                map.put("societe", FXCollections.observableList(new Model<Societe>(Model.getBeansClass("Societe")).getList()));
                map.put("section", FXCollections.observableList(new Model<Section>(Model.getBeansClass("Section")).getList()));
                map.put("groupe", FXCollections.observableList(new Model<Groupe>(Model.getBeansClass("Groupe")).getList()));
                //map.put("pays", FXCollections.observableList(new Model<Pays>(Model.getBeansClass("Pays")).getList()));
                return map;
            }
        };
        task.run();
        task.setOnSucceeded(event -> {
            ObservableMap<String,ObservableList> map = task.getValue();
            //comboPays.setItems(map.get("pays"));
            comboGroupe.setItems(map.get("groupe"));
            comboSociete.setItems(map.get("societe"));
            comboSection.setItems(map.get("section"));
        } );
    }

    private void buildtablePersonne() {

        columnMatricule.setCellValueFactory(new PropertyValueFactory<Personne, String>("matricule"));
        columnNom.setCellValueFactory(new PropertyValueFactory<Personne, String>("nom"));
        personneTableModel = personneTable.getSelectionModel();
        Task<ObservableList<Personne>> task = new Task<ObservableList<Personne>>() {
            @Override
            protected ObservableList<Personne> call() throws Exception {
                return FXCollections.observableList(new Model<Personne>(Model.getBeansClass("Personne")).getList());
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
        /*LocalDate date = person.getDate().toLocalDate();
        datePicker.getEditor().setText(date.format(FormatDate.dateFormat));
        String age = person.getAge() + ((person.getAge() > 1) ? " ans" : " an");*/
        labelAge.setText("Age");
        comboSection.setValue(person.getSection());
        comboSociete.setValue(person.getSociete());
        comboGroupe.setValue(person.getGroupe());
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
    }


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

                    disableComponent(btnModifier, false);
                    disableComponent(btnSuppr, false);
                    disableAllComponents(true);
                    btnNouveau.setText("Nouveau / New");
                    this.stateBtnNouveau = 0;
                    break;
            }
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

                    disableAllComponents(true);
                    btnModifier.setText("Modifier / New");
                    disableComponent(btnNouveau, false);
                    disableComponent(btnSuppr, false);
                    this.stateBtnModifier = 0;

                    break;
            }
    }

    public void clicSuppr(ActionEvent actionEvent) {
    }

    public void datePickerClic(ActionEvent actionEvent) {
        int a = LocalDate.now().getYear() - datePicker.getValue().getYear();
        String age = a + (a > 1 ? " ans" : " an");
        labelAge.setText(age);
    }

    public void delPhotoClic(ActionEvent actionEvent) {
    }

    public void addPhotoClic(ActionEvent actionEvent) {
        FileChooser choosePic = new FileChooser();
        choosePic.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Fichiers Image", "*.png","*.jpg","*.gif", "*.jpeg")
        );
        File selectFile = choosePic.showOpenDialog(Main.stage);

    }
}