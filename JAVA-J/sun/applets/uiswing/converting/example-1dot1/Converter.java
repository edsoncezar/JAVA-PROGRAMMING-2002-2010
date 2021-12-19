/*
 * 1.1 version.
 */

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.applet.Applet;

public class Converter extends Applet {
    ConversionPanel metricPanel, usaPanel;
    Unit[] metricDistances = new Unit[3];
    Unit[] usaDistances = new Unit[4];

    /** 
     * Create the ConversionPanels (one for metric, another for U.S.).
     * I used "U.S." because although Imperial and U.S. distance
     * measurements are the same, this program could be extended to
     * include volume measurements, which aren't the same.
     */
    public void init() {
        //Use a GridLayout with 2 rows, as many columns as necessary,
        //and 5 pixels of padding around all edges of each cell.
        setLayout(new GridLayout(2,0,5,5));

        //Create Unit objects for metric distances, and then 
        //instantiate a ConversionPanel with these Units.
        metricDistances[0] = new Unit("Centimeters", 0.01);
        metricDistances[1] = new Unit("Meters", 1.0);
        metricDistances[2] = new Unit("Kilometers", 1000.0);
        metricPanel = new ConversionPanel(this, "Metric System",
                                            metricDistances);

        //Create Unit objects for U.S. distances, and then 
        //instantiate a ConversionPanel with these Units.
        usaDistances[0] = new Unit("Inches", 0.0254);
        usaDistances[1] = new Unit("Feet", 0.305);
        usaDistances[2] = new Unit("Yards", 0.914);
        usaDistances[3] = new Unit("Miles", 1613.0);
        usaPanel = new ConversionPanel(this, "U.S. System", usaDistances);

        //Add both ConversionPanels to the Converter.
        add(metricPanel);
        add(usaPanel);
    }

    /**
     * Does the conversion from metric to U.S., or vice versa, and
     * updates the appropriate ConversionPanel. 
     */
    void convert(ConversionPanel from) {
        ConversionPanel to;

        if (from == metricPanel)
            to = usaPanel;
        else
            to = metricPanel;

        double multiplier = from.getMultiplier() / to.getMultiplier();
        to.setValue(multiplier * from.getValue());
    }

    /** Draws a box around this panel. */
    public void paint(Graphics g) {
        Dimension d = getSize();
        g.drawRect(0,0, d.width - 1, d.height - 1);
    }
        
    /**
     * Puts a little breathing space between
     * the panel and its contents, which lets us draw a box
     * in the paint() method.
     */
    public Insets getInsets() {
        return new Insets(5,5,5,5);
    }

    /** Executed only when this program runs as an application. */
    public static void main(String[] args) {
        //Create a new window.
        Frame f = new Frame("Converter Applet/Application");
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        //Create a Converter instance.
        Converter converter = new Converter();

        //Initialize the Converter instance.
        converter.init();

        //Add the Converter to the window and display the window.
        f.add("Center", converter);
        f.pack();        //Resizes the window to its natural size.
        f.setVisible(true);
    }
}
