/*
 * Swing version
 */

import javax.swing.*;

import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.event.*;

public class ComponentEventDemo extends JApplet
                                implements ComponentListener,
                                           ActionListener {
    JTextArea display;
    JFrame aFrame;
    public boolean showIt = false;
    final static String SHOW = "show";
    final static String CLEAR = "clear";
    String newline = "\n";

    public void init() {
        display = new JTextArea();
        display.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(display);
        scrollPane.setPreferredSize(new Dimension(200, 75));
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        JButton b1 = new JButton("Start playing...");
        b1.setActionCommand(SHOW);
        b1.addActionListener(this);
        getContentPane().add(b1, BorderLayout.NORTH);

        JButton b2 = new JButton("Clear");
        b2.setActionCommand(CLEAR);
        b2.addActionListener(this);
        getContentPane().add(b2, BorderLayout.SOUTH);

        aFrame = new JFrame("A Frame");
        ComponentPanel p = new ComponentPanel(this);
        aFrame.addComponentListener(this);
        p.addComponentListener(this);
        aFrame.getContentPane().add(p, BorderLayout.CENTER);
        aFrame.pack();

        aFrame.addWindowListener(new WindowAdapter() {
            // This event handler is executed before the
            // default close operation (hide) is applied.
            public void windowClosing(WindowEvent e) {
                showIt = false;
            }
        });
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand() == SHOW) {
            showIt = true;
            aFrame.setVisible(true);
        } else { //CLEAR
            display.setText("");
        }
    }

    public void stop() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                aFrame.setVisible(false);
            }
        });
    }

    public void start() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                if (showIt) {
                    aFrame.setVisible(true);
                }
            }
        });
    }

    protected void displayMessage(String message) {
        display.append(message + newline);
    }

    public void componentHidden(ComponentEvent e) {
        displayMessage("componentHidden event from "
                       + e.getComponent().getClass().getName());
    }

    public void componentMoved(ComponentEvent e) {
        displayMessage("componentMoved event from "
                       + e.getComponent().getClass().getName());
    }

    public void componentResized(ComponentEvent e) {
        displayMessage("componentResized event from "
                       + e.getComponent().getClass().getName());
    }

    public void componentShown(ComponentEvent e) {
        displayMessage("componentShown event from "
                       + e.getComponent().getClass().getName());
    }
}

class ComponentPanel extends JPanel 
                     implements ItemListener {
    JLabel label;
    JCheckBox checkbox;

    ComponentPanel(ComponentEventDemo listener) {
        super(new BorderLayout());

        label = new JLabel("This is a Label", JLabel.CENTER);
        add(label, BorderLayout.CENTER);

        checkbox = new JCheckBox("Label visible", true);
        checkbox.addItemListener(this);
        add(checkbox, BorderLayout.SOUTH);

        label.addComponentListener(listener);
        checkbox.addComponentListener(listener);
    }

    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            label.setVisible(true);

            //Need to revalidate and repaint, or else the label
            //will probably be drawn in the wrong place.
            label.revalidate();
            label.repaint();

        } else {
            label.setVisible(false);
        }
    }
}
