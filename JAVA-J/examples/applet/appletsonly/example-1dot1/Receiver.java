/* 
 * 1.1 version.
 */

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class Receiver extends Applet 
                      implements ActionListener {
    private final String waitingMessage = 
            "Waiting for a message...           ";
    private Label label = new Label(waitingMessage, 
                                    Label.RIGHT);

    public void init() {
	Button button = new Button("Clear");
        add(label);
        add(button);
	button.addActionListener(this);
        add(new Label("(My name is " + getParameter("name") 
                      + ".)", 
                      Label.LEFT)); 
    }

    public void actionPerformed(ActionEvent event) {
        label.setText(waitingMessage);
    }

    public void processRequestFrom(String senderName) {
        label.setText("Received message from " 
                      + senderName + "!");
    }

    public void paint(Graphics g) {
        g.drawRect(0, 0, 
                   getSize().width - 1, getSize().height - 1);
    }

    public String getAppletInfo() {
        return "Receiver (named " + getParameter("name") + 
               ") by Kathy Walrath";
    }
}
