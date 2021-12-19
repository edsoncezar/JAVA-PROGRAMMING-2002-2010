import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.*;
import java.text.*;

public class ComboBoxDemo2 extends JPanel {
    static JFrame frame;
    JLabel result;
    String currentPattern;

    public ComboBoxDemo2() {
        String[] patternExamples = { 
                 "dd MMMMM yyyy",
                 "dd.MM.yy", 
                 "MM/dd/yy",
                 "yyyy.MM.dd G 'at' hh:mm:ss z",
                 "EEE, MMM d, ''yy",
                 "h:mm a",
                 "H:mm:ss:SSS",
                 "K:mm a,z",
                 "yyyy.MMMMM.dd GGG hh:mm aaa"
                 };

        currentPattern = patternExamples[0];
 
        // Set up the UI for selecting a pattern.
        JLabel patternLabel1 = new JLabel("Enter the pattern string or");
        JLabel patternLabel2 = new JLabel("select one from the list:");
    
        JComboBox patternList = new JComboBox(patternExamples);
        patternList.setEditable(true);
        patternList.setAlignmentX(Component.LEFT_ALIGNMENT);
        patternList.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JComboBox cb = (JComboBox)e.getSource();
                String newSelection = (String)cb.getSelectedItem();
                currentPattern = newSelection;
                reformat();
            }
        });
    
        // Create the UI for displaying result
        JLabel resultLabel = new JLabel("Current Date/Time", JLabel.LEFT);
        result = new JLabel(" ");
        result.setForeground(Color.black);
        result.setBorder(BorderFactory.createCompoundBorder(
             BorderFactory.createLineBorder(Color.black),
             BorderFactory.createEmptyBorder(5,5,5,5)
        ));
    
        // Lay out everything
        JPanel patternPanel = new JPanel();
        patternPanel.setLayout(new BoxLayout(patternPanel, BoxLayout.Y_AXIS));
        patternPanel.add(patternLabel1);
        patternPanel.add(patternLabel2);
        patternPanel.add(patternList);
    
        JPanel resultPanel = new JPanel();
        resultPanel.setLayout(new GridLayout(0, 1));
        resultPanel.add(resultLabel);
        resultPanel.add(result);
    
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        patternPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        resultPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
    
        add(patternPanel);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(resultPanel);
    
        setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
 
        reformat();
    } // constructor

    /** Formats and displays today's date. */
    public void reformat() {
        Date today = new Date();
        SimpleDateFormat formatter = 
           new SimpleDateFormat(currentPattern);
        try {
            String dateString = formatter.format(today);
            result.setForeground(Color.black);
            result.setText(dateString);
        } catch (IllegalArgumentException iae) {
            result.setForeground(Color.red);
            result.setText("Error: " + iae.getMessage());
        }
    }

    public static void main(String s[]) {
         frame = new JFrame("ComboBoxDemo2");
         frame.addWindowListener(new WindowAdapter() {
             public void windowClosing(WindowEvent e) {
                 System.exit(0);
             }
         });
 
         frame.setContentPane(new ComboBoxDemo2());
         frame.pack();
         frame.setVisible(true);
    }
}
