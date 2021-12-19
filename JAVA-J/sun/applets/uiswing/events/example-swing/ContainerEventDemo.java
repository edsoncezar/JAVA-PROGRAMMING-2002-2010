/*
 * Swing version
 */

import javax.swing.*;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.Vector;

public class ContainerEventDemo extends JApplet 
                                implements ContainerListener,
                                           ActionListener {
    JTextArea display;
    JPanel buttonPanel;
    JButton addButton, removeButton, clearButton;
    Vector buttonList;
    static final String ADD = "add";
    static final String REMOVE = "remove";
    static final String CLEAR = "clear";
    static final String newline = "\n";

    public void init() {
        //Initialize an empty list of buttons.
        buttonList = new Vector(10, 10);

        //Create all the components.
        addButton = new JButton("Add a button");
        addButton.setActionCommand(ADD);
        addButton.addActionListener(this);

        removeButton = new JButton("Remove a button");
        removeButton.setActionCommand(REMOVE);
        removeButton.addActionListener(this);

        buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(200, 75));
        buttonPanel.addContainerListener(this);

        display = new JTextArea();
        display.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(display);
        scrollPane.setPreferredSize(new Dimension(200, 75));

        clearButton = new JButton("Clear text area");
        clearButton.setActionCommand(CLEAR);
        clearButton.addActionListener(this);

        //Lay out the components.
        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        JPanel contentPane = new JPanel();
        contentPane.setLayout(gridbag);
        c.fill = GridBagConstraints.BOTH; //Fill entire cell.

        c.weighty = 1.0;  //Button area and message area have equal height.
        c.gridwidth = GridBagConstraints.REMAINDER; //end of row
        gridbag.setConstraints(scrollPane, c);
        contentPane.add(scrollPane);

        c.weighty = 0.0;  
        gridbag.setConstraints(clearButton, c);
        contentPane.add(clearButton);

        c.weightx = 1.0;  //Add/remove buttons have equal width.
        c.gridwidth = 1;  //NOT end of row
        gridbag.setConstraints(addButton, c);
        contentPane.add(addButton);

        c.gridwidth = GridBagConstraints.REMAINDER; //end of row
        gridbag.setConstraints(removeButton, c);
        contentPane.add(removeButton);

        c.weighty = 1.0;  //Button area and message area have equal height.
        gridbag.setConstraints(buttonPanel, c);
        contentPane.add(buttonPanel);

        setContentPane(contentPane);
    }

    public void componentAdded(ContainerEvent e) {
        displayMessage(" added to ", e);
    }

    public void componentRemoved(ContainerEvent e) {
        displayMessage(" removed from ", e);
    }

    void displayMessage(String action, ContainerEvent e) {
        display.append(((JButton)e.getChild()).getText()
                       + " was"
                       + action
                       + e.getContainer().getClass().getName()
                       + newline);
    }

    /*
     * This could have been implemented as two or three
     * classes or objects, for clarity.
     */
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (command == ADD) {
            JButton newButton = new JButton("JButton #"
                                          + (buttonList.size() + 1));
            buttonList.addElement(newButton);
            buttonPanel.add(newButton);
            buttonPanel.revalidate(); //Make the button show up.

        } else if (command == REMOVE) {
            int lastIndex = buttonList.size() - 1;
            try {
                JButton nixedButton = (JButton)buttonList.elementAt(lastIndex);
                buttonPanel.remove(nixedButton);
                buttonList.removeElementAt(lastIndex);
                buttonPanel.revalidate(); //Make the button disappear.
                buttonPanel.repaint(); //Make the button disappear.
            } catch (ArrayIndexOutOfBoundsException exc) {}
        } else if (command == CLEAR) {
            display.setText("");
        }
    }
}
