package com.cfao.app.reports;

import com.cfao.app.beans.*;
import com.cfao.app.model.Model;
import com.cfao.app.util.AlertUtil;
import com.cfao.app.util.ServiceproUtil;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

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
public class ExcelFormation extends ExcelReport {
    static Logger logger = Logger.getLogger(ExcelFormation.class);
    private Formation formation;
    CellStyle timingPassed;
    CellStyle timingNotPassed;

    public ExcelFormation(Formation formation) {
        this();
        this.formation = formation;

    }

    public ExcelFormation() {
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

        CreationHelper createHelper = workbook.getCreationHelper();
        Sheet sheet = workbook.createSheet("new sheet");

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
            for (Tache tache : planification.getTaches()) {
                taches += tache.getLibelle() + "\n";
            }
            if (!taches.isEmpty())
                taches = taches.substring(0, taches.lastIndexOf("\n"));
            cell.setCellValue(taches);
            /*row..setCellValue(
                    createHelper.createRichTextString(planification.getTaches().toString()));*/
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
            for (Document doc : planification.getDocuments()) {
                documents += doc.getLibelle() + "\n";
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
        for (int colNum = 0; colNum < titres.length; colNum++) {
            sheet.autoSizeColumn(colNum);
        }

        setBorderToAllCells(0, sheet.getLastRowNum(), 0, titres.length);

        File file = new File(ResourceBundle.getBundle("Bundle").getString("document.dir") + File.separator + "workbook.xlsx");
        FileOutputStream fileOut = new FileOutputStream(file);
        workbook.write(fileOut);
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

    private CellStyle timingStyle(boolean passed) {
        CellStyle cs = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        cs.setFont(font);
        if (passed) {
            cs.setFillForegroundColor(IndexedColors.RED.getIndex());
            cs.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            font.setColor(IndexedColors.WHITE.getIndex());
        } else {
            cs.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            cs.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            font.setColor(IndexedColors.ORANGE.getIndex());
        }
        cs.setDataFormat(
                createHelper.createDataFormat().getFormat("dddd, dd mmmm, yyyy"));
        return cs;
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

    public void printPlanificationModele() throws Exception {
        List<PlanificationModele> planifications = new Model<PlanificationModele>("PlanificationModele").getList();
        if (planifications == null) {
            AlertUtil.showSimpleAlert("Information", "Aucun modèle de planification à imprimer");
            return;
        }

        Sheet sheet = workbook.createSheet("new sheet");
        short line = 0; // La ligne en cours de traitement
        /**
         * ENTETE
         */
        row = sheet.createRow(line);
        Cell cell = row.createCell(0);
        cell.setCellStyle(headerStyle);
        cell.setCellValue("NOM_DE_LA_FORMATION");
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
        for (PlanificationModele planification : planifications) {
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
            for (Tache tache : planification.getTaches()) {
                taches += tache.getLibelle() + "\n";
            }
            if (!taches.isEmpty())
                taches = taches.substring(0, taches.lastIndexOf("\n"));
            cell.setCellValue(taches);
            /*row..setCellValue(
                    createHelper.createRichTextString(planification.getTaches().toString()));*/
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
            for (Document doc : planification.getDocuments()) {
                documents += doc.getLibelle() + "\n";
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
                if (cal.getTime().getTime() < today.getTime()) {
                    cell.setCellStyle(timingPassed);
                }

            /** FAIT */
            cell = row.createCell(col++);
            cell.setCellStyle(timingNotPassed);

                cell.setCellStyle(redBackground);

            cell.setCellValue("Non");
            /** REMARQUES */
            cell = row.createCell(col++);
            cell.setCellValue(planification.getRemarque());
            cell.setCellStyle(defaultStyle);
            line++;
        }
        for (int colNum = 0; colNum < titres.length; colNum++) {
            sheet.autoSizeColumn(colNum);
        }

        setBorderToAllCells(0, sheet.getLastRowNum(), 0, titres.length);

        File file = new File(ResourceBundle.getBundle("Bundle").getString("document.dir") + File.separator + "workbook.xlsx");
        FileOutputStream fileOut = new FileOutputStream(file);
        workbook.write(fileOut);
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
