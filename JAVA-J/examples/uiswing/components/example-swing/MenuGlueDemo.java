import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/** 
 * @author ges
 * @author kwalrath 
 */
public class MenuGlueDemo extends JFrame {
    protected JMenuBar menuBar;

    public MenuGlueDemo() {
        super("MenuGlueDemo");
        menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        addNewMenu("Menu 1");
        addNewMenu("Menu 2");
        menuBar.add(Box.createHorizontalGlue());
        addNewMenu("Menu 3");

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    public void addNewMenu(String title) {
        JMenu m = (JMenu)menuBar.add(new JMenu(title));
        m.add("Menu item");
        m.add("Menu item");
        m.add("Menu item");
    }

    public static void main(String args[]) {
        MenuGlueDemo f = new MenuGlueDemo();
        f.setSize(300, 50);
        f.setVisible(true);
    }
}
