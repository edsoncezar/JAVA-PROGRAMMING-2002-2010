// Note: This class won't compile by design!
// See InputFileDeclared.java for a version of this
// class that will compile.
import java.io.*;

public class InputFile {

    private FileInputStream in;

    public InputFile(String filename) {
        in = new FileInputStream(filename);
    }

    public String getWord() {
        int c;
        StringBuffer buf = new StringBuffer();

        do {
            c = in.read();
            if (Character.isSpace((char)c))
                return buf.toString();
            else
                buf.append((char)c);
        } while (c != -1);

	return buf.toString();
    }
}
