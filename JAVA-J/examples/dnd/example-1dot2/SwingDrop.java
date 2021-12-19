import java.awt.*;
import java.awt.event.*;
import java.awt.datatransfer.*;
import java.awt.dnd.*;

import java.io.*;

import javax.swing.*;

public class SwingDrop {

    static JTextArea textArea;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Drop on Me!");
        textArea = new JTextArea(20, 40);
        textArea.setLineWrap(true);
        JScrollPane scroller = new JScrollPane(textArea);
        frame.setContentPane(scroller);

        textArea.setDropTarget(new DropTarget(textArea, new TextAreaTarget(textArea)));
        frame.pack();
        frame.show();
    }
}


class TextAreaTarget implements DropTargetListener {

    JTextArea textArea;

    public TextAreaTarget(JTextArea area) {
       textArea = area;
    }
    public void dragEnter(DropTargetDragEvent e) {
        System.err.println("[Target] dragEnter");

        e.acceptDrag(DnDConstants.ACTION_COPY);
    }

    public void dragOver(DropTargetDragEvent e) {
        e.acceptDrag(DnDConstants.ACTION_COPY);
        System.err.println("[Target] dragOver");
    }

    public void dragExit(DropTargetEvent e) {
        System.err.println("[Target] dragExit");
    }

    public void drop(DropTargetDropEvent e) {
        System.err.println("[Target] drop");
        DropTargetContext targetContext = e.getDropTargetContext();

        boolean outcome = false;

        if ((e.getSourceActions() & DnDConstants.ACTION_COPY) != 0)
            e.acceptDrop(DnDConstants.ACTION_COPY);
        else {
            e.rejectDrop();
            return;
        }

        DataFlavor[] dataFlavors = e.getCurrentDataFlavors();
        DataFlavor   transferDataFlavor = null;

        System.err.println(DataFlavor.plainTextFlavor.getMimeType());
        for (int i = 0; i < dataFlavors.length; i++) {
            System.err.println(dataFlavors[i].getMimeType());
            if (DataFlavor.plainTextFlavor.equals(dataFlavors[i])) {
                System.err.println("matched");
                transferDataFlavor = dataFlavors[i];
                break;
            }
        }

        if (transferDataFlavor != null) {
            Transferable t  = e.getTransferable();
            InputStream  is = null;

            try {
                System.err.println("get stream");
                is = (InputStream)t.getTransferData(transferDataFlavor);
            } catch (IOException ioe) {
                ioe.printStackTrace();
                System.err.println(ioe.getMessage());
                targetContext.dropComplete(false);

                return;
            } catch (UnsupportedFlavorException ufe) {
                ufe.printStackTrace();
                System.err.println(ufe.getMessage());
                targetContext.dropComplete(false);

                return;
            }

            if (is != null) {
                try {

                    Reader converter = new InputStreamReader(is);
                    StringWriter sWriter = new StringWriter();
                    boolean more = true;
                    while (more) {
                        int c = converter.read();
                        if (c != -1) {
                            sWriter.write(c);  
                        } else {
                            more = false;
                        }
                    }
                    textArea.append(sWriter.toString().trim());

                    outcome = true;
                } catch (Exception ex) {
                    ex.printStackTrace();
                    System.err.println(ex.getMessage());
                    targetContext.dropComplete(false);
                    return;
                }
            } else
                outcome = false;
        }

        targetContext.dropComplete(outcome);
    }

    public void dragScroll(DropTargetDragEvent e) {
    }

    public void dropActionChanged(DropTargetDragEvent e) {
        System.err.println("[Target] dropActionChanged");
    }
}

