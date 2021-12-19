/*  EmailClient.java: The GUI to a possible email client.
 *
 *  (c) 2001-2002 Edgar Troudt
 *                Department of Computer Science
 *                Queens College / CUNY
 *
 *  Warning: Impersonating individuals over email is a Federal offense and is
 *           punishable under the United States Computer Fraud and Abuse Act
 *           (18 U.S.C. section 1030)
 *              URL: http://www4.law.cornell.edu/uscode/18/1030.html
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


// ============================================================================
public class EmailClient implements ActionListener, MouseListener {

    // Fun Buttons
    private JButton    yes1, yes2;
    boolean badJoke = true;

    // Data
    private String     server   = "forbin.qc.edu";
    private String     fromAddr = "nobody@qc.edu";

    // GUI Components
    private JFrame     myFrame;
    private JTextField toField, ccField, bccField, subField, status;
    private JTextArea  message;
    private JMenuItem  aboutItem, sendItem, setServ, setFrom, quitItem;

    //-------------------------------------------------------------------------
    public EmailClient () {

        // Set up main frame for our application.
        myFrame = new JFrame();
        myFrame.setResizable( false );
        myFrame.setTitle( "Compose Message" );
        Container baseFrame = myFrame.getContentPane();
        baseFrame.setLayout( new BorderLayout() );
        myFrame.setSize( 500, 300 );


        JPanel top = new JPanel();
        top.setLayout( new BorderLayout() );

        // Set up a menu up top.
        JMenuBar  theMenu  = new JMenuBar();
        JMenu     optsMenu = new JMenu("Options");
        sendItem  = new JMenuItem("Send");
        sendItem.addActionListener( this );
        setServ   = new JMenuItem("Set SMTP Server");
        setServ.addActionListener( this );
        setFrom   = new JMenuItem("Set From Address");
        setFrom.addActionListener( this );
        aboutItem = new JMenuItem( "About" );
        aboutItem.addActionListener( this );
        quitItem  = new JMenuItem("Quit");
        quitItem.addActionListener( this );
        optsMenu.add( sendItem );
        optsMenu.add( setServ );
        optsMenu.add( setFrom );
        optsMenu.addSeparator();
        optsMenu.add( aboutItem );
        optsMenu.add( quitItem );
        theMenu.add( optsMenu );
        top.add( theMenu, BorderLayout.NORTH );

        // Set up a pane for message addressing information
        JPanel addrInfo = new JPanel();
        addrInfo.setLayout( new GridLayout ( 4, 2 ) );
        toField  = new JTextField("");
        ccField  = new JTextField("");
        bccField = new JTextField("");
        subField = new JTextField("");
        JLabel toLbl  = new JLabel ("To: ");
        JLabel ccLbl  = new JLabel ("CC: ");
        JLabel bccLbl = new JLabel ("BCC: ");
        JLabel subLbl = new JLabel ("Subject: ");
        toLbl.setToolTipText("The address of the e-mail recipient.");
        ccLbl.setToolTipText("The address of additional recipients.");
        bccLbl.setToolTipText
            ("The address of recipients whose names do not appear.");
        subLbl.setToolTipText("The subject of your e-mail message");
        addrInfo.add( toLbl );
        addrInfo.add( toField );
        addrInfo.add( ccLbl );
        addrInfo.add( ccField );
        addrInfo.add( bccLbl );
        addrInfo.add( bccField );
        addrInfo.add( subLbl );
        addrInfo.add( subField );
        top.add( addrInfo, BorderLayout.SOUTH );

        baseFrame.add( top, BorderLayout.NORTH );

        status = new JTextField("Enter your e-mail message");
        status.setEditable( false );
        baseFrame.add( status, BorderLayout.SOUTH );

        // Set up a pane for the text of the e-mail message
        message = new JTextArea("");
        message.setLineWrap(true);
        message.setWrapStyleWord(true);
        message.setRows( 8 );
        JScrollPane jsp = new JScrollPane( message );
        jsp.createVerticalScrollBar();
        baseFrame.add( jsp, BorderLayout.CENTER );

        // Update the display
        myFrame.show();
    } // end CONSTRUCTOR

    //-------------------------------------------------------------------------
    public void actionPerformed ( ActionEvent ae ) {
        if ( ae.getSource() == aboutItem ) {
            JOptionPane.showMessageDialog(null,
               "EmailClient v1.0: A simple JAVA-based e-mail client.\n\n"+
               "(c) 2001 Edgar Troudt\nQueens College / CUNY\nRights Reserved",
               "Credits", JOptionPane.ERROR_MESSAGE);
        } // end IF

        if ( ae.getSource() == setServ ) {
             server = JOptionPane.showInputDialog
                                ("Enter the address of your SMTP server:");
        } // end IF

        if ( ae.getSource() == setFrom ) {
             fromAddr = JOptionPane.showInputDialog
                                ("Enter your e-mail address:");
        } // end IF

        if ( ae.getSource() == sendItem ) {
            System.out.println( "Send Item selected.  It has no functionality"+
                                " yet.  Here is your message:\n" );
            System.out.println( "SMTP Server:\t" + server );
            System.out.println( "From Address:\t" + fromAddr );
            System.out.println( "To Address:\t" + toField.getText() );
            System.out.println( "CC Address:\t" + ccField.getText() );
            System.out.println( "BCC Address:\t" + bccField.getText() );
            System.out.println( "Subject:\t" + subField.getText() );
            System.out.println( "Message:\n" + message.getText() );
            toField.setText("");
            ccField.setText("");
            bccField.setText("");
            subField.setText("");
            message.setText("");
            status.setText("Message sent successfully.");
        } // end IF

        if ( ae.getSource() == quitItem ) {
            if ( badJoke ) {
                JOptionPane jopt = new JOptionPane();
                JDialog jdia = jopt.createDialog(myFrame.getContentPane(),
                                             "Quitting Application");

                jopt.setMessage("Would you like some extra credit?");

                yes1 = new JButton( "Yes!" );
                yes2 = new JButton( "YES!" );

                yes1.setVisible( false );
                yes2.addMouseListener( this );

                JButton no = new JButton( "No!" );
                JButton sOpts[] = { yes2, yes1, no };
                jopt.setOptions( sOpts );

                badJoke = false;
                System.out.println( "Just messing with you.  Click the X" +
                                    " on the JOptionPane " +
                                    "then choose Quit again." );

                jdia.show();

            } // end IF
            else {
                int opt = JOptionPane.showInternalConfirmDialog
                            ( myFrame.getContentPane(),
                              "Would you like to quit now?",
                              "Quitting Application",
                              JOptionPane.YES_NO_OPTION,
                              JOptionPane.INFORMATION_MESSAGE);

                if ( opt == JOptionPane.YES_OPTION ) {
                    System.exit(0);
                } // end IF
            } // end ELSE
        } // end IF

    } // end METHOD actionPerformed


    //-------------------------------------------------------------------------
    public void mouseClicked ( MouseEvent e ) {
    } // end METHOD mouseClicked

    //-------------------------------------------------------------------------
    public void mouseEntered ( MouseEvent e ) {
        if ( e.getSource() == yes1 ) {
            yes1.removeMouseListener( this );
            yes1.setVisible( false );
            yes2.addMouseListener( this );
            yes2.setVisible( true );
        } // end IF
        else {
            yes2.removeMouseListener( this );
            yes2.setVisible( false );
            yes1.addMouseListener( this );
            yes1.setVisible( true );
        } // end ELSE
    } // end METHOD mouseEntered

    //-------------------------------------------------------------------------
    public void mouseExited ( MouseEvent e ) {
    } // end METHOD mouseExited

    //-------------------------------------------------------------------------
    public void mousePressed ( MouseEvent e ) {
    } // end METHOD mousePressed

    //-------------------------------------------------------------------------
    public void mouseReleased ( MouseEvent e ) {
    } // end METHOD mouseReleased

    //-------------------------------------------------------------------------
    public static void main ( String argv[] ) {
        new EmailClient();
    } // end METHOD main

} // end CLASS EmailClient