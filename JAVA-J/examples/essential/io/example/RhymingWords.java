import java.io.*;

public class RhymingWords {
    public static void main(String[] args) throws IOException {

        DataInputStream words = new DataInputStream(
				    new FileInputStream("words.txt"));

        // do the reversing and sorting
        InputStream rhymedWords = reverse(sort(reverse(words)));

        // write new list to standard out
        DataInputStream in = new DataInputStream(rhymedWords);
        String input;

        while ((input = in.readLine()) != null)
            System.out.println(input);
        in.close();
    }

    public static InputStream reverse(InputStream source) throws IOException {

        DataInputStream in = new DataInputStream(source);

        PipedOutputStream pipeOut = new PipedOutputStream();
        PipedInputStream pipeIn = new PipedInputStream(pipeOut);
        PrintStream out = new PrintStream(pipeOut);

        new ReverseThread(out, in).start();

        return pipeIn;
    }

    public static InputStream sort(InputStream source) throws IOException {

        DataInputStream in = new DataInputStream(source);

        PipedOutputStream pipeOut = new PipedOutputStream();
        PipedInputStream pipeIn = new PipedInputStream(pipeOut);
        PrintStream out = new PrintStream(pipeOut);

        new SortThread(out, in).start();

        return pipeIn;
    }
}
