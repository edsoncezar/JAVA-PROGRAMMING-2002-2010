/*
 * 1.1 version.
 */

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.applet.Applet;

public class ButtonDemoApplet extends Applet
                              implements ActionListener {

    protected Button b1, b2, b3;
    protected static final String DISABLE = "disable";
    protected static final String ENABLE = "enable";

    public void init() {
        b1 = new Button();
        b1.setLabel("Disable middle button");
        b1.setActionCommand(DISABLE);

        b2 = new Button("Middle button");

        b3 = new Button("Enable middle button");
        b3.setEnabled(false);
        b3.setActionCommand(ENABLE);

        //Listen for actions on buttons 1 and 3.
        b1.addActionListener(this);
        b3.addActionListener(this);

        //Add Components to the Applet, using the default FlowLayout. 
        add(b1);
        add(b2);
        add(b3);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(DISABLE)) {
            b2.setEnabled(false);
            b1.setEnabled(false);
            b3.setEnabled(true);
        } else {
            b2.setEnabled(true);
            b1.setEnabled(true);
            b3.setEnabled(false);
        }
    }
}
