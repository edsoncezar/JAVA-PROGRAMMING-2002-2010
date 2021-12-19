import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

import java.util.*;

public class SplitPaneDemo2 extends JFrame
                            implements ListSelectionListener {
    private JLabel label;

    public SplitPaneDemo2() {
        super("SplitPaneDemo2");

        //Create an instance of SplitPaneDemo
        SplitPaneDemo splitPaneDemo = new SplitPaneDemo();
        JSplitPane top = splitPaneDemo.getSplitPane();
        splitPaneDemo.getImageList().addListSelectionListener(this);

        //XXXX: Bug #4131528, borders on nested split panes accumulate.
        //Workaround: Set the border on any split pane within
        //another split pane to null. Components within nested split
        //panes need to have their own border for this to work well.
        top.setBorder(null);

        //Create a regular old label
        label = new JLabel("Click on an image name in the list.",
                           JLabel.CENTER);

        //Create a split pane and put "top" (a split pane)
        //and JLabel instance in it.
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                                              top, label);
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(180);

        //Provide minimum sizes for the two components in the split pane
        top.setMinimumSize(new Dimension(100, 50));
        label.setMinimumSize(new Dimension(100, 30));

        //Add the split pane to this frame
        getContentPane().add(splitPane);
    }

    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting())
            return;

        JList theList = (JList)e.getSource();
        if (theList.isSelectionEmpty()) {
            label.setText("Nothing selected.");
        } else {
            int index = theList.getSelectedIndex();
            label.setText("Selected image number " + index);
        }
    } 

    public static void main(String s[]) {
        JFrame frame = new SplitPaneDemo2();

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {System.exit(0);}
        });
 
        frame.pack();
        frame.setVisible(true);
    }
}
