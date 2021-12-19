import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class CustomComboBoxDemo extends JPanel {
    ImageIcon images[];

    public CustomComboBoxDemo() {
        //Load the pet images
        String[] petStrings = {"Bird", "Cat", "Dog", "Rabbit", "Pig"};
        images = new ImageIcon[petStrings.length];
        for (int i = 0; i < petStrings.length; i++) {
            images[i] = new ImageIcon("images/" + petStrings[i] + ".gif");
            images[i].setDescription(petStrings[i]);
        }

        // Create the combo box
        JComboBox petList = new JComboBox(images);
        ComboBoxRenderer renderer= new ComboBoxRenderer();
        renderer.setPreferredSize(new Dimension(200, 130));
        petList.setRenderer(renderer);
        petList.setMaximumRowCount(3);

        // Layout the demo
        setLayout(new BorderLayout());
        add(petList, BorderLayout.NORTH);
        setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
    }

    public static void main(String s[]) {
        JFrame frame = new JFrame("CustomComboBoxDemo");
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {System.exit(0);}
        });
 
        frame.getContentPane().add(new CustomComboBoxDemo(),
                                   BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }

    class ComboBoxRenderer extends JLabel implements ListCellRenderer {
        public ComboBoxRenderer() {
            setOpaque(true);
            setHorizontalAlignment(CENTER);
            setVerticalAlignment(CENTER);
        }

        public Component getListCellRendererComponent(
                                           JList list,
                                           Object value,
                                           int index,
                                           boolean isSelected,
                                           boolean cellHasFocus) {
            if (isSelected) {
                setBackground(list.getSelectionBackground());
                setForeground(list.getSelectionForeground());
            } else {
                setBackground(list.getBackground());
                setForeground(list.getForeground());
            }

            ImageIcon icon = (ImageIcon)value;
            setText(icon.getDescription());
            setIcon(icon);
            return this;
        }
    }
}
