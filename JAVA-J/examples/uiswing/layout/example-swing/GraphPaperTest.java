import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GraphPaperTest extends JPanel {
    public GraphPaperTest() {
        setLayout(new GraphPaperLayout(new Dimension(5,5)));

        // Add a 1x1 Rect at (0,0)
        add(new JButton("1"), new Rectangle(0,0,1,1));

        // Add a 2x1 Rect at (2,0)
        add(new JButton("2"), new Rectangle(2,0,2,1));

        // Add a 1x2 Rect at (1,1)
        add(new JButton("3"), new Rectangle(1,1,1,2));

        // Add a 2x2 Rect at (3,2)
        add(new JButton("4"), new Rectangle(3,2,2,2));

        // Add a 1x1 Rect at (0,4)
        add(new JButton("5"), new Rectangle(0,4,1,1));

        // Add a 1x2 Rect at (2,3)
        add(new JButton("6"), new Rectangle(2,3,1,2));
    }

    public static void main(String[] args) {
	JFrame f = new JFrame("GraphPaperTest");
	f.addWindowListener(new WindowAdapter() {
	    public void windowClosing(WindowEvent e) {
		System.exit(0);
	    }
	});

	f.getContentPane().add(new GraphPaperTest(),
	                       BorderLayout.CENTER);
	f.pack();
	f.setVisible(true);
    }
}
