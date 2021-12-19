// Note: This class won't compile by design!
// See ListOfNumbersDeclared.java or ListOfNumbers.java
// for a version of this  class that will compile.
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
        PrintWriter out = new PrintWriter(new FileWriter("OutFile.txt"));
        
        for (int i = 0; i < size; i++)
            out.println("Value at: " + i + " = " + victor.elementAt(i));

        out.close();
    }
}
