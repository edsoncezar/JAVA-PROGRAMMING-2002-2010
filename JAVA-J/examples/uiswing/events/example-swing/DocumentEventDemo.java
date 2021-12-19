/*
 * Swing version
 */

import javax.swing.*;
import javax.swing.text.*;
import javax.swing.event.*;

import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import java.awt.event.*;

public class DocumentEventDemo extends JApplet 
                               implements ActionListener {
    JTextField textField;
    JTextArea textArea;
    JTextArea displayArea;

    public void init() {
        JButton button = new JButton("Clear");
        button.addActionListener(this);

        textField = new JTextField(20);
        textField.addActionListener(new MyTextActionListener());
        textField.getDocument().addDocumentListener(new MyDocumentListener());
        textField.getDocument().putProperty("name", "Text Field");

        textArea = new JTextArea();
        textArea.getDocument().addDocumentListener(new MyDocumentListener());
        textArea.getDocument().putProperty("name", "Text Area");

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(200, 75));

        displayArea = new JTextArea();
        displayArea.setEditable(false);
        JScrollPane displayScrollPane = new JScrollPane(displayArea);
        displayScrollPane.setPreferredSize(new Dimension(200, 75));

        JPanel contentPane = new JPanel();
        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        contentPane.setLayout(gridbag);

        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1.0;
        c.fill = GridBagConstraints.HORIZONTAL;
        gridbag.setConstraints(textField, c);
        contentPane.add(textField);

        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 0.0;
        c.gridheight = 2;
        c.fill = GridBagConstraints.BOTH;
        gridbag.setConstraints(scrollPane, c);
        contentPane.add(scrollPane);

        c.gridx = 1;
        c.gridy = 0;
        c.weightx = 1.0;
        c.weighty = 1.0;
        gridbag.setConstraints(displayScrollPane, c);
        contentPane.add(displayScrollPane);

        c.gridx = 1;
        c.gridy = 2;
        c.weightx = 0.0;
        c.gridheight = 1;
        c.weighty = 0.0;
        c.fill = GridBagConstraints.HORIZONTAL;
        gridbag.setConstraints(button, c);
        contentPane.add(button);

        setContentPane(contentPane);
    }

    class MyDocumentListener implements DocumentListener {
        final String newline = "\n";

        public void insertUpdate(DocumentEvent e) {
            updateLog(e, "inserted into");
        }
        public void removeUpdate(DocumentEvent e) {
            updateLog(e, "removed from");
        }
        public void changedUpdate(DocumentEvent e) {
            //Plain text components don't fire these events.
        }

        public void updateLog(DocumentEvent e, String action) {
            Document doc = (Document)e.getDocument();
            int changeLength = e.getLength();
            displayArea.append(
                changeLength + " character"
              + ((changeLength == 1) ? " " : "s ")
              + action + " " + doc.getProperty("name") + "."
              + newline
              + "  Text length = " + doc.getLength() + newline);
        }
    }

    class MyTextActionListener implements ActionListener {
        /** Handle the text field Return. */
        public void actionPerformed(ActionEvent e) {
            int selStart = textArea.getSelectionStart();
            int selEnd = textArea.getSelectionEnd();

            textArea.replaceRange(textField.getText(),
                                  selStart, selEnd);
            textField.selectAll();
        }
    }

    /** Handle button click. */
    public void actionPerformed(ActionEvent e) {
        displayArea.setText("");
        textField.requestFocus();
    }
}
