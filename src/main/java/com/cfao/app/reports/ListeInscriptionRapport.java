package com.cfao.app.reports;

import com.cfao.app.beans.Formation;
import com.cfao.app.beans.FormationPersonne;
import com.cfao.app.beans.Personne;
import com.cfao.app.model.FormationModel;
import org.apache.poi.ss.usermodel.DataConsolidateFunction;
import org.apache.poi.ss.util.AreaReference;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFPivotTable;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.*;

import java.time.Period;
import java.util.Date;
import java.util.List;

/**
 * Created by JP on 9/2/2017.
 */
public class ListeInscriptionRapport extends ExcelReport {
    public ListeInscriptionRapport(){
        super();
    }
    public void printListe() throws Exception{
        short line = 0; // La ligne en cours de traitement
        //XSSFTable table = sheet.createTable();
        /*table.getCTTable().addNewTableStyleInfo();
        table.getCTTable().getTableStyleInfo().setName("TableStyleMedium2");*/

        // Style the table
        /*XSSFTableStyleInfo style = (XSSFTableStyleInfo)table.getStyle();
        //style.setName("TableStyleMedium2");
        style.setShowColumnStripes(false);
        style.setShowRowStripes(true);
        style.setFirstColumn(false);
        style.setLastColumn(false);
        style.setShowRowStripes(true);
        style.setShowColumnStripes(true);*/
        /**
         * ENTETE
         */
        row = sheet.createRow(line);
        XSSFCell cell = row.createCell(0);
        cell.setCellValue("LISTE DES FORMATIONS ET PARTICIPANTS");
        cell.setCellStyle(headerStyle);

        sheet.addMergedRegion(new CellRangeAddress(line, line, 0, 4));
        cell = row.createCell(6);
        cell.setCellStyle(dateStyle);
        cell.setCellValue(new Date());
        sheet.addMergedRegion(new CellRangeAddress(line, line, 6, 9));

        line++;
        row = sheet.createRow(line);
        String[] titres = {"N°", "Pays", "Filiale", "Prénom", "Nom", "Fonction", "Formation", "Formateur", "Type", "Date", "Nbre jour", "Score", "TP", "Remarque"};
        for (int i = 0; i < titres.length; i++) {
            cell = row.createCell((short) i);
            cell.setCellValue(titres[i]);
            cell.setCellStyle(headerStyle);
            //table.addColumn();
        }

        line++;
        List<Formation> formations = new FormationModel().getList();
        int col;
        int i = 1;
        for(Formation formation : formations){
            for(FormationPersonne fp : formation.getFormationPersonnes()) {
                Personne p = fp.getPersonne();
                Formation f = fp.getFormation();
                col = 0;
                row = sheet.createRow(line);
                row.createCell(col++).setCellValue(i);
                /** Pays */
                cell = row.createCell(col++);
                cell.setCellStyle(defaultStyle);
                cell.setCellValue(p.getPays().getNamefr());
                /** Filiale */
                cell = row.createCell(col++);
                cell.setCellValue(p.getSociete().getNom());
                cell.setCellStyle(defaultStyle);
                /** Prenom */
                cell = row.createCell(col++);
                cell.setCellValue(p.getPrenom());
                cell.setCellStyle(defaultStyle);
                /** Nom */
                cell = row.createCell(col++);
                cell.setCellValue(p.getNom());
                cell.setCellStyle(defaultStyle);
                /** Fonction */
                cell = row.createCell(col++);
                cell.setCellValue(p.getFonction());
                cell.setCellStyle(defaultStyle);
                /** Formation */
                cell = row.createCell(col++);
                cell.setCellValue(f.getTitre());
                cell.setCellStyle(defaultStyle);
                /** Formateur */
                cell = row.createCell(col++);
                cell.setCellValue(f.getSocieteFormatrice().getLibelle());
                cell.setCellStyle(defaultStyle);
                /** Type */
                cell = row.createCell(col++);
                cell.setCellValue(f.getTypeFormation().getLibelle());
                cell.setCellStyle(defaultStyle);
                /** Date */
                cell = row.createCell(col++);
                cell.setCellStyle(shortDateStyle);
                cell.setCellValue(f.getDatedebut());
                /** Nbre jour */
                cell = row.createCell(col++);
                Period diff = Period.between(f.datedebutProperty().get(), f.datefinProperty().get());
               // int diffInDays = (int) ((f.getDatefin().getTime() - f.getDatedebut().getTime()) / (1000 * 60 * 60 * 24));
                cell.setCellValue(diff.getDays());
                cell.setCellStyle(defaultStyle);

                /** Score */
                cell = row.createCell(col++);
                cell.setCellValue(f.getScore());
                cell.setCellStyle(defaultStyle);
                /** TP */
                cell = row.createCell(col++);
                cell.setCellValue(f.getTp());
                cell.setCellStyle(defaultStyle);
                /** Remarque */
                cell = row.createCell(col++);
                cell.setCellValue(f.getRemarque());
                cell.setCellStyle(defaultStyle);
                line++;
                i++;
            }
        }
        sheet.createFreezePane(0, 2);
        AreaReference source = new AreaReference(new CellReference(1, 1), new CellReference(sheet.getLastRowNum(), titres.length - 1));
        //AreaReference source = new AreaReference("B2:N14", SpreadsheetVersion.EXCEL2007);
        // Position du debut du pivot table dans le second sheet
        CellReference position = new CellReference("A6");
        XSSFSheet pivotSheet = workbook.createSheet("Extraction");
        XSSFPivotTable pivotTable = pivotSheet.createPivotTable(source, position, sheet);
        pivotTable.addRowLabel(3); // Nom
        pivotTable.addColumnLabel(DataConsolidateFunction.SUM, 9, "Somme Nbre jour");
        addColumLabels(pivotTable, 5, STItemType.DEFAULT);
        pivotTable.addReportFilter(0); // Pays
        pivotTable.addReportFilter(1); // Filiale
        pivotTable.addReportFilter(8); // Date

        /** Deuxieme pivot table */
        pivotSheet = workbook.createSheet("Extraction (2)");
        pivotTable = pivotSheet.createPivotTable(source, position, sheet);
        pivotTable.addRowLabel(0); // Pays
        pivotTable.addColumnLabel(DataConsolidateFunction.SUM, 9, "Somme Nbre jour");
        pivotTable.addColumnLabel(DataConsolidateFunction.COUNT, 3, "Nombre de Nom");
        //addColumLabels(pivotTable, 3, STItemType.COUNT);
        pivotTable.addReportFilter(8); // Date
        finalize();
    }


