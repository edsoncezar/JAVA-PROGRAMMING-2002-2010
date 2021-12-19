import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

import java.util.*;
import java.awt.*;
import java.awt.event.*;

public class ListSelectionDemo extends JPanel {
    JTextArea output;
    JList list; 
    JTable table;
    String newline = "\n";
    ListSelectionModel listSelectionModel;

    public ListSelectionDemo() {
        super(new BorderLayout());

        String[] listData = { "one", "two", "three", "four",
                              "five", "six", "seven" };
        String[] columnNames = { "French", "Spanish", "Italian" };
        String[][] tableData = {{"un",     "uno",     "uno"     },
                                {"deux",   "dos",     "due"     },
                                {"trois",  "tres",    "tre"     },
                                { "quatre", "cuatro",  "quattro"},
                                { "cinq",   "cinco",   "cinque" },
                                { "six",    "seis",    "sei"    },
                                { "sept",   "siete",   "sette"  } };

        list = new JList(listData);

        listSelectionModel = list.getSelectionModel();
        listSelectionModel.addListSelectionListener(
                new SharedListSelectionHandler());
        JScrollPane listPane = new JScrollPane(list);

        table = new JTable(tableData, columnNames);
        table.setSelectionModel(listSelectionModel);
        JScrollPane tablePane = new JScrollPane(table);

        //Build control area (use default FlowLayout).
        JPanel controlPane = new JPanel();
        String[] modes = { "SINGLE_SELECTION",
                           "SINGLE_INTERVAL_SELECTION",
                           "MULTIPLE_INTERVAL_SELECTION" };

        final JComboBox comboBox = new JComboBox(modes);
        comboBox.setSelectedIndex(2);
        comboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String newMode = (String)comboBox.getSelectedItem();
                if (newMode.equals("SINGLE_SELECTION")) {
                    listSelectionModel.setSelectionMode(
                        ListSelectionModel.SINGLE_SELECTION);
                } else if (newMode.equals("SINGLE_INTERVAL_SELECTION")) {
                    listSelectionModel.setSelectionMode(
                        ListSelectionModel.SINGLE_INTERVAL_SELECTION);
                } else {
                    listSelectionModel.setSelectionMode(
                        ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
                }
                output.append("----------"
                              + "Mode: " + newMode
                              + "----------" + newline);
            }
        });
        controlPane.add(new JLabel("Selection mode:"));
        controlPane.add(comboBox);

        //Build output area.
        output = new JTextArea(1, 10);
        output.setEditable(false);
        JScrollPane outputPane = new JScrollPane(output,
                         ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                         ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        //Do the layout.
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        add(splitPane, BorderLayout.CENTER);

        JPanel topHalf = new JPanel();
        topHalf.setLayout(new BoxLayout(topHalf, BoxLayout.X_AXIS));
        JPanel listContainer = new JPanel(new GridLayout(1,1));
        listContainer.setBorder(BorderFactory.createTitledBorder(
                                                "List"));
        listContainer.add(listPane);
        JPanel tableContainer = new JPanel(new GridLayout(1,1));
        tableContainer.setBorder(BorderFactory.createTitledBorder(
                                                "Table"));
        tableContainer.add(tablePane);
        tablePane.setPreferredSize(new Dimension(300, 100));
        topHalf.setBorder(BorderFactory.createEmptyBorder(5,5,0,5));
        topHalf.add(listContainer);
        topHalf.add(tableContainer);

        topHalf.setMinimumSize(new Dimension(400, 50));
        topHalf.setPreferredSize(new Dimension(400, 110));
        splitPane.add(topHalf);

        JPanel bottomHalf = new JPanel(new BorderLayout());
        bottomHalf.add(controlPane, BorderLayout.NORTH);
        bottomHalf.add(outputPane, BorderLayout.CENTER);
        //XXX: next line needed if bottomHalf is a scroll pane:
        //bottomHalf.setMinimumSize(new Dimension(400, 50));
        bottomHalf.setPreferredSize(new Dimension(450, 135));
        splitPane.add(bottomHalf);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("ListSelectionDemo");
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        frame.setContentPane(new ListSelectionDemo());
        frame.pack();
        frame.setVisible(true);
    }

    class SharedListSelectionHandler implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent e) { 
            ListSelectionModel lsm = (ListSelectionModel)e.getSource();

            int firstIndex = e.getFirstIndex();
            int lastIndex = e.getLastIndex();
            boolean isAdjusting = e.getValueIsAdjusting(); 
            output.append("Event for indexes "
                          + firstIndex + " - " + lastIndex
                          + "; isAdjusting is " + isAdjusting
                          + "; selected indexes:");

            if (lsm.isSelectionEmpty()) {
                output.append(" <none>");
            } else {
                // Find out which indexes are selected.
                int minIndex = lsm.getMinSelectionIndex();
                int maxIndex = lsm.getMaxSelectionIndex();
                for (int i = minIndex; i <= maxIndex; i++) {
                    if (lsm.isSelectedIndex(i)) {
                        output.append(" " + i);
                    }
                }
            }
            output.append(newline);
        }
    }
}
