import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import Coordinator2;
import InstructorNotifier2; 
//all comments that are for testing purposses and will be removed in the final version are marked with three /  i.e. "///"

public class GUI2 extends JFrame 
          implements ActionListener, SchemeTesterInterface2 {
    JTextField txtname = new JTextField(50);
    JPasswordField txtpassword = new JPasswordField(50);
    JTextField txtlab = new JTextField(50);
    JTextField txtfilename = new JTextField(50);
    JTextField txtprofsemail = new JTextField(50);
    JTextArea txtaresults = new JTextArea("The tester has not been run yet.  Once the test button is pressed the results of the test will go here", 85, 16);
    JTextArea txtacomments = new JTextArea("Have any coments?  Put them here.", 57, 14);
    JButton btntest = new JButton("TEST");
    JButton btnsubmit = new JButton("SUBMIT");
    JButton btnsendcomments = new JButton("SEND COMMENTS");
    JButton btnquit = new JButton("QUIT");


   void buildConstraints(GridBagConstraints gbc, int gx, int gy,
        int gw, int gh, int wx, int wy) {

        gbc.gridx = gx;
        gbc.gridy = gy;
        gbc.gridwidth = gw;
        gbc.gridheight = gh;
        gbc.weightx = wx;
        gbc.weighty = wy;
    }


    public GUI2() {
	super("Jon, Jon, and Bevin ROCK!!");
	//x,y postition, width, height
	setBounds(200, 200, 800, 600);
	GridBagLayout gridbag = new GridBagLayout();
	GridBagConstraints constraints = new GridBagConstraints();
	JPanel pane = new JPanel();
	pane.setLayout(gridbag);
	
	//x,y,width, height, weightx, weighty
	//labels
	
	//name
	buildConstraints(constraints, 0, 0, 1, 1, 10, 10);
	JLabel lblname = new JLabel("NAME:");
	constraints.fill = GridBagConstraints.NONE;
	constraints.anchor = GridBagConstraints.EAST;
	gridbag.setConstraints(lblname, constraints);
	pane.add(lblname);
	
	//Password
	buildConstraints(constraints, 0, 1, 1, 1, 10, 10);
	JLabel lblpassword = new JLabel("PASSWORD:");
	constraints.fill = GridBagConstraints.NONE;
	gridbag.setConstraints(lblpassword, constraints);
	pane.add(lblpassword);
	
	//Lab#
	buildConstraints(constraints, 0, 2, 1, 1, 10, 10);
	JLabel lbllab = new JLabel("LAB #:");
	constraints.fill = GridBagConstraints.NONE;
	gridbag.setConstraints(lbllab, constraints);
	pane.add(lbllab);
	
	//filename
	buildConstraints(constraints, 0, 3, 1, 1, 10, 10);
	JLabel lblfilename = new JLabel("FILE NAME:");
	constraints.fill = GridBagConstraints.NONE;
	gridbag.setConstraints(lblfilename, constraints);
	pane.add(lblfilename);
	
	//profsemail
	buildConstraints(constraints, 0, 4, 1, 1, 10, 10);
	JLabel lblprofsemail = new JLabel("YOUR E-MAIL:");
	//prof email converted to your email
	constraints.fill = GridBagConstraints.NONE;
	gridbag.setConstraints(lblprofsemail, constraints);
	pane.add(lblprofsemail);
	
	//results
	buildConstraints(constraints, 0, 5, 1, 1, 10, 10);
	JLabel lblresults = new JLabel("TEST RESULTS:");
	constraints.fill = GridBagConstraints.NONE;
	constraints.anchor = GridBagConstraints.SOUTHWEST;
	gridbag.setConstraints(lblresults, constraints);
	pane.add(lblresults);
	
	////comments
	//buildConstraints(constraints, 5, 5, 1, 1, 10, 10);
	//JLabel lblcomments = new JLabel("COMMENTS:");
	//constraints.fill = GridBagConstraints.NONE;
	//gridbag.setConstraints(lblcomments, constraints);
	//pane.add(lblcomments);
	
	
	//textboxes
	
	//name
	buildConstraints(constraints, 1, 0, 3, 1, 10, 10);
	constraints.fill = GridBagConstraints.HORIZONTAL;
	constraints.anchor = GridBagConstraints.WEST;
	gridbag.setConstraints(txtname, constraints);
	pane.add(txtname);
	
	//password
	buildConstraints(constraints, 1, 1, 3, 1, 10, 10);
	//codePhrase.setEchoChar("$");
	constraints.fill = GridBagConstraints.HORIZONTAL;
	gridbag.setConstraints(txtpassword, constraints);
	pane.add(txtpassword);
	
	//lab#
	buildConstraints(constraints, 1, 2, 3, 1, 10, 10);
	constraints.fill = GridBagConstraints.HORIZONTAL;
	gridbag.setConstraints(txtlab, constraints);
	pane.add(txtlab);
	
	//file name
	buildConstraints(constraints, 1, 3, 3, 1, 10, 10);
	constraints.fill = GridBagConstraints.HORIZONTAL;
	gridbag.setConstraints(txtfilename, constraints);
	pane.add(txtfilename);
	
	//profsemail
	buildConstraints(constraints, 1, 4, 3, 1, 10, 10);
	constraints.fill = GridBagConstraints.HORIZONTAL;
	gridbag.setConstraints(txtprofsemail, constraints);
	pane.add(txtprofsemail);
	
	
	//TextArea
	//txtaresults
	buildConstraints(constraints, 0, 6, 4, 10, 100, 100);
	//constraints.ipadx = 2;
	//constraints.ipady = 2;
	constraints.fill = GridBagConstraints.BOTH;
	constraints.anchor = GridBagConstraints.CENTER;
	gridbag.setConstraints(txtaresults, constraints);
	txtaresults.setLineWrap(true);
	txtaresults.setEditable(false);
	pane.add(txtaresults);
	
	//comments box
	buildConstraints(constraints, 7, 6, 1, 1, 10, 10);
	//constraints.ipadx = 2;
	//constraints.ipady = 2;
	constraints.fill = GridBagConstraints.BOTH;
	constraints.anchor = GridBagConstraints.WEST;
	gridbag.setConstraints(txtacomments, constraints);
	txtacomments.setLineWrap(true);
	pane.add(txtacomments);
	

	//JScrollPane scroll1 = new JScrollPane(txtaresults, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	//pane.add(scroll1);	
	//JScrollPane scroll2 = new JScrollPane(txtacomments, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	//pane.add(scroll2);
	
	//buttons
	
	//btntest
	//test
	buildConstraints(constraints, 0, 17, 1, 1, 10, 10);
	constraints.fill = GridBagConstraints.NONE;
	gridbag.setConstraints(btntest, constraints);
	btntest.addActionListener(this);
	pane.add(btntest);
	
	//submit
	buildConstraints(constraints, 1, 17, 1, 1, 10, 10);
	constraints.fill = GridBagConstraints.NONE;
	gridbag.setConstraints(btnsubmit, constraints);
	btnsubmit.addActionListener(this);
	pane.add(btnsubmit);
	
	//sendcomments
	buildConstraints(constraints, 2, 17, 1, 1, 10, 10);
	constraints.fill = GridBagConstraints.NONE;
	gridbag.setConstraints(btnsendcomments, constraints);
	btnsendcomments.addActionListener(this);
	pane.add(btnsendcomments);
	
	//quit
	buildConstraints(constraints, 3, 17, 1, 1, 10, 10);
	constraints.fill = GridBagConstraints.NONE;
	gridbag.setConstraints(btnquit, constraints);
	btnquit.addActionListener(this);
	pane.add(btnquit);
	

	//picture
	buildConstraints(constraints, 5, 0, 4, 4, 10, 10);
	ImageIcon picture1 = new ImageIcon("ferrari1.jpg");	
	JLabel picturelabel = new JLabel(picture1, SwingConstants.CENTER);
	constraints.fill = GridBagConstraints.BOTH;
	constraints.anchor = GridBagConstraints.NORTH;
	gridbag.setConstraints(picturelabel, constraints);
	pane.add(picturelabel);

	setContentPane(pane);
    } //GUI2
    
    public static void main(String[] arguments) {
	       	    GUI2 frame = new GUI2();
	    ExitWindow exit = new ExitWindow();
	    frame.addWindowListener(exit);
	    frame.show();
		    } //main

 
    //printer is a method that is called by the output formatter.  
    //Printer takes the formatted results and displays them in the results box.
    public void printer(String results1) {
	txtaresults.setText(results1);
	repaint();
    }//printer

    public void actionPerformed(ActionEvent evt) {
	Object source = evt.getSource();
	if (source==btntest) {	    
	    String name = txtname.getText();
	    double labnumber 
                = Double.parseDouble(sizeField.getText()).doubleValue();
	    String filename = txtfilename.getText();
	    Coordinator2 coordinator = new Coordinator2();
            try {
                coordinator.dostuff(name, labnumber, filename, this); 
            } catch (Exception e) {}
	   
	    txtaresults.setText("you program is now being tested. please wait");
} 
	
	if (source==btnsubmit){
	    String name = txtname.getText();
	    String addressee = txtprofsemail.getText();
	    String sender = "dummy -- must be fixed";
	    Integer labnumber = new Integer(txtlab.getText());
	    String subject = "Lab " + labnumber  + " from " + name;
	    String messageBody = "Results: " + "\n" + txtaresults.getText() + "\n" + "\n" + "Comments: " + "\n" + txtacomments.getText();
	    InstructorNotifier2 notifier = new InstructorNotifier2();
            try {
                notifier.sendEmail(addressee, sender, subject, messageBody); 
            } catch (Exception e) {}

	    txtaresults.setText("Your program has been submitted.  Thank You.");
}

	if (source==btnsendcomments){
	    txtacomments.setText("send comments button pressed");
	}
	if (source==btnquit){
	    System.exit(0);
	}
	repaint();
	 } //actionevent 

}  //class

class ExitWindow extends WindowAdapter {
    public void windowClosing(WindowEvent e) {
        System.exit(0);
    } //exitwindow
}


//KNOWN ISSUES
//catch tons of text field exceptions
//fix up gui appearance
//open file name using windows/linux wizard
//clear results field before each use
//switch the picture
//get the scroll bars to work
//catch exception from coordinator
//add sender txt field
