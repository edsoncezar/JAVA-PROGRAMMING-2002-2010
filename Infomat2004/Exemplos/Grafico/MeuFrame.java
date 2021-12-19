import java.awt.*;
import java.io.*;
import java.awt.event.*;

public class MeuFrame implements ActionListener, ItemListener, MouseListener {

	
	private Frame 		f;
	private TextField 	tf;
	private TextArea 	ta ;
	private TextArea 	ta1 ;
	private Panel 		p ;
	private Panel	    	p1;
	private Panel 		p2;
	private Panel 		p3;
	private Button	    	b1;
	private Button 		b2;
	private Button 		b3;
	private Label 		l;
	private Label 		l1;
	private	Choice 		ch;
	private CheckboxGroup 	cbg;
	private Checkbox 	cb;
	private Checkbox 	cb1;
	private MenuBar 	mb;
	private Menu 		m;
   	private Menu 		m1;
	private MenuItem    	mi;
	private MenuItem    	mi1;
  

	public void metodo() {

		
			Frame f = new Frame("Meu Frame");
			f.setSize(400,400);
			tf = new TextField(60);
			ta = new TextArea(20,60);
			ta1 = new TextArea(4,50);
			ta.addMouseListener(this);
			ta1.addMouseListener(this);
		
           
			MenuBar mb = new MenuBar();
		    	Menu m = new Menu("Menu Principal");
			Menu mc = new Menu ("FAZ");
			MenuItem mi = new MenuItem("Não faz nada");
			MenuItem mi1 = new MenuItem("Faz Menos ainda");
			Menu m1 = new Menu("NÃO FAZ");
			MenuItem mi2 = new MenuItem("Se o que FAZ não faz imagine se o que NÃO FAZ iria fazer");
			MenuItem mi3 = new MenuItem("Ainda vai tentar clicar aqui???");	
			MenuItem mi4 = new MenuItem("Ha Ha Ha Ha Ha");
	
			mb.add(m);
			m.add(mc);
			mc.add(mi);
			mc.add(mi1);
			m.addSeparator();
			m1.add(mi2);
			m1.add(mi3);
			m.add(m1);
			m.addSeparator();
			m.add(mi4);
			
			f.setMenuBar(mb);		
			
			p = new Panel();
			p1 = new Panel();
		    	p2 = new Panel();
			p3 = new Panel();
			
			Label l = new Label("Por: Maria Ane Dias ®");
			Label l1 = new Label("Texto ");
		
			p.setSize(200,400);
			p1.setSize(100,400);
			b1 = new Button ("Enviar");
			b2 = new Button ("Sair");
			b3 = new Button ("Limpar");
			
		    b1.addActionListener(this);
		    b2.addActionListener(this);
			b3.addActionListener(this);
			
			Choice ch = new Choice();
			ch.addItem("Azul");
			ch.addItem("Vermelho");
			ch.addItem("Verde");
			ch.addItem("Cinza");
			ch.addItem("Amarelo");
			ch.addItem("Preto");
			ch.addItem("Branco");
			ch.addItemListener(this);
			p1.add(ch);
									
			f.add(l1, BorderLayout.NORTH);
			f.add(p,  BorderLayout.WEST);
			f.add(p1, BorderLayout.EAST);
			f.add(p2, BorderLayout.CENTER);
			f.add(p3, BorderLayout.SOUTH);
			
			p.setBackground(Color.red);
			p1.setBackground(Color.blue);
			p2.setBackground(Color.black);
			p3.setBackground(Color.green);
			
		        CheckboxGroup cbg = new CheckboxGroup();
			Checkbox cb = new Checkbox("Bom Dia", false, cbg);
			Checkbox cb1 = new Checkbox("Boa Tarde", false, cbg);
		 	cb.addItemListener(this);
			cb1.addItemListener(this);
			
			p.add(l1);
			p1.add(b1);
			p1.add(b2);
			p1.add(b3);
			p3.add(ta1);
			p.add(tf);
			p2.add(tf);
			p2.add(ta);
			p3.add(l);
			
			p.add(cb);
			p.add(cb1);
		
			p.setLayout( new GridLayout(12,1));	
			p1.setLayout( new GridLayout(8,8));	
			f.pack();
			f.setVisible(true);
			
}
	public void actionPerformed (ActionEvent ap) {
		
		String n = tf.getText();
		System.out.println(n.length());
				
	                                                                        
		 if (ap.getActionCommand().equals("Sair")) {
			 System.exit(0);		 	
		 }
		 else if (ap.getActionCommand().equals("Enviar")) {
			
			if (tf.getText().equals("Tomaz Mikio Sasaki")) {
			  	if (ta.getText().equals("")) {
					 ta.setText(tf.getText() + ": Professor de Java!!!");
					 tf.setText(": Professor de Java!!!");
				}
			 	else {
				 	 ta.setText(ta.getText() + "\n" + tf.getText() + ": Professor de Java!!!");
			         tf.setText("");
					
				}
		  	 }
			else if (tf.getText().equals("")) {
		 				 System.out.println("Digite algo antes de enviar!!!!"); 
			 }
		 	else if (ta.getText().equals("")) {
					 ta.setText(tf.getText());
  		     		 tf.setText("");
				}
			else {
			 		ta.setText(ta.getText() + "\n" + tf.getText());
					tf.setText("");
			 	}
			}
		 else if (ap.getActionCommand().equals("Limpar")) {
				ta.setText("");
				ta1.setText("");
				tf.setText("");
				ta.setEnabled(true);
		   		ta1.setEnabled(true);
		}
		else {
			System.exit (0);
		}
		String g = ta.getText();
		System.out.println (g.length());		
	}
	
