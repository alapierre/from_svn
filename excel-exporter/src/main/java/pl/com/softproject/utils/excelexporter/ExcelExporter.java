/*
 * Copyright 2011-03-30 the original author or authors.
 */

package pl.com.softproject.utils.excelexporter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import org.apache.commons.beanutils.NestedNullException;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * Serializuje obiekty do pliku .xls
 *
 * @author <a href="mailto:alapierre@soft-project.pl">Adrian Lapierre</a>
 * $Rev: $, $LastChangedBy: $
 * $LastChangedDate: $
 */
public class ExcelExporter {

    private Logger logger = Logger.getLogger(getClass());

    protected List<ColumnDescriptor> columns = new LinkedList<ColumnDescriptor>();
    protected OutputStream out;
    protected String sheetName;
    protected HSSFSheet sheet;
    protected HSSFWorkbook wb;
    protected HSSFRow header;
    protected short currentRowNumber;
    protected int currentColumnNumber;

    protected DataFormat format;
    protected CellStyle styleMoney;
    protected CellStyle styleDate;
    protected CellStyle styleHeader;
    protected CellStyle styleDefault;

    public ExcelExporter(String sheetName) {        
        this.sheetName = sheetName;        
        init();        
    }

    /**
     * Tworzy kolumnę w wynikowym arkuszu. Do arkusza trafią wyłącznie
     * te właściwości serializowanego obiektu, które wcześniej zostaną dodane
     * przy pomocy tej metody
     *
     * @param columnDescriptor
     * @return
     */
    public ExcelExporter addColumn(ColumnDescriptor columnDescriptor) {
        columns.add(columnDescriptor);
        return this;
    }
    
    /**
     * Usuwa zdefiniowane kolumny dla arkusza
     */
    public void clearColumns() {
        columns.clear();
    }
    
    /**
     * Dodaje wiersz do akrusza pobierając refleksją dane z dostarczonego beana
     *
     * @param bean
     */
    public void createRow(Object bean) {

        if(currentRowNumber == 0)
            createHeaderRow();

        HSSFRow row = sheet.createRow(currentRowNumber);
        for(ColumnDescriptor column : columns) {
            createCell(row, bean, column);
        }
        currentColumnNumber = 0;
        currentRowNumber++;
    }

//    public void createOtherRow(Object bean, int colNumber, List<ColumnDescriptor> columns) {
//        
//        currentColumnNumber = colNumber;
//        HSSFRow row = sheet.createRow(currentRowNumber);
//        for(ColumnDescriptor column : columns) {
//            createCell(row, bean, column);
//        }
//        currentColumnNumber = 0;
//        currentRowNumber++;
//    }

    public int getColumnIndex(String headerName) {
        
        int result = 0;
        boolean finded = false;
        
        for(ColumnDescriptor column : columns) {
            if(headerName.equals(column.getHeaderName())) {
                finded=true;
                break;
            }
            result++;
        }
        
        return finded ? result : -1;
    }
    
    /**
     * Zapisuje utworzony arkusz do pliku
     *
     * @param outputFile
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void save(File outputFile) throws FileNotFoundException, IOException {
        OutputStream os = new FileOutputStream(outputFile);
        wb.write(os);
        os.close();
    }

    /**
     * Zapisuje utworzony arkusz do OutputStream
     *
     * @param outputFile
     * @param os need to by closed
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void save(OutputStream os) throws FileNotFoundException, IOException {
        wb.write(os);
    }

    /**
     * Ustawia dla wszystkich wypełnionych kolumn szerokość na auto-size
     */
    public void autoSizeAllColumns() {
        if(header!=null) {
            for (Cell col : header) {
                sheet.autoSizeColumn(col.getColumnIndex());
            }
        }
    }

    private void init() {
        createExcelSheet();
        format = wb.createDataFormat();
        initDefaultCellStyles();
        initHeaderStyle();
    }

    protected void initDefaultCellStyles() {        
        styleMoney = createCellStyle("#,##0.00");
        styleDate = createCellStyle("yyyy-mm-d hh:mm");
        styleDefault = wb.createCellStyle();
    }

    protected void initHeaderStyle() {
        Font font = wb.createFont();
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        styleHeader = wb.createCellStyle();
        styleHeader.setAlignment(CellStyle.ALIGN_CENTER);
        styleHeader.setFont(font);
    }

    protected CellStyle createCellStyle(String mask) {        
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setDataFormat(format.getFormat(mask));
        return cellStyle;
    }

    protected CellStyle cellStyleFromColumnlDescriptor(ColumnDescriptor columnDescriptor) {
        return createCellStyle(columnDescriptor.getExcelFormatMask());
    }

