package com.cfao.app.reports;

import com.cfao.app.beans.Formation;
import com.cfao.app.beans.Planification;
import com.cfao.app.beans.Tache;
import com.cfao.app.util.AlertUtil;
import com.cfao.app.util.ServiceproUtil;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.ResourceBundle;


/**
 * Created by JP on 8/30/2017.
 */
public class ExcelFormation {
    private Formation formation;

    public ExcelFormation(Formation formation) {
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
        Workbook wb = new XSSFWorkbook();
        CreationHelper createHelper = wb.getCreationHelper();
        Sheet sheet = wb.createSheet("new sheet");

        int i = 0;
        int j = 0;
        for (Planification planification : planifications) {
            j = 0;
            Row row = sheet.createRow((short) i);
            if (planification.getTaches().size() > 0) {
                row.setHeightInPoints(((planification.getTaches().size() + 1) * sheet.getDefaultRowHeightInPoints()));
            }
            row.createCell(j).setCellValue(i + 1);
            row.createCell(j++).setCellValue(planification.getSujet().getLibelle());
            CellStyle cs = wb.createCellStyle();
            cs.setWrapText(true);
            cs.setVerticalAlignment(VerticalAlignment.TOP);
            Cell cell = row.createCell(j++);
            cell.setCellStyle(cs);
            cell.setCellStyle(cs);
            String taches = "";
            for (Tache tache : planification.getTaches()) {
                taches += "\n" + tache.getLibelle();
            }
            cell.setCellValue(taches);
            /*row..setCellValue(
                    createHelper.createRichTextString(planification.getTaches().toString()));*/
            row.createCell(j++).setCellValue(planification.getResponsable().getLibelle());
            row.createCell(j++).setCellValue(planification.getValidation().getLibelle());
            //sheet.auto;
            i++;
        }
        for (int colNum = 0; colNum < sheet.getRow(0).getLastCellNum(); colNum++) {
            wb.getSheetAt(0).autoSizeColumn(colNum);
        }
        File file = new File(ResourceBundle.getBundle("Bundle").getString("document.dir") + File.separator + "workbook.xlsx");
        FileOutputStream fileOut = new FileOutputStream(file);
        wb.write(fileOut);
        fileOut.close();
        Path path = Paths.get(ResourceBundle.getBundle("Bundle").getString("document.dir")).toAbsolutePath();
        String document = path.toString() + File.separator + "workbook.xlsx";
        File f = new File((document));
        if (f.exists() && !f.isDirectory()) {
            ServiceproUtil.openDocument(f);
        }
    }
}
