/* 
 * Swing 1.1 version (compatible with both JDK 1.1 and Java 2).
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;

public class AppletDemo extends JApplet
                        implements ActionListener {
    protected JButton b1, b2, b3;

    protected static final String DISABLE = "disable";
    protected static final String ENABLE = "enable";

    protected String leftButtonFilename = "images/right.gif";
    protected String middleButtonFilename = "images/middle.gif";
    protected String rightButtonFilename = "images/left.gif";

    private boolean inAnApplet = true;
    URL codeBase; //used for applet version only

    //Hack to avoid ugly message about system event access check.
    public AppletDemo() {
        this(true);
    }

    public AppletDemo(boolean inAnApplet) {
        this.inAnApplet = inAnApplet;
        if (inAnApplet) {
            getRootPane().putClientProperty("defeatSystemEventQueueCheck",
                                            Boolean.TRUE);
        }
    }

    public void init() {
        setContentPane(makeContentPane());
    }

    public Container makeContentPane() {
        ImageIcon leftButtonIcon;
        ImageIcon middleButtonIcon;
        ImageIcon rightButtonIcon;

        if (inAnApplet) {
            URL leftButtonURL = getURL(leftButtonFilename);
            URL middleButtonURL = getURL(middleButtonFilename);
            URL rightButtonURL = getURL(rightButtonFilename);

            leftButtonIcon = new ImageIcon(leftButtonURL);
            middleButtonIcon = new ImageIcon(middleButtonURL);
            rightButtonIcon = new ImageIcon(rightButtonURL);
        } else {
            leftButtonIcon = new ImageIcon(leftButtonFilename);
            middleButtonIcon = new ImageIcon(middleButtonFilename);
            rightButtonIcon = new ImageIcon(rightButtonFilename);
        }

        b1 = new JButton("Disable middle button", leftButtonIcon);
        b1.setVerticalTextPosition(AbstractButton.CENTER);
        b1.setHorizontalTextPosition(AbstractButton.LEFT);
        b1.setMnemonic(KeyEvent.VK_D);
        b1.setActionCommand(DISABLE);

        b2 = new JButton("Middle button", middleButtonIcon);
        b2.setVerticalTextPosition(AbstractButton.BOTTOM);
        b2.setHorizontalTextPosition(AbstractButton.CENTER);
        b2.setMnemonic(KeyEvent.VK_M);

        b3 = new JButton("Enable middle button", rightButtonIcon);
        //Use the default text position of CENTER, RIGHT.
        b3.setMnemonic(KeyEvent.VK_E);
        b3.setActionCommand(ENABLE);
        b3.setEnabled(false);

        //Listen for actions on buttons 1 and 3.
        b1.addActionListener(this);
        b3.addActionListener(this);

        b1.setToolTipText("Click this button to disable the middle button.");
        b2.setToolTipText("This middle button does nothing when you click it.");
        b3.setToolTipText("Click this button to enable the middle button.");

        //Add Components to a JPanel, using the default FlowLayout. 
        JPanel pane = new JPanel();
        pane.add(b1);
        pane.add(b2);
        pane.add(b3);
        pane.setBackground(new Color(255,255,204));
        pane.setBorder(BorderFactory.createMatteBorder(1,1,2,2,Color.black));

        return pane;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(DISABLE)) {
            b2.setEnabled(false);
            b1.setEnabled(false);
            b3.setEnabled(true);
        } else {
            b2.setEnabled(true);
            b1.setEnabled(true);
            b3.setEnabled(false);
        }
    }
    
    /* One day, JApplet will make this method obsolete. */
    protected URL getURL(String filename) {
        URL url = null;
        if (codeBase == null) {
            codeBase = getCodeBase();
        }

        try {
            url = new URL(codeBase, filename);
        } catch (java.net.MalformedURLException e) {
            System.out.println("Couldn't create image: badly specified URL");
            return null;
        }

        return url;
    }
    
    public static void main(String[] args) {
        JFrame frame = new JFrame("Application version: AppletDemo");

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        AppletDemo applet = new AppletDemo(false);
        frame.setContentPane(applet.makeContentPane());
        frame.pack();
        frame.setVisible(true);
    }
}