    /**
     * Zwraca domyślny styl lub styl zdefiniowany w ColumnDescriptor
     *
     * @param columnDescriptor
     * @param defaultCellStyle - domyślny styl dla tej kolumny
     * @return
     */
    protected CellStyle determinateCellStyle(ColumnDescriptor columnDescriptor, CellStyle defaultCellStyle) {
        if (columnDescriptor.isMaskSet()) {
            return cellStyleFromColumnlDescriptor(columnDescriptor);
        }

        return defaultCellStyle;
    }

    protected void createExcelSheet() {
        wb = new HSSFWorkbook();
        sheet = wb.createSheet(sheetName);
    }
    
    public void addSheet(String name) {
        sheetName = name;
        sheet = wb.createSheet(name);
        currentColumnNumber = 0;
        currentRowNumber = 0;
    }
    
    public Workbook getWorkbook() {
        return wb;
    }
    
    protected void createHeaderRow() {
        header = sheet.createRow(currentRowNumber);
        for(ColumnDescriptor column : columns) {
            Cell cell = header.createCell(currentColumnNumber++);
            cell.setCellValue(column.getHeaderName());
            cell.setCellStyle(styleHeader);
        }
        currentColumnNumber = 0;
        currentRowNumber++;
    }

    protected void createCell(HSSFRow row, Object bean, ColumnDescriptor columnDescriptor) {

        if(columnDescriptor.getPropertyName() == null) {
            currentColumnNumber++;
            return;
        }

        Object property = null;

        if(columnDescriptor instanceof EnumeratedColumnDescription) {
            property = ((EnumeratedColumnDescription)columnDescriptor).getValue(currentRowNumber, bean);
        } else if(columnDescriptor instanceof ConcatationColumnDescriptor) {
            property = getMultiProperty(bean, (ConcatationColumnDescriptor)columnDescriptor);
        }
        else
            property = getProperty(bean, columnDescriptor);

        if(property == null) {
            currentColumnNumber++;
            return;
        }

        if(property instanceof Date) {
            HSSFCell cell = row.createCell(currentColumnNumber++);
            cell.setCellValue((Date)property);
            cell.setCellStyle(determinateCellStyle(columnDescriptor, styleDate));
        } else if(property instanceof String) {
            HSSFCell cell = row.createCell(currentColumnNumber++);
            cell.setCellValue((String)property);
            cell.setCellStyle(determinateCellStyle(columnDescriptor, styleDefault));
        } else if(property instanceof Integer) {
            HSSFCell cell = row.createCell(currentColumnNumber++);
            cell.setCellValue((Integer)property);
            cell.setCellStyle(determinateCellStyle(columnDescriptor, styleDefault));
        } else if(property instanceof Long) {
            HSSFCell cell = row.createCell(currentColumnNumber++);
            cell.setCellValue((Long)property);
            cell.setCellStyle(determinateCellStyle(columnDescriptor, styleDefault));
        } else if(property instanceof Double) {
            HSSFCell cell = row.createCell(currentColumnNumber++);
            cell.setCellValue((Double)property);
            cell.setCellStyle(determinateCellStyle(columnDescriptor, styleDefault));
        } else {
            HSSFCell cell = row.createCell(currentColumnNumber++);
            cell.setCellValue(property.toString().trim());
            cell.setCellStyle(determinateCellStyle(columnDescriptor, styleDefault));
        }
    }

    protected Object getProperty(Object bean, ColumnDescriptor columnDescriptor) {
        try {
            return PropertyUtils.getNestedProperty(bean, columnDescriptor.getPropertyName());
        } catch (IllegalAccessException ex) {
            throw new PropertyAccessException(ex);
        } catch (InvocationTargetException ex) {
            throw new PropertyAccessException(ex);
        } catch (NoSuchMethodException ex) {
            throw new PropertyAccessException(ex);
        } catch (NestedNullException ignore) {
            if(logger.isDebugEnabled())
                logger.debug(ignore.getMessage());
            return null;
        } catch (IndexOutOfBoundsException ignore) {
            if(logger.isDebugEnabled())
                logger.debug("for property " + columnDescriptor.getPropertyName() + " " + ignore.getMessage());
            return null;
        }
    }

    public boolean isEmpty() {
        return currentRowNumber == 0;
    }

    private Object getMultiProperty(Object bean, ConcatationColumnDescriptor columnDescriptor) {

        StringBuilder sb = new StringBuilder();

        Object prop = getProperty(bean, columnDescriptor);
        if(prop != null)
            sb.append(prop);

        for(ColumnDescriptor cd : columnDescriptor.getColumnDescriptors()) {
            
            if(getProperty(bean, cd) != null) {
                sb.append(' ');
                sb.append(getProperty(bean, cd));
            }
        }

        return sb.toString();
    }

}
