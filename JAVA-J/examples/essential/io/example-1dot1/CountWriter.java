import java.io.*;

public class CountWriter extends FilterWriter {

    private int count = 0;
    private char lookFor = 0;

    public CountWriter(Writer in, char lookFor) {
        super(in);
        this.lookFor = lookFor;
    }
    public void write(int character) throws IOException {
        super.write(character);
        if ((char)character == lookFor) {
            count++;
        }
    }
    public void write(char[] cbuf) throws IOException {
        super.write(cbuf);
        int length = cbuf.length;
        for (int i = 0; i < length; i++) {
            if (cbuf[i] == lookFor) {
                count++;
            }
        }
    }
    public void write(char[] cbuf, int off, int len) throws IOException {
        super.write(cbuf, off, len);
        int length = cbuf.length;
        for (int i = 0; i < length; i++) {
            if (cbuf[i] == lookFor) {
                count++;
            }
        }
    }
    public void write(String str) throws IOException {
        super.write(str);
        int length = str.length();
        for (int i = 0; i < length; i++) {
            if (str.charAt(i) == lookFor) {
                count++;
            }
        }
    }
    public void write(String str, int off, int len) throws IOException {
        super.write(str, off, len);
        int length = str.length();
        for (int i = 0; i < length; i++) {
            if (str.charAt(i) == lookFor) {
                count++;
            }
        }
    }
    public int getCount() {
        return count;
    }
}
