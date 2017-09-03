package com.cfao.app.reports;

import com.cfao.app.beans.*;
import com.cfao.app.model.Model;
import com.cfao.app.util.AlertUtil;
import com.cfao.app.util.ServiceproUtil;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.log4j.Logger;
import org.apache.poi.ss.SpreadsheetVersion;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.AreaReference;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.*;

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
            if(planification.getTaches() != null) {
                for (Tache tache : planification.getTaches()) {
                    taches += tache.getLibelle() + "\n";
                }
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
            if(planification.getDocuments() != null) {
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



    public void pivotTable() throws Exception{
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet();

        //Create some data to build the pivot table on
        setCellData(sheet);

        AreaReference source = new AreaReference("A1:D4", SpreadsheetVersion.EXCEL2007);
        CellReference position = new CellReference("H5");
        // Create a pivot table on this sheet, with H5 as the top-left cell..
        // The pivot table's data source is on the same sheet in A1:D4
        XSSFPivotTable pivotTable = sheet.createPivotTable(source, position);
        //Configure the pivot table
        //Use first column as row label
        pivotTable.addRowLabel(0);
        //Sum up the second column
        pivotTable.addColumnLabel(DataConsolidateFunction.SUM, 1);
        //Set the third column as filter
        pivotTable.addColumnLabel(DataConsolidateFunction.AVERAGE, 2);
        //Add filter on forth column
        pivotTable.addReportFilter(3);

        FileOutputStream fileOut = new FileOutputStream("ooxml-pivottable.xlsx");
        wb.write(fileOut);
        fileOut.close();
        wb.close();
    }
    public static void setCellData(XSSFSheet sheet){
        Row row1 = sheet.createRow(0);
        // Create a cell and put a value in it.
        Cell cell11 = row1.createCell(0);
        cell11.setCellValue("Names");
        Cell cell12 = row1.createCell(1);
        cell12.setCellValue("#");
        Cell cell13 = row1.createCell(2);
        cell13.setCellValue("%");
        Cell cell14 = row1.createCell(3);
        cell14.setCellValue("Human");

        Row row2 = sheet.createRow(1);
        Cell cell21 = row2.createCell(0);
        cell21.setCellValue("Jane");
        Cell cell22 = row2.createCell(1);
        cell22.setCellValue(10);
        Cell cell23 = row2.createCell(2);
        cell23.setCellValue(100);
        Cell cell24 = row2.createCell(3);
        cell24.setCellValue("Yes");

        Row row3 = sheet.createRow(2);
        Cell cell31 = row3.createCell(0);
        cell31.setCellValue("Tarzan");
        Cell cell32 = row3.createCell(1);
        cell32.setCellValue(5);
        Cell cell33 = row3.createCell(2);
        cell33.setCellValue(90);
        Cell cell34 = row3.createCell(3);
        cell34.setCellValue("Yes");

        Row row4 = sheet.createRow(3);
        Cell cell41 = row4.createCell(0);
        cell41.setCellValue("Terk");
        Cell cell42 = row4.createCell(1);
        cell42.setCellValue(10);
        Cell cell43 = row4.createCell(2);
        cell43.setCellValue(90);
        Cell cell44 = row4.createCell(3);
        cell44.setCellValue("No");
    }
    /**
     * XSSFSheet license_usage = wb.getSheet("license_usage");
     XSSFSheet sheet = (XSSFSheet) wb.createSheet("license_pivot_table");
     XSSFPivotTable pivotTable = sheet.createPivotTable(new AreaReference("license_usage!D:E"), new CellReference("A1"),license_usage);
     */
    public void createTable() throws Exception{
        Workbook wb = new XSSFWorkbook();
        Sheet sheet = (XSSFSheet) wb.createSheet();

        // Create
        XSSFTable table = ((XSSFSheet)sheet).createTable();
        table.setName("Test");
        table.setDisplayName("Test_Table");

        // For now, create the initial style in a low-level way
        table.getCTTable().addNewTableStyleInfo().setName("TableStyleMedium9");
        //table.getCTTable().getTableStyleInfo().setName("TableStyleLight15");

        // Style the table
        XSSFTableStyleInfo style = (XSSFTableStyleInfo)table.getStyle();
        style.setName("TableStyleMedium9");
        style.setShowColumnStripes(false);
        style.setShowRowStripes(true);
        style.setFirstColumn(false);
        style.setLastColumn(false);
        style.setShowRowStripes(true);
        style.setShowColumnStripes(true);

        // Set the values for the table
        Row row;
        Cell cell;
        for(int i=0; i<3; i++) {
            // Create row
            row = sheet.createRow(i);
            for(int j=0; j<3; j++) {
                // Create cell
                cell = row.createCell(j);
                if(i == 0) {
                    cell.setCellValue("Column"+(j+1));
                } else {
                    cell.setCellValue((i+1)*(j+1));
                }
            }
        }
        // Create the columns
        table.addColumn();
        table.addColumn();
        table.addColumn();

        // Set which area the table should be placed in
        AreaReference reference = new AreaReference(
                new CellReference(0, 0), new CellReference(2, 2));
        table.setCellReferences(reference);

        // Save
        FileOutputStream fileOut = new FileOutputStream("ooxml-table.xlsx");
        wb.write(fileOut);
        fileOut.close();
        wb.close();
    }
}
