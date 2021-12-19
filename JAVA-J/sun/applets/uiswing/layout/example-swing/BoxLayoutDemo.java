import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class BoxLayoutDemo {
    protected static int NUM_COMPONENTS = 3;
    protected static float[] xAlignment = {Component.LEFT_ALIGNMENT,
                                 Component.CENTER_ALIGNMENT,
                                 Component.RIGHT_ALIGNMENT};
    protected static float[] hue = {0.0f, 0.33f, 0.67f};
    protected static boolean restrictSize = true;
    protected static boolean sizeIsRandom = false;
    protected static BLDComponent[] bldComponent = 
        new BLDComponent[NUM_COMPONENTS];

    public static void main(String[] args) {
        final JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        //Create the rectangles.
        int shortSideSize = 15;
        for (int i = 0; i < NUM_COMPONENTS; i++) {
            if (sizeIsRandom) {
                shortSideSize = (int)(30.0 * Math.random()) + 30;
            } else {
                shortSideSize += 10;
            }
            bldComponent[i] = new BLDComponent(xAlignment[i], hue[i], 
                                             shortSideSize, 
                                             restrictSize,
                                             sizeIsRandom,
                                             String.valueOf(i));
            panel.add(bldComponent[i]);
        }

        //Create the instructions.
        JLabel label = new JLabel("Click a rectangle to "
                                + "change its X alignment.");
        JCheckBox cb = new JCheckBox("Restrict maximum rectangle size.");
        cb.setSelected(restrictSize);
        cb.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    restrictSize = true;
                } else {
                    restrictSize = false;
                }
                notifyBLDComponents();
            }
        });

        JFrame f = new JFrame("BoxLayoutDemo");
        Container contentPane = f.getContentPane();
        contentPane.add(panel, BorderLayout.CENTER);
        panel.setBorder(BorderFactory.createLineBorder(Color.red));

        Box box = Box.createVerticalBox();
        box.add(label);
        box.add(cb);

        contentPane.add(box, BorderLayout.SOUTH);
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        f.pack();
        f.setVisible(true);
    }

    static public void notifyBLDComponents() {
        for (int i = 0; i < NUM_COMPONENTS; i++) {
            bldComponent[i].setSizeRestriction(restrictSize);
        }
        bldComponent[0].revalidate();
    }
}