    public  void addColumLabels(XSSFPivotTable pivotTable, int columnIndex, STItemType.Enum sttItemType) {
        AreaReference pivotArea = pivotTable.getPivotCacheDefinition().getPivotArea(sheet.getWorkbook());
        int lastColIndex = pivotArea.getLastCell().getCol() - pivotArea.getFirstCell().getCol();
        CTPivotFields pivotFields = pivotTable.getCTPivotTableDefinition().getPivotFields();

        CTPivotField pivotField = CTPivotField.Factory.newInstance();
        CTItems items = pivotField.addNewItems();

        pivotField.setAxis(STAxis.AXIS_COL);
        pivotField.setShowAll(false);
        for (int i = 0; i <= lastColIndex; i++) {
            items.addNewItem().setT(sttItemType);
        }
        items.setCount(items.sizeOfItemArray());
        pivotFields.setPivotFieldArray(columnIndex, pivotField);

        // colfield should be added for the second one.
        CTColFields colFields;
        if (pivotTable.getCTPivotTableDefinition().getColFields() != null) {
            colFields = pivotTable.getCTPivotTableDefinition().getColFields();
        } else {
            colFields = pivotTable.getCTPivotTableDefinition().addNewColFields();
        }
        colFields.addNewField().setX(columnIndex);
        colFields.setCount(colFields.sizeOfFieldArray());
    }
}

