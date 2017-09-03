package com.cfao.app.reports;

import com.cfao.app.util.ServiceproUtil;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;

/**
 * Created by JP on 8/30/2017.
 */
public class ExcelReport {
    public XSSFWorkbook workbook = new XSSFWorkbook();
    public CellStyle headerStyle, defaultStyle, redStyle, wrapStyle, redBackground, dateStyle;
    public CellStyle shortDateStyle;
    public XSSFRow row;
    public CreationHelper createHelper = workbook.getCreationHelper();
    XSSFSheet sheet = workbook.createSheet("new sheet");

    CellStyle timingPassed;
    CellStyle timingNotPassed;

    public ExcelReport() {
        headerStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 13);
        font.setBold(true);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setFont(font);

        redStyle = workbook.createCellStyle();
        font = workbook.createFont();
        font.setColor(Font.COLOR_RED);
        redStyle.setFont(font);

        /** Red Background Style */
        redBackground = workbook.createCellStyle();
        redBackground.setFillForegroundColor(IndexedColors.RED.getIndex());
        font = workbook.createFont();
        font.setBold(true);
        redBackground.setFont(font);
        redBackground.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        /** Wrapable Cell Style */
        wrapStyle = workbook.createCellStyle();
        wrapStyle.setWrapText(true);
        //wrapStyle.setVerticalAlignment(VerticalAlignment.TOP);

        /** Date Style */
        dateStyle = workbook.createCellStyle();
        shortDateStyle = workbook.createCellStyle();
        dateStyle.setDataFormat(
                createHelper.createDataFormat().getFormat("dddd, dd mmmm, yyyy"));
        shortDateStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-mmm-yy"));

        defaultStyle = workbook.createCellStyle();
        defaultStyle.setBorderBottom(BorderStyle.THIN);
        defaultStyle.setBorderLeft(BorderStyle.THIN);
        //defaultStyle.setLeftBorderColor(IndexedColors.GREEN.getIndex());
        defaultStyle.setBorderRight((BorderStyle.THIN));
        defaultStyle.setBorderTop((BorderStyle.THIN));

        /** TABLE Options */
        // Create
        /*XSSFTable table = null;
        table.setName("Test");
        table.setDisplayName("Test_Table");

        // Create the initial style in a low-level way
        table.getCTTable().addNewTableStyleInfo();
        table.getCTTable().getTableStyleInfo().setName("TableStyleMedium2");

        // Style the table
        XSSFTableStyleInfo style = (XSSFTableStyleInfo)table.getStyle();
        style.setName("TableStyleMedium2");
        style.setShowColumnStripes(false);
        style.setShowRowStripes(true);
        style.setFirstColumn(false);
        style.setLastColumn(false);
        style.setShowRowStripes(true);
        style.setShowColumnStripes(true);

        table.addColumn();*/

    }

    public void setBorderToAllCells(int firstRow, int lastRow, int firstCol, int lastCol) {
        CellRangeAddress region = new CellRangeAddress(firstRow, lastRow, firstCol, lastCol);
        Sheet sheet = workbook.getSheetAt(0);
        Cell cell;
        for (int i = region.getFirstRow(); i < region.getLastRow(); i++) {
            Row row = sheet.getRow(i);
            for (int j = region.getFirstColumn(); j < region.getLastColumn(); j++) {
                cell = row.getCell(j);
                if (cell != null) {
                    if (cell.getCellStyle() != null) {
                        cell.getCellStyle().setBorderLeft(BorderStyle.THIN);
                        cell.getCellStyle().setBorderRight(BorderStyle.THIN);
                        cell.getCellStyle().setBorderTop(BorderStyle.THIN);
                        cell.getCellStyle().setBorderBottom(BorderStyle.THIN);
                    } else {
                        cell.setCellStyle(defaultStyle);
                    }
                }
            }
        }
    }
    //myStyle.setRotation((short) 120);

    /**
     * Finalise le fichier excel, l'imprime et l'ouvre
     */
    public void finalize() throws Exception{
        Row row = sheet.getRow(sheet.getLastRowNum());
        for (int colNum = 0; colNum < row.getLastCellNum(); colNum++) {
            sheet.autoSizeColumn(colNum);
        }

        setBorderToAllCells(0, sheet.getLastRowNum(), 0, row.getLastCellNum());

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

    public CellStyle timingStyle(boolean passed) {
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
}
