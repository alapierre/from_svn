/*
 * Copyright 2011-10-18 the original author or authors.
 */
package pl.com.softproject.utils.excelexporter;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import java.util.Iterator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;

/**
 *
 * @author Adrian Lapierre <adrian@softproject.com.pl>
 */
public class POIExcelReader {

    /**
     * This method is used to display the Excel content to command line.
     *
     * @param xlsPath
     */
    @SuppressWarnings("unchecked")
    public void displayFromExcel(String xlsPath) throws FileNotFoundException, IOException {
        InputStream inputStream = null;


        inputStream = new FileInputStream(xlsPath);

        POIFSFileSystem fileSystem = null;

        fileSystem = new POIFSFileSystem(inputStream);

        HSSFWorkbook workBook = new HSSFWorkbook(fileSystem);
        HSSFSheet sheet = workBook.getSheetAt(0);
        Iterator<Row> rows = sheet.rowIterator();

        while (rows.hasNext()) {
            Row row = rows.next();

            // display row number in the console.
            System.out.println("Row No.: " + row.getRowNum());

            // once get a row its time to iterate through cells.
            Iterator<Cell> cells = row.cellIterator();

            while (cells.hasNext()) {
                Cell cell = cells.next();

                System.out.println("Cell No.: " + cell.getColumnIndex());

                /*
                 * Now we will get the cell type and display the values
                 * accordingly.
                 */
                switch (cell.getCellType()) {
                    case HSSFCell.CELL_TYPE_NUMERIC: {

                        // cell type numeric.
                        System.out.println("Numeric value: " + cell.getNumericCellValue());

                        break;
                    }

                    case HSSFCell.CELL_TYPE_STRING: {

                        // cell type string.
                        RichTextString richTextString = cell.getRichStringCellValue();

                        System.out.println("String value: " + richTextString.getString());

                        break;
                    }

                    default: {

                        // types other than String and Numeric.
                        System.out.println("Type not supported.");

                        break;
                    }
                }
            }
        }

    }

    /**
     * The main executable method to test displayFromExcel method.
     *
     * @param args
     */
    public static void main(String[] args) throws IOException {
        POIExcelReader poiExample = new POIExcelReader();
        String xlsPath = "D:/Moje Dokumenty/firma/chartsearch/rozliczenie/2011.01 - wypłaty.xls";

        poiExample.displayFromExcel(xlsPath);
    }
}
