import java.io.*;
import java.util.Vector;

public class ListOfNumbers {
    private Vector victor;
    private static final int size = 10;

    public ListOfNumbers () {
        victor = new Vector(size);
        for (int i = 0; i < size; i++)
            victor.addElement(new Integer(i));
    }
    public void writeList() {
        PrintStream out = new PrintStream(
			      new FileOutputStream("OutFile.txt"));
        
        for (int i = 0; i < size; i++)
            out.println("Value at: " + i + " = " + victor.elementAt(i));

        out.close();
    }
}
