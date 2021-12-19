import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.filechooser.*;

public class FileChooserDemo2 extends JFrame {
    static private String newline = "\n";

    public FileChooserDemo2() {
        super("FileChooserDemo2");

        //Create the log first, because the action listener
        //needs to refer to it.
        final JTextArea log = new JTextArea(5,20);
        log.setMargin(new Insets(5,5,5,5));
        log.setEditable(false);
        JScrollPane logScrollPane = new JScrollPane(log);

        JButton sendButton = new JButton("Attach...");
        sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                fc.addChoosableFileFilter(new ImageFilter());
                fc.setFileView(new ImageFileView());
                fc.setAccessory(new ImagePreview(fc));

                int returnVal = fc.showDialog(FileChooserDemo2.this,
                                              "Attach");

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    log.append("Attaching file: " + file.getName()
                               + "." + newline);
                } else {
                    log.append("Attachment cancelled by user." + newline);
                }
            }
        });

        Container contentPane = getContentPane();
        contentPane.add(sendButton, BorderLayout.NORTH);
        contentPane.add(logScrollPane, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        JFrame frame = new FileChooserDemo2();
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        frame.pack();
        frame.setVisible(true);
    }
}
