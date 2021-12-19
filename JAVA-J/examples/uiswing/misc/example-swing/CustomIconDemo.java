import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class CustomIconDemo extends JPanel
                            implements ActionListener {
    protected JButton b1, b2, b3;

    public CustomIconDemo() {
        Icon leftButtonIcon = new ArrowIcon(SwingConstants.RIGHT);
        Icon middleButtonIcon = new ImageIcon("images/middle.gif");
        Icon rightButtonIcon = new ArrowIcon(SwingConstants.LEFT);

        b1 = new JButton("Disable middle button", leftButtonIcon);
        b1.setVerticalTextPosition(AbstractButton.CENTER);
        b1.setHorizontalTextPosition(AbstractButton.LEFT);
        b1.setMnemonic(KeyEvent.VK_D);
        b1.setActionCommand("disable");

        b2 = new JButton("Middle button", middleButtonIcon);
        b2.setVerticalTextPosition(AbstractButton.BOTTOM);
        b2.setHorizontalTextPosition(AbstractButton.CENTER);
        b2.setMnemonic(KeyEvent.VK_M);

        b3 = new JButton("Enable middle button", rightButtonIcon);
        //Use the default text position of CENTER, RIGHT.
        b3.setMnemonic(KeyEvent.VK_E);
        b3.setActionCommand("enable");
        b3.setEnabled(false);

        //Listen for actions on buttons 1 and 3.
        b1.addActionListener(this);
        b3.addActionListener(this);

        b1.setToolTipText("Click this button to disable the middle button.");
        b2.setToolTipText("This middle button does nothing when you click it.");
        b3.setToolTipText("Click this button to enable the middle button.");

        //Add Components to this container, using the default FlowLayout. 
        add(b1);
        add(b2);
        add(b3);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("disable")) {
            b2.setEnabled(false);
            b1.setEnabled(false);
            b3.setEnabled(true);
        } else { 
            b2.setEnabled(true);
            b1.setEnabled(true);
            b3.setEnabled(false);
        }
    }
    
    public static void main(String[] args) {
        JFrame frame = new JFrame("CustomIconDemo");
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        frame.getContentPane().add(new CustomIconDemo(),
                                   BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }
}
