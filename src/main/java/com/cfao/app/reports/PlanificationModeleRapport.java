package com.cfao.app.reports;

import com.cfao.app.beans.Document;
import com.cfao.app.beans.PlanificationModele;
import com.cfao.app.beans.Tache;
import com.cfao.app.model.Model;
import com.cfao.app.util.AlertUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by JP on 9/2/2017.
 */
public class PlanificationModeleRapport extends ExcelReport {
    public PlanificationModeleRapport(){
        super();
        timingPassed = timingStyle(true);
        timingNotPassed = timingStyle(false);
    }
    public void printPlanificationModele() throws Exception {
        List<PlanificationModele> planifications = new Model<PlanificationModele>("PlanificationModele").getList();
        if (planifications == null) {
            AlertUtil.showSimpleAlert("Information", "Aucun modèle de planification à imprimer");
            return;
        }

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
        Date debut = new Date();
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
        finalize();
    }
}
