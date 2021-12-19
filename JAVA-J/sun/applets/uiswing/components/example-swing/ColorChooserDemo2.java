import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.colorchooser.*;

public class ColorChooserDemo2 extends JFrame {
    public ColorChooserDemo2() {
        super("ColorChooserDemo2");

        //Set up banner to use as custom preview panel
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

        //Set up color chooser for setting background color
        JPanel panel = new JPanel();
        JButton bcc = new JButton("Show Color Chooser...");
        bcc.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    Color newColor = JColorChooser.showDialog(
                                                ColorChooserDemo2.this,
                                                "Choose Background Color",
                                                banner.getBackground());
                    if (newColor != null) {
                        banner.setBackground(newColor);
                    }
                }
            }
        );
        panel.add(bcc);
        panel.setBorder(BorderFactory.createTitledBorder(
                                "Choose Background Color"));

        //Set up color chooser for setting text color
        final JColorChooser tcc = new JColorChooser();
        tcc.getSelectionModel().addChangeListener(
            new ChangeListener() {
                public void stateChanged(ChangeEvent e) {
                    Color newColor = tcc.getColor();
                    banner.setForeground(newColor);
                }
            }    
        );
        tcc.setBorder(BorderFactory.createTitledBorder("Choose Text Color"));

        //Remove the preview panel
        //XXXXXX: bug #4166059, in Swing 1.1 beta 2 and earlier
        //setPreviewPanel throws an exception. no known workaround.
        tcc.setPreviewPanel(new JPanel());

        //Override the chooser panels with our own
        AbstractColorChooserPanel panels[] = { new CrayonPanel() };
        tcc.setChooserPanels(panels);
        tcc.setColor(banner.getForeground());

        Container contentPane = getContentPane();
        contentPane.add(bannerPanel, BorderLayout.NORTH);
        contentPane.add(panel, BorderLayout.CENTER);
        contentPane.add(tcc, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {

        JFrame frame = new ColorChooserDemo2();

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        frame.pack();
        frame.setVisible(true);
    }
}
