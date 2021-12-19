import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.colorchooser.*;

public class CrayonPanel extends AbstractColorChooserPanel {
    JToggleButton redCrayon;
    JToggleButton yellowCrayon;
    JToggleButton greenCrayon;
    JToggleButton blueCrayon;

    public CrayonPanel() {
        super();
    }

    public void updateChooser() {
        Color color = getColorFromModel();
        if (color.equals(Color.red)) {
            redCrayon.setSelected(true);
        } else if (color.equals(Color.yellow)) {
            yellowCrayon.setSelected(true);
        } else if (color.equals(Color.green)) {
            greenCrayon.setSelected(true);
        } else if (color.equals(Color.blue)) {
            blueCrayon.setSelected(true);
        }
    }

    protected void buildChooser() {
        setLayout(new GridLayout(0, 1));

        CrayonListener cl = new CrayonListener();
        ButtonGroup boxOfCrayons = new ButtonGroup();
        Border border = BorderFactory.createEmptyBorder(4,4,4,4);

        redCrayon = new JToggleButton(new ImageIcon("images/red.gif"));
        redCrayon.setActionCommand("red");
        redCrayon.addActionListener(cl);
        redCrayon.setBorder(border);
        boxOfCrayons.add(redCrayon);
        add(redCrayon);

        yellowCrayon = new JToggleButton(new ImageIcon("images/yellow.gif"));
        yellowCrayon.setActionCommand("yellow");
        yellowCrayon.addActionListener(cl);
        yellowCrayon.setBorder(border);
        boxOfCrayons.add(yellowCrayon);
        add(yellowCrayon);

        greenCrayon = new JToggleButton(new ImageIcon("images/green.gif"));
        greenCrayon.setActionCommand("green");
        greenCrayon.addActionListener(cl);
        greenCrayon.setBorder(border);
        boxOfCrayons.add(greenCrayon);
        add(greenCrayon);

        blueCrayon = new JToggleButton(new ImageIcon("images/blue.gif"));
        blueCrayon.setActionCommand("blue");
        blueCrayon.addActionListener(cl);
        blueCrayon.setBorder(border);
        boxOfCrayons.add(blueCrayon);
        add(blueCrayon);
    }

    class CrayonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Color newColor = null;
            JToggleButton source = (JToggleButton)e.getSource();
            if (source.getActionCommand().equals("green"))
                newColor = Color.green;
            else if (source.getActionCommand().equals("red"))
                newColor = Color.red;
            else if (source.getActionCommand().equals("yellow"))
                newColor = Color.yellow;
            else if (source.getActionCommand().equals("blue"))
                newColor = Color.blue;
            getColorSelectionModel().setSelectedColor(newColor);
        }
    }

    public String getDisplayName() {
        return "Crayons";
    }

    public Icon getSmallDisplayIcon() {
        return null;
    }

    public Icon getLargeDisplayIcon() {
        return null;
    }
}
