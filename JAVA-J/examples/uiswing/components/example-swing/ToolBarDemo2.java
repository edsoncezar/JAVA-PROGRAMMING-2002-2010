import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.ImageIcon;

import javax.swing.JTextField;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JPanel;

import java.awt.*;
import java.awt.event.*;

public class ToolBarDemo2 extends JFrame {
    protected JTextArea textArea;
    protected String newline = "\n";

    public ToolBarDemo2() {
        //Do frame stuff.
        super("ToolBarDemo2");
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        //Create the toolbar.
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);
        addButtons(toolBar);

        //Create the text area used for output.
        textArea = new JTextArea(5, 30);
        JScrollPane scrollPane = new JScrollPane(textArea);

        //Lay out the content pane.
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        contentPane.setPreferredSize(new Dimension(400, 100));
        contentPane.add(toolBar, BorderLayout.NORTH);
        contentPane.add(scrollPane, BorderLayout.CENTER);
        setContentPane(contentPane);
    }

    protected void addButtons(JToolBar toolBar) {
        JButton button = null;

        //first button
        button = new JButton(new ImageIcon("images/left.gif"));
        button.setToolTipText("This is the left button");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                displayResult("Action for first button");
            }
        });
        toolBar.add(button);

        //second button
        button = new JButton(new ImageIcon("images/middle.gif"));
        button.setToolTipText("This is the middle button");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                displayResult("Action for second button");
            }
        });
        toolBar.add(button);

        //third button
        button = new JButton(new ImageIcon("images/right.gif"));
        button.setToolTipText("This is the right button");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                displayResult("Action for third button");
            }
        });
        toolBar.add(button);

        //separator
        toolBar.addSeparator();

        //fourth button
        button = new JButton("Another button");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                displayResult("Action for fourth button");
            }
        });
        toolBar.add(button);

        //fifth component is NOT a button!
        JTextField textField = new JTextField("A text field");
        //Action handler implementation would go here.
        toolBar.add(textField);
    }

    protected void displayResult(String actionDescription) {
        textArea.append(actionDescription + newline);
    }

    public static void main(String[] args) {
        ToolBarDemo2 frame = new ToolBarDemo2();
        frame.pack();
        frame.setVisible(true);
    }
}
