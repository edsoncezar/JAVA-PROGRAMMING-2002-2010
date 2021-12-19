import java.io.*;
import java.net.*;

public class Reverse {
    public static void main(String[] args) throws Exception {

	if (args.length != 1) {
	    System.err.println("Usage:  java Reverse string_to_reverse");
	    System.exit(1);
	}

	String stringToReverse = URLEncoder.encode(args[0]);

	URL url = new URL("http://java.sun.com/cgi-bin/backwards");
	URLConnection connection = url.openConnection();

	PrintStream out = new PrintStream(connection.getOutputStream());
	out.println("string=" + stringToReverse);
	out.close();

	DataInputStream in = new DataInputStream(connection.getInputStream());
	String inputLine;

	while ((inputLine = in.readLine()) != null)
	    System.out.println(inputLine);

	in.close();
    }
}
