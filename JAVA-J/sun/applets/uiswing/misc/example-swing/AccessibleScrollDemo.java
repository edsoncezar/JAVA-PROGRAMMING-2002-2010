import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class AccessibleScrollDemo extends JPanel {
    private Rule columnView;
    private Rule rowView;

    private JToggleButton isMetric;
    private ScrollablePicture picture;

    public AccessibleScrollDemo() {
        //Load the photograph into an image icon.
        ImageIcon david = new ImageIcon("images/youngdad.jpeg");
        david.setDescription("Photograph of David McNabb in his youth.");

        //Create the row and column headers
        columnView = new Rule(Rule.HORIZONTAL, true);
        columnView.setPreferredWidth(david.getIconWidth());
        columnView.getAccessibleContext().setAccessibleName("Column Header");
        columnView.getAccessibleContext().
                setAccessibleDescription("Displays horizontal ruler for " +
                                         "measuring scroll pane client.");
        rowView = new Rule(Rule.VERTICAL, true);
        rowView.setPreferredHeight(david.getIconHeight());
        rowView.getAccessibleContext().setAccessibleName("Row Header");
        rowView.getAccessibleContext().
                setAccessibleDescription("Displays vertical ruler for " +
                                         "measuring scroll pane client.");

        //Create the corners
        JPanel buttonCorner = new JPanel();
        isMetric = new JToggleButton("cm", true);
        isMetric.setFont(new Font("SansSerif", Font.PLAIN, 11));
        isMetric.setMargin(new Insets(2,2,2,2));
        isMetric.addItemListener(new UnitsListener());
        isMetric.setToolTipText("Toggles rulers' unit of measure " +
                                "between inches and centimeters.");
        buttonCorner.add(isMetric); //Use the default FlowLayout
        buttonCorner.getAccessibleContext().
                     setAccessibleName("Upper Left Corner");

        String desc = "Fills the corner of a scroll pane " +
                      "with color for aesthetic reasons.";
        Corner lowerLeft = new Corner();
        lowerLeft.getAccessibleContext().
                  setAccessibleName("Lower Left Corner");
        lowerLeft.getAccessibleContext().setAccessibleDescription(desc);

        Corner upperRight = new Corner();
        upperRight.getAccessibleContext().
                   setAccessibleName("Upper Right Corner");
        upperRight.getAccessibleContext().setAccessibleDescription(desc);
        
        //Set up the scroll pane
        picture = new ScrollablePicture(david,
                                        columnView.getIncrement());
        picture.setToolTipText(david.getDescription());
        picture.getAccessibleContext().setAccessibleName(
                                         "Scroll pane client");

        JScrollPane pictureScrollPane = new JScrollPane(picture);
        pictureScrollPane.setPreferredSize(new Dimension(300, 250));
        pictureScrollPane.setViewportBorder(
                BorderFactory.createLineBorder(Color.black));

        pictureScrollPane.setColumnHeaderView(columnView);
        pictureScrollPane.setRowHeaderView(rowView);

        pictureScrollPane.setCorner(JScrollPane.UPPER_LEFT_CORNER,
                                    buttonCorner);
        pictureScrollPane.setCorner(JScrollPane.LOWER_LEFT_CORNER,
                                    lowerLeft);
        pictureScrollPane.setCorner(JScrollPane.UPPER_RIGHT_CORNER,
                                    upperRight);

        //Put it in this panel
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        add(pictureScrollPane);
        setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
    }

    class UnitsListener implements ItemListener {
        public void itemStateChanged(ItemEvent e) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                //turn it to metric
                rowView.setIsMetric(true);
                columnView.setIsMetric(true);
            } else {
                //turn it to inches
                rowView.setIsMetric(false);
                columnView.setIsMetric(false);
            }
            picture.setMaxUnitIncrement(rowView.getIncrement());
        }
    }


    public static void main(String s[]) {
        JFrame frame = new JFrame("AccessibleScrollDemo");
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
 
        frame.setContentPane(new AccessibleScrollDemo());
        frame.pack();
        frame.setVisible(true);
    }
}
