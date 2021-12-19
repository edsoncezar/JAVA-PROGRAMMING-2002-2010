package applet2;

import java.awt.*;
import java.awt.event.*;
import java.applet.*;

public class Applet2 extends Applet {
  private boolean isStandalone = false;
  Label label1 = new Label();
  TextField textField2 = new TextField();
  Label label2 = new Label();
  TextField textField1 = new TextField();
  Button button1 = new Button();
  //Get a parameter value
  public String getParameter(String key, String def) {
    return isStandalone ? System.getProperty(key, def) :
      (getParameter(key) != null ? getParameter(key) : def);
  }

  //Construct the applet
  public Applet2() {
  }
  //Initialize the applet
  public void init() {
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }
  //Component initialization
  private void jbInit() throws Exception {
    button1.setLabel("OK");
    button1.setBounds(new Rectangle(87, 104, 58, 32));
    button1.addActionListener(new Applet2_button1_actionAdapter(this));
    label1.setAlignment(Label.LEFT);
    label1.setText("Cidade");
    label1.setBounds(new Rectangle(6, 29, 42, 15));
    this.setLayout(null);
    textField2.setColumns(0);
    textField2.setEditable(true);
    textField2.setEnabled(true);
    textField2.setFont(new java.awt.Font("Dialog", 0, 12));
    textField2.setForeground(Color.black);
    textField2.setLocale(java.util.Locale.getDefault());
    textField2.setText("");
    textField2.setBounds(new Rectangle(57, 56, 130, 23));
    textField2.addHierarchyBoundsListener(new Applet2_textField2_hierarchyBoundsAdapter(this));
    label2.setBounds(new Rectangle(8, 63, 42, 15));
    label2.setText("Estado");
    label2.setAlignment(Label.LEFT);
    textField1.setBounds(new Rectangle(57, 22, 130, 23));
    textField1.setText("");
    textField1.setLocale(java.util.Locale.getDefault());
    textField1.setForeground(Color.black);
    textField1.setFont(new java.awt.Font("Dialog", 0, 12));
    textField1.setEnabled(true);
    textField1.setEditable(true);
    textField1.setColumns(0);
    this.setBackground(SystemColor.menu);
    this.add(textField2, null);
    this.add(label2, null);
    this.add(textField1, null);
    this.add(label1, null);
    this.add(button1, null);
  }
  //Get Applet information
  public String getAppletInfo() {
    return "Applet Information";
  }
  //Get parameter info
  public String[][] getParameterInfo() {
    return null;
  }

  void textField2_ancestorResized(HierarchyEvent e) {

  }

  void button1_actionPerformed(ActionEvent e) {
    textField1.setText("Brasil");
    textField2.setText(textField1.getText());
  }

}

class Applet2_textField2_hierarchyBoundsAdapter extends java.awt.event.HierarchyBoundsAdapter {
  Applet2 adaptee;

  Applet2_textField2_hierarchyBoundsAdapter(Applet2 adaptee) {
    this.adaptee = adaptee;
  }
  public void ancestorResized(HierarchyEvent e) {
    adaptee.textField2_ancestorResized(e);
  }
}

class Applet2_button1_actionAdapter implements java.awt.event.ActionListener {
  Applet2 adaptee;

  Applet2_button1_actionAdapter(Applet2 adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.button1_actionPerformed(e);
  }
}