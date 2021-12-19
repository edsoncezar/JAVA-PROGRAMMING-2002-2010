/* 
 * 1.1 version.
 */

import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Enumeration;

public class GetApplets extends Applet 
                        implements ActionListener {
    private TextArea textArea;
    private String newline;

    public void init() {
        Button b = new Button("Click to call getApplets()");
        b.addActionListener(this);

        setLayout(new BorderLayout());
        add("North", b);

        textArea = new TextArea(5, 40);
        textArea.setEditable(false);
        add("Center", textArea);

	newline = System.getProperty("line.separator");
    }

    public void actionPerformed(ActionEvent event) {
        printApplets();
    }

    public String getAppletInfo() {
        return "GetApplets by Kathy Walrath";
    }

    public void printApplets() {
        //Enumeration will contain all applets on this page
        //(including this one) that we can send messages to.
        Enumeration e = getAppletContext().getApplets();

        textArea.append("Results of getApplets():" + newline);

        while (e.hasMoreElements()) {
            Applet applet = (Applet)e.nextElement();
            String info = ((Applet)applet).getAppletInfo();
            if (info != null) {
                textArea.append("- " + info + newline);
            } else {
                textArea.append("- " 
				+ applet.getClass().getName() 
                                + newline);
            } 
        }
        textArea.append("________________________" 
                        + newline + newline);
    }
}
