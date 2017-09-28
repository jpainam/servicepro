package com.cfao.app.reports;

import com.cfao.app.beans.*;
import com.cfao.app.model.Model;
import com.cfao.app.util.AlertUtil;
import com.cfao.app.util.ServiceproUtil;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


/**
 * Created by JP on 8/30/2017.
 */
public class FormationExcel extends ExcelRapport {
    private static final String FEUILLE_PRESENCE_TEMPLATE = "feuille_presence.xlsx";
    static Logger logger = Logger.getLogger(FormationExcel.class);
    private Formation formation;


    public FormationExcel(Formation formation) {
        this();
        this.formation = formation;

    }

    public FormationExcel() {
        super();
        timingPassed = timingStyle(true);
        timingNotPassed = timingStyle(false);
    }

    public void setFormation(Formation formation) {
        this.formation = formation;
    }

    public void printPlanification() throws Exception {
        if (formation == null) {
            AlertUtil.showWarningMessage("Attention", "Aucune donnée à imprimer");
            return;
        }
        List<Planification> planifications = formation.getPlanifications();
        if (planifications == null) {
            AlertUtil.showSimpleAlert("Information", "Aucune planification à imprimer");
            return;
        }
        short line = 0; // La ligne en cours de traitement

        /**
         * ENTETE
         */
        row = sheet.createRow(line);
        Cell cell = row.createCell(0);
        cell.setCellStyle(headerStyle);
        cell.setCellValue(formation.getTitre());
        sheet.addMergedRegion(new CellRangeAddress(line, line, 0, 4));
        cell = row.createCell(6);
        cell.setCellStyle(dateStyle);
        cell.setCellValue(new Date());
        sheet.addMergedRegion(new CellRangeAddress(line, line, 6, 9));

        line++;
        row = sheet.createRow(line);
        String[] titres = {"N°", "Sujet", "Tache", "Responsable", "Validation", "Document", "Commentaire", "Timing", "", "Fait", "Remarque"};
        for (int i = 0; i < titres.length; i++) {
            cell = row.createCell((short) i);
            cell.setCellValue(titres[i]);
            cell.setCellStyle(headerStyle);
        }
        sheet.addMergedRegion(new CellRangeAddress(line, line, 7, 8));

        line++;
        int col;
        int numero = 1;
        Calendar cal = Calendar.getInstance();
        Date debut = formation.getDatedebut();
        cal.setTime(debut);
        Date today = new Date();
        for (Planification planification : planifications) {
            col = 0;
            row = sheet.createRow(line);
            if (planification.getTaches().size() > 0) {
                row.setHeightInPoints(((planification.getTaches().size() + 1) * sheet.getDefaultRowHeightInPoints()));
            }
            /** Numero */
            cell = row.createCell(col++);
            cell.setCellValue(numero++);
            cell.setCellStyle(defaultStyle);
            /** Sujet */
            cell = row.createCell(col++);
            cell.setCellValue(planification.getSujet().getLibelle());
            cell.setCellStyle(defaultStyle);

            /** Tache */
            cell = row.createCell(col++);
            cell.setCellStyle(wrapStyle);
            String taches = "";
            if (planification.getTaches() != null) {
                for (Tache tache : planification.getTaches()) {
                    taches += tache.getLibelle() + "\n";
                }
            }
            if (!taches.isEmpty())
                taches = taches.substring(0, taches.lastIndexOf("\n"));
            cell.setCellValue(taches);
            /*row..setCellValue(
                    helper.createRichTextString(planification.getTaches().toString()));*/
            /** RESPONSABLE et VALIDATION */
            cell = row.createCell(col++);
            cell.setCellValue(planification.getResponsable().getLibelle());
            cell.setCellStyle(defaultStyle);
            cell = row.createCell(col++);
            cell.setCellValue(planification.getValidation().getLibelle());
            cell.setCellStyle(defaultStyle);

            /** DOCUMENTS */
            cell = row.createCell(col++);
            cell.setCellStyle(wrapStyle);
            String documents = "";
            if (planification.getDocuments() != null) {
                for (Document doc : planification.getDocuments()) {
                    documents += doc.getLibelle() + "\n";
                }
            }
            if (!documents.isEmpty())
                documents.substring(0, documents.lastIndexOf("\n"));
            cell.setCellValue(documents);

            /** COMMENTAIRE */
            cell = row.createCell(col++);
            cell.setCellValue(planification.getCommentaire());
            cell.setCellStyle(defaultStyle);
            /** TIMING */
            cell = row.createCell(col++);
            cell.setCellStyle(defaultStyle);
            cell.setCellValue(Math.abs(planification.getTiming()));
            cal.add(Calendar.DATE, planification.getTiming());

            cell = row.createCell(col++);
            cell.setCellStyle(dateStyle);
            cell.setCellValue(cal.getTime());
            cell.setCellStyle(timingNotPassed);
            if (!planification.isFait()) {
                if (cal.getTime().getTime() < today.getTime()) {
                    cell.setCellStyle(timingPassed);
                }
            }
            /** FAIT */
            cell = row.createCell(col++);
            cell.setCellStyle(timingNotPassed);
            if (!planification.isFait()) {
                cell.setCellStyle(redBackground);
            }
            cell.setCellValue(planification.isFait() ? "Oui" : "Non");
            /** REMARQUES */
            cell = row.createCell(col++);
            cell.setCellValue(planification.getRemarque());
            cell.setCellStyle(defaultStyle);
            line++;
        }
        terminer();
    }

