import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.colorchooser.*;

public class ColorChooserDemo extends JFrame {
    public ColorChooserDemo() {
        super("ColorChooserDemo");

        //Set up the banner at the top of the window
        final JLabel banner = new JLabel("Welcome to the Tutorial Zone!",
                                         JLabel.CENTER);
        banner.setForeground(Color.yellow);
        banner.setBackground(Color.blue);
        banner.setOpaque(true);
        banner.setFont(new Font("SansSerif", Font.BOLD, 24));
        banner.setPreferredSize(new Dimension(100, 65));

        JPanel bannerPanel = new JPanel(new BorderLayout());
        bannerPanel.add(banner, BorderLayout.CENTER);
        bannerPanel.setBorder(BorderFactory.createTitledBorder("Banner"));

        //Set up color chooser for setting text color
        final JColorChooser tcc = new JColorChooser(banner.getForeground());
        tcc.getSelectionModel().addChangeListener(
            new ChangeListener() {
                public void stateChanged(ChangeEvent e) {
                    Color newColor = tcc.getColor();
                    banner.setForeground(newColor);
                }
            }
        );
        tcc.setBorder(BorderFactory.createTitledBorder(
                                             "Choose Text Color"));

        //Add the components to the demo frame
        Container contentPane = getContentPane();
        contentPane.add(bannerPanel, BorderLayout.CENTER);
        contentPane.add(tcc, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        JFrame frame = new ColorChooserDemo();
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {System.exit(0);}
        });

        frame.pack();
        frame.setVisible(true);
    }
}
