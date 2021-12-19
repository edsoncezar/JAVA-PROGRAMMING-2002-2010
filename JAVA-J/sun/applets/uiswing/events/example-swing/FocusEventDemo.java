/*
 * Swing version
 */

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import java.util.Vector;

import java.awt.event.FocusListener;
import java.awt.event.FocusEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;

public class FocusEventDemo extends JApplet
                            implements FocusListener,
                                       ActionListener {

    JTextArea display;
    FocusWindow window;
    JButton b1, b2;
    static final String SHOW = "show";
    static final String CLEAR = "clear";
    static final String newline = "\n";

    public void init() {
        b1 = new JButton("Click to bring up a window.");
        b1.setActionCommand(SHOW);
        b1.addActionListener(this);

        b2 = new JButton("Click to clear the display.");
        b2.setActionCommand(CLEAR);
        b2.addActionListener(this);

        display = new JTextArea();
        display.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(display);
        scrollPane.setPreferredSize(new Dimension(375, 125));
                                
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(b1, BorderLayout.NORTH);
        contentPane.add(scrollPane, BorderLayout.CENTER);
        contentPane.add(b2, BorderLayout.SOUTH);
        setContentPane(contentPane);

        //Create but don't show window.
        window = new FocusWindow(this);
    }

    public void stop() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                window.setVisible(false);
            }
        });
    }

    public void focusGained(FocusEvent e) {
        displayMessage("Focus gained", e);
    }

    public void focusLost(FocusEvent e) {
        displayMessage("Focus lost", e);
    }

    void displayMessage(String prefix, FocusEvent e) {
        display.append(prefix
                       + ": "
                       + e.getComponent()
                       + newline); 
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand() == SHOW) {
            window.pack();
            window.setVisible(true);
        } else { //CLEAR
            display.setText("");
        }
    }
}

class FocusWindow extends JFrame {
    public FocusWindow(FocusListener listener) {
        super("Focus Event Window");
        //We'll use the default close operation -- hiding.

        JPanel contentPane = new JPanel();
        this.addFocusListener(listener);

        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        contentPane.setLayout(gridbag);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;  //Make column as wide as possible.
        JTextField textField = new JTextField("A TextField");
        textField.setMargin(new Insets(0,2,0,2));
        textField.addFocusListener(listener);
        gridbag.setConstraints(textField, c);
        contentPane.add(textField);

        c.weightx = 0.1;  //Widen every other column a bit, when possible. 
        c.fill = GridBagConstraints.NONE;
        JLabel label = new JLabel("A Label");
        label.setBorder(BorderFactory.createEmptyBorder(0,5,0,5));
        label.addFocusListener(listener);
        gridbag.setConstraints(label, c);
        contentPane.add(label);

        //We'll add a focus listener to a choice, but since it's
        //typically implemented as a compound component, we aren't
        //likely to get any events.
        String choiceprefix = "Choice item #";
        final int numItems = 10;
        Vector vector = new Vector(numItems);
        for (int i = 0; i < numItems; i++) {
            vector.addElement(choiceprefix + i);
        }
        JComboBox choice = new JComboBox(vector);
        choice.addFocusListener(listener);
        gridbag.setConstraints(choice, c);
        contentPane.add(choice);

        c.gridwidth = GridBagConstraints.REMAINDER;
        JButton button = new JButton("A Button");
        button.addFocusListener(listener);
        gridbag.setConstraints(button, c);
        contentPane.add(button);

        c.weighty = 1.0;   //Make this row as tall as possible.
        c.weightx = 0.0;   
        c.fill = GridBagConstraints.BOTH;
        String listprefix = "List item #";
        Vector listVector = new Vector(numItems);
        for (int i = 0; i < numItems; i++) {
            listVector.addElement(listprefix + i);
        }
        JList list = new JList(listVector);
        JScrollPane scrollPane = new JScrollPane(list);
        list.addFocusListener(listener);
        gridbag.setConstraints(scrollPane, c);
        contentPane.add(scrollPane);

        setContentPane(contentPane);
    }
}
