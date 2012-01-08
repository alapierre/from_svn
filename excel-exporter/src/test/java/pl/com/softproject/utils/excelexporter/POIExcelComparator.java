/*
 * Copyright 2011-10-18 the original author or authors.
 */
package pl.com.softproject.utils.excelexporter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;

/**
 *
 * @author Adrian Lapierre <adrian@softproject.com.pl>
 */
public class POIExcelComparator {

    HSSFWorkbook expectedWorkBook;
    HSSFWorkbook acctualWorkBook;

    public POIExcelComparator(String expectedXlsFileName, String acctualXlsFileName) throws IOException {

        InputStream expectedInputStream = null;
        expectedInputStream = new FileInputStream(expectedXlsFileName);

        POIFSFileSystem expectedFileSystem = null;
        expectedFileSystem = new POIFSFileSystem(expectedInputStream);
        expectedWorkBook = new HSSFWorkbook(expectedFileSystem);

        InputStream acctualInputStream = null;
        acctualInputStream = new FileInputStream(acctualXlsFileName);

        POIFSFileSystem acctualFileSystem = null;
        acctualFileSystem = new POIFSFileSystem(acctualInputStream);
        acctualWorkBook = new HSSFWorkbook(acctualFileSystem);

    }

    public boolean compare() {

        HSSFSheet sheet = expectedWorkBook.getSheetAt(0);
        Iterator<Row> rows = sheet.rowIterator();

        while (rows.hasNext()) {
            Row row = rows.next();
            Iterator<Cell> cells = row.cellIterator();

            while (cells.hasNext()) {
                Cell cell = cells.next();
                System.out.print(getCellValueAsJavaObject(cell) + " = ");
                System.out.println(getCellValueAsJavaObject(getAcctualCell(cell.getRowIndex(), cell.getColumnIndex())));
            }
            System.out.println();
        }

        return false;
    }

    protected Cell getAcctualCell(int r, int c) {
        HSSFSheet sheet = acctualWorkBook.getSheetAt(0);
        Row row = sheet.getRow(r);
        
        return row != null ? row.getCell(c) : null;
    }
    
    protected Object getCellValueAsJavaObject(Cell cell) {

        if(cell == null) return null;
        
        switch (cell.getCellType()) {
            
            case HSSFCell.CELL_TYPE_NUMERIC: 
                return new Double(cell.getNumericCellValue());
            
            case HSSFCell.CELL_TYPE_STRING: 
                RichTextString richTextString = cell.getRichStringCellValue();
                return richTextString.getString();

            default: 
                throw new RuntimeException("illegal cell type " + cell.getCellType() + " for " + cell.getRowIndex() + " " + cell.getColumnIndex());
                
        }
    }
}
