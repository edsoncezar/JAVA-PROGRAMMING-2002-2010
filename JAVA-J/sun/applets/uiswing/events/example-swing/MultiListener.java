/*
 * Swing version
 */

import javax.swing.*;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Dimension;
import java.awt.Color;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MultiListener extends JApplet 
                           implements ActionListener {
    JTextArea topTextArea;
    JTextArea bottomTextArea;
    JButton button1, button2;
    final static String newline = "\n";

    public void init() {
        JLabel l = null;

        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        JPanel contentPane = new JPanel();
        contentPane.setLayout(gridbag);
        contentPane.setBorder(BorderFactory.createCompoundBorder(
                                BorderFactory.createMatteBorder(
                                                1,1,2,2,Color.black),
                                BorderFactory.createEmptyBorder(5,5,5,5)));

        c.fill = GridBagConstraints.BOTH;
        c.gridwidth = GridBagConstraints.REMAINDER;
        l = new JLabel("What MultiListener hears:");
        gridbag.setConstraints(l, c);
        contentPane.add(l);

        c.weighty = 1.0;
        topTextArea = new JTextArea();
        topTextArea.setEditable(false);
        JScrollPane topScrollPane = new JScrollPane(topTextArea);
        Dimension preferredSize = new Dimension(200, 75);
        topScrollPane.setPreferredSize(preferredSize);
        gridbag.setConstraints(topScrollPane, c);
        contentPane.add(topScrollPane);

        c.weightx = 0.0;
        c.weighty = 0.0;
        l = new JLabel("What Eavesdropper hears:");
        gridbag.setConstraints(l, c);
        contentPane.add(l);

        c.weighty = 1.0;
        bottomTextArea = new JTextArea();
        bottomTextArea.setEditable(false);
        JScrollPane bottomScrollPane = new JScrollPane(bottomTextArea);
        bottomScrollPane.setPreferredSize(preferredSize);
        gridbag.setConstraints(bottomScrollPane, c);
        contentPane.add(bottomScrollPane);

        c.weightx = 1.0;
        c.weighty = 0.0;
        c.gridwidth = 1;
        c.insets = new Insets(10, 10, 0, 10);
        button1 = new JButton("Blah blah blah");
        gridbag.setConstraints(button1, c);
        contentPane.add(button1);

        c.gridwidth = GridBagConstraints.REMAINDER;
        button2 = new JButton("You don't say!");
        gridbag.setConstraints(button2, c);
        contentPane.add(button2);

        button1.addActionListener(this);
        button2.addActionListener(this);

        button2.addActionListener(new Eavesdropper(bottomTextArea));

        setContentPane(contentPane);
    }

    public void actionPerformed(ActionEvent e) {
        topTextArea.append(e.getActionCommand() + newline);
    }
}

class Eavesdropper implements ActionListener {
    JTextArea myTextArea;
    public Eavesdropper(JTextArea ta) {
        myTextArea = ta;
    }

    public void actionPerformed(ActionEvent e) {
        myTextArea.append(e.getActionCommand()
                        + MultiListener.newline);
    }
}