	public void itemStateChanged(ItemEvent ie) {
		
		if (ie.getItem().equals("Bom Dia")) {
			if (ta.getText().equals("")) {			    
				ta.setText("Bom Dia");
			}
			else {
				ta.setText(ta.getText() + "\n" + "Bom Dia");
			}
		}
		else if (ie.getItem().equals("Boa Tarde")) {
			if (ta.getText().equals("")) {			    
				ta.setText("Boa Tarde ");
			}
			else {
				ta.setText(ta.getText() + "\n" + "Boa Tarde");
			}			
		}
		
		else if ( ie.getItem().equals("Vermelho")) {
			p1.setBackground(Color.red);
		}
		else if ( ie.getItem().equals("Azul")){
			p1.setBackground(Color.blue);
		}
		else if ( ie.getItem().equals("Verde")){
					p1.setBackground(Color.green);
		}
		else if ( ie.getItem().equals("Cinza")){
					p1.setBackground(Color.gray);
		}
		else if ( ie.getItem().equals("Amarelo")){
					p1.setBackground(Color.yellow);
		}
		else if ( ie.getItem().equals("Preto")){
					p1.setBackground(Color.black);
		}
		else if ( ie.getItem().equals("Branco")){
					p1.setBackground(Color.white);
		}
		else {
			p1.setBackground(Color.blue);
		}
		
		
	}
	
	
	
	    public void mousePressed(MouseEvent e) {
		    ta1.setEnabled(false);
		   saySomething("Mouse pressionado; "
	                    + e.getClickCount()+ " clique(s) "  , e);
 		 ta.setEnabled(false);
		 ta1.setEnabled(false);

			
		  	
	    }

	    public void mouseReleased(MouseEvent e) {
			
	       saySomething("Mouse solto;"
	                    + e.getClickCount()+ " clique(s) ", e);
		   ta.setEnabled(true);
		   ta1.setEnabled(true);

	    }
	
	    public void mouseEntered(MouseEvent e) {

	        saySomething("Mouse sobre área de texto", e);

	  }
	
	    public void mouseExited(MouseEvent e) {
			
	       saySomething("Mouse fora da área de texto", e);
		   ta.setEnabled(true);
		   ta1.setEnabled(true);
	    } 
	
	    public void mouseClicked(MouseEvent e) {
		
	      saySomething("Mouse clicado; "
	                  + e.getClickCount() + " clique(s) ", e);

 		}
	
	   public void saySomething(String eventDescription, MouseEvent e) {
         ta1.append(eventDescription + " detectado(s) em " + e.getComponent().getClass().getName()
	                       + "." + "\n");
						 
	    }


	public static void main (String [] args) {
			
				MeuFrame meuFrame = new MeuFrame();
				meuFrame.metodo(); 
				
				
		}
}

