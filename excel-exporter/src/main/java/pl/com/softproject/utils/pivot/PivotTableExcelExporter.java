/*
 * Copyright 2012-12-10 the original author or authors.
 */
package pl.com.softproject.utils.pivot;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 *
 * @author Adrian Lapierre <adrian@softproject.com.pl>
 */
public class PivotTableExcelExporter {

    private Workbook wb;

    public PivotTableExcelExporter() {
    }

    public PivotTableExcelExporter(Workbook wb) {
        this.wb = wb;
    }
    
    private void createWorkbook() {
        wb = new HSSFWorkbook();
    }

    public void export(PivotTableModel pivotTableModel, String sheetName, String[] columns) {

        if (wb == null) {
            createWorkbook();
        }

        Font font = wb.createFont();
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        CellStyle cs = wb.createCellStyle();
        cs.setFont(font);
        cs.setAlignment(CellStyle.ALIGN_CENTER);

        Sheet sheet = wb.createSheet(sheetName);

        Row row ;
        Cell cell;

        Row header = addHeader(columns, sheet, cs);

        PivotTableModelImpl.RowIterator rows = (PivotTableModelImpl.RowIterator) pivotTableModel.iterator();

        short rownum = 1;

        while (rows.hasNext()) {
            row = sheet.createRow(rownum++);

            Map<String, Object> pivotRow = rows.next();
            String rowKey = rows.rowKey();
            cell = row.createCell(0);
            cell.setCellValue(rowKey);
            short cellnum = 1;
            for (String mag : columns) {
                cell = row.createCell(cellnum++);
                Number number = (Number) pivotRow.get(mag);
                cell.setCellValue(number != null ? number.doubleValue() : 0);
            }

        }

//        for (Row sumRow : sheet) {
//            Cell sumCell = sumRow.createCell(8);
//            sumCell.setCellFormula(String.format("SUM(B%s:H%s)", sumRow.getRowNum() + 1, sumRow.getRowNum() + 1));
//
//            //cell.setCellFormula("SUM(F1:H1)+B1");
//        }

    }

    public void saveWorkbook(File file) throws FileNotFoundException, IOException {
        
        if (wb == null) {
            createWorkbook();
        }
        
        FileOutputStream out = new FileOutputStream(file);
        wb.write(out);
        out.close();
    }

    private Row addHeader(String[] columns, Sheet sheet, CellStyle cs) {

        Row row = sheet.createRow(0);
        short cellnum = 1;

        for (String colName : columns) {
            Cell cell = row.createCell(cellnum++);
            cell.setCellValue(colName);
            cell.setCellStyle(cs);
        }
        return row;
    }
}
