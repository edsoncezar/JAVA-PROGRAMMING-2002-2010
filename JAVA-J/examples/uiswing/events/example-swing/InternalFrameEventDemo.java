import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class InternalFrameEventDemo 
                     extends JFrame
                     implements InternalFrameListener,
                                ActionListener {
    JTextArea display;
    JDesktopPane desktop;
    JInternalFrame displayWindow;
    JInternalFrame listenedToWindow;
    static final String SHOW = "show";
    static final String CLEAR = "clear";
    String newline = "\n";
    static final int desktopWidth = 500;
    static final int desktopHeight = 300;

    public InternalFrameEventDemo(String title) {
        super(title);

        //Set up the GUI.
        desktop = new JDesktopPane();
        desktop.putClientProperty("JDesktopPane.dragMode",
                                  "outline");
        //Because we use pack, it's not enough to call setSize.
        //We must set the desktop's preferred size.
        desktop.setPreferredSize(new Dimension(desktopWidth, desktopHeight));
        setContentPane(desktop);

        createDisplayWindow();
        desktop.add(displayWindow); //DON'T FORGET THIS!!!
        Dimension displaySize = displayWindow.getSize();
        displayWindow.setSize(desktopWidth, displaySize.height);
    }

    //Create the window that displays event information.
    protected void createDisplayWindow() {
        JButton b1 = new JButton("Show internal frame");
        b1.setActionCommand(SHOW);
        b1.addActionListener(this);

        JButton b2 = new JButton("Clear event info");
        b2.setActionCommand(CLEAR);
        b2.addActionListener(this);

        display = new JTextArea(3, 30);
        display.setEditable(false);
        JScrollPane textScroller = new JScrollPane(display);
        //Have to supply a preferred size, or else the scroll
        //area will try to stay as large as the text area.
        textScroller.setPreferredSize(new Dimension(200, 75));
        textScroller.setMinimumSize(new Dimension(10, 10));

        displayWindow = new JInternalFrame("Event Watcher",
                                           true,  //resizable
                                           false, //not closable
                                           false, //not maximizable
                                           true); //iconifiable
        JPanel contentPane = new JPanel();
        contentPane.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        contentPane.setLayout(new BoxLayout(contentPane,
                                            BoxLayout.Y_AXIS));
        b1.setAlignmentX(CENTER_ALIGNMENT);
        contentPane.add(b1);
        contentPane.add(Box.createRigidArea(new Dimension(0, 5)));
        contentPane.add(textScroller);
        contentPane.add(Box.createRigidArea(new Dimension(0, 5)));
        b2.setAlignmentX(CENTER_ALIGNMENT);
        contentPane.add(b2);

        displayWindow.setContentPane(contentPane);
        displayWindow.pack();
        displayWindow.show();  //necessary as of 1.3
    }

    //Create the listened-to window.
    protected void createListenedToWindow() {
        listenedToWindow = new JInternalFrame("Event Generator",
                                              true,  //resizable
                                              true,  //closable
                                              true,  //maximizable
                                              true); //iconifiable
        //The next statement is necessary to work around bug 4138031.
        listenedToWindow.setDefaultCloseOperation(
                                WindowConstants.DISPOSE_ON_CLOSE);
        listenedToWindow.setSize(300, 100);
    }

    public void internalFrameClosing(InternalFrameEvent e) {
        displayMessage("Internal frame closing", e);
    }

    public void internalFrameClosed(InternalFrameEvent e) {
        displayMessage("Internal frame closed", e);
        listenedToWindow = null;
    }

    public void internalFrameOpened(InternalFrameEvent e) {
        //XXX: We don't seem to get any of these.
        displayMessage("Internal frame opened", e);
    }

    public void internalFrameIconified(InternalFrameEvent e) {
        displayMessage("Internal frame iconified", e);
    }

    public void internalFrameDeiconified(InternalFrameEvent e) {
        displayMessage("Internal frame deiconified", e);
    }

    public void internalFrameActivated(InternalFrameEvent e) {
        displayMessage("Internal frame activated", e);
    }

    public void internalFrameDeactivated(InternalFrameEvent e) {
        displayMessage("Internal frame deactivated", e);
    }

    void displayMessage(String prefix, InternalFrameEvent e) {
        String s = prefix + ": " + e.getSource(); 
        display.append(s + newline);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(SHOW)) {
            //XXX: Can't reuse internal frame (bug 4138031).
            //listenedToWindow.setVisible(true);

            //XXX: Instead, create a new internal frame.
            if (listenedToWindow == null) {
                createListenedToWindow();
                listenedToWindow.addInternalFrameListener(this);
                desktop.add(listenedToWindow);
                listenedToWindow.setLocation(
                    desktopWidth/2 - listenedToWindow.getWidth()/2,
                    desktopHeight - listenedToWindow.getHeight());
                listenedToWindow.show();  //necessary as of 1.3
            }
        } else {
            display.setText("");
        }
    }

    public static void main(String[] args) {
        JFrame frame = new InternalFrameEventDemo(
                "InternalFrameEventDemo");

        //Quit this app when the big window closes.
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        frame.pack();
        frame.setVisible(true);
    }
}
