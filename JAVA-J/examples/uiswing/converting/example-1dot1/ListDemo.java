/*
 * 1.1 version.
 */

import java.awt.*;
import java.awt.event.*;
import java.applet.Applet;

public class ListDemo extends Applet
                      implements ActionListener,
                                 ItemListener {
    TextArea output;
    List spanish, italian; 
    String newline;

    public void init() {
        newline = System.getProperty("line.separator");

        //Build first list, which allows multiple selections.
        spanish = new List(4, true); //prefer 4 items visible
        spanish.add("uno");
        spanish.add("dos");
        spanish.add("tres");
        spanish.add("cuatro");
        spanish.add("cinco");
        spanish.add("seis");
        spanish.add("siete");
        spanish.addActionListener(this);
        spanish.addItemListener(this);

        //Build second list, which allows one selection at a time.
        italian = new List(); //Defaults to none visible, only one selectable
        italian.add("uno");
        italian.add("due");
        italian.add("tre");
        italian.add("quattro");
        italian.add("cinque");
        italian.add("sei");
        italian.add("sette");
        italian.addActionListener(this);
        italian.addItemListener(this);

        //Add lists to the Applet. 
        GridBagLayout gridBag = new GridBagLayout();
        setLayout(gridBag);

        //Can't put text area on right due to GBL bug
        //(can't span rows in any column but the first). 
        output = new TextArea(10, 40);
        output.setEditable(false);
        GridBagConstraints tc = new GridBagConstraints();
        tc.fill = GridBagConstraints.BOTH;
        tc.weightx = 1.0;
        tc.weighty = 1.0;
        tc.gridheight = 2;
        gridBag.setConstraints(output, tc);
        add(output);

        GridBagConstraints lc = new GridBagConstraints();
        lc.fill = GridBagConstraints.VERTICAL;
        lc.gridwidth = GridBagConstraints.REMAINDER; //end row
        gridBag.setConstraints(spanish, lc);
        add(spanish);
        gridBag.setConstraints(italian, lc);
        add(italian);
    }

    public void actionPerformed(ActionEvent e) {
        List list = (List)(e.getSource());
        String language = (list == spanish) ?
                          "Spanish" : "Italian";
        output.append("Action event occurred on \""
                          + list.getSelectedItem()  + "\" in "
                          + language + "." + newline);
    }

    public void itemStateChanged(ItemEvent e) {
        List list = (List)(e.getItemSelectable());
        String language = (list == spanish) ?
                          "Spanish" : "Italian";

        int index = ((Integer)(e.getItem())).intValue();
        if (e.getStateChange() == ItemEvent.SELECTED) {
            output.append("Select event occurred on item #"
                          + index + " (\""
                          + list.getItem(index) + "\") in "
                          + language + "." + newline);
        } else { //the item was deselected
            output.append("Deselect event occurred on item #"
                          + index + " (\""
                          + list.getItem(index) + "\") in "
                          + language + "." + newline);
        }
    }
}
