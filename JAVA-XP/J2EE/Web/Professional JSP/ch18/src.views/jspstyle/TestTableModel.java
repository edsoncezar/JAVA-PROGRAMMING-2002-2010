package jspstyle;

import javax.swing.table.AbstractTableModel;

// Dummy table model to demonstrate jspTable tag extension.
public class TestTableModel extends AbstractTableModel {

  private int rows;
  private int cols;

  //  Create a random number of cells
  public TestTableModel() {
    java.util.Random rand = new java.util.Random();
    rows = rand.nextInt(20) + 3;
    cols = rand.nextInt(5) + 3;
  }

  public int getRowCount() {
    return rows;
  }

  public int getColumnCount() {
    return cols;
  }

  //  Return a String value showing the cell location
  public Object getValueAt(int row, int column) {
    return "[" + row + "," + column + "]";
  }

  public String getColumnName(int column) {
    return "Column " + column;
  }
}
