package appletbd;

import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import javax.swing.*;
import java.net.*;
import java.io.*;

public class Applet1 extends Applet{
  private boolean isStandalone = false;
  JLabel jLabel1 = new JLabel();
  JLabel jLabel2 = new JLabel();
  JTextField jTextField1 = new JTextField();
  JPasswordField jPasswordField1 = new JPasswordField();
  JButton jButton1 = new JButton();
  JButton jButton2 = new JButton();
  //Get a parameter value
  public String getParameter(String key, String def) {
    return isStandalone ? System.getProperty(key, def) :
      (getParameter(key) != null ? getParameter(key) : def);
  }

  //Construct the applet
  public Applet1() {
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
    jLabel1.setText("Nome");
    jLabel1.setBounds(new Rectangle(19, 23, 34, 15));
    this.setLayout(null);
    jLabel2.setText("Senha");
    jLabel2.setBounds(new Rectangle(17, 54, 40, 20));
    jTextField1.setText("");
    jTextField1.setBounds(new Rectangle(77, 22, 132, 18));
    jPasswordField1.setText("");
    jPasswordField1.setBounds(new Rectangle(77, 52, 131, 19));
    jButton1.setBounds(new Rectangle(15, 92, 98, 32));
    jButton1.setText("Envia");
    jButton1.addActionListener(new Applet1_jButton1_actionAdapter(this));
    jButton2.setBounds(new Rectangle(135, 92, 99, 33));
    jButton2.setEnabled(false);
    jButton2.setText("Pr�ximo");
    this.add(jLabel1, null);
    this.add(jLabel2, null);
    this.add(jTextField1, null);
    this.add(jPasswordField1, null);
    this.add(jButton1, null);
    this.add(jButton2, null);
  }
  //Get Applet information
  public String getAppletInfo() {
    return "Applet Information";
  }
  //Get parameter info
  public String[][] getParameterInfo() {
    return null;
  }

  void jButton1_actionPerformed(ActionEvent e) {
    try {
  String parametros= "nome="+jTextField1.getText()+"&senha="+jPasswordField1.getText();

  // Envia dados pelo m�todo post
  URL url = new URL("http://127.0.0.1:8080/confere"); // numero do ip do servidor
  URLConnection conn = url.openConnection();
  conn.setDoOutput(true);
  OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
  wr.write(parametros);
  wr.flush();
  wr.close();

  // Recupera Resposta
  BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
  String situacao = rd.readLine();
  if (situacao.equals("OK")){
    jButton2.setEnabled(true);
  } else {
    jButton2.setEnabled(false);
  }
  rd.close();
} catch (Exception err) {
   System.out.println(err.getMessage()); }

  }
}

class Applet1_jButton1_actionAdapter implements java.awt.event.ActionListener {
  Applet1 adaptee;

  Applet1_jButton1_actionAdapter(Applet1 adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jButton1_actionPerformed(e);
  }
}