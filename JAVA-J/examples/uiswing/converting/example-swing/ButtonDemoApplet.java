/*
 * Swing 1.1 version (compatible with both JDK 1.1 and Java 2).
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;

public class ButtonDemoApplet extends JApplet
                              implements ActionListener {

    protected JButton b1, b2, b3;

    protected static final String DISABLE = "disable";
    protected static final String ENABLE = "enable";

    protected String leftButtonFilename = "images/right.gif";
    protected String middleButtonFilename = "images/middle.gif";
    protected String rightButtonFilename = "images/left.gif";

    public void init() {
        ImageIcon leftButtonIcon = new ImageIcon(
                                        getURL(leftButtonFilename));
        ImageIcon middleButtonIcon = new ImageIcon(
                                        getURL(middleButtonFilename));
        ImageIcon rightButtonIcon = new ImageIcon(
                                        getURL(rightButtonFilename));

        b1 = new JButton("Disable middle button", leftButtonIcon);
        b1.setVerticalTextPosition(AbstractButton.CENTER);
        b1.setHorizontalTextPosition(AbstractButton.LEFT);
        b1.setMnemonic(KeyEvent.VK_D);
        b1.setActionCommand(DISABLE);

        b2 = new JButton("Middle button", middleButtonIcon);
        b2.setVerticalTextPosition(AbstractButton.BOTTOM);
        b2.setHorizontalTextPosition(AbstractButton.CENTER);
        b2.setMnemonic(KeyEvent.VK_M);

        b3 = new JButton("Enable middle button", rightButtonIcon);
        //Use the default text position of CENTER, RIGHT.
        b3.setMnemonic(KeyEvent.VK_E);
        b3.setActionCommand(ENABLE);
        b3.setEnabled(false);

        //Listen for actions on buttons 1 and 3.
        b1.addActionListener(this);
        b3.addActionListener(this);

        //Add Components to a JPanel, using the default FlowLayout. 
        JPanel pane = new JPanel();
        pane.add(b1);
        pane.add(b2);
        pane.add(b3);

        //Make the JPanel this applet's content pane.
        setContentPane(pane);
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
    
    protected URL getURL(String filename) {
        URL codeBase = getCodeBase();
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
