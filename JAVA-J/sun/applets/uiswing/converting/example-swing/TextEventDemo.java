/*
 * Swing 1.1 version (compatible with both JDK 1.1 and Java 2).
 */

import javax.swing.*;
import javax.swing.text.*;
import javax.swing.event.*;

import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import java.awt.event.*;

public class TextEventDemo extends JApplet 
                           implements ActionListener {
    JTextField textField;
    JTextArea textArea;
    JTextArea displayArea;

    public void init() {
        JButton button = new JButton("Clear");
        button.addActionListener(this);

        textField = new JTextField(20);
        textField.addActionListener(new MyTextActionListener());
        textField.getDocument().addDocumentListener(
            new MyDocumentListener("Text Field"));

        textArea = new JTextArea();
        textArea.getDocument().addDocumentListener(
            new MyDocumentListener("Text Area"));
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
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;

        /*
         * Hack to get around gridbag's refusal to allow
         * multi-row components in anything but the left column.
         */
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());
        leftPanel.add(textField, BorderLayout.NORTH);
        leftPanel.add(scrollPane, BorderLayout.CENTER);

        c.gridheight = 2;
        gridbag.setConstraints(leftPanel, c);
        contentPane.add(leftPanel);

        c.weighty = 1.0;
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.gridheight = 1;
        gridbag.setConstraints(displayScrollPane, c);
        contentPane.add(displayScrollPane);

        c.weighty = 0.0;
        gridbag.setConstraints(button, c);
        contentPane.add(button);

        textField.requestFocus();

        setContentPane(contentPane);
    }

    class MyDocumentListener implements DocumentListener {
        String preface;
        String newline;

        public MyDocumentListener(String source) {
            newline = System.getProperty("line.separator");
            preface = source
                      + " text value changed."
                      + newline
                      + "   First 10 characters: \"";
        }

        public void insertUpdate(DocumentEvent e) {
            update(e);
        }

        public void removeUpdate(DocumentEvent e) {
            update(e);
        }
        public void changedUpdate(DocumentEvent e) {
            //You don't get these with a plain text component.
        }
        public void update(DocumentEvent e) {
            Document doc = (Document)e.getDocument();
            int length = doc.getLength();
            String s = null;
            try {
                s = doc.getText(0, (length > 10) ? 10 : length);
            } catch (BadLocationException ex) {
            }
            displayArea.append(preface + s + "\"" + newline);
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
