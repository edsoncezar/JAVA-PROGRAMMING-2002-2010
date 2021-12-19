import java.net.*;
import java.io.*;

public class URLConnectionReader {
    public static void main(String[] args) throws Exception {
	URL yahoo = new URL("http://www.yahoo.com/");
	URLConnection yahooConnection = yahoo.openConnection();
	DataInputStream in = new DataInputStream(
				 yahooConnection.getInputStream());
	String inputLine;

	while ((inputLine = in.readLine()) != null)
	    System.out.println(inputLine);

	in.close();
    }
}
