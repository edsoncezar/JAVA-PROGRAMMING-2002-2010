/**
 * ---- general information ----
 *
 * Moonlanding.java: A Moon landing simulation applet.
 * 
 * @version 1.0, 30 Aug 1999
 * @author  Jan Keller
 * @mail    post@jan-keller.de
 * @url     www.jan-keller.de
 * @place   Leipzig, Germany
 *
 * ---- Description ----
 *
 * JAVA programm for playing a simulation of the landing on the moon on the internet.
 *
 * This program is postcard-ware.
 * If you like this program, send a postcard to:
 *
 *   Jan Keller
 *   Grassistraße 31/315
 *   04107 Leipzig
 *   Germany
 *
 * Thanks in advance!
 * You are free to do anything you want with this program.
 * However, I am not responsible for any bugs in this program and
 * possible damage to hard- or software when using this program.
 * Please refer to this program when you are using it in one of your programs
 * Feel free to e-mail me at any time at post@jan-keller.de
 **/

import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyVetoException;
import java.util.EventObject;

public class Moonlanding extends Applet implements ActionListener, MouseListener
{

    // spezielle Sachen von Symantec
    Firework Firework;
    ProgressBar ProgressBarTreibstoff;
    ProgressBar ProgressBarHoehe;
    WrappingLabel Ergebnis;
    // der allgemeine Rest
    Label Treibstoffanzeige;
    Label Hoehenanzeige;
    Label Titel;
    Label VelocityAnzeige;
    Label Frage;
    TextField Input;
    Label Beschriftung;
    Button Bremsen;
    Button NeuesSpiel;
    Button Info;
    Label Infotext;
    // das Mondbild
    MediaTracker tracker = new MediaTracker(this);  
    Image image;
    // die Variablen
    float Treibstoff, Hoehe, Velocity, Fuel;
    boolean end, win;

    // die Ergebnisstrings
    String Status;
    String Str1 = "Something rumbles, but You came closer to the surface!";
    String Str2 = "Congratulations, You eventually landed on the moon!";
    String Str3 = "I'm very sorry, but You crashed on the surface. Try again!";
    String Str4 = "Illegal charakters in input fuel field.";
    String Str5 = "With no fuel left, You simply fall down and ....";
    String Str6 = "Something rumbles again, You even drifted away a bit!";
    
    // Konstanten
    float gOnMoon = 10;        // acceleration on the moon ca. 1/6 of earths
    float deltatime = 2;      // to make it easy    
    float braking_acc   = 0;  // the somehow ominöser Effekt of the Bremsen o:)
    
    private void initValues() {

      // die Variablen
      Treibstoff = 100;
      Hoehe = 10000;
      Velocity = 100;
      Fuel = 0;
      end = false;
      win = false;
      Status = "";

      // die Componenten
      Firework.setVisible(false);     
    }
    
    private void updateValues() {
      // Anzeigen erneueren  
      try { ProgressBarTreibstoff.setValue((int)Treibstoff); }
      catch(java.beans.PropertyVetoException e) {    e.printStackTrace(); }      
      Treibstoffanzeige.setText(String.valueOf(Treibstoff) + " units of fuel left");
      
      try { ProgressBarHoehe.setValue((int)Hoehe/100); }
      catch(java.beans.PropertyVetoException e) {    e.printStackTrace(); }      
      Hoehenanzeige.setText("Your altitude is approx. " + String.valueOf(Hoehe) +"m");
      
      VelocityAnzeige.setText("Your velocity is " + String.valueOf(Velocity) +"m/s");
      Input.setText(String.valueOf(Fuel));              
      
      try { Ergebnis.setText(Status); }
      catch(java.beans.PropertyVetoException e) {    e.printStackTrace(); }            
    }

