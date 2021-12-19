/*
 * 1.1 version.
 */

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.applet.Applet;

public class ConversionPanel extends Panel 
                      implements ActionListener,
                                 AdjustmentListener,
                                 ItemListener {
    TextField textField;
    Choice unitChooser;
    Scrollbar slider;
    int max = 10000;
    int block = 100;
    Converter controller;
    Unit[] units;

    ConversionPanel(Converter myController, String myTitle, Unit[] myUnits) {
        //Initialize this ConversionPanel to use a GridBagLayout.
        GridBagConstraints c = new GridBagConstraints();
        GridBagLayout gridbag = new GridBagLayout();
        setLayout(gridbag);

        //Save arguments in instance variables.
        controller = myController;
        units = myUnits;

        //Set up default layout constraints.
        c.fill = GridBagConstraints.HORIZONTAL;

        //Add the label.  It displays this panel's title, centered.
        Label label = new Label(myTitle, Label.CENTER); 
        c.gridwidth = GridBagConstraints.REMAINDER; //It ends a row.
        gridbag.setConstraints(label, c);
        add(label);

        //Add the text field.  It initially displays "0" and needs
        //to be at least 10 columns wide.
        textField = new TextField("0", 10); 
        c.weightx = 1.0;  //Use maximum horizontal space...
        c.gridwidth = 1; //The default value.
        gridbag.setConstraints(textField, c);
        add(textField);
        textField.addActionListener(this);

        //Add the pop-up list (Choice).
        unitChooser = new Choice(); 
        for (int i = 0; i < units.length; i++) { //Populate it.
            unitChooser.add(units[i].description);
        }
        c.weightx = 0.0; //The default value.
        c.gridwidth = GridBagConstraints.REMAINDER; //End a row.
        gridbag.setConstraints(unitChooser, c);
        add(unitChooser);
        unitChooser.addItemListener(this);

        //Add the slider.  It's horizontal, and it has the maximum
        //value specified by the instance variable max.  Its initial 
        //and minimum values are the default (0).  A click increments
        //the value by block units.
        slider = new Scrollbar(Scrollbar.HORIZONTAL);
        slider.setMaximum(max + 10);
        slider.setBlockIncrement(block);
        c.gridwidth = 1; //The default value.
        gridbag.setConstraints(slider, c);
        add(slider);
        slider.addAdjustmentListener(this);
    }

    /** 
     * Returns the multiplier (units/meter) for the currently
     * selected unit of measurement.
     */
    double getMultiplier() {
        int i = unitChooser.getSelectedIndex();
        return units[i].multiplier;
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
     * We add more pixels to the right, to work around a
     * Choice bug.
     */
    public Insets getInsets() {
        return new Insets(5,5,5,8);
    }

    /**
     * Gets the current value in the text field.
     * It's guaranteed to be the same as the value
     * in the scroller (subject to rounding, of course).
     */
    double getValue() {
        double f;
        try {
            f = (double)Double.valueOf(textField.getText()).doubleValue(); 
        } catch (java.lang.NumberFormatException e) {
            f = 0.0;
        }
        return f;
    }

    public void actionPerformed(ActionEvent e) {
        setSliderValue(getValue());
        controller.convert(this);
    }

    public void itemStateChanged(ItemEvent e) {
        controller.convert(this);
    }

    /** Respond to the slider. */
    public void adjustmentValueChanged(AdjustmentEvent e) {
        textField.setText(String.valueOf(e.getValue()));
        controller.convert(this);
    }

    /** Set the values in the slider and text field. */
    void setValue(double f) {
        setSliderValue(f);
        textField.setText(String.valueOf((float)f));
    }

    /** Set the slider value. */
    void setSliderValue(double f) {
        int sliderValue = (int)f;

        if (sliderValue > max)
               sliderValue = max;
        if (sliderValue < 0)
            sliderValue = 0;
        slider.setValue(sliderValue);
    }
}