    public boolean importerPlanification(File file, HashMap<String, Integer> colParam) {
        List<Sujet> sujets = new Model<Sujet>("Sujet").getList();
        List<Tache> taches = new Model<Tache>("Tache").getList();
        List<UserProfil> userprofils = new Model<UserProfil>("UserProfil").getList();
        List<Document> documents = new Model<Document>("Document").getList();
        try {
            FileInputStream fileIn = new FileInputStream(file);
            Row row;
            Sheet sheet = workbook.getSheetAt(0);
            ObservableList<Planification> planifications = FXCollections.observableArrayList();
            Planification planification;
            for (int i = 0; i < sheet.getLastRowNum(); i++) {
                row = sheet.getRow(i);
                /** Si la premiere cellule est vide, passer a la ligne suivante */
                if (row.getCell(0).getStringCellValue().isEmpty())
                    continue;

                planification = new Planification();
                String sujet = row.getCell(colParam.get("SUJET")).getStringCellValue().trim();
                for (Sujet ss : sujets) {
                    if (ss.getLibelle().toLowerCase().equals(sujet.toLowerCase()))
                        break;
                }
                Date date = row.getCell(0).getDateCellValue();
            }
            formation.setPlanifications(planifications);
            return true;
        } catch (Exception ex) {
            Platform.runLater(() -> {
                logger.error(ex);
                AlertUtil.showErrorMessage(ex);
            });
        }
        return false;
    }

