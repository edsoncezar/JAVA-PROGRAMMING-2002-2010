import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import javax.swing.DefaultCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.JOptionPane;
import java.awt.*;
import java.awt.event.*;

public class TableRenderDemo extends JFrame {
    private boolean DEBUG = true;

    public TableRenderDemo() {
        super("TableRenderDemo");

        MyTableModel myModel = new MyTableModel();
        JTable table = new JTable(myModel);
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));

        //Create the scroll pane and add the table to it. 
        JScrollPane scrollPane = new JScrollPane(table);

        //Set up column sizes.
        initColumnSizes(table, myModel);

        //Fiddle with the Sport column's cell editors/renderers.
        setUpSportColumn(table.getColumnModel().getColumn(2));

        //Add the scroll pane to this window.
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    /*
     * This method picks good column sizes.
     * If all column heads are wider than the column's cells' 
     * contents, then you can just use column.sizeWidthToFit().
     */
    private void initColumnSizes(JTable table, MyTableModel model) {
        TableColumn column = null;
        Component comp = null;
        int headerWidth = 0;
        int cellWidth = 0;
        Object[] longValues = model.longValues;

        for (int i = 0; i < 5; i++) {
            column = table.getColumnModel().getColumn(i);

            try {
                comp = column.getHeaderRenderer().
                                 getTableCellRendererComponent(
                                     null, column.getHeaderValue(), 
                                     false, false, 0, 0);
                headerWidth = comp.getPreferredSize().width;
            } catch (NullPointerException e) {
                System.err.println("Null pointer exception!");
                System.err.println("  getHeaderRenderer returns null in 1.3.");
                System.err.println("  The replacement is getDefaultRenderer.");
            }

            comp = table.getDefaultRenderer(model.getColumnClass(i)).
                             getTableCellRendererComponent(
                                 table, longValues[i],
                                 false, false, 0, i);
            cellWidth = comp.getPreferredSize().width;

            if (DEBUG) {
                System.out.println("Initializing width of column "
                                   + i + ". "
                                   + "headerWidth = " + headerWidth
                                   + "; cellWidth = " + cellWidth);
            }

            //XXX: Before Swing 1.1 Beta 2, use setMinWidth instead.
            column.setPreferredWidth(Math.max(headerWidth, cellWidth));
        }
    } 

    public void setUpSportColumn(TableColumn sportColumn) {
        //Set up the editor for the sport cells.
        JComboBox comboBox = new JComboBox();
        comboBox.addItem("Snowboarding");
        comboBox.addItem("Rowing");
        comboBox.addItem("Chasing toddlers");
        comboBox.addItem("Speed reading");
        comboBox.addItem("Teaching high school");
        comboBox.addItem("None");
        sportColumn.setCellEditor(new DefaultCellEditor(comboBox));

        //Set up tool tips for the sport cells.
        DefaultTableCellRenderer renderer =
                new DefaultTableCellRenderer();
        renderer.setToolTipText("Click for combo box");
        sportColumn.setCellRenderer(renderer);

        //Set up tool tip for the sport column header.
        TableCellRenderer headerRenderer = sportColumn.getHeaderRenderer();
        if (headerRenderer instanceof DefaultTableCellRenderer) {
            ((DefaultTableCellRenderer)headerRenderer).setToolTipText(
                     "Click the sport to see a list of choices");
        } 
    }

    class MyTableModel extends AbstractTableModel {
        final String[] columnNames = {"First Name", 
                                      "Last Name",
                                      "Sport",
                                      "# of Years",
                                      "Vegetarian"};
        final Object[][] data = {
            {"Mary", "Campione", 
             "Snowboarding", new Integer(5), new Boolean(false)},
            {"Alison", "Huml", 
             "Rowing", new Integer(3), new Boolean(true)},
            {"Kathy", "Walrath",
             "Chasing toddlers", new Integer(2), new Boolean(false)},
            {"Mark", "Andrews",
             "Speed reading", new Integer(20), new Boolean(true)},
            {"Angela", "Lih",
             "Teaching high school", new Integer(4), new Boolean(false)}
        };
        public final Object[] longValues = {"Angela", "Andrews", 
                                            "Teaching high school",
                                            new Integer(20), Boolean.TRUE};

        public int getColumnCount() {
            return columnNames.length;
        }
        
        public int getRowCount() {
            return data.length;
        }

        public String getColumnName(int col) {
            return columnNames[col];
        }

        public Object getValueAt(int row, int col) {
            return data[row][col];
        }

        /*
         * JTable uses this method to determine the default renderer/
         * editor for each cell.  If we didn't implement this method,
         * then the last column would contain text ("true"/"false"),
         * rather than a check box.
         */
        public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }

        /*
         * Don't need to implement this method unless your table's
         * editable.
         */
        public boolean isCellEditable(int row, int col) {
            //Note that the data/cell address is constant,
            //no matter where the cell appears onscreen.
            if (col < 2) { 
                return false;
            } else {
                return true;
            }
        }

        /*
         * Don't need to implement this method unless your table's
         * data can change.
         */
        public void setValueAt(Object value, int row, int col) {
            if (DEBUG) {
                System.out.println("Setting value at " + row + "," + col
                                   + " to " + value
                                   + " (an instance of " 
                                   + value.getClass() + ")");
            }

            if (data[0][col] instanceof Integer
                    && !(value instanceof Integer)) {
                //With JFC/Swing 1.1 and JDK 1.2, we need to create
                //an Integer from the value; otherwise, the column
                //switches to contain Strings.  Starting with v 1.3,
                //the table automatically converts value to an Integer,
                //so you only need the code in the 'else' part of this
                //'if' block.
                try {
                    data[row][col] = new Integer(value.toString());
                    fireTableCellUpdated(row, col);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(TableRenderDemo.this,
                        "The \"" + getColumnName(col)
                        + "\" column accepts only integer values.");
                }
            } else {
                data[row][col] = value;
                fireTableCellUpdated(row, col);
            }

            if (DEBUG) {
                System.out.println("New value of data:");
                printDebugData();
            }
        }

        private void printDebugData() {
            int numRows = getRowCount();
            int numCols = getColumnCount();

            for (int i=0; i < numRows; i++) {
                System.out.print("    row " + i + ":");
                for (int j=0; j < numCols; j++) {
                    System.out.print("  " + data[i][j]);
                }
                System.out.println();
            }
            System.out.println("--------------------------");
        }
    }

    public static void main(String[] args) {
        TableRenderDemo frame = new TableRenderDemo();
        frame.pack();
        frame.setVisible(true);
    }
}
