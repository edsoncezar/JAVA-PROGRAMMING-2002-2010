import java.net.*;
import java.io.*;

public class URLReader {
    public static void main(String[] args) throws Exception {
	URL yahoo = new URL("http://www.yahoo.com/");
	DataInputStream in = new DataInputStream(
				 yahoo.openStream());

	String inputLine;

	while ((inputLine = in.readLine()) != null)
	    System.out.println(inputLine);

	in.close();
    }
}
