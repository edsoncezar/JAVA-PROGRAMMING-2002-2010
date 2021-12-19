package aplicacao;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Frame1 extends JFrame {
  JPanel contentPane;
  JButton jButton1 = new JButton();
  JTextField jTextField1 = new JTextField();

  //Construct the frame
  public Frame1() {
    enableEvents(AWTEvent.WINDOW_EVENT_MASK);
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }
  //Component initialization
  private void jbInit() throws Exception  {
    contentPane = (JPanel) this.getContentPane();
    jButton1.setBounds(new Rectangle(63, 83, 95, 35));
    jButton1.setText("jButton1");
    jButton1.addActionListener(new Frame1_jButton1_actionAdapter(this));
    contentPane.setLayout(null);
    this.setSize(new Dimension(504, 392));
    this.setTitle("Frame Title");
    jTextField1.setText("jTextField1");
    jTextField1.setBounds(new Rectangle(37, 27, 188, 26));
    contentPane.add(jButton1, null);
    contentPane.add(jTextField1, null);
  }
  //Overridden so we can exit when window is closed
  protected void processWindowEvent(WindowEvent e) {
    super.processWindowEvent(e);
    if (e.getID() == WindowEvent.WINDOW_CLOSING) {
      System.exit(0);
    }
  }

  void jButton1_actionPerformed(ActionEvent e) {
    jTextField1.setText("Ivan");
  }
}

class Frame1_jButton1_actionAdapter implements java.awt.event.ActionListener {
  Frame1 adaptee;

  Frame1_jButton1_actionAdapter(Frame1 adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jButton1_actionPerformed(e);
  }
}