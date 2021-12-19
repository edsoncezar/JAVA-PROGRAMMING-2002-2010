import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.net.URL;

public class ListDataEventDemo extends JApplet 
                               implements ListSelectionListener {
    private JList list;
    private DefaultListModel listModel;

    private static final String addString = "Add";
    private static final String deleteString = "Delete";
    private static final String upString = "Move up";
    private static final String downString = "Move down";

    private JButton addButton;
    private JButton deleteButton;
    private JButton upButton;
    private JButton downButton;

    private JTextField nameField;
    private JTextArea log;
    static private String newline = "\n";

    public void init() {
        //Create and populate the list model.
        listModel = new DefaultListModel();
        listModel.addElement("Whistler, Canada");
        listModel.addElement("Jackson Hole, Wyoming");
        listModel.addElement("Squaw Valley, California");
        listModel.addElement("Telluride, Colorado");
        listModel.addElement("Taos, New Mexico");
        listModel.addElement("Snowbird, Utah");
        listModel.addElement("Chamonix, France");
        listModel.addElement("Banff, Canada");
        listModel.addElement("Arapahoe Basin, Colorado");
        listModel.addElement("Kirkwood, California");
        listModel.addElement("Sun Valley, Idaho");
        listModel.addListDataListener(new MyListDataListener());

        //Create the list and put it in a scroll pane.
        list = new JList(listModel);
        list.setSelectionMode(
            ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);
        JScrollPane listScrollPane = new JScrollPane(list);

        //Create the list modifying buttons.
        addButton = new JButton(addString);
        addButton.setActionCommand(addString);
        addButton.addActionListener(new AddButtonListener());

        deleteButton = new JButton(deleteString);
        deleteButton.setActionCommand(deleteString);
        deleteButton.addActionListener(
            new DeleteButtonListener());

        upButton = new JButton(
            new ImageIcon(getURL("images/up.gif")));
        upButton.setMargin(new Insets(0,0,0,0));
        upButton.setActionCommand(upString);
        upButton.addActionListener(new UpDownListener());

        downButton = new JButton(
            new ImageIcon(getURL("images/down.gif")));
        downButton.setMargin(new Insets(0,0,0,0));
        downButton.setActionCommand(downString);
        downButton.addActionListener(new UpDownListener());

        JPanel upDownPanel = new JPanel(new GridLayout(2, 1));
        upDownPanel.add(upButton);
        upDownPanel.add(downButton);

        //Create the text field for entering new names.
        nameField = new JTextField(15);
        nameField.addActionListener(new AddButtonListener());
        String name = listModel.getElementAt(list.getSelectedIndex())
                               .toString();
        nameField.setText(name);

        //Create a control panel (uses the default FlowLayout).
        JPanel buttonPane = new JPanel();
        buttonPane.add(nameField);
        buttonPane.add(addButton);
        buttonPane.add(deleteButton);
        buttonPane.add(upDownPanel);

        //Create the log for reporting list data events.
        log = new JTextArea(10, 20);
        JScrollPane logScrollPane = new JScrollPane(log);

        //Create a split pane for the log and the list.
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                                        listScrollPane, logScrollPane);

        Container contentPane = getContentPane();
        contentPane.add(buttonPane, BorderLayout.NORTH);
        contentPane.add(splitPane, BorderLayout.CENTER);
    }

    class MyListDataListener implements ListDataListener {
        public void contentsChanged(ListDataEvent e) {
            log.append("contentsChanged: " + e.getIndex0() +
                       ", " + e.getIndex1() + newline); 
        }
        public void intervalAdded(ListDataEvent e) {
            log.append("intervalAdded: " + e.getIndex0() +
                       ", " + e.getIndex1() + newline); 
        }
        public void intervalRemoved(ListDataEvent e) {
            log.append("intervalRemoved: " + e.getIndex0() +
                       ", " + e.getIndex1() + newline); 
        }
    }

    class DeleteButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            /* 
             * This method can be called only if
             * there's a valid selection,
             * so go ahead and remove whatever's selected.
             */

            ListSelectionModel lsm = list.getSelectionModel();
            int firstSelected = lsm.getMinSelectionIndex();
            int lastSelected = lsm.getMaxSelectionIndex();
            listModel.removeRange(firstSelected, lastSelected);

            int size = listModel.size();

            if (size == 0) {
            //List is empty: disable delete, up, and down buttons.
                deleteButton.setEnabled(false);
                upButton.setEnabled(false);
                downButton.setEnabled(false);

            } else {
            //Adjust the selection.
                if (firstSelected == listModel.getSize()) {
                //Removed item in last position.
                    firstSelected--;
                }
                list.setSelectedIndex(firstSelected);
            }
        }
    }

    /** A listener shared by the text field and add button. */
    class AddButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (nameField.getText().equals("")) {
            //User didn't type in a name...
                Toolkit.getDefaultToolkit().beep();
                return;
            }

            int index = list.getSelectedIndex();
            int size = listModel.getSize();

            //If no selection or if item in last position is selected,
            //add the new one to end of list, and select new one.
            if (index == -1 || (index+1 == size)) {
                listModel.addElement(nameField.getText());
                list.setSelectedIndex(size);

            //Otherwise insert the new one after the current selection,
            //and select new one.
            } else {
                listModel.insertElementAt(nameField.getText(), index+1);
                list.setSelectedIndex(index+1);
            }
        }
    }

    //Listen for clicks on the up and down arrow buttons.
    class UpDownListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //This method can be called only when
            //there's a valid selection,
            //so go ahead and move the list item.
            int moveMe = list.getSelectedIndex();

            if (e.getActionCommand().equals(upString)) {
            //UP ARROW BUTTON
                if (moveMe != 0) {     
                //not already at top
                    swap(moveMe, moveMe-1);
                    list.setSelectedIndex(moveMe-1);
                    list.ensureIndexIsVisible(moveMe-1);
                }
            } else {
            //DOWN ARROW BUTTON
                if (moveMe != listModel.getSize()-1) {
                //not already at bottom
                    swap(moveMe, moveMe+1);
                    list.setSelectedIndex(moveMe+1);
                    list.ensureIndexIsVisible(moveMe+1);
                }
            }
        }
    }

    //Swap two elements in the list.
    private void swap(int a, int b) {
        Object aObject = listModel.getElementAt(a);
        Object bObject = listModel.getElementAt(b);
        listModel.set(a, bObject);
        listModel.set(b, aObject);
    }

    //Listener method for list selection changes.
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting() == false) {

            if (list.getSelectedIndex() == -1) {
            //No selection: disable delete, up, and down buttons.
                deleteButton.setEnabled(false);
                upButton.setEnabled(false);
                downButton.setEnabled(false);
                nameField.setText("");

            } else if (list.getSelectedIndices().length > 1) {
            //Multiple selection: disable up and down buttons.
                deleteButton.setEnabled(true);
                upButton.setEnabled(false);
                downButton.setEnabled(false);

            } else {
            //Single selection: permit all operations.
                deleteButton.setEnabled(true);
                upButton.setEnabled(true);
                downButton.setEnabled(true);
                nameField.setText(list.getSelectedValue().toString());
            }
        }
    }

    protected URL getURL(String filename) {
        URL codeBase = this.getCodeBase();
        URL url = null;

        try {
            url = new URL(codeBase, filename);
        } catch (java.net.MalformedURLException e) {
            System.out.println("Couldn't create image: badly specified URL");
            return null;
        }

        return url;
    }
}