    public void printInscription(Formation formation) throws Exception {
        short line = 0;
        /**
         * ENTETE
         */
        row = sheet.createRow(line++);
        XSSFCell cell = row.createCell(0);
        cell.setCellValue(formation.getTitre());
        cell.setCellStyle(headerStyle);
        sheet.addMergedRegion(new CellRangeAddress(line - 1, line - 1, 0, 5));
        cell = row.createCell(6);
        cell.setCellStyle(shortDateStyle);
        cell.setCellValue(new Date());
        sheet.addMergedRegion(new CellRangeAddress(line - 1, line - 1, 6, 7));
        row = sheet.createRow(line++);
        createCell(1, "Start : ");
        createCell(2, formation.getDatedebut(), shortDateStyle);
        createCell(6, "End : ");
        createCell(7, formation.getDatefin(), shortDateStyle);

        line++;
        row = sheet.createRow(line);
        String[] titres = {"N°", "Pays", "Filiale", "Prénom", "Nom", "Téléphone", "Fonction", "Section", "Groupe",};
        for (int i = 0; i < titres.length; i++) {
            cell = row.createCell((short) i);
            cell.setCellValue(titres[i]);
            cell.setCellStyle(headerStyle);
            //table.addColumn();
        }

        line++;
        int col;
        int i = 1;
        List<FormationPersonne> formationPersonnes = formation.getFormationPersonnes();
        for (FormationPersonne fp : formationPersonnes) {
            Personne p = fp.getPersonne();

            col = 0;
            row = sheet.createRow(line);
            row.createCell(col++).setCellValue(i);
            /** Pays */
            cell = row.createCell(col++);
            cell.setCellStyle(defaultStyle);
            if(p.getPays() != null) {
                cell.setCellValue(p.getPays().getNamefr());
            }
            /** Filiale */
            cell = row.createCell(col++);
            if(p.getSociete() != null) {
                cell.setCellValue(p.getSociete().getNom());
            }
            cell.setCellStyle(defaultStyle);
            /** Prenom */
            cell = row.createCell(col++);
            cell.setCellValue(p.getPrenom());
            cell.setCellStyle(defaultStyle);
            /** Nom */
            cell = row.createCell(col++);
            cell.setCellValue(p.getNom());
            cell.setCellStyle(defaultStyle);
            /** Telephone */
            createCell(col++, p.getTelephone() != null ? p.getTelephone() : "");
            /** Fonction */
            createCell(col++, p.getFonction());
            /** Section */
            createCell(col++, p.getSection() != null ? p.getSection().getLibelle() : "");
            /** Groupe */
            createCell(col++, p.getGroupe() != null ? p.getGroupe().getLibelle() : "");

            line++;
            i++;
        }

        sheet.createFreezePane(0, 4);
        terminer();
    }

    public void printFeuillePresence(Formation formation) throws Exception {
        File file = new File(ResourceBundle.getBundle("Bundle").
                getString("report.dir") + File.separator + FEUILLE_PRESENCE_TEMPLATE);

        XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(file));
        XSSFSheet sheet = wb.getSheetAt(0);
        Cell cell;
        short line = 2;
        //Update the value of cell
        cell = sheet.getRow(line).getCell(0);
        cell.setCellValue("Formation : " + formation.getTitre());
        sheet.addMergedRegion(new CellRangeAddress(line, line, 0, 4));

        cell = sheet.getRow(line).getCell(6);
        if(formation.getSocieteFormatrice() != null) {
            cell.setCellValue(formation.getSocieteFormatrice().getLibelle());
        }
        line = 7;
        List<FormationPersonne> formationPersonnes = formation.getFormationPersonnes();
        for (FormationPersonne fp : formationPersonnes) {
            Personne p = fp.getPersonne();
            row = sheet.getRow(line);
            if(row == null){
                row = sheet.createRow(line);
            }
            cell = row.getCell(0);
            if(cell == null){
                cell = row.createCell(0);
            }
            cell.setCellValue(p.getNom() + " " + p.getPrenom());
            line++;
        }

        /*sheet.setColumnWidth(0, 8000);
        for (int i = 1; i < 10; i++) {
            sheet.setColumnWidth(i, 3000);
        }*/
        sheet.autoSizeColumn(0);
        //save workbook
        FileOutputStream fileOut = new FileOutputStream(new File(ResourceBundle.getBundle("Bundle").getString("document.dir") + File.separator + "workbook.xlsx"));
        wb.write(fileOut);
        fileOut.close();
        Path path = Paths.get(ResourceBundle.getBundle("Bundle").getString("document.dir")).toAbsolutePath();
        if (Files.notExists(path)) {
            Files.createDirectories(path);
        }
        String document = path.toString() + File.separator + "workbook.xlsx";
        File f = new File((document));
        if (f.exists() && !f.isDirectory()) {
            ServiceproUtil.openDocument(f);
        }
    }
}
