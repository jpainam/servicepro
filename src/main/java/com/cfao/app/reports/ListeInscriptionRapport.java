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
                row.createCell(col++).setCellValue(p.getPays().getNamefr());
                /** Filiale */
                row.createCell(col++).setCellValue(p.getSociete().getNom());
                /** Prenom */
                row.createCell(col++).setCellValue(p.getPrenom());
                /** Nom */
                row.createCell(col++).setCellValue(p.getNom());
                /** Fonction */
                row.createCell(col++).setCellValue(p.getFonction());
                /** Formation */
                row.createCell(col++).setCellValue(f.getTitre());
                /** Formateur */
                row.createCell(col++).setCellValue(f.getSocieteFormatrice().getLibelle());
                /** Type */
                row.createCell(col++).setCellValue(f.getTypeFormation().getLibelle());
                /** Date */
                cell = row.createCell(col++);
                cell.setCellStyle(shortDateStyle);
                cell.setCellValue(f.getDatedebut());
                /** Nbre jour */
                cell = row.createCell(col++);
                Period diff = Period.between(f.datedebutProperty().get(), f.datefinProperty().get());
               // int diffInDays = (int) ((f.getDatefin().getTime() - f.getDatedebut().getTime()) / (1000 * 60 * 60 * 24));
                cell.setCellValue(diff.getDays());

                /** Score */
                row.createCell(col++).setCellValue(f.getScore());
                /** TP */
                row.createCell(col++).setCellValue(f.getTp());
                /** Remarque */
                row.createCell(col++).setCellValue(f.getRemarque());
                line++;
                i++;
            }
        }

        /*AreaReference reference = new AreaReference(
                new CellReference(0, 0), new CellReference(sheet.getLastRowNum(), titres.length));
        if(reference == null){
            System.err.println("Ref is null");
        }
        table.setCellReferences(reference);*/
        sheet.createFreezePane(0, 2);

        //Create some data to build the pivot table on

        AreaReference source = new AreaReference(new CellReference(0, 0), new CellReference(sheet.getLastRowNum(), titres.length));
        CellReference position = new CellReference("A1");
        // Create a pivot table on this sheet, with H5 as the top-left cell..
        // The pivot table's data source is on the same sheet in A1:D4
        XSSFPivotTable pivotTable = sheet.createPivotTable(source, position);
        //Configure the pivot table
        //Use first column as row label
        pivotTable.addRowLabel(4); // Nom
        //Sum up the second column
        pivotTable.addColumnLabel(DataConsolidateFunction.COUNT, 5);
        //Set the third column as filter
        pivotTable.addColumnLabel(DataConsolidateFunction.SUM, 2);
        //Add filter on forth column
        pivotTable.addReportFilter(1); // Pays
        pivotTable.addReportFilter(2); // Filiale
        pivotTable.addReportFilter(8); // Date

        finalize();
    }
}
