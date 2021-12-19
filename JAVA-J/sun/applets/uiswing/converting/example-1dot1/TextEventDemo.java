/*
 * 1.1 code.
 */

import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;

public class TextEventDemo extends Applet 
                           implements ActionListener {
    TextField textField;
    TextArea textArea;
    TextArea displayArea;

    public void init() {
        Button button = new Button("Clear");
        button.addActionListener(this);

        textField = new TextField(20);
        textField.addActionListener(new MyTextActionListener());
        textField.addTextListener(new MyTextListener("Text Field"));

        textArea = new TextArea(5, 20);
        textArea.addTextListener(new MyTextListener("Text Area"));

        displayArea = new TextArea(5, 20);
        displayArea.setEditable(false);

        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        setLayout(gridbag);
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;

        /*
         * Hack to get around gridbag's refusal to allow
         * multi-row components in anything but the left column.
         */
        Panel leftPanel = new Panel();
        leftPanel.setLayout(new BorderLayout());
        leftPanel.add("North", textField);
        leftPanel.add("Center", textArea);

        c.gridheight = 2;
        gridbag.setConstraints(leftPanel, c);
        add(leftPanel);

        c.weighty = 1.0;
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.gridheight = 1;
        gridbag.setConstraints(displayArea, c);
        add(displayArea);

        c.weighty = 0.0;
        gridbag.setConstraints(button, c);
        add(button);

        textField.requestFocus();
    }

    class MyTextListener implements TextListener {
        String preface;
        String newline;

        public MyTextListener(String source) {
            newline = System.getProperty("line.separator");
            preface = source
                      + " text value changed."
                      + newline
                      + "   First 10 characters: \"";
        }

        public void textValueChanged(TextEvent e) {
            TextComponent tc = (TextComponent)e.getSource();
            String s = tc.getText();
            try {
                s = s.substring(0, 10);
            } catch (StringIndexOutOfBoundsException ex) {
            }

            displayArea.append(preface + s + "\"" + newline);

            //Scroll down, unless the peer still needs to be created.
            if (displayArea.isValid()) {
                displayArea.setCaretPosition(java.lang.Integer.MAX_VALUE);
            } 
        }
    }

    class MyTextActionListener implements ActionListener {
        /** Handle the text field Return. */
        public void actionPerformed(ActionEvent e) {
            int selStart = textArea.getSelectionStart();
            int selEnd = textArea.getSelectionEnd();

            textArea.replaceRange(textField.getText(),
                                  selStart, selEnd);
            textField.selectAll();
        }
    }

    /** Handle button click. */
    public void actionPerformed(ActionEvent e) {
        displayArea.setText("");
        textField.requestFocus();
    }
}
