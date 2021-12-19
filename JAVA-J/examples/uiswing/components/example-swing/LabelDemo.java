import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.ImageIcon;

public class LabelDemo extends JPanel {
    JLabel label1, label2, label3;

    public LabelDemo() {
        ImageIcon icon = new ImageIcon("images/middle.gif",
                                       "a pretty but meaningless splat");
        setLayout(new GridLayout(3,1));     //3 rows, 1 column

        label1 = new JLabel("Image and Text",
                            icon,
                            JLabel.CENTER);
        //Set the position of the text, relative to the icon:
        label1.setVerticalTextPosition(JLabel.BOTTOM);
        label1.setHorizontalTextPosition(JLabel.CENTER);

        label2 = new JLabel("Text-Only Label");

        label3 = new JLabel(icon);

        //Add labels to the JBufferedPane. 
        add(label1);
        add(label2);
        add(label3);
    }

    public static void main(String[] args) {
        /*
         * Create a window.  Use JFrame since this window will include 
         * lightweight components.
         */
        JFrame frame = new JFrame("LabelDemo");

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        frame.setContentPane(new LabelDemo());
        frame.pack();
        frame.setVisible(true);
    }
}