    public void init()
    {
        setLayout(null);
        setSize(550,370);
        setBackground(new Color(16711624));

        // das Feuerwerk für den Gewinn
        Firework = new Firework();
        Firework.setBounds(250,0,300,370);
        add(Firework);

        // Treibstoffanzeige
        ProgressBarTreibstoff = new ProgressBar();
        ProgressBarTreibstoff.setBounds(25,75,220,15);
        ProgressBarTreibstoff.setFont(new Font("Dialog", Font.PLAIN, 11));
        add(ProgressBarTreibstoff);

        Treibstoffanzeige = new Label("100 units of fuel left",Label.CENTER);
        Treibstoffanzeige.setBounds(25,50,220,15);
        Treibstoffanzeige.setFont(new Font("Dialog", Font.PLAIN, 11));
        Treibstoffanzeige.setBackground(new Color(12632256));
        add(Treibstoffanzeige);

        // Höhenanzeige
        ProgressBarHoehe = new ProgressBar();
        ProgressBarHoehe.setBounds(25,130,220,15);
        ProgressBarHoehe.setFont(new Font("Dialog", Font.PLAIN, 11));
        add(ProgressBarHoehe);

        Hoehenanzeige = new Label("Your altitude is approx. 10000 m",Label.CENTER);
        Hoehenanzeige.setBounds(25,110,220,15);
        Hoehenanzeige.setFont(new Font("Dialog", Font.PLAIN, 11));
        Hoehenanzeige.setBackground(new Color(12632256));
        add(Hoehenanzeige);

        // Titel
        Titel = new Label("Moonlanding!",Label.CENTER);
        Titel.setBounds(60,10,140,20);
        Titel.setFont(new Font("Serif", Font.BOLD, 20));
        add(Titel);

        // Geschwindigkeitsanzeige
        VelocityAnzeige = new Label("Your velocity is 100 m/s",Label.CENTER);
        VelocityAnzeige.setBounds(25,160,220,15);
        VelocityAnzeige.setFont(new Font("Dialog", Font.PLAIN, 11));
        VelocityAnzeige.setBackground(new Color(12632256));
        add(VelocityAnzeige);

        // Bremsen
        Frage = new Label("Use fuel and apply the brakes!",Label.CENTER);
        Frage.setBounds(25,190,220,15);
        Frage.setFont(new Font("Dialog", Font.PLAIN, 11));
        Frage.setBackground(new Color(12632256));
        add(Frage);

        Input = new TextField();
        Input.setBounds(25,220,60,18);
        Input.setFont(new Font("Dialog", Font.PLAIN, 11));
        add(Input);

        Beschriftung = new Label("Units of fuel");
        Beschriftung.setBounds(100,220,145,15);
        Beschriftung.setFont(new Font("Dialog", Font.PLAIN, 11));
        Beschriftung.setBackground(new Color(12632256));
        add(Beschriftung);

        Bremsen = new Button();
        Bremsen.setLabel("Do it now!");
        Bremsen.setBounds(60,250,80,20);
        Bremsen.setFont(new Font("Dialog", Font.BOLD, 12));
        Bremsen.setBackground(new Color(12632256));
        add(Bremsen);
        Bremsen.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Und das Ergebnis
        Ergebnis = new WrappingLabel();
        try {
            Ergebnis.setAlignStyle(WrappingLabel.ALIGN_CENTERED);
        }
        catch(java.beans.PropertyVetoException e)
        {    e.printStackTrace(); }
        Ergebnis.setBounds(25,280,220,30);
        Ergebnis.setFont(new Font("Dialog", Font.ITALIC, 11));
        Ergebnis.setBackground(new Color(12632256));
        add(Ergebnis);

        // die Buttons
        NeuesSpiel = new Button();
        NeuesSpiel.setLabel("New Game");
        NeuesSpiel.setBounds(25,325,80,20);
        NeuesSpiel.setFont(new Font("Dialog", Font.BOLD, 12));
        NeuesSpiel.setBackground(new Color(12632256));
        add(NeuesSpiel);
        NeuesSpiel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        Info = new Button();
        Info.setLabel("Info");
        Info.setBounds(185,325,60,20);
        Info.setFont(new Font("Dialog", Font.BOLD, 12));
        Info.setBackground(new Color(12632256));
        add(Info);
        Info.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // der Infotext
        Infotext = new Label("Moonlanding by Jan Keller",Label.CENTER);
        Infotext.setBounds(130,130,250,50);
        Infotext.setFont(new Font("Serif", Font.BOLD, 14));
        Infotext.setForeground(new Color(0));
        Infotext.setBackground(new Color(12632256));
        add(Infotext);
        Infotext.setVisible(false);
        
        // das Mondbild
        try {
            image = getImage(getCodeBase(), "moon.gif");
            tracker.addImage(image, 0);
            tracker.waitForID(0);
        }
        catch(Exception e) { e.printStackTrace(); }     
        
        initValues();
        
        // die ActionListeners
        Info.addActionListener(this);
        Info.addMouseListener(this);
        NeuesSpiel.addActionListener(this);
        Bremsen.addActionListener(this);        
        // addMouseListener(this); würde man machen, falls das Applet gemeint ist
    }

