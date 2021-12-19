/**
  * This applet is the same in 1.1 as in 1.0.
  *
  * @author  Marianne Mueller
  * @author  Kathy Walrath
  */

import java.awt.*;
import java.applet.*;

public class GetOpenProperties extends Applet 
                               implements Runnable {
    String[] propertyNames = {"file.separator",
                              "line.separator",
                              "path.separator",
                              "java.class.version",
                              "java.vendor",
                              "java.vendor.url",
                              "java.version",
                              "os.name",
                              "os.arch",
                              "os.version"};
    final int numProperties = propertyNames.length;
    Label[] values;

    public void init() {
        //Set up the layout.
        GridBagLayout gridbag = new GridBagLayout();
        setLayout(gridbag);
        GridBagConstraints labelConstraints = 
                new GridBagConstraints();
        GridBagConstraints valueConstraints = 
                new GridBagConstraints();
        labelConstraints.anchor = GridBagConstraints.WEST;
        labelConstraints.ipadx = 10;
        valueConstraints.fill = GridBagConstraints.HORIZONTAL;
        valueConstraints.gridwidth = GridBagConstraints.REMAINDER;
        valueConstraints.weightx = 1.0; //Extra space to values column.

        //Set up the Label arrays.
        Label[] names = new Label[numProperties];
        values = new Label[numProperties];
        String firstValue = "not read yet";
 
        for (int i = 0; i < numProperties; i++) {
            names[i] = new Label(propertyNames[i]);
            gridbag.setConstraints(names[i], labelConstraints);
            add(names[i]);

            values[i] = new Label(firstValue);
            gridbag.setConstraints(values[i], valueConstraints);
            add(values[i]);
        }

        new Thread(this, "Loading System Properties").start();
    }

    /*
     * This method runs in a separate thread, loading 
     * properties one by one.
     */
    public void run() {
        String value = null;

        Thread.currentThread().setPriority(Thread.MIN_PRIORITY);

        //Pause to let the reader see the default strings.
        pause(3000);

        for (int i = 0; i < numProperties; i++) {
            //Pause for dramatic effect.
            pause(250);

            try {
                value = System.getProperty(propertyNames[i]);
                values[i].setText(value);
            } catch (SecurityException e) {
                values[i].setText("Could not read: "
                                  + "SECURITY EXCEPTION!");
            }
        }
    }

    synchronized void pause(int millis) {
        try {
            wait(millis);
        } catch (InterruptedException e) {
        }
    }
}
