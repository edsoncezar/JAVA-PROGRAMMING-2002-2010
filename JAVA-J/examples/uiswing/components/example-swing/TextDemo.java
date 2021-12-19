/*
 * Swing Version
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TextDemo extends JApplet implements ActionListener {
    JTextField textField;
    JTextArea textArea;
    String newline = "\n";

    //Hack to avoid annoying error message (1.1).
    public TextDemo() {
        getRootPane().putClientProperty("defeatSystemEventQueueCheck",
                                        Boolean.TRUE);
    }

    public void init() {
        textField = new JTextField(20);
        textField.addActionListener(this);

        textArea = new JTextArea(5, 20);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea,
                                       JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                                       JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        //Add Components to the Applet. 
        GridBagLayout gridBag = new GridBagLayout();
        Container contentPane = getContentPane();
        contentPane.setLayout(gridBag);
        GridBagConstraints c = new GridBagConstraints();
        c.gridwidth = GridBagConstraints.REMAINDER;

        c.fill = GridBagConstraints.HORIZONTAL;
        gridBag.setConstraints(textField, c);
        contentPane.add(textField);

        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        c.weighty = 1.0;
        gridBag.setConstraints(scrollPane, c);
        contentPane.add(scrollPane);
    }

    public void actionPerformed(ActionEvent evt) {
        String text = textField.getText();
        textArea.append(text + newline);
        textField.selectAll();
    }
}
