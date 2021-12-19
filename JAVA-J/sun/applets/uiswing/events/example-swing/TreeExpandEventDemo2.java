import javax.swing.*;
import javax.swing.tree.*;
import javax.swing.event.*;
import java.awt.*;

public class TreeExpandEventDemo2 extends JApplet {
    DemoArea demoArea;
    JTextArea textArea;
    final static String newline = "\n";

    public void init() {
        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        JPanel contentPane = new JPanel();
        contentPane.setLayout(gridbag);

        c.fill = GridBagConstraints.BOTH;
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.weightx = 1.0;
        c.weighty = 1.0;

        c.insets = new Insets(1, 1, 1, 1);
        demoArea = new DemoArea();
        gridbag.setConstraints(demoArea, c);
        contentPane.add(demoArea);

        c.insets = new Insets(0, 0, 0, 0);
        textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(
            JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(200, 75));
        gridbag.setConstraints(scrollPane, c);
        contentPane.add(scrollPane);

        setContentPane(contentPane);
    }

    void saySomething(String eventDescription, TreeExpansionEvent e) {
        textArea.append(eventDescription + "; "
                        + "path = " + e.getPath()
                        + newline);
    }

    class DemoArea extends JScrollPane
                   implements TreeExpansionListener,
                              TreeWillExpandListener {
        Dimension minSize = new Dimension(100, 100);
        JTree tree;
        Object[] willExpandOptions = {"Cancel Expansion", "Expand"};
        String willExpandText = "A branch node is about to be expanded.\n"
                              + "Click \"Cancel Expansion\" to prevent it.";
        String willExpandTitle = "Tree Will Expand";
    
        public DemoArea() {
            TreeNode rootNode = createNodes();
            tree = new JTree(rootNode);
            tree.addTreeExpansionListener(this);
            tree.addTreeWillExpandListener(this);

            setViewportView(tree);
        }

        private TreeNode createNodes() {
            DefaultMutableTreeNode root;
            DefaultMutableTreeNode grandparent;
            DefaultMutableTreeNode parent;
            DefaultMutableTreeNode child;

            root = new DefaultMutableTreeNode("San Francisco");

            grandparent = new DefaultMutableTreeNode("Potrero Hill");
            root.add(grandparent);
            //
            parent = new DefaultMutableTreeNode("Restaurants");
            grandparent.add(parent);
            child = new DefaultMutableTreeNode("Thai Barbeque");
            parent.add(child);
            child = new DefaultMutableTreeNode("Goat Hill Pizza");
            parent.add(child);
            //
            parent = new DefaultMutableTreeNode("Grocery Stores");
            grandparent.add(parent);
            child = new DefaultMutableTreeNode("Good Life Grocery");
            parent.add(child);
            child = new DefaultMutableTreeNode("Safeway");
            parent.add(child);
            
            grandparent = new DefaultMutableTreeNode("Noe Valley");
            root.add(grandparent);
            //
            parent = new DefaultMutableTreeNode("Restaurants");
            grandparent.add(parent);
            child = new DefaultMutableTreeNode("Hamano Sushi");
            parent.add(child);
            child = new DefaultMutableTreeNode("Hahn's Hibachi");
            parent.add(child);
            //
            parent = new DefaultMutableTreeNode("Grocery Stores");
            grandparent.add(parent);
            child = new DefaultMutableTreeNode("Real Foods");
            parent.add(child);
            child = new DefaultMutableTreeNode("Bell Market");
            parent.add(child);
            
            return root;
        }
    
        public Dimension getMinimumSize() {
            return minSize;
        }

        public Dimension getPreferredSize() {
            return minSize;
        }

        // Required by TreeWillExpandListener interface.
        public void treeWillExpand(TreeExpansionEvent e) 
                    throws ExpandVetoException {
            saySomething("Tree-will-expand event detected", e);
            int n = JOptionPane.showOptionDialog(
                this, willExpandText, willExpandTitle,
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                willExpandOptions,
                willExpandOptions[1]);
            if (n == 0) {
                //User said cancel expansion.
                saySomething("Tree expansion cancelled", e);
                throw new ExpandVetoException(e);
            }
        }

        // Required by TreeWillExpandListener interface.
        public void treeWillCollapse(TreeExpansionEvent e) {
            saySomething("Tree-will-collapse event detected", e);
        }

        // Required by TreeExpansionListener interface.
        public void treeExpanded(TreeExpansionEvent e) {
            saySomething("Tree-expanded event detected", e);
        }

        // Required by TreeExpansionListener interface.
        public void treeCollapsed(TreeExpansionEvent e) {
            saySomething("Tree-collapsed event detected", e);
        }
    }
}
