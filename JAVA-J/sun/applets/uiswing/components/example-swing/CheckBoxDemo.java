import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class CheckBoxDemo extends JPanel {
    JCheckBox chinButton;
    JCheckBox glassesButton;
    JCheckBox hairButton;
    JCheckBox teethButton;

    /*
     * Four accessory choices provide for 16 different
     * combinations. The image for each combination is
     * contained in a separate image file whose name indicates
     * the accessories. The filenames are "geek-XXXX.gif"
     * where XXXX can be one of the following 16 choices.
     * The "choices" StringBuffer contains the string that
     * indicates the current selection and is used to generate
     * the file name of the image to display.
      
       ----             // zero accessories
      
       c---             // one accessory
       -g--
       --h-
       ---t
      
       cg--             // two accessories
       c-h-
       c--t
       -gh-
       -g-t
       --ht
      
       -ght             // three accessories
       c-ht
       cg-t
       cgh-
      
       cght             // all accessories
     */

    StringBuffer choices;
    JLabel pictureLabel;

    public CheckBoxDemo() {

        // Create the check boxes
        chinButton = new JCheckBox("Chin");
        chinButton.setMnemonic(KeyEvent.VK_C); 
        chinButton.setSelected(true);

        glassesButton = new JCheckBox("Glasses");
        glassesButton.setMnemonic(KeyEvent.VK_G); 
        glassesButton.setSelected(true);

        hairButton = new JCheckBox("Hair");
        hairButton.setMnemonic(KeyEvent.VK_H); 
        hairButton.setSelected(true);

        teethButton = new JCheckBox("Teeth");
        teethButton.setMnemonic(KeyEvent.VK_T); 
        teethButton.setSelected(true);

        // Register a listener for the check boxes.
        CheckBoxListener myListener = new CheckBoxListener();
        chinButton.addItemListener(myListener);
        glassesButton.addItemListener(myListener);
        hairButton.addItemListener(myListener);
        teethButton.addItemListener(myListener);

        // Indicates what's on the geek.
        choices = new StringBuffer("cght");

        // Set up the picture label
        pictureLabel = new JLabel(new ImageIcon(
                                        "images/geek/geek-" 
                                         + choices.toString()
                                         + ".gif"));
        pictureLabel.setToolTipText(choices.toString());

        // Put the check boxes in a column in a panel
        JPanel checkPanel = new JPanel();
        checkPanel.setLayout(new GridLayout(0, 1));
        checkPanel.add(chinButton);
        checkPanel.add(glassesButton);
        checkPanel.add(hairButton);
        checkPanel.add(teethButton);

        setLayout(new BorderLayout());
        add(checkPanel, BorderLayout.WEST);
        add(pictureLabel, BorderLayout.CENTER);
        setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
    }


    /** Listens to the check boxes. */
    class CheckBoxListener implements ItemListener {
        public void itemStateChanged(ItemEvent e) {
            int index = 0;
            char c = '-';
            Object source = e.getItemSelectable();

            if (source == chinButton) {
                index = 0;
                c = 'c';
            } else if (source == glassesButton) {
                index = 1;
                c = 'g';
            } else if (source == hairButton) {
                index = 2;
                c = 'h';
            } else if (source == teethButton) {
                index = 3;
                c = 't';
            }

            if (e.getStateChange() == ItemEvent.DESELECTED)
                c = '-';

            choices.setCharAt(index, c);
            pictureLabel.setIcon(new ImageIcon(
                                        "images/geek/geek-"
                                        + choices.toString()
                                        + ".gif"));
            pictureLabel.setToolTipText(choices.toString());
        }
    }

    public static void main(String s[]) {
         JFrame frame = new JFrame("CheckBoxDemo");
         frame.addWindowListener(new WindowAdapter() {
             public void windowClosing(WindowEvent e) {
                 System.exit(0);
             }
         });
 
         frame.setContentPane(new CheckBoxDemo());
         frame.pack();
         frame.setVisible(true);
    }

}
