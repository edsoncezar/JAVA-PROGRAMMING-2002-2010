/*
 * 1.0 code (remains the same in 1.1).
 */

import java.applet.Applet;
import java.awt.TextField;

public class ScrollingSimple extends Applet {

    TextField field;

    public void init() {
        //Create the text field and make it uneditable.
        field = new TextField();
        field.setEditable(false);

        //Set the layout manager so that the text field will be
        //as wide as possible.
        setLayout(new java.awt.GridLayout(1,0));

        //Add the text field to the applet.
        add(field);
        validate();  //this shouldn't be necessary

        addItem("initializing... ");
    }

    public void start() {
        addItem("starting... ");
    }

    public void stop() {
        addItem("stopping... ");
    }

    public void destroy() {
        addItem("preparing for unloading...");
    }

    void addItem(String newWord) {
        String t = field.getText();
        System.out.println(newWord);
        field.setText(t + newWord);
    }
}
