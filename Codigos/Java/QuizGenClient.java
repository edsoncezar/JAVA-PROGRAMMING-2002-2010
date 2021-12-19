import java.io.*;
import java.net.*;

/**
 * Use David Carter-Tod's Quiz generator XMLRPC service to make a BlackBoard.com 
 * question pool.
 *
 * The first command line paramerter is a text file in the style of 
 * http://blackboard.unomaha.edu/quizgen/default.html
 *
 * The program will call the XMLRPC service, download the resulting zip file 
 * to the current directory and print the name of the zip file to the user.
 *
 * This is a quick and dirty program.  It uses no third party libraries.  Just
 * compile and go.  Tested with JDK 1.3.1.
 *
 * BB5 is currently a hard coded question pool type.
 *
 * @date January 29, 2003
 * @author Matt Payne, Payne@MattPayne.org
 * @copyright Matt Payne Licensed under GPL and the same license as David Carter-Tod's Quiz generator
 *
 */
public class QuizGenClient {
	private static final String QUIZ_BASE_URL="http://www.wcc.vccs.edu/services/blackboard";
	private static final String QUIZ_GENERATOR_URL=QUIZ_BASE_URL + "/xmlrpcGenerateQuiz.asp";
	private boolean bVerbose=false;

	public static void main(String arg[]) {
		try { 
			if (arg.length < 1) {
				System.out.println("Usage: QuizGenClient <quiz-text-file>");
				System.exit(1);
			}
			new QuizGenClient().run(arg);
		} catch (Exception bland) {
			bland.printStackTrace();
		}
	}

	void run(String arg[]) throws Exception {
		String strQuiz=readFile(arg[0]);
		// Currently hard coded to BB5
		String strXmlRpcRequest = 
			"<?xml version=\"1.0\"?>\n" +
			"<methodCall>\n" +
			"      <methodName>generateQuiz</methodName>\n" +
			"      <params>\n" +
			"        <param><value><string>"+strQuiz+"</string></value></param>\n" +
			"        <param><value><string>BB5</string></value></param>\n" +
			"      </params>\n" +
			"</methodCall>\n";

		if (bVerbose) System.out.println("\n************\n"+strXmlRpcRequest+"\n************\n");

		Stopwatch timer = new Stopwatch().start();

		URL url = new URL(QUIZ_GENERATOR_URL);
		URLConnection connection = url.openConnection();
		connection.setDoOutput(true);

		PrintWriter out = new PrintWriter(
       	                       connection.getOutputStream());
		out.println(strXmlRpcRequest);
		out.close();
	
		BufferedReader in = new BufferedReader(
					new InputStreamReader(
					connection.getInputStream()));
		String inputLine;

		String wholeResponse="";

		while ((inputLine = in.readLine()) != null) {
			wholeResponse += inputLine;
		}
		downloadZip(wholeResponse);
	    	if (bVerbose) {
			System.out.println("Response is :\n'"+wholeResponse+"'");
		}
		in.close();
	        timer.stop();
	        System.out.println("\n\nelapsed time is : " + timer.getElapsedTime());
	}//run()

	/** Download the zip file that's mentioned in this XMLRPC response.
	 */
	void downloadZip(String wholeResponse) throws Exception {
		// Get the URL
		// Use the fact that there's only one string in the response
		String strStartMarker="<string>";
		int a=wholeResponse.indexOf(strStartMarker)+strStartMarker.length();
		int b=wholeResponse.indexOf("</string>");
		String strRelativeZipUrl = wholeResponse.substring(a,b);
		URL zipURL = new URL(QUIZ_BASE_URL + "/" + strRelativeZipUrl); 
	    	if (bVerbose) {
			System.out.println("zipURL="+zipURL);
		}
		InputStream in = zipURL.openConnection().getInputStream();
		byte gulp[] = new byte[8*1024];
		int bytesRead;
		String zipFileName=strRelativeZipUrl.substring(strRelativeZipUrl.lastIndexOf('/')+1);
		FileOutputStream out = new FileOutputStream(zipFileName);
		while (-1 != (bytesRead = in.read(gulp))) {
			out.write(gulp,0,bytesRead);
		}
		out.close();
		in.close();
		System.out.println(zipFileName + " has been copied to the current directory. ");
	}//downloadZip()

	String readFile(String fn) throws Exception {
		String strResult="";
		LineNumberReader f = new LineNumberReader(
					new InputStreamReader(
					new FileInputStream(fn)));
		String line;
		int    ln;
		while (null != (line = f.readLine())) {
			strResult += line + "\r\n";
		}
		f.close();
		return strResult;
	}//readFile()

}

/**
   * A class to help benchmark code
   * It simulates a real stop watch.
   * @author From http://java.sun.com/docs/books/performance/1st_edition/html/JPMeasurement.fm.html#18181
   */
class Stopwatch {

    private long startTime = -1;
    private long stopTime = -1;
    private boolean running = false;

    public Stopwatch start() {
       startTime = System.currentTimeMillis();
       running = true;
       return this;
    }
    public Stopwatch stop() {
       stopTime = System.currentTimeMillis();
       running = false;
       return this;
    }
    /** returns elapsed time in milliseconds
      * if the watch has never been started then
      * return zero
      */
    public long getElapsedTime() {
       if (startTime == -1) {
          return 0;
       }
       if (running){
       return System.currentTimeMillis() - startTime;
       } else {
       return stopTime-startTime;
       } 
    }

    public Stopwatch reset() {
       startTime = -1;
       stopTime = -1;
       running = false;
       return this;
    }
}

