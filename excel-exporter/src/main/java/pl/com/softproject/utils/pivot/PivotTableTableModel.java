/*
 * Copyright 2013-03-07 the original author or authors.
 */
package pl.com.softproject.utils.pivot;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.table.AbstractTableModel;
import pl.com.softproject.utils.pivot.PivotTableModel;
import pl.com.softproject.utils.pivot.PivotTableModelImpl;

/**
 *
 * @author Adrian Lapierre <alapierre@softproject.com.pl>
 */
public class PivotTableTableModel<T> extends AbstractTableModel {

    protected PivotTableModel pivot = new PivotTableModelImpl();
    
    protected Object[][] dataTab;
    protected String columnNames[];
    protected String rowNames[];
    protected int columnCount;
    protected int rowCount;

    public void add(String rowKey, String columnKey, T value) {
        pivot.add(rowKey, columnKey, value);
    }

    public void prepareForTabe() {

        Set<String> columns = pivot.getColumnNames();
        columnNames = columns.toArray(new String[0]);
        columnCount = columnNames.length + 1;

        rowNames = pivot.getRowNames().toArray(new String[0]);
        rowCount = rowNames.length;

        dataTab = new Object[rowCount][];

        PivotTableModelImpl.RowIterator rows = (PivotTableModelImpl.RowIterator) pivot.iterator();

        short rownum = 0;

        while (rows.hasNext()) {
            Map<String, Object> pivotRow = rows.next();

            List<Object> tmp = new LinkedList<Object>(pivotRow.values());
            tmp.add(0, rowNames[rownum]);
            dataTab[rownum++] = tmp.toArray();
        }
        rowCount = rownum;

    }

    @Override
    public int getRowCount() {
        return rowCount;
    }

    @Override
    public int getColumnCount() {
        return columnCount;
    }

    @Override
    public T getValueAt(int rowIndex, int columnIndex) {
        return (T)dataTab[rowIndex][columnIndex];
    }

    @Override
    public String getColumnName(int column) {
        return column == 0 ? "Klucz" : columnNames[column - 1];
    }
}
