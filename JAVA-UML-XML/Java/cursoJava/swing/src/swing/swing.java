package swing;

import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import javax.swing.*;
import com.borland.jbcl.layout.*;
import javax.swing.border.*;

public class swing extends Applet {
  private boolean isStandalone = false;
  JCheckBox jCheckBox1 = new JCheckBox();
  JLabel jLabel1 = new JLabel();
  JTextField jTextField1 = new JTextField();
  JLabel jLabel2 = new JLabel();
  JTextField jTextField2 = new JTextField();
  JPanel jPanel1 = new JPanel();
  JLabel jLabel3 = new JLabel();
  JLabel jLabel4 = new JLabel();
  JLabel jLabel5 = new JLabel();
  JTextField jTextField3 = new JTextField();
  JTextField jTextField5 = new JTextField();
  Object [] ObjUF = {"SP","RJ","ES","BA","MT","PR","RS"};
  JComboBox jComboBox1 = new JComboBox(ObjUF);
  JPanel jPanel2 = new JPanel();
  TitledBorder titledBorder1;
  TitledBorder titledBorder2;
  TitledBorder titledBorder3;
  TitledBorder titledBorder4;
  JRadioButton jRadioButton1 = new JRadioButton();
  JRadioButton jRadioButton2 = new JRadioButton();
  JRadioButton jRadioButton3 = new JRadioButton();
  JLabel jLabel6 = new JLabel();
  ButtonGroup groupPagamento = new ButtonGroup();
  JSpinner jSpinner1 = new JSpinner();
  JLabel jLabel7 = new JLabel();
  JTextArea jTextArea1 = new JTextArea();
  JPanel jPanel3 = new JPanel();
  TitledBorder titledBorder5;
  JRadioButton jRadioButton4 = new JRadioButton();
  JRadioButton jRadioButton5 = new JRadioButton();
  ButtonGroup groupSexo = new ButtonGroup();
  JButton jButton1 = new JButton();
  JButton jButton2 = new JButton();
  //Get a parameter value
  public String getParameter(String key, String def) {
    return isStandalone ? System.getProperty(key, def) :
      (getParameter(key) != null ? getParameter(key) : def);
  }

