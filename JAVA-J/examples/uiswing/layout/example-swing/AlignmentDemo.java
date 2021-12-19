/* 
 * Shows how to specify alignments when you're using
 * a BoxLayout for components with maximum sizes
 * and different default alignments. 
 */

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class AlignmentDemo extends JFrame {
    public AlignmentDemo() {
        super("AlignmentDemo");

        JTabbedPane tabbedPane = new JTabbedPane();

        JPanel buttonRow = new JPanel();
        //Use default FlowLayout.
        buttonRow.add(createButtonRow(false));
        buttonRow.add(createButtonRow(true));
        tabbedPane.addTab("Altering alignments", buttonRow);

        JPanel labelAndComponent = new JPanel();
        //Use default FlowLayout.
        labelAndComponent.add(createLabelAndComponent(false));
        labelAndComponent.add(createLabelAndComponent(true));
        tabbedPane.addTab("X alignment mismatch", labelAndComponent);

        JPanel buttonAndComponent = new JPanel();
        //Use default FlowLayout.
        buttonAndComponent.add(createYAlignmentExample(false));
        buttonAndComponent.add(createYAlignmentExample(true));
        tabbedPane.addTab("Y alignment mismatch", buttonAndComponent);

        //Add tabbedPane to this frame.
        getContentPane().add(tabbedPane, BorderLayout.CENTER);
    }

    protected JPanel createButtonRow(boolean changeAlignment) {
        JButton button1 = new JButton("A JButton",
                                      new ImageIcon("images/middle.gif"));
        button1.setVerticalTextPosition(AbstractButton.BOTTOM);
        button1.setHorizontalTextPosition(AbstractButton.CENTER);

        JButton button2 = new JButton("Another JButton",
                                      new ImageIcon("images/geek-cght.gif"));
        button2.setVerticalTextPosition(AbstractButton.BOTTOM);
        button2.setHorizontalTextPosition(AbstractButton.CENTER);

        String title;
        if (changeAlignment) {
            title = "Desired";
            button1.setAlignmentY(BOTTOM_ALIGNMENT);
            button2.setAlignmentY(BOTTOM_ALIGNMENT);
        } else {
            title = "Default";
        }

        JPanel pane = new JPanel();
        pane.setBorder(BorderFactory.createTitledBorder(title));
        pane.setLayout(new BoxLayout(pane, BoxLayout.X_AXIS));
        pane.add(button1);
        pane.add(button2);
        return pane;
    }

    protected JPanel createLabelAndComponent(boolean doItRight) {
        JPanel pane = new JPanel();

        JComponent component = new JPanel();
        Dimension size = new Dimension(150,100);
        component.setMaximumSize(size);
        component.setPreferredSize(size);
        component.setMinimumSize(size);
        TitledBorder border = new TitledBorder(
                                  new LineBorder(Color.black),
                                  "A JPanel",
                                  TitledBorder.CENTER,
                                  TitledBorder.BELOW_TOP);
        border.setTitleColor(Color.black);
        component.setBorder(border);

        JLabel label = new JLabel("This is a JLabel");
        String title;
        if (doItRight) {
            title = "Matched";
            label.setAlignmentX(CENTER_ALIGNMENT);
        } else {
            title = "Mismatched";
        }

        pane.setBorder(BorderFactory.createTitledBorder(title));
        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
        pane.add(label);
        pane.add(component);
        return pane;
    }

    protected JPanel createYAlignmentExample(boolean doItRight) {
        JPanel pane = new JPanel();
        String title;

        JComponent component1 = new JPanel();
        Dimension size = new Dimension(100, 50);
        component1.setMaximumSize(size);
        component1.setPreferredSize(size);
        component1.setMinimumSize(size);
        TitledBorder border = new TitledBorder(
                                  new LineBorder(Color.black),
                                  "A JPanel",
                                  TitledBorder.CENTER,
                                  TitledBorder.BELOW_TOP);
        border.setTitleColor(Color.black);
        component1.setBorder(border);

        JComponent component2 = new JPanel();
        size = new Dimension(100, 50);
        component2.setMaximumSize(size);
        component2.setPreferredSize(size);
        component2.setMinimumSize(size);
        border = new TitledBorder(new LineBorder(Color.black),
                                  "A JPanel",
                                  TitledBorder.CENTER,
                                  TitledBorder.BELOW_TOP);
        border.setTitleColor(Color.black);
        component2.setBorder(border);

        if (doItRight) {
            title = "Matched";
        } else {
            component1.setAlignmentY(TOP_ALIGNMENT);
            title = "Mismatched";
        }

        pane.setBorder(BorderFactory.createTitledBorder(title));
        pane.setLayout(new BoxLayout(pane, BoxLayout.X_AXIS));
        pane.add(component1);
        pane.add(component2);
        return pane;
    }

    public static void main(String[] args) {
        JFrame frame = new AlignmentDemo();
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        frame.pack();
        frame.setVisible(true);
    }
}
