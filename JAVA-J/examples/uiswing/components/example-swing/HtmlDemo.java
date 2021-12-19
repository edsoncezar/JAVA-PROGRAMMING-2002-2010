import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class HtmlDemo extends JPanel {

    JLabel theLabel;
    JTextArea htmlTextArea;

    public HtmlDemo() {
        String initialText = "<html>\n" +
                "Color and font test:\n" +
                "<ul>\n" +
                "<li><font color=red>red</font>\n" +
                "<li><font color=blue>blue</font>\n" +
                "<li><font color=green>green</font>\n" +
                "<li><font size=-2>small</font>\n" +
                "<li><font size=+2>large</font>\n" +
                "<li><i>italic</i>\n" +
                "<li><b>bold</b>\n" +
                "</ul>\n";

        htmlTextArea = new JTextArea(10, 20);
        htmlTextArea.setText(initialText);
        JScrollPane scrollPane = new JScrollPane(htmlTextArea);

        JButton changeTheLabel = new JButton("Change the label");
        changeTheLabel.setMnemonic(KeyEvent.VK_C);
        changeTheLabel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    theLabel.setText(htmlTextArea.getText());
                } catch (Throwable exc) {
                    JOptionPane.showMessageDialog(
                            HtmlDemo.this,
                            "The HTML you specified was invalid.");
                }
            }
        });
        changeTheLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        theLabel = new JLabel(initialText) {
            public Dimension getPreferredSize() {
                return new Dimension(200, 200);
            }
            public Dimension getMinimumSize() {
                return new Dimension(200, 200);
            }
            public Dimension getMaximumSize() {
                return new Dimension(200, 200);
            }
        };
        theLabel.setVerticalAlignment(SwingConstants.CENTER);
        theLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                    "Edit the HTML, then click the button"),
                BorderFactory.createEmptyBorder(10,10,10,10)));
        leftPanel.add(scrollPane);
        leftPanel.add(Box.createRigidArea(new Dimension(0,10)));
        leftPanel.add(changeTheLabel);

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createTitledBorder("A label with HTML"),
                        BorderFactory.createEmptyBorder(10,10,10,10)));
        rightPanel.add(theLabel);

        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        add(leftPanel);
        add(Box.createRigidArea(new Dimension(10,0)));
        add(rightPanel);
    }

    public static void main(String args[]) {
        JFrame f = new JFrame("HtmlDemo");

        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        f.getContentPane().add(new HtmlDemo());
        f.pack();
        f.setVisible(true);
    }
}