  //Construct the applet
  public swing() {
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
    titledBorder1 = new TitledBorder("");
    titledBorder2 = new TitledBorder("");
    titledBorder3 = new TitledBorder("");
    titledBorder4 = new TitledBorder(BorderFactory.createEtchedBorder(Color.white,new Color(148, 145, 140)),"Pagamento");
    titledBorder5 = new TitledBorder(BorderFactory.createEtchedBorder(Color.white,new Color(148, 145, 140)),"Sexo");
    jCheckBox1.setText("Usuario Cadastrado");
    jCheckBox1.setBounds(new Rectangle(10, 11, 124, 23));
    jCheckBox1.addActionListener(new swing_jCheckBox1_actionAdapter(this));
    this.setLayout(null);
    jLabel1.setText("e-mail");
    jLabel1.setBounds(new Rectangle(17, 44, 41, 16));
    jTextField1.setText("");
    jTextField1.setBounds(new Rectangle(55, 42, 146, 20));
    jLabel2.setText("Senha");
    jLabel2.setBounds(new Rectangle(15, 80, 46, 16));
    jTextField2.setText("");
    jTextField2.setBounds(new Rectangle(55, 78, 91, 20));
    jPanel1.setBorder(BorderFactory.createEtchedBorder());
    jPanel1.setBounds(new Rectangle(13, 112, 238, 131));
    jPanel1.setLayout(null);

    jLabel3.setText("Nome");
    jLabel3.setBounds(new Rectangle(12, 13, 34, 15));
    jLabel4.setText("Cidade");
    jLabel4.setBounds(new Rectangle(8, 40, 66, 18));
    jLabel5.setText("Estado");
    jLabel5.setBounds(new Rectangle(10, 68, 47, 22));
    jTextField3.setText("");
    jTextField3.setBounds(new Rectangle(58, 10, 160, 20));
    jTextField5.setText("");
    jTextField5.setBounds(new Rectangle(59, 42, 159, 20));
    jComboBox1.setDoubleBuffered(false);
    jComboBox1.setActionCommand("comboBoxChanged");
    jComboBox1.setPopupVisible(false);
    jComboBox1.setBounds(new Rectangle(59, 72, 52, 22));
    jComboBox1.addActionListener(new swing_jComboBox1_actionAdapter(this));
    jPanel2.setBorder(titledBorder4);
    jPanel2.setBounds(new Rectangle(23, 258, 156, 98));
    jPanel2.setLayout(null);
    jRadioButton1.setText("Dep?sito");
    jRadioButton1.setBounds(new Rectangle(16, 16, 91, 23));
    jRadioButton2.setText("Cart?o de Cr?dito");
    jRadioButton2.setBounds(new Rectangle(14, 42, 115, 18));
    jRadioButton3.setText("Boleto Banc?rio");
    jRadioButton3.setBounds(new Rectangle(14, 66, 107, 14));
    jLabel6.setText("Idade");
    jLabel6.setBounds(new Rectangle(278, 76, 34, 15));
    jSpinner1.setBounds(new Rectangle(313, 70, 84, 24));
    jLabel7.setText("Observa??o");
    jLabel7.setBounds(new Rectangle(301, 112, 86, 20));
    jTextArea1.setBorder(BorderFactory.createLoweredBevelBorder());
    jTextArea1.setCaretColor(Color.black);
    jTextArea1.setText("");
    jTextArea1.setLineWrap(true);
    jTextArea1.setBounds(new Rectangle(301, 132, 152, 92));
    jPanel3.setBorder(titledBorder5);
    jPanel3.setBounds(new Rectangle(303, 244, 150, 71));
    jPanel3.setLayout(null);
    jRadioButton4.setText("Feminino");
    jRadioButton4.setBounds(new Rectangle(12, 18, 96, 16));
    jRadioButton5.setText("Masculino");
    jRadioButton5.setBounds(new Rectangle(12, 40, 96, 18));
    jButton1.setBounds(new Rectangle(241, 328, 88, 32));
    jButton1.setText("Envia");
    jButton1.addActionListener(new swing_jButton1_actionAdapter(this));
    jButton2.setBounds(new Rectangle(361, 328, 88, 32));
    jButton2.setText("Limpa");
    jPanel3.add(jRadioButton4, null);
    jPanel3.add(jRadioButton5, null);
    this.add(jButton2, null);
    this.add(jButton1, null);
    this.add(jCheckBox1, null);
    this.add(jLabel1, null);
    this.add(jTextField1, null);
    this.add(jTextField2, null);
    this.add(jPanel1, null);
    jPanel1.add(jLabel3, null);
    jPanel1.add(jTextField3, null);
    jPanel1.add(jLabel5, null);
    jPanel1.add(jLabel4, null);
    jPanel1.add(jTextField5, null);
    jPanel1.add(jComboBox1, null);
    this.add(jLabel2, null);
    this.add(jPanel2, null);
    jPanel2.add(jRadioButton2, null);
    jPanel2.add(jRadioButton3, null);
    jPanel2.add(jRadioButton1, null);
    this.add(jLabel6, null);
    groupPagamento.add(jRadioButton1);
    groupPagamento.add(jRadioButton2);
    groupPagamento.add(jRadioButton3);
    this.add(jSpinner1, null);
    this.add(jLabel7, null);
    this.add(jTextArea1, null);
    this.add(jPanel3, null);
    groupSexo.add(jRadioButton4);
    groupSexo.add(jRadioButton5);
  }
  //Get Applet information
  public String getAppletInfo() {
    return "Applet Information";
  }
  //Get parameter info
  public String[][] getParameterInfo() {
    return null;
  }

  void jComboBox1_actionPerformed(ActionEvent e) {

  }

  void jButton1_actionPerformed(ActionEvent e) {
    JOptionPane.showMessageDialog(null,"Ola Tecnoponta","Titulo", JOptionPane.QUESTION_MESSAGE);

  }

  void jCheckBox1_actionPerformed(ActionEvent e) {
    jPanel1.setVisible(!jCheckBox1.isSelected());
   }
}

class swing_jComboBox1_actionAdapter implements java.awt.event.ActionListener {
  swing adaptee;

  swing_jComboBox1_actionAdapter(swing adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jComboBox1_actionPerformed(e);
  }
}

class swing_jButton1_actionAdapter implements java.awt.event.ActionListener {
  swing adaptee;

  swing_jButton1_actionAdapter(swing adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jButton1_actionPerformed(e);
  }
}

class swing_jCheckBox1_actionAdapter implements java.awt.event.ActionListener {
  swing adaptee;

  swing_jCheckBox1_actionAdapter(swing adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jCheckBox1_actionPerformed(e);
  }
}