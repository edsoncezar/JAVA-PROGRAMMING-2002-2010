package applet;

import java.awt.*;
import java.awt.event.*;
import java.applet.*;

public class Applet1 extends Applet {
   public Applet1(){
      Label l1=new Label();
      l1.setText("Digite sua idade");
      TextField t1=new TextField();
      Button b1=new Button();
      b1.setLabel("OK");
      add(l1);
      add(t1);
      add(b1);
   }
}