    public void paint(Graphics g) {
      if (!tracker.isErrorID(0))    
      g.drawImage(image,240,50,this);
      updateValues();
      super.paint(g);
    }
    
    public void actionPerformed(java.awt.event.ActionEvent event)
    {
        Object object = event.getSource();
        if (object == Info) {
            Infotext.setVisible(!Infotext.isVisible());
            VelocityAnzeige.setVisible(!VelocityAnzeige.isVisible());
            ProgressBarHoehe.setVisible(!ProgressBarHoehe.isVisible());
            if (win) Firework.setVisible(!Firework.isVisible());
        }
        if (object == NeuesSpiel) {
            // alle Initialisierungen
            initValues();
            updateValues();
        }
        if (object == Bremsen) {
           if (!end) {
             // jetzt beginnt die Rechnerei            
             try { Fuel = Float.valueOf(Input.getText()).floatValue(); }
             catch (NumberFormatException e) { Fuel = -1;} //zwei Fehler: negativ ...
             if (Fuel < 0) {
                Status = Str4;
                Fuel = 0;
             } else {
               // Treibstoff verbrauchen
               if (Fuel < Treibstoff) {
                  Treibstoff -= Fuel;
                  braking_acc = 5 * Fuel;  /* der Einfluß dieser Konstanten erlaubt mir,
                                              das Spiel zu justieren */
                  // and here comes the physics
                  // v = a * t + vo
                  Velocity += (gOnMoon * (1 + 10000 / (10000 + Hoehe)) - braking_acc) * deltatime;
                  // s = v * t + s0    | Richtungen der Kräfte, ... beachten
                  if (Velocity > 0) Status = Str1; else Status = Str6;
                  Hoehe -= Velocity * deltatime;
               } else {
                  Status = Str5;
                  // die ungebremste Reise zum Mond 
                  Fuel = Treibstoff;
                  Treibstoff = 0;
                  // noch kurz bremsen
                  braking_acc = 5 * Fuel;                  
                  Velocity += (gOnMoon * (1 + 10000 / (10000 + Hoehe)) - braking_acc) * deltatime;
                  Hoehe -= Velocity * deltatime;                  
                  // und jetzt fallen lassen
                  while (Hoehe >= 0) {
                    Velocity += (gOnMoon * (1 + 10000 / (10000 + Hoehe))) * deltatime;
                    Hoehe -= Velocity * deltatime;                                      
                  }  
                 updateValues();                               
               }

               if (Hoehe < 0) Hoehe = 0; // just for fun
               if (Hoehe == 0) { // the end --> win/loose ?
                 // eine Weile warten, damit der letzte Zug angeschaut werden kann
                 end = true;
                 if (Velocity < 50) { 
                    Firework.setVisible(true);
                    Status = Str2;
                    win = true;
                 } else Status = Str3;
               }  
             } 
             updateValues();             
           }  
        }        
    }
    
    public void mouseExited(MouseEvent event) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }
    public String getAppletInfo() {
    return "Moonlanding by Jan Keller (1999)";
    }   
}