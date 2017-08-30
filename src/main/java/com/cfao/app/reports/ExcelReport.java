package com.cfao.app.reports;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Created by JP on 8/30/2017.
 */
public class ExcelReport {
    public Workbook workbook = new XSSFWorkbook();
    public CellStyle headerStyle, defaultStyle, redStyle, wrapStyle, redBackground, dateStyle;
    public Row row;
    public CreationHelper createHelper = workbook.getCreationHelper();

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
        dateStyle.setDataFormat(
                createHelper.createDataFormat().getFormat("dddd, dd mmmm, yyyy"));

        defaultStyle = workbook.createCellStyle();
        defaultStyle.setBorderBottom(BorderStyle.THIN);
        defaultStyle.setBorderLeft(BorderStyle.THIN);
        //defaultStyle.setLeftBorderColor(IndexedColors.GREEN.getIndex());
        defaultStyle.setBorderRight((BorderStyle.THIN));
        defaultStyle.setBorderTop((BorderStyle.THIN));
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
}
